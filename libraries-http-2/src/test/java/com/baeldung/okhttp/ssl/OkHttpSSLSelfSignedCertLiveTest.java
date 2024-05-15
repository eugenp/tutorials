package com.baeldung.okhttp.ssl;

import static com.baeldung.okhttp.Consts.SSL_APPLICATION_PORT;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.security.cert.CertificateException;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLHandshakeException;
import javax.net.ssl.SSLPeerUnverifiedException;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.junit.Before;
import org.junit.Test;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Execute <code>spring-security-web-boot-2</code> module before running this live test
 * @see com.baeldung.ssl.HttpsEnabledApplication
 */
public class OkHttpSSLSelfSignedCertLiveTest {

    private final String HTTPS_WELCOME_URL = "https://localhost:" + SSL_APPLICATION_PORT + "/welcome";

    private OkHttpClient.Builder builder;

    @Before
    public void init() {
        builder = new OkHttpClient.Builder();
    }

    @Test(expected = SSLHandshakeException.class)
    public void whenHTTPSSelfSignedCertGET_thenException() throws IOException {
        builder.build()
            .newCall(new Request.Builder().url(HTTPS_WELCOME_URL)
                .build())
            .execute();
    }

    @Test(expected = SSLPeerUnverifiedException.class)
    public void givenTrustAllCerts_whenHTTPSSelfSignedCertGET_thenException() throws GeneralSecurityException, IOException {
        final TrustManager TRUST_ALL_CERTS = new X509TrustManager() {
            @Override
            public void checkClientTrusted(java.security.cert.X509Certificate[] chain, String authType) throws CertificateException {
            }

            @Override
            public void checkServerTrusted(java.security.cert.X509Certificate[] chain, String authType) throws CertificateException {
            }

            @Override
            public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                return new java.security.cert.X509Certificate[] {};
            }
        };
        final SSLContext sslContext = SSLContext.getInstance("SSL");
        sslContext.init(null, new TrustManager[] { TRUST_ALL_CERTS }, new java.security.SecureRandom());
        builder.sslSocketFactory(sslContext.getSocketFactory(), (X509TrustManager) TRUST_ALL_CERTS);
        builder.build()
            .newCall(new Request.Builder().url(HTTPS_WELCOME_URL)
                .build())
            .execute();
    }

    @Test
    public void givenTrustAllCertsSkipHostnameVerification_whenHTTPSSelfSignedCertGET_then200OK() throws GeneralSecurityException, IOException {
        final TrustManager TRUST_ALL_CERTS = new X509TrustManager() {
            @Override
            public void checkClientTrusted(java.security.cert.X509Certificate[] chain, String authType) throws CertificateException {
            }

            @Override
            public void checkServerTrusted(java.security.cert.X509Certificate[] chain, String authType) throws CertificateException {
            }

            @Override
            public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                return new java.security.cert.X509Certificate[] {};
            }
        };
        final SSLContext sslContext = SSLContext.getInstance("SSL");
        sslContext.init(null, new TrustManager[] { TRUST_ALL_CERTS }, new java.security.SecureRandom());
        builder.sslSocketFactory(sslContext.getSocketFactory(), (X509TrustManager) TRUST_ALL_CERTS);
        builder.hostnameVerifier(new HostnameVerifier() {
            @Override
            public boolean verify(String hostname, SSLSession session) {
                return true;
            }
        });
        Response response = builder.build()
            .newCall(new Request.Builder().url(HTTPS_WELCOME_URL)
                .build())
            .execute();
        assertEquals(200, response.code());
        assertNotNull(response.body());
        assertEquals("<h1>Welcome to Secured Site</h1>", response.body()
            .string());
    }
}
