package com.baki.crypto;

import org.junit.Test;

import java.io.FileInputStream;
import java.io.InputStream;
import java.security.*;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;

import static org.junit.Assert.*;

public class CertificateTest extends BaseTest {

    @Test
    public void getCertificateFromKeyStore() throws Exception {
        KeyStore keyStore = loadKeyStoreFromFile("PKCS12", "keys/testkey.jks", "123456");
        KeyStore.PrivateKeyEntry privateKeyEntry = (KeyStore.PrivateKeyEntry)
                keyStore.getEntry("testkey", new KeyStore.PasswordProtection("123456".toCharArray()));

        Certificate certificate = privateKeyEntry.getCertificate();

        assertNotNull(certificate);
    }

    @Test
    public void getCertificateFromCertFile() throws Exception {
        CertificateFactory certificateFactory = CertificateFactory.getInstance("X.509");

        InputStream certificateIS = new FileInputStream("keys/exported_cert.cert");

        Certificate certificate = certificateFactory.generateCertificate(certificateIS);

        assertNotNull(certificate);
    }

    @Test
    public void encodeCertificate() throws Exception {
        KeyStore keyStore = loadKeyStoreFromFile("PKCS12", "keys/testkey.jks", "123456");
        KeyStore.PrivateKeyEntry privateKeyEntry = (KeyStore.PrivateKeyEntry)
                keyStore.getEntry("testkey", new KeyStore.PasswordProtection("123456".toCharArray()));
        Certificate certificate = privateKeyEntry.getCertificate();

        // The Java Certificate getEncoded() method returns an encoded version of the Certificate as a byte array.
        // For instance, if the Certificate is an X509Certificate the returned byte array
        // will contain an X.590 (ASN.1 DER) encoded version of the Certificate instance
        byte[] certificateEncoded = certificate.getEncoded();
        System.out.println(new String(certificateEncoded));

        assertNotNull(certificateEncoded);
    }

    @Test
    public void certificatePublicKey() throws Exception {
        KeyStore keyStore = loadKeyStoreFromFile("PKCS12", "keys/testkey.jks", "123456");
        KeyStore.PrivateKeyEntry privateKeyEntry = (KeyStore.PrivateKeyEntry)
                keyStore.getEntry("testkey", new KeyStore.PasswordProtection("123456".toCharArray()));
        Certificate certificate = privateKeyEntry.getCertificate();

        PublicKey publicKey = certificate.getPublicKey();

        assertNotNull(publicKey);
    }

    @Test
    public void certificateGetType() throws Exception {
        KeyStore keyStore = loadKeyStoreFromFile("PKCS12", "keys/testkey.jks", "123456");
        KeyStore.PrivateKeyEntry privateKeyEntry = (KeyStore.PrivateKeyEntry)
                keyStore.getEntry("testkey", new KeyStore.PasswordProtection("123456".toCharArray()));
        Certificate certificate = privateKeyEntry.getCertificate();

        String certificateType = certificate.getType();

        assertNotNull(certificateType);
        assertEquals(certificateType, "X.509");
    }

    @Test
    public void verifyCertificate() throws Exception {
        KeyStore keyStore = loadKeyStoreFromFile("PKCS12", "keys/testkey.jks", "123456");
        KeyStore.PrivateKeyEntry privateKeyEntry = (KeyStore.PrivateKeyEntry)
                keyStore.getEntry("testkey", new KeyStore.PasswordProtection("123456".toCharArray()));
        Certificate certificate = privateKeyEntry.getCertificate();

        // get expected public key from somewhere else (not Certificate instance !!)
        PublicKey expectedPublicKey = certificate.getPublicKey();

        boolean passed = false;
        try {
            certificate.verify(expectedPublicKey);
            passed = true;
        } catch (InvalidKeyException e) {
            // certificate was not signed with given public key
        } catch (NoSuchAlgorithmException |
                NoSuchProviderException |
                SignatureException |
                CertificateException e) {
            // something else went wrong
        }

        assertTrue(passed);
    }

}
