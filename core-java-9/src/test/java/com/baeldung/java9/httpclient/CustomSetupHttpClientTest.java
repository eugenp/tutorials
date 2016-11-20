package com.baeldung.java9.httpclient;

import static java.net.HttpURLConnection.HTTP_OK;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.net.CookieManager;
import java.net.CookiePolicy;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.security.NoSuchAlgorithmException;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLParameters;

import org.junit.Test;

public class CustomSetupHttpClientTest {

    @Test
    public void configureHttpClient() throws NoSuchAlgorithmException, URISyntaxException, IOException, InterruptedException {
        CookieManager cManager = new CookieManager();
        cManager.setCookiePolicy(CookiePolicy.ACCEPT_ORIGINAL_SERVER);

        SSLParameters sslParam = new SSLParameters(new String[] { "TLS_ECDHE_ECDSA_WITH_AES_128_GCM_SHA256" }, new String[] { "TLSv1.2" });

        HttpClient.Builder hcBuilder = HttpClient.create();
        hcBuilder.cookieManager(cManager).sslContext(SSLContext.getDefault()).sslParameters(sslParam);
        HttpClient httpClient = hcBuilder.build();
        HttpRequest.Builder reqBuilder = httpClient.request(new URI("https://www.facebook.com"));

        HttpRequest request = reqBuilder.followRedirects(HttpClient.Redirect.ALWAYS).GET();
        HttpResponse response = request.response();
        int statusCode = response.statusCode();
        assertTrue("HTTP return code", statusCode == HTTP_OK);
    }

    SSLParameters getDefaultSSLParameters() throws NoSuchAlgorithmException {
        SSLParameters sslP1 = SSLContext.getDefault().getSupportedSSLParameters();
        String[] proto = sslP1.getApplicationProtocols();
        String[] cifers = sslP1.getCipherSuites();
        System.out.println(printStringArr(proto));
        System.out.println(printStringArr(cifers));
        return sslP1;
    }

    String printStringArr(String... args) {
        if (args == null) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        for (String s : args) {
            sb.append(s);
            sb.append("\n");
        }
        return sb.toString();
    }

}
