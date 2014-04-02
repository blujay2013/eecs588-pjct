package com.example.transactionsigner;

import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.os.Build;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.bitcoin.core.Wallet;

public class MainActivity extends AbstractWalletActivity implements OnClickListener {

	private Button scanBtn,getKeyBtn;
	private TextView formatTxt, contentTxt,addressText,pubKeyText;
	
	private WalletApp app;
	private Wallet wallet;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        
        //app = getWalletApp();
        //wallet = app.getWallet();
        
        scanBtn = (Button)findViewById(R.id.scan_button);
        formatTxt = (TextView)findViewById(R.id.scan_format);
        contentTxt = (TextView)findViewById(R.id.scan_content);
        
        scanBtn.setOnClickListener(this);
        
        getKeyBtn = (Button)findViewById(R.id.scan_key);
        addressText = (TextView)findViewById(R.id.key_text);
        pubKeyText = (TextView)findViewById(R.id.pubKeyTxt);
        getKeyBtn.setOnClickListener(this);
        
       
    }
    
	@Override
	protected void onResume()
	{
		super.onResume();

		//getWalletApplication().startBlockchainService(true);

		//checkLowStorageAlert();
	}
    
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
    		//addressText.setText("ADDRESS: " + app.getAddressOf(0).toString());
    		//pubKeyText.setText("PUBLIC KEY: " + app.getWallet());//TODO: Just print pub key here
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
