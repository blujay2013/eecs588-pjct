package com.example.transactionsigner;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.google.bitcoin.core.InsufficientMoneyException;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import android.content.Intent;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.bitcoin.core.Wallet;

import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AbstractWalletActivity implements OnClickListener {

	private Button scanBtn,getKeyBtn, getPrivKeyBtn, sendSomeMoney, getBalance, signButton;
	private TextView formatTxt, contentTxt,addressText,pubKeyText, balanceText;

    private PrivateKeyDialog privateKeyDialog;
	private SignKeyDialog signKeyDialog;

	private WalletApp app;
	private Wallet wallet;

    private SendCoinsImpl sendCoinsImpl;

    private ProgressBar progress;
    private long progressStatus = 0;
    private Handler handler = new Handler();
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        app = getWalletApp();
        wallet = app.getWallet();
        
        scanBtn = (Button)findViewById(R.id.scan_button);
        formatTxt = (TextView)findViewById(R.id.scan_format);
        contentTxt = (TextView)findViewById(R.id.scan_content);
        
        scanBtn.setOnClickListener(this);
        
        getKeyBtn = (Button)findViewById(R.id.scan_key);
        addressText = (TextView)findViewById(R.id.key_text);
        pubKeyText = (TextView)findViewById(R.id.pubKeyTxt);
        getKeyBtn.setOnClickListener(this);

        //sendSomeMoney = (Button)findViewById(R.id.money);
        //sendSomeMoney.setOnClickListener(this);

        //getBalance = (Button) findViewById(R.id.balance);
        //getBalance.setOnClickListener(this);

        //balanceText = (TextView)findViewById(R.id.balance_text);

        sendCoinsImpl = new SendCoinsImpl(wallet, this, app);

        privateKeyDialog = new PrivateKeyDialog(app.getWallet());
        signKeyDialog = new SignKeyDialog(app.getWallet(), sendCoinsImpl);

        final long startingTime = sendCoinsImpl.getStatus();
        if (savedInstanceState != null) {
            progressStatus = savedInstanceState.getInt("progressStatus", (int) startingTime);
        }
        //progress = (ProgressBar) findViewById(R.id.progress_bar);
        //Calendar c = Calendar.getInstance();
        //System.out.println("*****" + startingTime + "    " + c.get(Calendar.SECOND));
        //progress.setMax((int) new Date().getTime());
        /*
        new Thread(new Runnable() {
            public void run() {
                while (progressStatus < progress.getMax()) {
                    progressStatus = sendCoinsImpl.getStatus();
                    //System.out.println("progressStatus: " + progressStatus + ", startingTime: " + startingTime);
                    //Update progress bar
                    handler.post(new Runnable() {
                        public void run() {
                            progress.setProgress((int) progressStatus - (int) startingTime);
                            //setContentView(R.layout.activity_main);
                        }
                    });
                }
            }
        }).start();
        */
        signButton = (Button) findViewById(R.id.sign_button);
        signButton.setOnClickListener(this);
    }
    
	@Override
	protected void onResume()
	{
		super.onResume();

		getWalletApp().startBlockchainService(true);

		//checkLowStorageAlert();
	}
    
    @SuppressLint("NewApi")
    public void onClick(View v){
    	//respond to clicks
    	if(v.getId()==R.id.scan_button){
    		//scan
    		IntentIntegrator scanIntegrator = new IntentIntegrator(this);
    		scanIntegrator.initiateScan();
    	}
    	if(v.getId()==R.id.scan_key){
    		//Create a public/private key pair
    		//app.addNewKeyToWallet();
            System.out.println("Num keys: " + app.getWallet().getKeys().size());
            sendCoinsImpl.addKeyToWallet(app.getWallet().getKeys().get(4));
    		//addressText.setText("ADDRESS: " + app.getAddressOf(0));
            //sendCoinsImpl.getWallet().getKeys().get(4).getPrivateKeyEncoded(netParams);
            pubKeyText.setText("TEST" + sendCoinsImpl.getWallet().getKeys().get(4).toString());
    		//pubKeyText.setText("PUBLIC KEY: " + app.getWallet());//TODO: Just print pub key here
            privateKeyDialog.show(getFragmentManager(), "privatekeydialog");
    	}
        if (v.getId() == R.id.sign_button) {
            // Popup input dialog to enter in partially signed transaction
            signKeyDialog.show(getFragmentManager(), "signkeydialog");
        }
    }
    
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
    	//retrieve scan result
    	IntentResult scanningResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);
    	if (scanningResult != null) {
    		//we have a result
    		String scanContent = scanningResult.getContents();
    		String scanFormat = scanningResult.getFormatName();
    		formatTxt.setText("FORMAT: " + scanFormat);
    		contentTxt.setText("CONTENT: " + scanContent);
            String delims = ":";
            String[] tokens = scanContent.split(delims);
            System.out.println("Here's the transaction: " + tokens[0]);
            System.out.println("Here's the redeem script: " + tokens[1]);
            // signed t, redeem,
            // String transactionBytes, String redeemScript, SendCoinsImpl sendCoinsImpl, NetworkParameters netParams, Wallet wallet) {
            SignPartialTransaction spt = new SignPartialTransaction(tokens[0], tokens[1], sendCoinsImpl, sendCoinsImpl.netParams, wallet);
    	} else{
    	    Toast toast = Toast.makeText(getApplicationContext(), 
    	            "No scan data received!", Toast.LENGTH_SHORT);
    	    toast.show();
    	}
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);
            return rootView;
        }
    }

}
