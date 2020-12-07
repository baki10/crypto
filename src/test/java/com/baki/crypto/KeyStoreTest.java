package com.baki.crypto;

import org.junit.Test;

import javax.crypto.SecretKey;
import java.io.FileOutputStream;
import java.security.Key;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.cert.Certificate;
import java.util.Arrays;

import static org.junit.Assert.*;

public class KeyStoreTest extends BaseTest {

    @Test
    public void loadKeyStoreFromFile() throws Exception {
        String keyStoreType = "PKCS12";
        String keyStoreFile = "keys/testkey.jks";
        String keyStorePassword = "123456";
        KeyStore keyStore = loadKeyStoreFromFile(keyStoreType, keyStoreFile, keyStorePassword);

        assertNotNull(keyStore.getType());
    }

    @Test
    public void loadEmptyKeyStore() throws Exception {
        String keyStoreType = "PKCS12";
        String keyStorePassword = "password";
        KeyStore keyStore = loadEmptyKeyStore(keyStoreType, keyStorePassword);

        assertNotNull(keyStore.getType());
    }

    @Test
    public void getKeys() throws Exception {
        String keyStoreType = "PKCS12";
        String keyStoreFile = "keys/testkey.jks";
        String keyStorePassword = "123456";
        KeyStore keyStore = loadKeyStoreFromFile(keyStoreType, keyStoreFile, keyStorePassword);

        char[] keyPassword = "123456".toCharArray();
        String entryAlias = "testkey";
        KeyStore.ProtectionParameter entryPassword = new KeyStore.PasswordProtection(keyPassword);

        KeyStore.Entry keyEntry = keyStore.getEntry(entryAlias, entryPassword);
        KeyStore.PrivateKeyEntry privateKeyEntry = (KeyStore.PrivateKeyEntry) keyEntry;
        PrivateKey privateKey = privateKeyEntry.getPrivateKey();
        Certificate certificate = privateKeyEntry.getCertificate();
        Certificate[] certificateChain = privateKeyEntry.getCertificateChain();
        PublicKey publicKey = certificate.getPublicKey();

        assertNotNull(privateKeyEntry);
        assertNotNull(privateKey);
        assertNotNull(certificate);
        assertNotNull(publicKey);
        assertNotEquals(certificateChain.length, 0);
        assertTrue(Arrays.asList(certificateChain).contains(certificate));
    }

    @Test
    public void setKeys() throws Exception {
        String keyStoreType = "PKCS12";
        String keyStorePassword = "password";
        KeyStore keyStore = loadEmptyKeyStore(keyStoreType, keyStorePassword);

        // set entry
        KeyStore.SecretKeyEntry secretKeyEntry = new KeyStore.SecretKeyEntry(getSymmetricKey());
        KeyStore.ProtectionParameter entryPassword = new KeyStore.PasswordProtection("password".toCharArray());
        keyStore.setEntry("entryAlias", secretKeyEntry, entryPassword);

        // write to file
        try (FileOutputStream keyStoreOutputStream = new FileOutputStream("keys/saveEntryFromJava.jks")) {
            keyStore.store(keyStoreOutputStream, keyStorePassword.toCharArray());
        }
    }
}
