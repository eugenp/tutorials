package com.baeldung.hmac;

import org.junit.Test;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import static org.junit.Assert.assertEquals;

public class HMACUtilUnitTest {

    private static String hmacSHA256Value = "5b50d80c7dc7ae8bb1b1433cc0b99ecd2ac8397a555c6f75cb8a619ae35a0c35";
    private static String hmacMD5Value = "621dc816b3bf670212e0c261dc9bcdb6";
    private static String hmacSHA512Value = "b313a21908df55c9e322e3c65a4b0b7561ab1594ca806b3affbc0d769a1290c1922aa6622587bea3c0c4d871470a6d06f54dbd20dbda84250e2741eb01f08e33";

    //given
    String hmacSHA256Algorithm = "HmacSHA256";
    String hmacSHA512Algorithm = "HmacSHA512";
    String hmacMD5Algorithm = "HmacMD5";
    String data = "baeldung";
    String key = "123456";

    @Test
    public void givenDataAndKeyAndAlgorithm_whenHmacWithJava_thenSuccess()
        throws NoSuchAlgorithmException, InvalidKeyException {
        //when
        String result = HMACUtil.hmacWithJava(hmacSHA256Algorithm, data, key);

        //then
        assertEquals(hmacSHA256Value, result);
    }

    @Test
    public void givenDataAndKeyAndAlgorithm_whenHmacWithApacheCommons_thenSuccess() {
        //when
        String result = HMACUtil.hmacWithApacheCommons(hmacMD5Algorithm, data, key);

        //then
        assertEquals(hmacMD5Value, result);
    }

    @Test
    public void givenDataAndKeyAndAlgorithm_whenHmacWithBouncyCastle_thenSuccess() {
        //when
        String result = HMACUtil.hmacWithBouncyCastle(hmacSHA512Algorithm, data, key);

        //then
        assertEquals(hmacSHA512Value, result);
    }
}
