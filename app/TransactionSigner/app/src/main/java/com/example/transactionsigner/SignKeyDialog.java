package com.example.transactionsigner;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.InputType;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.google.bitcoin.core.BlockChain;
import com.google.bitcoin.core.ECKey;
import com.google.bitcoin.core.InsufficientMoneyException;
import com.google.bitcoin.core.NetworkParameters;
import com.google.bitcoin.core.PeerGroup;
import com.google.bitcoin.core.Sha256Hash;
import com.google.bitcoin.core.Transaction;
import com.google.bitcoin.core.TransactionInput;
import com.google.bitcoin.core.TransactionOutPoint;
import com.google.bitcoin.core.TransactionOutput;
import com.google.bitcoin.core.Wallet;
import com.google.bitcoin.crypto.TransactionSignature;
import com.google.bitcoin.script.Script;
import com.google.bitcoin.script.ScriptBuilder;
import com.google.bitcoin.script.ScriptChunk;

import org.spongycastle.util.encoders.Hex;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Formatter;
import java.util.List;


/**
 * Created by evarobert on 4/2/14.
 */
@SuppressLint("NewApi")
public class SignKeyDialog extends DialogFragment {
    private Wallet wallet;
    String hex, redeemScript;
    SendCoinsImpl sendCoinsImpl;

    public SignKeyDialog() {}

    public SignKeyDialog(Wallet wallet, SendCoinsImpl sendCoinsImpl) {
        this.wallet = wallet;
        this.sendCoinsImpl = sendCoinsImpl;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        final NetworkParameters netParams;
        final String network = "test";
        if (network.equalsIgnoreCase("prod")) {
            netParams = NetworkParameters.prodNet();
        } else {
            netParams = NetworkParameters.testNet();
        }

        LinearLayout layout = new LinearLayout(getActivity());
        layout.setOrientation(LinearLayout.VERTICAL);

        final EditText input = new EditText(getActivity());
        // Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
        input.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_CLASS_TEXT);
        layout.addView(input);

        final EditText input2 = new EditText(getActivity());
        input2.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_CLASS_TEXT);
        layout.addView(input2);

        builder.setView(layout);

        builder.setMessage("Address: " + wallet.getKeys().get(4).toAddress(netParams))
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // TODO do something with input.getText() here.
                        //hex = input.getText().toString();
                        hex = "0100000001507e15f33fad60b5be17c71b85f73703db2a5291ab52844ed6af4747262d6d78000000009100473044022018733777dde4659f159998e26ef04d341cba2113daa2fb5914360dde683e001702201628ad97acdf54374526f313094a48e233bdcb4bdc5fd9d4ddb8537355cead770147522102284ccece08fb21efc56c61268e7994d0f350ddbd15de6235f44155659149561521021f3ccaeaf4759cc7a0b6835c47226f50d0bedcc2ce33a5001ebd6b147105e3aa52aeffffffff0240420f00000000001976a9145070c2fdd88d72abecea88edd03627d4b86ebe0888aca06c3414000000001976a914c8f339546ae4cc080aea19fb27b383158173001488ac00000000";
                        Transaction t = new Transaction(netParams, Hex.decode(hex));
                        redeemScript = "522102284ccece08fb21efc56c61268e7994d0f350ddbd15de6235f44155659149561521021f3ccaeaf4759cc7a0b6835c47226f50d0bedcc2ce33a5001ebd6b147105e3aa52ae";

                        Transaction toSign = new Transaction(netParams, Hex.decode(hex));
                        //toSign.clearInputs();
                        System.out.println("*******Transaction to sign!!!!");
                        final StringBuilder sb2 = new StringBuilder();
                        Formatter formatter2 = new Formatter(sb2);
                        try {
                            ByteArrayOutputStream os2 = new ByteArrayOutputStream();
                            toSign.bitcoinSerialize(os2);
                            byte[] bytes = os2.toByteArray();
                            for (byte b : bytes) {
                                formatter2.format("%02x", b);
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                        System.out.println(sb2.toString());


                        // Commented out code here is for the spendingFrom transaction that we thought we were supposed to use
                        //Transaction spendingFrom = new Transaction(netParams, Hex.decode("010000000158c7c7f2b8450ad7f2a43a0ba22d8c512feba07c9dbc20248c9cfd005e2f4790010000006a473044022010317bfdbd3b4e57fb6c163eb4034fe0b87d6843bb76469ce9856fb0ac287e0702206222a2c8ff16d99941407c1eab6d94a58916307c58ff0b4c11cd8cb2b7a25ebc012102284ccece08fb21efc56c61268e7994d0f350ddbd15de6235f441556591495615ffffffff01f0d543140000000017a914a25e9a42e09df41322af22322b16c05fc0209d598700000000"));

                        //ECKey key1 = new ECKey(Hex.decode("cPcgGL8Nn3P9agxuhRBmxGDyb7FaVK5rwWmkpj5safHunhosEYCv"), Hex.decode("03b0073558c13fb906c984d0033a271c2f80948ccaf73c615f7b9a064349b9193d"));
                        ECKey key2 = wallet.getKeys().get(4);
                        //Script multisigScript = ScriptBuilder.createMultiSigOutputScript(2, Arrays.asList(key1, key2));
                        //System.out.println("Multisig script: " + multisigScript);
                        System.out.println("Redeem script: " + new Script(Hex.decode(redeemScript)));
                        //System.out.println("Prev tx script: " + spendingFrom.getOutput(0).getScriptPubKey());

                        // Trying the new thing from Jess yay...
                        Transaction tx = new Transaction(netParams, Hex.decode(hex));
                        //Script spk = spendingFrom.getOutput(0).getScriptPubKey();

                        System.out.println("Number of inputs before we add one: " + toSign.getInputs().size());
                        // This is what
                        //toSign.addInput(spendingFrom.getOutput(0));

                        tx.getInput(0).setScriptSig(
                                new ScriptBuilder()
                                        .data(t.getInput(0).getScriptSig().getChunks().get(0).data)
                                        .data(t.getInput(0).getScriptSig().getChunks().get(1).data)
                                        .data(toSign.calculateSignature(0, key2, new Script(Hex.decode(redeemScript)), Transaction.SigHash.ALL, false).encodeToBitcoin())
                                        .data(t.getInput(0).getScriptSig().getChunks().get(2).data)
                                        .build()
                        );

                        final StringBuilder sbJess = new StringBuilder();
                        Formatter formatterJess = new Formatter(sbJess);
                        try {
                            ByteArrayOutputStream os = new ByteArrayOutputStream();
                            tx.bitcoinSerialize(os);
                            byte[] bytes = os.toByteArray();
                            for (byte b : bytes) {
                                formatterJess.format("%02x", b);
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }


                        System.out.println("%%%%%Trying Jess's link");
                        System.out.println(sbJess.toString());

                        // Actually send out the transaction
                        try {
                            sendCoinsImpl.sendCoins(tx);
                        } catch (InsufficientMoneyException e) {
                            e.printStackTrace();
                            System.out.println("No monies here :( ");
                        }
                    }
                });

        AlertDialog dialog = builder.create();
        //dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
        return dialog;
    }

    String getHex() {
        return hex;
    }
}
