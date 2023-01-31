package sharifplus.core.security;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HexFormat;

/**
 * This class used to hash text to specified hashing algorithm
 */
public class Hash {

    /**
     * Convert byte array to hex string. Ex. [1,15] -> 1f
     *
     * @param bytes The byte array
     * @return The hexadecimal string
     */
    private static String bytesToHexString(byte[] bytes) {
        return HexFormat.of().formatHex(bytes);
    }

    /**
     * Make a hash from string with specified algorithm
     *
     * @param s         The string want to be hash
     * @param algorithm The algorithm
     * @return The hexadecimal string
     * @throws NoSuchAlgorithmException
     */
    public static String make(String s, String algorithm) throws NoSuchAlgorithmException {
        MessageDigest messageDigest = MessageDigest.getInstance(algorithm);
        return bytesToHexString(messageDigest.digest(s.getBytes(StandardCharsets.US_ASCII)));
    }

    /**
     * Make a hash from string with SHA-256 algorithm
     *
     * @param s The string want to be hash
     * @return The hexadecimal string
     */
    public static String make(String s) {
        try {
            return make(s, "SHA-256");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Check the given string equals to the given hash with specified algorithm
     *
     * @param s         The original string
     * @param hash      The hashed string
     * @param algorithm The hashing algorithm
     * @return If given string equals to the given hash return true, otherwise false
     * @throws NoSuchAlgorithmException
     */
    public static boolean check(String s, String hash, String algorithm) throws NoSuchAlgorithmException {
        return hash.equals(make(s, algorithm));
    }

    /**
     * Check the given string equals to the given hash with SHA-256 algorithm
     *
     * @param s    The original string
     * @param hash The hashed string
     * @return If given string equals to the given hash return true, otherwise false
     */
    public static boolean check(String s, String hash) {
        return hash.equals(make(s));
    }
}
