package com.baeldung.hexagonalarchitecture2.core;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Formatter;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

public class HmacGenerator {
    private static final String HMAC_SHA512 = "HmacSHA256";

    public static String calculateHMAC(String data, String key) throws NoSuchAlgorithmException, InvalidKeyException {
        Mac mac = Mac.getInstance(HMAC_SHA512);
        mac.init(new SecretKeySpec(key.getBytes(), HMAC_SHA512));
        return toHexString(mac.doFinal(data.getBytes()));
    }

    private static String toHexString(byte[] bytes) {
        Formatter formatter = new Formatter();
        for (byte b : bytes) {
            formatter.format("%02x", b);
        }
        return formatter.toString();
    }
}
