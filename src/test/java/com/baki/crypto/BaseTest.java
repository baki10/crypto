package com.baki.crypto;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.io.FileInputStream;
import java.io.InputStream;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.KeyStore;
import java.security.SecureRandom;

public abstract class BaseTest {

    protected SecretKey getSymmetricKey() throws Exception {
        KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
        keyGenerator.init(256, new SecureRandom());
        return keyGenerator.generateKey();
    }

    protected KeyPair getAsymmetricKeyPair() throws Exception {
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        keyPairGenerator.initialize(2048);
        return keyPairGenerator.generateKeyPair();
    }

    protected KeyStore loadKeyStoreFromFile(String keyStoreType, String keyStoreFile, String keyStorePassword) throws Exception {
        KeyStore keyStore = KeyStore.getInstance(keyStoreType); // JKS, PKCS12
        char[] password = keyStorePassword.toCharArray();
        try (InputStream is = new FileInputStream(keyStoreFile)) {
            keyStore.load(is, password);
        }
        return keyStore;
    }

    protected KeyStore loadEmptyKeyStore(String keyStoreType, String keyStorePassword) throws Exception {
        KeyStore keyStore = KeyStore.getInstance(keyStoreType); // JKS, PKCS12
        char[] password = keyStorePassword.toCharArray();
        keyStore.load(null, password);
        return keyStore;
    }
}
