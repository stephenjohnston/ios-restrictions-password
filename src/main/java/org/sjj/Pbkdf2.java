package org.sjj;

import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Base64;

/**
 * Created by stephen on 12/18/15.
 */
public class Pbkdf2 {
    private static int decodeInt(String s) {
        byte[] bytes = Base64.getDecoder().decode(s);
        BigInteger bi = new BigInteger(1, bytes);
        return bi.intValue();
    }

    private static String encodeString(byte[] bytes) {
        return Base64.getEncoder().encodeToString(bytes);
    }

    private static String encodeInt(int i) {
        // TODO toByteArray may not always return the right number of bytes
        // because it is can vary them.  Might need to pad with extra zero bytes.
        byte[] bytes = BigInteger.valueOf(i).toByteArray();
        return Base64.getEncoder().encodeToString(bytes);
    }

    private static String encrypt(String raw, int iterations, byte[] salt) {
        PBEKeySpec keySpec = new PBEKeySpec(raw.toCharArray(), salt, iterations, 160);
        SecretKeyFactory keyFactory = null;
        try {
            keyFactory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
            SecretKey key = keyFactory.generateSecret(keySpec);
            byte[] encoded = key.getEncoded();
            String encodedStr = encodeString(encoded);
            String result = encodeInt(iterations) + "$" + encodeString(salt) + "$" + encodedStr;
            return result;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static boolean checkPbkdf2(String hash, String s)  {
        String[] toks = hash.split("\\$");
        byte[] salt = Base64.getDecoder().decode(toks[1]);
        int iterations = decodeInt(toks[0]);
        String result = encrypt(s, iterations, salt);

        return result.equals(hash);
    }
}
