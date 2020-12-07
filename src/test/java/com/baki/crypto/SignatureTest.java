package com.baki.crypto;

import org.junit.Test;

import java.nio.charset.StandardCharsets;
import java.security.*;

import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

/**
 * A digital signature is a message digest encrypted with a private key of a private/public key pair.
 * Anyone in possession of the public key can verify the digital signature.
 */
public class SignatureTest extends BaseTest {

    @Test
    public void initSignature() throws Exception {
        byte[] data = "abcdefghijklmnopqrstuvxyz".getBytes(StandardCharsets.UTF_8);

        KeyPair keyPair = getAsymmetricKeyPair();

        // sign
        Signature signature1 = Signature.getInstance("SHA256WithRSA"); // the name of the digital signature algorithm
        signature1.initSign(keyPair.getPrivate(), new SecureRandom());
        signature1.update(data);
        byte[] digitalSignature = signature1.sign();

        // verify
        Signature signature2 = Signature.getInstance("SHA256WithRSA");
        signature2.initVerify(keyPair.getPublic());
        signature2.update(data);
        boolean verified = signature2.verify(digitalSignature);

        assertTrue(verified);
    }
}
