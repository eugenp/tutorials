package com.baeldung.cipher;


import com.baeldung.cipher.Encryptor;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;

public class EncryptorTest {
    private String encKeyString;
    private String message;
    private String  certificateString;
    private Encryptor encryptor;

    @Before
    public void init(){
        encKeyString =  "1234567890123456";
        message = "This is a secret message";
        encryptor =  new Encryptor();
        certificateString = "-----BEGIN CERTIFICATE-----\n" +
                "MIICVjCCAb8CAg37MA0GCSqGSIb3DQEBBQUAMIGbMQswCQYDVQQGEwJKUDEOMAwG\n" +
                "A1UECBMFVG9reW8xEDAOBgNVBAcTB0NodW8ta3UxETAPBgNVBAoTCEZyYW5rNERE\n" +
                "MRgwFgYDVQQLEw9XZWJDZXJ0IFN1cHBvcnQxGDAWBgNVBAMTD0ZyYW5rNEREIFdl\n" +
                "YiBDQTEjMCEGCSqGSIb3DQEJARYUc3VwcG9ydEBmcmFuazRkZC5jb20wHhcNMTIw\n" +
                "ODIyMDUyNzIzWhcNMTcwODIxMDUyNzIzWjBKMQswCQYDVQQGEwJKUDEOMAwGA1UE\n" +
                "CAwFVG9reW8xETAPBgNVBAoMCEZyYW5rNEREMRgwFgYDVQQDDA93d3cuZXhhbXBs\n" +
                "ZS5jb20wgZ8wDQYJKoZIhvcNAQEBBQADgY0AMIGJAoGBAMYBBrx5PlP0WNI/ZdzD\n" +
                "+6Pktmurn+F2kQYbtc7XQh8/LTBvCo+P6iZoLEmUA9e7EXLRxgU1CVqeAi7QcAn9\n" +
                "MwBlc8ksFJHB0rtf9pmf8Oza9E0Bynlq/4/Kb1x+d+AyhL7oK9tQwB24uHOueHi1\n" +
                "C/iVv8CSWKiYe6hzN1txYe8rAgMBAAEwDQYJKoZIhvcNAQEFBQADgYEAASPdjigJ\n" +
                "kXCqKWpnZ/Oc75EUcMi6HztaW8abUMlYXPIgkV2F7YanHOB7K4f7OOLjiz8DTPFf\n" +
                "jC9UeuErhaA/zzWi8ewMTFZW/WshOrm3fNvcMrMLKtH534JKvcdMg6qIdjTFINIr\n" +
                "evnAhf0cwULaebn+lMs8Pdl7y37+sfluVok=\n" +
                "-----END CERTIFICATE-----";
    }

    @Test
    public void givenEncryptionKey_thenMessageCanBeEncrypted() throws Exception {
        byte[] encryptedMessage = encryptor.encryptMessage(message.getBytes(),encKeyString.getBytes());
        
        Assert.assertNotNull(encryptedMessage);
        Assert.assertEquals(encryptedMessage.length  % 32, 0);
    }

    @Test
    public void givenCertificateWithPublicKey_thenMessageCanBeEncrypted() throws Exception {
        CertificateFactory factory = CertificateFactory.getInstance("X.509");
        InputStream is = new ByteArrayInputStream(certificateString.getBytes());
        X509Certificate certificate = (X509Certificate) factory.generateCertificate(is);

        byte[] encryptedMessage = encryptor.encryptMessage(message.getBytes(),certificate);
        
        Assert.assertNotNull(encryptedMessage);
        Assert.assertEquals(encryptedMessage.length  % 128, 0);
    }

    @Test
    public void givenEncryptionKey_whenMessageEncrypted_thenDecryptMessage() throws Exception{
        byte[] encryptedMessageBytes = encryptor.encryptMessage(message.getBytes(),encKeyString.getBytes());
        
        byte[] clearMessageBytes = encryptor.decryptMessage(encryptedMessageBytes, encKeyString.getBytes());
        
        Assert.assertEquals(message, new String(clearMessageBytes));
    }
}
