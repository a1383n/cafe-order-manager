package sharifplus.core.security;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class Hash {
    public static String make(String s, String algorithm) throws NoSuchAlgorithmException {
        MessageDigest messageDigest = MessageDigest.getInstance(algorithm);
        return Base64.getEncoder().encodeToString(messageDigest.digest(s.getBytes(StandardCharsets.US_ASCII)));
    }

    public static String make(String s) {
        try {
            return make(s,"SHA-256");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    public static boolean check(String s,String hash,String algorithm) throws NoSuchAlgorithmException {
        return hash.equals(make(s,algorithm));
    }

    public static boolean check(String s,String hash) {
        return hash.equals(make(s));
    }
}
