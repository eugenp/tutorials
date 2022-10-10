package com.baeldung.spring.mail;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Value;

import com.baeldung.SpringContextTest;

public class GeneratePasswordTest extends SpringContextTest {

    @Value("${jasypt.encryptor.password}")
    private String secretKey;

    @Test
    public void generatePassword() {
        String password = "test";
        String encryptPassword = JasyptUtil.encrypt(secretKey, password);
        // ENC() shall be added when used in application.properties.
        // Like this ENC(encryptPassword).
        String decryptPassword = JasyptUtil.decrypt(secretKey, encryptPassword);
        System.out.println("encryptPassword = " + encryptPassword);
        Assert.assertEquals(password, decryptPassword);
    }

}
