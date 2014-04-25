package com.example.transactionsigner;

/**
 * Created by evarobert on 4/9/14.
 */

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Handler;
import android.os.IBinder;
import android.os.PowerManager;
import android.support.v4.app.NotificationCompat;
import android.text.format.DateUtils;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

import com.google.bitcoin.core.*;
import com.google.bitcoin.kits.WalletAppKit;
import com.google.bitcoin.net.discovery.DnsDiscovery;
import com.google.bitcoin.net.discovery.PeerDiscovery;
import com.google.bitcoin.net.discovery.PeerDiscoveryException;
import com.google.bitcoin.store.BlockStore;
import com.google.bitcoin.store.BlockStoreException;
import com.google.bitcoin.store.MemoryBlockStore;
import com.google.bitcoin.store.SPVBlockStore;
import com.google.bitcoin.utils.Threading;
import com.google.common.util.concurrent.ListenableFuture;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class SendCoinsImpl extends android.app.Service implements SendCoins{
    String network = "test";
    String recipient = "mvBa1p4an5iJohPE2LPHDyvjrhHHdXs1GZ"; // TODO MAKE THIS CONFIGURABLE

    final NetworkParameters netParams;
    BlockStore blockStore;
    File blockChainFile;
    BlockChain blockChain;
    //Wallet wallet;
    WalletApp walletApp;
    PeerGroup peerGroup;
    Configuration config;

    private NotificationManager nm;
    private static final int NOTIFICATION_ID_CONNECTED = 0;
    private final Handler handler = new Handler();
    private final Handler delayHandler = new Handler();
    private int bestChainHeightEver;
    private PeerConnectivityListener peerConnectivityListener;
    private PowerManager.WakeLock wakeLock;
    private static final long APPWIDGET_THROTTLE_MS = DateUtils.SECOND_IN_MILLIS;
    private AtomicInteger transactionsReceived = new AtomicInteger();
    private static final int NOTIFICATION_ID_COINS_RECEIVED = 1;
    private int notificationCount = 0;
    private BigInteger notificationAccumulatedAmount = BigInteger.ZERO;
    private final List<Address> notificationAddresses = new LinkedList<Address>();
    private static final int MIN_COLLECT_HISTORY = 2;
    private static final int IDLE_BLOCK_TIMEOUT_MIN = 2;
    private static final int IDLE_TRANSACTION_TIMEOUT_MIN = 9;
    private static final int MAX_HISTORY_SIZE = Math.max(IDLE_TRANSACTION_TIMEOUT_MIN, IDLE_BLOCK_TIMEOUT_MIN);
    private boolean resetBlockchainOnShutdown = false;
    private long serviceCreatedAt;

    Context context;

    private final BroadcastReceiver connectivityReceiver = new BroadcastReceiver()
    {
        private boolean hasConnectivity;
        private boolean hasStorage = true;

        @Override
        public void onReceive(final Context context, final Intent intent)
        {
            final String action = intent.getAction();

            if (ConnectivityManager.CONNECTIVITY_ACTION.equals(action))
            {
                hasConnectivity = !intent.getBooleanExtra(ConnectivityManager.EXTRA_NO_CONNECTIVITY, false);
                System.out.println("network is " + (hasConnectivity ? "up" : "down"));

                check();
            }
            else if (Intent.ACTION_DEVICE_STORAGE_LOW.equals(action))
            {
                hasStorage = false;
                System.out.println("device storage low");

                check();
            }
            else if (Intent.ACTION_DEVICE_STORAGE_OK.equals(action))
            {
                hasStorage = true;
                System.out.println("device storage ok");

                check();
            }
        }

        //@SuppressLint("Wakelock")
        private void check()
        {
            //final Wallet wallet = walletApp.getWallet();
            final boolean hasEverything = hasConnectivity && hasStorage;

            if (hasEverything && peerGroup == null)
            {
                System.out.println("acquiring wakelock");
                wakeLock.acquire();

                // consistency check
                final int walletLastBlockSeenHeight = walletApp.getWallet().getLastBlockSeenHeight();
                final int bestChainHeight = blockChain.getBestChainHeight();
                if (walletLastBlockSeenHeight != -1 && walletLastBlockSeenHeight != bestChainHeight)
                {
                    final String message = "wallet/blockchain out of sync: " + walletLastBlockSeenHeight + "/" + bestChainHeight;
                    System.out.println(message);
                    //CrashReporter.saveBackgroundTrace(new RuntimeException(message), application.packageInfo());
                }

                System.out.println("starting peergroup");
                peerGroup = new PeerGroup(Constants.NETWORK_PARAMETERS, blockChain);
                peerGroup.addWallet(walletApp.getWallet());
                peerGroup.setUserAgent(Constants.USER_AGENT, walletApp.packageInfo().versionName);
                peerGroup.addEventListener(peerConnectivityListener);

                final int maxConnectedPeers = walletApp.maxConnectedPeers();

                final String trustedPeerHost = config.getTrustedPeerHost();
                final boolean hasTrustedPeer = !trustedPeerHost.isEmpty();

                final boolean connectTrustedPeerOnly = hasTrustedPeer && config.getTrustedPeerOnly();
                peerGroup.setMaxConnections(connectTrustedPeerOnly ? 1 : maxConnectedPeers);

                peerGroup.addPeerDiscovery(new PeerDiscovery()
                {
                    private final PeerDiscovery normalPeerDiscovery = new DnsDiscovery(Constants.NETWORK_PARAMETERS);

                    @Override
                    public InetSocketAddress[] getPeers(final long timeoutValue, final TimeUnit timeoutUnit) throws PeerDiscoveryException
                    {
                        final List<InetSocketAddress> peers = new LinkedList<InetSocketAddress>();

                        boolean needsTrimPeersWorkaround = false;

                        if (hasTrustedPeer)
                        {
                            System.out.println("trusted peer '" + trustedPeerHost + "'" + (connectTrustedPeerOnly ? " only" : ""));

                            final InetSocketAddress addr = new InetSocketAddress(trustedPeerHost, Constants.NETWORK_PARAMETERS.getPort());
                            if (addr.getAddress() != null)
                            {
                                peers.add(addr);
                                needsTrimPeersWorkaround = true;
                            }
                        }

                        if (!connectTrustedPeerOnly)
                            peers.addAll(Arrays.asList(normalPeerDiscovery.getPeers(timeoutValue, timeoutUnit)));

                        // workaround because PeerGroup will shuffle peers
                        if (needsTrimPeersWorkaround)
                            while (peers.size() >= maxConnectedPeers)
                                peers.remove(peers.size() - 1);

                        return peers.toArray(new InetSocketAddress[0]);
                    }

                    @Override
                    public void shutdown()
                    {
                        normalPeerDiscovery.shutdown();
                    }
                });

                // start peergroup
                peerGroup.start();
                peerGroup.startBlockChainDownload(blockchainDownloadListener);
            }
            else if (!hasEverything && peerGroup != null)
            {
                System.out.println("stopping peergroup");
                peerGroup.removeEventListener(peerConnectivityListener);
                peerGroup.removeWallet(walletApp.getWallet());
                peerGroup.stop();
                peerGroup = null;

                System.out.println("releasing wakelock");
                wakeLock.release();
            }

            final int download = (hasConnectivity ? 0 : ACTION_BLOCKCHAIN_STATE_DOWNLOAD_NETWORK_PROBLEM)
                    | (hasStorage ? 0 : ACTION_BLOCKCHAIN_STATE_DOWNLOAD_STORAGE_PROBLEM);

            sendBroadcastBlockchainState(download);
        }
    };

    private final PeerEventListener blockchainDownloadListener = new AbstractPeerEventListener()
    {
        private final AtomicLong lastMessageTime = new AtomicLong(0);

        @Override
        public void onBlocksDownloaded(final Peer peer, final Block block, final int blocksLeft)
        {
            bestChainHeightEver = Math.max(bestChainHeightEver, blockChain.getChainHead().getHeight());
            System.out.println("Current Chain Height: " + bestChainHeightEver + " at " + blockChain.getChainHead().getHeader().getTime());
            delayHandler.removeCallbacksAndMessages(null);

            final long now = System.currentTimeMillis();

            if (now - lastMessageTime.get() > Constants.BLOCKCHAIN_STATE_BROADCAST_THROTTLE_MS)
                delayHandler.post(runnable);
            else
                delayHandler.postDelayed(runnable, Constants.BLOCKCHAIN_STATE_BROADCAST_THROTTLE_MS);
        }

        private final Runnable runnable = new Runnable()
        {
            @Override
            public void run()
            {
                lastMessageTime.set(System.currentTimeMillis());
                //System.out.println(lastMessageTime);
                sendBroadcastBlockchainState(ACTION_BLOCKCHAIN_STATE_DOWNLOAD_OK);
            }
        };
    };

    private final WalletEventListener walletEventListener = new ThrottlingWalletChangeListener(APPWIDGET_THROTTLE_MS)
    {
        @Override
        public void onThrottledWalletChanged()
        {
            // TODO Make sure there isn't something here that is needed
            //notifyWidgets();
        }

        @Override
        public void onCoinsReceived(final Wallet wallet, final Transaction tx, final BigInteger prevBalance, final BigInteger newBalance)
        {
            transactionsReceived.incrementAndGet();

            final int bestChainHeight = blockChain.getBestChainHeight();

            try
            {
                final Address from = WalletUtils.getFirstFromAddress(tx);
                final BigInteger amount = tx.getValue(wallet);
                final TransactionConfidence.ConfidenceType confidenceType = tx.getConfidence().getConfidenceType();

                handler.post(new Runnable()
                {
                    @Override
                    public void run()
                    {
                        final boolean isReceived = amount.signum() > 0;
                        final boolean replaying = bestChainHeight < bestChainHeightEver;
                        final boolean isReplayedTx = confidenceType == TransactionConfidence.ConfidenceType.BUILDING && replaying;

                        if (isReceived && !isReplayedTx)
                            notifyCoinsReceived(from, amount);
                    }
                });
            }
            catch (final ScriptException x)
            {
                throw new RuntimeException(x);
            }
        }

        @Override
        public void onCoinsSent(final Wallet wallet, final Transaction tx, final BigInteger prevBalance, final BigInteger newBalance)
        {
            transactionsReceived.incrementAndGet();
        }
    };

    private void notifyCoinsReceived(@Nullable final Address from, @Nonnull final BigInteger amount)
    {
        if (notificationCount == 1)
            nm.cancel(NOTIFICATION_ID_COINS_RECEIVED);

        notificationCount++;
        notificationAccumulatedAmount = notificationAccumulatedAmount.add(amount);
        if (from != null && !notificationAddresses.contains(from))
            notificationAddresses.add(from);

        final int btcPrecision = config.getBtcPrecision();
        final int btcShift = config.getBtcShift();
        final String btcPrefix = config.getBtcPrefix();

        final String packageFlavor = walletApp.applicationPackageFlavor();
        final String msgSuffix = packageFlavor != null ? " [" + packageFlavor + "]" : "";

        final String tickerMsg = context.getString(R.string.notification_coins_received_msg,
                btcPrefix + ' ' + GenericUtils.formatValue(amount, btcPrecision, btcShift))
                + msgSuffix;

        final String msg = context.getString(R.string.notification_coins_received_msg,
                btcPrefix + ' ' + GenericUtils.formatValue(notificationAccumulatedAmount, btcPrecision, btcShift))
                + msgSuffix;

        final StringBuilder text = new StringBuilder();
        for (final Address address : notificationAddresses)
        {
            if (text.length() > 0)
                text.append(", ");

            final String addressStr = address.toString();
            final String label = AddressBookProvider.resolveLabel(getApplicationContext(), addressStr);
            text.append(label != null ? label : addressStr);
        }

        final NotificationCompat.Builder notification = new NotificationCompat.Builder(this);
        //notification.setSmallIcon(R.drawable.stat_notify_received);
        notification.setTicker(tickerMsg);
        notification.setContentTitle(msg);
        if (text.length() > 0)
            notification.setContentText(text);
        notification.setContentIntent(PendingIntent.getActivity(this, 0, new Intent(this, MainActivity.class), 0));
        notification.setNumber(notificationCount == 1 ? 0 : notificationCount);
        notification.setWhen(System.currentTimeMillis());
        //notification.setSound(Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.coins_received));
        nm.notify(NOTIFICATION_ID_COINS_RECEIVED, notification.getNotification());
    }

    private final static class ActivityHistoryEntry
    {
        public final int numTransactionsReceived;
        public final int numBlocksDownloaded;

        public ActivityHistoryEntry(final int numTransactionsReceived, final int numBlocksDownloaded)
        {
            this.numTransactionsReceived = numTransactionsReceived;
            this.numBlocksDownloaded = numBlocksDownloaded;
        }

        @Override
        public String toString()
        {
            return numTransactionsReceived + "/" + numBlocksDownloaded;
        }
    }


    private final BroadcastReceiver tickReceiver = new BroadcastReceiver()
    {
        private int lastChainHeight = 0;
        private final List<ActivityHistoryEntry> activityHistory = new LinkedList<ActivityHistoryEntry>();

        @Override
        public void onReceive(final Context context, final Intent intent)
        {
            final int chainHeight = blockChain.getBestChainHeight();

            if (lastChainHeight > 0)
            {
                final int numBlocksDownloaded = chainHeight - lastChainHeight;
                final int numTransactionsReceived = transactionsReceived.getAndSet(0);

                // push history
                activityHistory.add(0, new ActivityHistoryEntry(numTransactionsReceived, numBlocksDownloaded));

                // trim
                while (activityHistory.size() > MAX_HISTORY_SIZE)
                    activityHistory.remove(activityHistory.size() - 1);

                // print
                final StringBuilder builder = new StringBuilder();
                for (final ActivityHistoryEntry entry : activityHistory)
                {
                    if (builder.length() > 0)
                        builder.append(", ");
                    builder.append(entry);
                }
                System.out.println("History of transactions/blocks: " + builder);

                // determine if block and transaction activity is idling
                boolean isIdle = false;
                if (activityHistory.size() >= MIN_COLLECT_HISTORY)
                {
                    isIdle = true;
                    for (int i = 0; i < activityHistory.size(); i++)
                    {
                        final ActivityHistoryEntry entry = activityHistory.get(i);
                        final boolean blocksActive = entry.numBlocksDownloaded > 0 && i <= IDLE_BLOCK_TIMEOUT_MIN;
                        final boolean transactionsActive = entry.numTransactionsReceived > 0 && i <= IDLE_TRANSACTION_TIMEOUT_MIN;

                        if (blocksActive || transactionsActive)
                        {
                            isIdle = false;
                            break;
                        }
                    }
                }

                // if idling, shutdown service
                if (isIdle)
                {
                    System.out.println("idling detected, stopping service");
                    stopSelf();
                }
            }

            lastChainHeight = chainHeight;
        }
    };


    SendCoinsImpl(Wallet wallet, Context context, WalletApp walletApp) {
        serviceCreatedAt = System.currentTimeMillis();
        this.context = context;
        this.walletApp = walletApp;
        nm = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        if (network.equalsIgnoreCase("prod")) {
            netParams = NetworkParameters.prodNet();
        } else {
            netParams = NetworkParameters.testNet();
        }
        final String lockName = context.getPackageName() + " blockchain sync";
        final PowerManager pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
        wakeLock = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, lockName);

        config = walletApp.getConfiguration();

        bestChainHeightEver = config.getBestChainHeightEver();
        peerConnectivityListener = new PeerConnectivityListener();
        sendBroadcastPeerState(0);


        final IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        intentFilter.addAction(Intent.ACTION_DEVICE_STORAGE_LOW);
        intentFilter.addAction(Intent.ACTION_DEVICE_STORAGE_OK);
        context.registerReceiver(connectivityReceiver, intentFilter);

        blockChainFile = new File(context.getDir("blockstore", Context.MODE_PRIVATE), Constants.BLOCKCHAIN_FILENAME);
        final boolean blockChainFileExists = blockChainFile.exists();
        if (!blockChainFileExists) {
            System.out.println("blockchain does not exist, resetting wallet");
            wallet.clearTransactions(0);
            wallet.setLastBlockSeenHeight(-1); // magic value
            wallet.setLastBlockSeenHash(null);
        }

        try {
            blockStore = new SPVBlockStore(Constants.NETWORK_PARAMETERS, blockChainFile);
            blockStore.getChainHead(); // detect corruptions as early as possible
            final long earliestKeyCreationTime = wallet.getEarliestKeyCreationTime();

            if (!blockChainFileExists && earliestKeyCreationTime > 0) {
                try {
                    final InputStream checkpointsInputStream = context.getAssets().open(Constants.CHECKPOINTS_FILENAME);
                    CheckpointManager.checkpoint(Constants.NETWORK_PARAMETERS, checkpointsInputStream, blockStore, earliestKeyCreationTime);
                } catch (final IOException x) {
                    System.out.println("problem reading checkpoints, continuing without" + x);
                }
            }
        } catch (final BlockStoreException x) {
            blockChainFile.delete();
            final String msg = "blockstore cannot be created";
            System.out.println(msg + x);
            //throw new Error(msg, x);
        }

        System.out.println("using " + blockStore.getClass().getName());
        try {
            blockChain = new BlockChain(Constants.NETWORK_PARAMETERS, wallet, blockStore);
        } catch (final BlockStoreException x) {
            throw new Error("blockchain cannot be created", x);
        }

        wallet.addEventListener(walletEventListener, Threading.SAME_THREAD);

        if (tickReceiver == null) {
            System.out.println("TICK  RECEIVER IS NULL :(");
        } else {
            context.registerReceiver(tickReceiver, new IntentFilter(Intent.ACTION_TIME_TICK));
        }

    }

    void sendCoins(Transaction t) throws InsufficientMoneyException {
        //chain = new BlockChain(netParams, wallet, blockStore);
        PeerGroup pg = new PeerGroup(netParams, blockChain);
        System.out.println("Created peer group");
        final ListenableFuture<Transaction> transactionListenableFuture = pg.broadcastTransaction(t);
        System.out.println("Created transactionListenableFuture");
        Thread thead = new Thread(new Runnable() {
            public void run() {
                try {
                    transactionListenableFuture.get();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
            }
        });

        System.out.println("Calling get on the transaction listenenable future");
    }

    void sendCoins(String amountToSend) throws InsufficientMoneyException {
        final BigInteger btcToSend = new BigInteger(amountToSend);

        try {
            //chain = new BlockChain(netParams, wallet, blockStore);
            PeerGroup pg = new PeerGroup(netParams, blockChain);
            pg.addWallet(walletApp.getWallet());
            pg.stopAndWait();
            Address recipientAddress = new Address(netParams, recipient);
            System.out.println("Balance is: " + walletApp.getWallet().getBalance());
            Wallet.SendResult sendTxn = walletApp.getWallet().sendCoins(pg, recipientAddress, btcToSend);

            if (sendTxn == null) {
                // Failure
                System.out.println("FAILUREEEEE TO SEND");
            } else {
                // success
                System.out.println("Success!!!!!!!!! TO SEND");
            }
        } catch (AddressFormatException e) {
            System.out.println(e);
        } catch (InsufficientMoneyException e) {
            System.out.println(e);
        }
    }

    BigInteger getBalance() {
        //System.out.println("Current wallet balance: " + wallet.getBalance());
        return walletApp.getWallet().getBalance(Wallet.BalanceType.ESTIMATED);
    }

    @Override
    public List<Peer> getConnectedPeers() {
        return null;
    }

    @Override
    public List<StoredBlock> getRecentBlocks(int maxBlocks) {
        return null;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private final class PeerConnectivityListener extends AbstractPeerEventListener implements SharedPreferences.OnSharedPreferenceChangeListener
    {
        private int peerCount;
        private AtomicBoolean stopped = new AtomicBoolean(false);

        public PeerConnectivityListener()
        {
            config.registerOnSharedPreferenceChangeListener(this);
        }

        public void stop()
        {
            stopped.set(true);

            config.unregisterOnSharedPreferenceChangeListener(this);

            nm.cancel(NOTIFICATION_ID_CONNECTED);
        }

        @Override
        public void onPeerConnected(final Peer peer, final int peerCount)
        {
            this.peerCount = peerCount;
            changed(peerCount);
        }

        @Override
        public void onPeerDisconnected(final Peer peer, final int peerCount)
        {
            this.peerCount = peerCount;
            changed(peerCount);
        }

        @Override
        public void onSharedPreferenceChanged(final SharedPreferences sharedPreferences, final String key)
        {
            if (Configuration.PREFS_KEY_CONNECTIVITY_NOTIFICATION.equals(key))
                changed(peerCount);
        }

        private void changed(final int numPeers)
        {
            if (stopped.get())
                return;

            handler.post(new Runnable()
            {
                @Override
                public void run()
                {
                    final boolean connectivityNotificationEnabled = config.getConnectivityNotificationEnabled();

                    if (!connectivityNotificationEnabled || numPeers == 0)
                    {
                        nm.cancel(NOTIFICATION_ID_CONNECTED);
                    }
                    else
                    {
                        final NotificationCompat.Builder notification = new NotificationCompat.Builder(SendCoinsImpl.this);
                        //notification.setSmallIcon(R.drawable.stat_sys_peers, numPeers > 4 ? 4 : numPeers);
                        notification.setContentTitle(getString(R.string.app_name));
                        notification.setContentText(getString(R.string.notification_peers_connected_msg, numPeers));
                        //notification.setContentIntent(PendingIntent.getActivity(BlockchainServiceImpl.this, 0, new Intent(BlockchainServiceImpl.this,
                        //        WalletActivity.class), 0));
                        notification.setWhen(System.currentTimeMillis());
                        notification.setOngoing(true);
                        nm.notify(NOTIFICATION_ID_CONNECTED, notification.getNotification());
                    }

                    // send broadcast
                    sendBroadcastPeerState(numPeers);
                }
            });
        }
    }

    private void sendBroadcastPeerState(final int numPeers)
    {
        final Intent broadcast = new Intent(ACTION_PEER_STATE);
        broadcast.setPackage(context.getPackageName());
        broadcast.putExtra(ACTION_PEER_STATE_NUM_PEERS, numPeers);
        context.sendStickyBroadcast(broadcast);
    }

    private void sendBroadcastBlockchainState(final int download)
    {
        final StoredBlock chainHead = blockChain.getChainHead();

        final Intent broadcast = new Intent(ACTION_BLOCKCHAIN_STATE);
        broadcast.setPackage(context.getPackageName());
        broadcast.putExtra(ACTION_BLOCKCHAIN_STATE_BEST_CHAIN_DATE, chainHead.getHeader().getTime());
        broadcast.putExtra(ACTION_BLOCKCHAIN_STATE_BEST_CHAIN_HEIGHT, chainHead.getHeight());
        broadcast.putExtra(ACTION_BLOCKCHAIN_STATE_REPLAYING, chainHead.getHeight() < bestChainHeightEver);
        broadcast.putExtra(ACTION_BLOCKCHAIN_STATE_DOWNLOAD, download);

        context.sendStickyBroadcast(broadcast);
    }
    @Override
    public void onDestroy()
    {
        System.out.println(".onDestroy()");

        WalletApp.scheduleStartBlockchainService(this);

        unregisterReceiver(tickReceiver);

        walletApp.getWallet().removeEventListener(walletEventListener);

        if (peerGroup != null)
        {
            peerGroup.removeEventListener(peerConnectivityListener);
            peerGroup.removeWallet(walletApp.getWallet());
            peerGroup.stopAndWait();

            System.out.println("peergroup stopped");
        }

        peerConnectivityListener.stop();

        unregisterReceiver(connectivityReceiver);

        removeBroadcastPeerState();
        removeBroadcastBlockchainState();

        config.setBestChainHeightEver(bestChainHeightEver);

        delayHandler.removeCallbacksAndMessages(null);

        try
        {
            blockStore.close();
        }
        catch (final BlockStoreException x)
        {
            throw new RuntimeException(x);
        }

        walletApp.saveWallet();

        if (wakeLock.isHeld())
        {
            System.out.println("wakelock still held, releasing");
            wakeLock.release();
        }

        if (resetBlockchainOnShutdown)
        {
            System.out.println("removing blockchain");
            blockChainFile.delete();
        }

        super.onDestroy();

        System.out.println("service was up for " + ((System.currentTimeMillis() - serviceCreatedAt) / 1000 / 60) + " minutes");
    }

    private void removeBroadcastPeerState()
    {
        removeStickyBroadcast(new Intent(ACTION_PEER_STATE));
    }

    private void removeBroadcastBlockchainState()
    {
        removeStickyBroadcast(new Intent(ACTION_BLOCKCHAIN_STATE));
    }

    public long getStatus() {
        long num = blockChain.getChainHead().getHeader().getTime().getTime();
        return num / 1000;
        //return blockChain.getChainHead().getHeader().getTime().getSeconds();
    }

    public Wallet getWallet() {
        return walletApp.getWallet();
    }

    public void addKeyToWallet(ECKey k) {
        System.out.println("THE REAL PRIV KEY????" + walletApp.getWallet().getKeys().get(4).getPrivateKeyEncoded(netParams));
        walletApp.getWallet().addKey(k);
    }
}
