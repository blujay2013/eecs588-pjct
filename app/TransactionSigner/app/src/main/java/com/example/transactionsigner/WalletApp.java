package com.example.transactionsigner;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.bitcoinj.wallet.Protos;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.pm.PackageManager;
import android.preference.PreferenceManager;
import android.text.format.DateUtils;
import android.widget.Toast;
import java.util.Locale;
import java.util.concurrent.TimeUnit;
import android.content.Context;
import android.content.Intent;
import android.app.Application;
import android.content.pm.PackageInfo;
import android.app.ActivityManager;

import android.app.Application;
import com.google.protobuf.GeneratedMessageLite;
import com.google.protobuf.MessageLiteOrBuilder;
import com.google.bitcoin.core.*;
import com.google.bitcoin.store.UnreadableWalletException;
import com.google.bitcoin.store.WalletProtobufSerializer;
import com.google.bitcoin.wallet.WalletFiles;

import javax.annotation.Nonnull;


public class WalletApp extends Application {

    private Configuration config;
	private Wallet wallet;
	File walletFile ;//= new File("test.wallet");//TODO: change to be more secure
    private PackageInfo packageInfo;
    private ActivityManager activityManager;

    private Intent blockchainServiceIntent;
    private Intent blockchainServiceCancelCoinsReceivedIntent;
    private Intent blockchainServiceResetBlockchainIntent;

	@Override
	public void onCreate()
	{
		new LinuxSecureRandom(); // init proper random number generator

		//StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().detectAll().permitDiskReads().permitDiskWrites().penaltyLog().build());

		//Threading.throwOnLockCycles();

		super.onCreate();

		packageInfo = packageInfoFromContext(this);

		//CrashReporter.init(getCacheDir());

		/*Threading.uncaughtExceptionHandler = new Thread.UncaughtExceptionHandler()
		{
			@Override
			public void uncaughtException(final Thread thread, final Throwable throwable)
			{
				log.info("bitcoinj uncaught exception", throwable);
				CrashReporter.saveBackgroundTrace(throwable, packageInfo);
			}
		};
        */
		config = new Configuration(PreferenceManager.getDefaultSharedPreferences(this));
		activityManager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);

		blockchainServiceIntent = new Intent(this, BlockchainServiceImpl.class);
		blockchainServiceCancelCoinsReceivedIntent = new Intent(BlockchainService.ACTION_CANCEL_COINS_RECEIVED, null, this,
				BlockchainServiceImpl.class);
		blockchainServiceResetBlockchainIntent = new Intent(BlockchainService.ACTION_RESET_BLOCKCHAIN, null, this, BlockchainServiceImpl.class);

		walletFile = getFileStreamPath(Constants.WALLET_FILENAME_PROTOBUF);

		loadWalletFromProtobuf();
		wallet.autosaveToFile(walletFile, 1, TimeUnit.SECONDS, new WalletAutosaveEventListener());

		// clean up spam
		//wallet.cleanup();

		//config.updateLastVersionCode(packageInfo.versionCode);

		/*if (config.versionCodeCrossed(packageInfo.versionCode, KEY_ROTATION_VERSION_CODE))
		{
			log.info("detected version jump crossing key rotation");
			wallet.setKeyRotationTime(System.currentTimeMillis() / 1000);
		}*/

		ensureKey();

		migrateBackup();
		
		//work with testnet?
		//final NetworkParameters netParams = NetworkParameters.testNet();

	}
	
	private static final class WalletAutosaveEventListener implements WalletFiles.Listener
	{
		@Override
		public void onBeforeAutoSave(final File file)
		{
		}

		@Override
		public void onAfterAutoSave(final File file)
		{
			// make wallets world accessible in test mode
			//if (Constants.TEST)
				//Io.chmod(file, 0777);
		}
	}
	
	public Wallet getWallet()
	{
		return wallet;
	}
	
	public Address getAddressOf(int idx)
	{
		List<ECKey> keys = wallet.getKeys();
		if(keys.size() > idx)
		{
			return keys.get(idx).toAddress(null);
		}
		return null;
	}
	
	private void loadWalletFromProtobuf()
	{
		if (walletFile.exists())
		{
			final long start = System.currentTimeMillis();

			FileInputStream walletStream = null;

			try
			{
				walletStream = new FileInputStream(walletFile);

				wallet = new WalletProtobufSerializer().readWallet(walletStream);

				System.out.println("wallet loaded from: '" + walletFile + "', took " + (System.currentTimeMillis() - start) + "ms");
			}
			catch (final FileNotFoundException x)
			{
				System.out.println("problem loading wallet");

				Toast.makeText(WalletApp.this, x.getClass().getName(), Toast.LENGTH_LONG).show();

				wallet = restoreWalletFromBackup();
			}
			catch (final UnreadableWalletException x)
			{
				System.out.println("problem loading wallet unreadable");

				Toast.makeText(WalletApp.this, x.getClass().getName(), Toast.LENGTH_LONG).show();

				wallet = restoreWalletFromBackup();
			}
			finally
			{
				if (walletStream != null)
				{
					try
					{
						walletStream.close();
					}
					catch (final IOException x)
					{
						// swallow
					}
				}
			}

			if (!wallet.isConsistent())
			{
				Toast.makeText(this, "inconsistent wallet: " + walletFile, Toast.LENGTH_LONG).show();

				wallet = restoreWalletFromBackup();
			}

			if (!wallet.getParams().equals(Constants.NETWORK_PARAMETERS))
				throw new Error("bad wallet network parameters: " + wallet.getParams().getId());
		}
		else
		{
			wallet = new Wallet(Constants.NETWORK_PARAMETERS);

			System.out.println("new wallet created");
		}

		// this check is needed so encrypted wallets won't get their private keys removed accidently
		for (final ECKey key : wallet.getKeys())
			if (key.getPrivKeyBytes() == null)
				throw new Error("found read-only key, but wallet is likely an encrypted wallet from the future");
	}
	
	private Wallet restoreWalletFromBackup()
	{
		InputStream is = null;

		try
		{
			is = openFileInput(Constants.WALLET_KEY_BACKUP_PROTOBUF);

			final Wallet wallet = new WalletProtobufSerializer().readWallet(is);

			if (!wallet.isConsistent())
				throw new Error("inconsistent backup");

			//resetBlockchain();

			Toast.makeText(this, R.string.toast_wallet_reset, Toast.LENGTH_LONG).show();

			System.out.println("wallet restored from backup: '" + Constants.WALLET_KEY_BACKUP_PROTOBUF + "'");

			return wallet;
		}
		catch (final IOException x)
		{
			throw new Error("cannot read backup", x);
		}
		catch (final UnreadableWalletException x)
		{
			throw new Error("cannot read backup", x);
		}
		finally
		{
			try
			{
				is.close();
			}
			catch (final IOException x)
			{
				// swallow
			}
		}
	}
	
	private void ensureKey()
	{
		for (final ECKey key : wallet.getKeys())
			if (!wallet.isKeyRotating(key))
				return; // found

		//log.info("wallet has no usable key - creating");
		addNewKeyToWallet();
	}
	
	public void addNewKeyToWallet()
	{
		try{
			wallet.addKey(new ECKey());
			//backupWallet();
			wallet.saveToFile(walletFile);
		} catch (IOException e) {
			System.out.println("unable to create wallet file.");
		}

	}
	
	public void saveWallet()
	{
		try
		{
			protobufSerializeWallet(wallet);
		}
		catch (final IOException x)
		{
			throw new RuntimeException(x);
		}
	}
	
	private void protobufSerializeWallet(final Wallet wallet) throws IOException
	{
		final long start = System.currentTimeMillis();

		wallet.saveToFile(walletFile);

		// make wallets world accessible in test mode
		//if (Constants.TEST)
			//Io.chmod(walletFile, 0777);

		System.out.println("wallet saved to: '" + walletFile + "', took " + (System.currentTimeMillis() - start) + "ms");
	}
	
private void backupWallet()
	{
		final Protos.Wallet.Builder builder = new WalletProtobufSerializer().walletToProto(wallet).toBuilder();

		// strip redundant
		builder.clearTransaction();
		builder.clearLastSeenBlockHash();
		builder.setLastSeenBlockHeight(-1);
		builder.clearLastSeenBlockTimeSecs();
		final Protos.Wallet walletProto = builder.build();

		OutputStream os = null;

		try
		{
			os = openFileOutput(Constants.WALLET_KEY_BACKUP_PROTOBUF, Context.MODE_PRIVATE);
			walletProto.writeTo(os);
		}
		catch (final IOException x)
		{
			System.out.println("problem writing key backup");
		}
		finally
		{
			try
			{
				os.close();
			}
			catch (final IOException x)
			{
				// swallow
			}
		}

		try
		{
			final String filename = String.format(Locale.US, "%s.%02d", Constants.WALLET_KEY_BACKUP_PROTOBUF,
					(System.currentTimeMillis() / DateUtils.DAY_IN_MILLIS) % 100l);
			os = openFileOutput(filename, Context.MODE_PRIVATE);
			walletProto.writeTo(os);
		}
		catch (final IOException x)
		{
			System.out.println("problem writing key backup");
		}
		finally
		{
			try
			{
				os.close();
			}
			catch (final IOException x)
			{
				// swallow
			}
		}
	}

	private void migrateBackup()
	{
		if (!getFileStreamPath(Constants.WALLET_KEY_BACKUP_PROTOBUF).exists())
		{
			System.out.println("migrating automatic backup to protobuf");

			// make sure there is at least one recent backup
			//backupWallet();

			// remove old backups
			for (final String filename : fileList())
				if (filename.startsWith(Constants.WALLET_KEY_BACKUP_BASE58))
					new File(getFilesDir(), filename).delete();
		}
	}

    public final String applicationPackageFlavor()
    {
        final String packageName = getPackageName();
        final int index = packageName.lastIndexOf('_');

        if (index != -1)
            return packageName.substring(index + 1);
        else
            return null;
    }

    public PackageInfo packageInfo()
    {
        return packageInfo;
    }

    public int maxConnectedPeers()
    {
        final int memoryClass = activityManager.getMemoryClass();
        if (memoryClass <= Constants.MEMORY_CLASS_LOWEND)
            return 4;
        else
            return 6;
    }

    public static PackageInfo packageInfoFromContext(final Context context)
    {
        try
        {
            return context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
        }
        catch (final PackageManager.NameNotFoundException x)
        {
            throw new RuntimeException(x);
        }
    }

    public Configuration getConfiguration()
    {
        return config;
    }

    public static void scheduleStartBlockchainService(@Nonnull final Context context)
    {
        final Configuration config = new Configuration(PreferenceManager.getDefaultSharedPreferences(context));
        final long lastUsedAgo = config.getLastUsedAgo();

        // apply some backoff
        final long alarmInterval;
        if (lastUsedAgo < Constants.LAST_USAGE_THRESHOLD_JUST_MS)
            alarmInterval = AlarmManager.INTERVAL_FIFTEEN_MINUTES;
        else if (lastUsedAgo < Constants.LAST_USAGE_THRESHOLD_RECENTLY_MS)
            alarmInterval = AlarmManager.INTERVAL_HALF_DAY;
        else
            alarmInterval = AlarmManager.INTERVAL_DAY;

        //log.info("last used {} minutes ago, rescheduling blockchain sync in roughly {} minutes", lastUsedAgo / DateUtils.MINUTE_IN_MILLIS,
         //       alarmInterval / DateUtils.MINUTE_IN_MILLIS);

        final AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        final PendingIntent alarmIntent = PendingIntent.getService(context, 0, new Intent(context, BlockchainServiceImpl.class), 0);
        alarmManager.cancel(alarmIntent);

        // workaround for no inexact set() before KitKat
        final long now = System.currentTimeMillis();
        alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, now + alarmInterval, AlarmManager.INTERVAL_DAY, alarmIntent);
    }

    public void startBlockchainService(final boolean cancelCoinsReceived)
    {
        if (cancelCoinsReceived)
            startService(blockchainServiceCancelCoinsReceivedIntent);
        else
            startService(blockchainServiceIntent);
    }

}
