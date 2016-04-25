package com.example.asus.bdcricketteam.security;

/**
 * Created by ASUS on 2/7/2016.
 */

import android.util.Base64;

import java.security.Key;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

public class SecureProcessor {
    private static final String encryptionType = "AES";
    private static final String crack = "LOTD_YO_APPLICAT";

    public static String onEncrypt(String plainText) {
        try {
            Key aesKey = new SecretKeySpec(crack.getBytes(), encryptionType);
            Cipher cipher = Cipher.getInstance(encryptionType);

            cipher.init(Cipher.ENCRYPT_MODE, aesKey);
            byte[] encrypted = cipher.doFinal(plainText.getBytes());

            return Base64.encodeToString(encrypted, Base64.DEFAULT);

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String onDecrypt(String encryptedText) {
        try {
            Key aesKey = new SecretKeySpec(crack.getBytes(), encryptionType);
            Cipher cipher = Cipher.getInstance(encryptionType);

            cipher.init(Cipher.DECRYPT_MODE, aesKey);

            return (new String(cipher.doFinal(Base64.decode(encryptedText, Base64.DEFAULT))));

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
