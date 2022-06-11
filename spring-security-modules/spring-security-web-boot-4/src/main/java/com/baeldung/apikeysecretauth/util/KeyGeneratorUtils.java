package com.baeldung.apikeysecretauth.util;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Hex;
import org.springframework.stereotype.Component;

@Component
public class KeyGeneratorUtils {

    public String generateSecret(String apiKey, String password) throws NoSuchAlgorithmException, InvalidKeyException {
        SecretKeySpec spec = new SecretKeySpec(password.getBytes(), "HmacSHA256");
        Mac mac = Mac.getInstance("HmacSHA256");
        mac.init(spec);
        return Hex.encodeHexString(mac.doFinal(apiKey.getBytes()));
    }
}
