package com.lntqatar.crypto;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * This class encrypts and decrypts data using AES256 algorithm.
 */
public class EncryptionEngine {

    private final static int[] INDICES = {4, 10, 0, 2, 3, 14, 5, 8, 1, 13, 7,
        6, 9, 11, 15, 12};
    private final static String[] SEGMENTS = {"9B2C", "BE58", "6018", "7A1E",
        "54C9", "4B8D", "A65D", "9880", "E121", "2B71", "C7D0", "3F6C",
        "AB62", "A3C0", "BD9E", "24E4"};

    /**
     * Encrypts string data using AES256 algorithm. Throws exception in case an
     * invalid key is used or an unknown algorithm is used. All exceptions
     * should be handled at individual level.
     *
     * @param key key to be used to encrypt the data
     * @param cleartext data to be encrypted
     * @return encrypted data in the form of string
     * @throws Exception throws exception which can be an instance of any one of
     * the following: NoSuchAlgorithmException, UnsupportedEncodingException,
     * IllegalArgumentException, NoSuchPaddingException, InvalidKeyException,
     * IllegalBlockSizeException, BadPaddingException
     */
    public static String encrypt(String key, String cleartext) throws Exception {
        // byte[] rawKey = getRawKeyAsByte(key);// getRawKey(seed.getBytes());
        byte[] result = encrypt(key.getBytes(), cleartext.getBytes());
        return toHex(result);
    }

    /**
     * Decrypts string data using AES256 algorithm. Throws exception in case an
     * invalid key is used or an unknown algorithm is used. All exceptions
     * should be handled at individual level.
     *
     * @param key key to be used to decrypt the data
     * @param cleartext data to be decrypted
     * @return decrypted data in the form of string
     * @throws Exception throws Exception which can be an instance of any one of
     * the following: NoSuchAlgorithmException, UnsupportedEncodingException,
     * IllegalArgumentException, NoSuchPaddingException, InvalidKeyException,
     * IllegalBlockSizeException, BadPaddingException
     */
    public static String decrypt(String key, String cleartext) throws Exception {
        byte[] enc = toByte(cleartext);
        byte[] result = decrypt(key.getBytes(), enc);
        return new String(result);
    }

    private static String encryptWithEncryptedKey(String raw, String clearText)
            throws Exception {
        SecretKeySpec skeySpec = new SecretKeySpec(raw.getBytes(), "AES");
        Cipher cipher = Cipher.getInstance("AES");
        byte[] vector = {0, 2, 4, 8, 16, 32, 64, 127, 127, 64, 32, 16, 8, 4,
            2, 0};
        IvParameterSpec ips = new IvParameterSpec(vector);
        cipher.init(Cipher.ENCRYPT_MODE, skeySpec, ips);
        byte[] encrypted = cipher.doFinal(clearText.getBytes());
        return toHex(encrypted);
    }

    private static byte[] encrypt(byte[] raw, byte[] clear) throws Exception {
        SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
        Cipher cipher = Cipher.getInstance("AES");
        // byte[] vector = {0, 2, 4, 8, 16, 32, 64, 127, 127, 64, 32, 16, 8, 4,
        // 2, 0};
        // IvParameterSpec ips = new IvParameterSpec(vector);
        cipher.init(Cipher.ENCRYPT_MODE, skeySpec);
        byte[] encrypted = cipher.doFinal(clear);
        return encrypted;
    }

    private static byte[] decrypt(byte[] raw, byte[] encrypted)
            throws Exception {
        SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.DECRYPT_MODE, skeySpec);
        byte[] decrypted = cipher.doFinal(encrypted);
        return decrypted;
    }

    public static String toHex(String txt) {
        return toHex(txt.getBytes());
    }

    public static String fromHex(String hex) {
        return new String(toByte(hex));
    }

    private static byte[] toByte(String hexString) {
        int len = hexString.length() / 2;
        byte[] result = new byte[len];
        for (int i = 0; i < len; i++) {
            result[i] = Integer.valueOf(hexString.substring(2 * i, 2 * i + 2),
                    16).byteValue();
        }
        return result;
    }

    private static String toHex(byte[] buf) {
        if (buf == null) {
            return "";
        }
        StringBuffer result = new StringBuffer(2 * buf.length);
        for (int i = 0; i < buf.length; i++) {
            appendHex(result, buf[i]);
        }
        return result.toString();
    }
    private final static String HEX = "0123456789ABCDEF";

    private static void appendHex(StringBuffer sb, byte b) {
        sb.append(HEX.charAt((b >> 4) & 0x0f)).append(HEX.charAt(b & 0x0f));
    }

    private static final String constructKey() {
        String key = "";
        String[] keyArr = new String[16];

        for (int i = 0; i < 16; i++) {
            int index = INDICES[i];
            keyArr[index] = SEGMENTS[i];
        }
        for (int i = 0; i < keyArr.length; i++) {
            key = key + keyArr[i];
        }
        return key;
    }

    private static final byte[] getRawKeyAsByte(String key)
            throws NoSuchAlgorithmException, UnsupportedEncodingException {
        String seed = MD5.getHash(key);
        KeyGenerator kgen = KeyGenerator.getInstance("AES");
        SecureRandom sr = SecureRandom.getInstance("SHA1PRNG");
        sr.setSeed(seed.getBytes());
        kgen.init(256, sr);
        SecretKey skey = kgen.generateKey();
        byte[] raw = skey.getEncoded();
        return raw;
    }

    private static final String getRawKeyAsString(String key)
            throws NoSuchAlgorithmException, UnsupportedEncodingException {
        String seed = MD5.getHash(key);
        KeyGenerator kgen = KeyGenerator.getInstance("AES");
        SecureRandom sr = SecureRandom.getInstance("SHA1PRNG");
        sr.setSeed(seed.getBytes());
        kgen.init(256, sr);
        SecretKey skey = kgen.generateKey();
        byte[] raw = skey.getEncoded();
        String str = new String(raw);
        return str;
    }
}
