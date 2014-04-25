package com.example.transactionsigner;

import com.google.bitcoin.core.Address;
import com.google.bitcoin.core.ECKey;
import com.google.bitcoin.core.InsufficientMoneyException;
import com.google.bitcoin.core.NetworkParameters;
import com.google.bitcoin.core.PeerGroup;
import com.google.bitcoin.core.Sha256Hash;
import com.google.bitcoin.core.Transaction;
import com.google.bitcoin.core.Wallet;
import com.google.bitcoin.crypto.TransactionSignature;
import com.google.bitcoin.script.Script;
import com.google.bitcoin.script.ScriptBuilder;
import com.google.bitcoin.script.ScriptChunk;

import org.bitcoinj.wallet.Protos;
import org.spongycastle.util.encoders.Hex;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Formatter;
import java.util.List;

/**
 * Created by evarobert on 4/14/14.
 */
public class SignPartialTransaction {
    Script redeemScript;
    Address payTo;
    Sha256Hash sigHash; // hash of redeemScript
    Transaction spendTx;
    ECKey.ECDSASignature key2sig; // signature using our private key
    byte[] key1TransactionSig; // should be in byte format already
    TransactionSignature key2TransactionSig; // needs to be converted into byte format

    SignPartialTransaction(String transactionBytes, String redeemScript, SendCoinsImpl sendCoinsImpl, NetworkParameters netParams, Wallet wallet) {
        Transaction t = new Transaction(netParams, Hex.decode(transactionBytes));
        Transaction toSign = new Transaction(netParams, Hex.decode(transactionBytes));
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

        Script redeem = new Script(Hex.decode(redeemScript));
        List<ScriptChunk> chunks = redeem.getChunks();
        ECKey foundKey = null;

        for (ScriptChunk sc : chunks) {
            for (ECKey k : wallet.getKeys()) {
                if (k.getPubKey() == sc.data) {
                    System.out.println("WE FOUND ZE KEY!!!");
                    foundKey = k;
                }
            }
        }

        if (foundKey != null) {

            //ECKey key2 = wallet.getKeys().get(4);
            System.out.println("Redeem script: " + redeem);
            Transaction tx = new Transaction(netParams, Hex.decode(transactionBytes));

            tx.getInput(0).setScriptSig(
                 new ScriptBuilder()
                         .data(t.getInput(0).getScriptSig().getChunks().get(0).data)
                         .data(t.getInput(0).getScriptSig().getChunks().get(1).data)
                         .data(toSign.calculateSignature(0, foundKey, redeem, Transaction.SigHash.ALL, false).encodeToBitcoin())
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
    }

    SignPartialTransaction(byte[] redeemScript, Address payTo, ECKey key2, byte[] key1TransactionSig, byte[] spendTxBytes) {

        this.redeemScript = new Script(redeemScript);
        this.payTo = payTo;
        this.key1TransactionSig = key1TransactionSig;
        NetworkParameters params = NetworkParameters.testNet();
        this.spendTx = new Transaction(params, spendTxBytes); // this might not create a transaction correctly
        this.sigHash = spendTx.hashForSignature(0, this.redeemScript, Transaction.SigHash.ALL, false);
        this.key2sig = key2.sign(this.sigHash);
        this.key2TransactionSig = new TransactionSignature(key2sig, Transaction.SigHash.ALL, false);
        Script inputScript = createP2SHMultiSigInputScript();
        assert(inputScript.getChunks().size() == 4);
        Transaction t = new Transaction(params);


        //Wallet.SendRequest req = Wallet.SendRequest.forTx()
    }


    Script createP2SHMultiSigInputScript() {
        //Script multisigScript = new Script(redeemScript);
        ScriptBuilder builder = new ScriptBuilder();
        builder.smallNum(0);
        builder.data(key1TransactionSig); // remove this when we get the bytes (probably just signature)
        builder.data(key2TransactionSig.encodeToBitcoin());
        builder.data(redeemScript.getProgram());
        return builder.build();
    }

}
