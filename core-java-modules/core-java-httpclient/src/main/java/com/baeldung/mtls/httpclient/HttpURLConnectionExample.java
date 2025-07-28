package com.baeldung.mtls.httpclient;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.charset.Charset;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.security.spec.InvalidKeySpecException;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;

public class HttpURLConnectionExample {

    public static void main(String[] args)
        throws IOException, CertificateException, NoSuchAlgorithmException, KeyStoreException, UnrecoverableKeyException, InvalidKeySpecException,
               KeyManagementException {
        SSLContext sslContext = SslContextBuilder.buildSslContext();

        HostnameVerifier allHostsValid = (hostname, session) -> true;
        HttpsURLConnection httpsURLConnection = (HttpsURLConnection) new URL("https://127.0.0.1/ping").openConnection();
        httpsURLConnection.setSSLSocketFactory(sslContext.getSocketFactory());
        httpsURLConnection.setHostnameVerifier(allHostsValid);

        InputStream inputStream = httpsURLConnection.getInputStream();
        String response = new String(inputStream.readAllBytes(), Charset.defaultCharset());
    }

}
