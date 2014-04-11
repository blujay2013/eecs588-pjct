package com.example.transactionsigner;

import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import com.example.transactionsigner.WalletApp;

public abstract class AbstractWalletActivity extends FragmentActivity {
	
	private WalletApp application;
	
	@Override
	protected void onCreate(final Bundle savedInstanceState)
	{
		application = (WalletApp) getApplication();

		super.onCreate(savedInstanceState);
	}

	protected WalletApp getWalletApp()
	{
		return application;
	}
}
