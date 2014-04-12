package com.example.transactionsigner;

/**
 * Created by evarobert on 4/9/14.
 */

import java.io.File;
import java.io.IOException;
import java.math.BigInteger;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.concurrent.Executor;

import com.google.bitcoin.core.*;
import com.google.bitcoin.kits.WalletAppKit;
import com.google.bitcoin.store.BlockStore;
import com.google.bitcoin.store.BlockStoreException;
import com.google.bitcoin.store.MemoryBlockStore;

public class SendCoins {
    String network = "test";
    String recipient = "mvBa1p4an5iJohPE2LPHDyvjrhHHdXs1GZ"; // TODO MAKE THIS CONFIGURABLE

    final NetworkParameters netParams;
    BlockStore blockStore;
    BlockChain chain;
    Wallet wallet;

    SendCoins(Wallet wallet) {
        if (network.equalsIgnoreCase("prod")) {
            netParams = NetworkParameters.prodNet();
        } else {
            netParams = NetworkParameters.testNet();
        }

        blockStore = new MemoryBlockStore(netParams);
        this.wallet = wallet;
        try {
            chain = new BlockChain(netParams, wallet, blockStore);
            PeerGroup pg = new PeerGroup(netParams, chain);
            pg.addWallet(wallet);
            pg.stopAndWait();
        } catch (BlockStoreException e) {
            e.printStackTrace();
        }
    }

    void sendCoins(String amountToSend) throws InsufficientMoneyException {
        final BigInteger btcToSend = new BigInteger(amountToSend);
        final WalletAppKit wak = new WalletAppKit(netParams, new File(Constants.WALLET_FILENAME_PROTOBUF), "") {
            //@Override
            protected void OnSetupCompleted() {
                Address recipientAddress = null;
                try {
                    recipientAddress = new Address(netParams, recipient);
                    final Wallet.SendResult sendResult = this.wallet().sendCoins(this.peerGroup(), recipientAddress, btcToSend);
                    System.out.println("Sending ...");
// Register a callback that is invoked when the transaction has propagated across the network.
// This shows a second style of registering ListenableFuture callbacks, it works when you don't
// need access to the object the future returns.
                    sendResult.broadcastComplete.addListener(new Runnable() {
                                                                 @Override
                                                                 public void run() {
                                                                     // The wallet has changed now, it'll get auto saved shortly or when the app shuts down.
                                                                     System.out.println("Sent coins onwards! Transaction hash is " + sendResult.tx.getHashAsString());
                                                                 }
                                                             }, new Executor() {

                                                                 @Override
                                                                 public void execute(Runnable runnable) {
                                                                     // do nothing?
                                                                 }
                                                             });
                } catch (AddressFormatException e) {
                    e.printStackTrace();
                } catch (InsufficientMoneyException e) {
                    e.printStackTrace();
                }
            }
        };
        wak.stopAndWait();

        /*try {
            chain = new BlockChain(netParams, wallet, blockStore);
            PeerGroup pg = new PeerGroup(netParams, chain);
            pg.addWallet(wallet);
            pg.stopAndWait();
            Address recipientAddress = new Address(netParams, recipient);
            Wallet.SendResult sendTxn = wallet.sendCoins(pg, recipientAddress, btcToSend);

            if (sendTxn == null) {
                // Failure
            } else {
                // success
                System.out.println("Success!!!!!!!!!");
            }
        } catch(BlockStoreException e) {
            System.out.println(e);
        } catch (AddressFormatException e) {
            System.out.println(e);
        } catch (InsufficientMoneyException e) {
            System.out.println(e);
        }
        */
    }

    void getBalance() {
        System.out.println("Current wallet balance: " + wallet.getBalance());
    }

}
