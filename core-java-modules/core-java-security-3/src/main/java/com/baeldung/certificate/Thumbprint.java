package com.baeldung.certificate;

import org.apache.commons.codec.digest.DigestUtils;

import javax.xml.bind.DatatypeConverter;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateEncodingException;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;

public class Thumbprint {

    public static void main(String[] args) throws CertificateException, IOException, NoSuchAlgorithmException {
        X509Certificate certObject = getCertObject("baeldung.pem");
        System.out.println(getThumbprint(certObject));
    }

    public static X509Certificate getCertObject(String filePath) throws IOException, CertificateException {
        try (FileInputStream is = new FileInputStream(filePath)) {
            CertificateFactory certificateFactory = CertificateFactory.getInstance("X.509");
            return (X509Certificate) certificateFactory.generateCertificate(is);
        }
    }

    private static String getThumbprint(X509Certificate cert) throws NoSuchAlgorithmException, CertificateEncodingException {
        MessageDigest md = MessageDigest.getInstance("SHA-1");
        md.update(cert.getEncoded());
        return DatatypeConverter.printHexBinary(md.digest()).toLowerCase();
    }

    private static String getThumbprintWithApache(X509Certificate cert) throws CertificateEncodingException {
        return DigestUtils.sha1Hex(cert.getEncoded());
    }
}
