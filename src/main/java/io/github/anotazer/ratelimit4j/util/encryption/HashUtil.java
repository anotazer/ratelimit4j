package io.github.anotazer.ratelimit4j.util.encryption;

import javax.crypto.Cipher;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.util.Base64;

public class HashUtil {

    private final static String SALT = "Xf9Y!@s9T^23KlmN#zQpV7*rD1bC&O8wRx4u5Y$JkL2Pn3MqZ";

    public static String generateHash(String key) throws NoSuchAlgorithmException {

        String saltedKey = key + SALT;

        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] hashBytes = digest.digest(saltedKey.getBytes(StandardCharsets.UTF_8));

        StringBuilder hexString = new StringBuilder();
        for (byte b : hashBytes) {
            hexString.append(String.format("%02x", b));
        }

        return hexString.toString();
    }

}
