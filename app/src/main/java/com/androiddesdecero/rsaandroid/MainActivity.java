package com.androiddesdecero.rsaandroid;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;

import javax.crypto.Cipher;

public class MainActivity extends AppCompatActivity {

    private KeyPairGenerator kpg;
    private KeyPair kp;
    private PublicKey publicKey;
    private PrivateKey privateKey;

    private String descryptedString;
    private byte[] encrytedByte;
    private byte[] descryptedByte;
    private Cipher cipher;

    private final static String CRYPTO_METHOD = "RSA";
    private final static int CRYPTO_BITS = 2048;
    private final static String OPCION_RSA= "RSA/ECB/OAEPWithSHA1AndMGF1Padding";
    private String message = "Este mensaje es secreto, por ello va encriptado";
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        try{
        generateKayPair();
        String mensajeEncriptado = encrypt(message);
        Log.d("TAG1", "mensaje encriptado -> " + mensajeEncriptado);
        String mensajeDesencirptado = descrypt(mensajeEncriptado);
        Log.d("TAG1", "mensaje desencritpatado -> " + mensajeDesencirptado);
        }catch (Exception e){

        }
    }

    private void generateKayPair() throws Exception{
        kpg = KeyPairGenerator.getInstance(CRYPTO_METHOD);
        kpg.initialize(CRYPTO_BITS);
        kp = kpg.generateKeyPair();
        publicKey = kp.getPublic();
        Log.d("TAG1", "public key -> " + publicKey);
        privateKey = kp.getPrivate();
        Log.d("TAG1", "private key -> " + privateKey);
    }

    private String encrypt(String mensajeAEncriptar) throws Exception{
        cipher = Cipher.getInstance(OPCION_RSA);
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        encrytedByte = cipher.doFinal(mensajeAEncriptar.getBytes());
        return Base64.encodeToString(encrytedByte, Base64.DEFAULT);
    }

    private String descrypt(String result) throws Exception{
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        descryptedByte = cipher.doFinal(Base64.decode(result, Base64.DEFAULT));
        descryptedString = new String(descryptedByte);
        return descryptedString;
    }

}
