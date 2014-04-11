package com.example.transactionsigner;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;

import com.google.bitcoin.core.NetworkParameters;
import com.google.bitcoin.core.Wallet;


/**
 * Created by evarobert on 4/2/14.
 */
public class PrivateKeyDialog extends DialogFragment {
    private Wallet wallet;

    public PrivateKeyDialog(Wallet wallet) {
        this.wallet = wallet;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        /*
        builder.setMessage("Private key: " + wallet.getKeys().get(0).toStringWithPrivate())
               .setPositiveButton("OK", new DialogInterface.OnClickListener() {
        public void onClick(DialogInterface dialog, int id) {
                       // FIRE ZE MIZZLESSSS
                   }
               });
               */
        NetworkParameters netParams;
        String network = "test";
        if (network.equalsIgnoreCase("prod")) {
            netParams = NetworkParameters.prodNet();
        } else {
            netParams = NetworkParameters.testNet();
        }
        builder.setMessage("Address: " + wallet.getKeys().get(0).toAddress(netParams));
        return builder.create();
    }
}
