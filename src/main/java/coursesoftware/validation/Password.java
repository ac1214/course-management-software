package coursesoftware.validation;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Random;

public class Password {
    public static String getHash(String password, String salt) {
        MessageDigest md = null;
        String concatenated = password + salt;

        try {
            md = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        byte[] hashBytes = md.digest(concatenated.getBytes(StandardCharsets.UTF_8));
        String pwHash = bytesToHex(hashBytes);

        return pwHash;
    }

    /**
     * This function is from https://www.baeldung.com/sha-256-hashing-java
     *
     * @param arr Array of bytes that contain the hash
     * @return String value of the hashed password
     */
    private static String bytesToHex(byte[] arr) {
        StringBuffer hexString = new StringBuffer();
        for (int i = 0; i < arr.length; i++) {
            String hex = Integer.toHexString(0xff & arr[i]);
            if (hex.length() == 1)
                hexString.append('0');
            hexString.append(hex);
        }
        return hexString.toString();
    }

    /**
     * Get a salt in hex
     *
     * @return a random salt in hex
     */
    public static String getNextSalt() {
        Random secureRandom = new SecureRandom();
        byte[] salt = new byte[16];
        secureRandom.nextBytes(salt);

        return bytesToHex(salt);
    }
}
