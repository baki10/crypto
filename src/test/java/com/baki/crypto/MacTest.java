package com.baki.crypto;

import org.junit.Test;

import javax.crypto.Mac;
import java.nio.charset.StandardCharsets;
import java.security.Key;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class MacTest extends BaseTest {

    @Test
    public void initMac() throws Exception {
        Mac mac = Mac.getInstance("HmacSHA256");
        Key key = getSymmetricKey();
        mac.init(key);
    }

    @Test
    public void calcHash() throws Exception {
        Mac mac = Mac.getInstance("HmacSHA256");
        Key key = getSymmetricKey();
        mac.init(key);

        byte[] data = "0123456789".getBytes(StandardCharsets.UTF_8);
        byte[] hash1 = mac.doFinal(data);
        byte[] hash2 = mac.doFinal(data);

        assertNotEquals(new String(hash1), new String(data));
        assertEquals(new String(hash1), new String(hash2));
    }
}
