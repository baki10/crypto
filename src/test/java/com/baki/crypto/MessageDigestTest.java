package com.baki.crypto;

import org.junit.Test;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;

import static org.junit.Assert.*;

public class MessageDigestTest extends BaseTest {

    @Test
    public void createMessageDigest() throws Exception {
        MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
        // Algorithm Names:
        // MD2
        // MD5
        // SHA-1
        // SHA-256
        // SHA-384
        // SHA-512

        assertNotNull(messageDigest);
    }

    @Test
    public void calcHash() throws Exception {
        MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
        byte[] data = "0123456789".getBytes(StandardCharsets.UTF_8);
        byte[] hash = messageDigest.digest(data);
        System.out.println(new String(hash));

        assertNotEquals(new String(hash), new String(data));
    }
}
