package com.baki.crypto;

import org.junit.Test;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.Key;

import static org.junit.Assert.*;

/**
 * The Java Cipher (javax.crypto.Cipher) class represents an encryption algorithm
 * You can use a Cipher instance to encrypt and decrypt data in Java
 */
public class CipherTest extends BaseTest {

    @Test
    public void createCipher() throws Exception {
        Cipher cipher1 = Cipher.getInstance("AES");
        // Some encryption algorithms can work in different modes. The most known cipher modes:
        // ECB - Electronic Codebook
        // CBC - Cipher Block Chaining
        // CFB - Cipher Feedback
        // OFB - Output Feedback
        // CTR - Counter
        Cipher cipher2 = Cipher.getInstance("AES/CBC/PKCS5Padding");
        // Keep in mind that not all encryption algorithms and modes are supported by the default Java SDK cryptography provider.
        // You might need an external provider like Bouncy Castle installed

        assertNotNull(cipher1);
        assertNotNull(cipher2);
    }

    @Test
    public void initCipher() throws Exception {
        // The init() method takes two parameters:
        // - Encryption / decryption cipher operation mode.
        // - Encryption / decryption key.
        Cipher cipher = Cipher.getInstance("AES");
        Key key = getSymmetricKey(); // get / create symmetric encryption key
        cipher.init(Cipher.ENCRYPT_MODE, key);

        assertNotNull(cipher);
        assertNotNull(key);
    }

    @Test
    public void encryptDecrypt() throws Exception {
        Cipher cipher = Cipher.getInstance("AES");
        Key key = getSymmetricKey();

        String text = "abcdefghijklmnopqrstuvwxyz";
        byte[] textBytes  = text.getBytes(StandardCharsets.UTF_8);

        // encrypt
        cipher.init(Cipher.ENCRYPT_MODE, key);
        byte[] encrypted = cipher.doFinal(textBytes);

        // decrypt
        cipher.init(Cipher.DECRYPT_MODE, key);
        byte[] decrypted = cipher.doFinal(encrypted);

        assertNotEquals(new String(encrypted), text);
        assertEquals(new String(decrypted), text);
    }
}
