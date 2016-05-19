package com.lntqatar.crypto;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Creates MD5 hash for a string data.
 *
 */
public class MD5 {

    /**
     * Creates MD5 hash for the data passed as argument. Uses Base64 and UTF-8
     * encoding .
     *
     * @param data The string to be hashed
     * @return Returns hashed string
     * @throws NoSuchAlgorithmException If the specified algorithm is not
     * available
     * @throws UnsupportedEncodingException If the charset is not supported
     */
    public static String getHash(String data) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        MessageDigest md = MessageDigest.getInstance("MD5");
        byte[] bytes = data.getBytes("UTF-8");
        md.update(bytes, 0, data.length());
        byte[] md5hash = md.digest();
        byte[] hashValue = Base64.encodeBytes(md5hash).getBytes();
        return convertToHex(hashValue);

    }

    private static String convertToHex(byte[] data) {
        StringBuffer buf = new StringBuffer();
        int length = data.length;
        for (int i = 0; i < length; ++i) {
            int halfbyte = (data[i] >>> 4) & 0x0F;
            int two_halfs = 0;
            do {
                if ((0 <= halfbyte) && (halfbyte <= 9)) {
                    buf.append((char) ('0' + halfbyte));
                } else {
                    buf.append((char) ('a' + (halfbyte - 10)));
                }
                halfbyte = data[i] & 0x0F;
            } while (++two_halfs < 1);
        }
        return buf.toString();
    }
}
