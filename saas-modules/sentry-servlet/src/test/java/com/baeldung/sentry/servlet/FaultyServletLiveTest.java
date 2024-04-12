package com.baeldung.sentry.servlet;

import static org.assertj.core.api.Assertions.assertThat;

import java.net.HttpURLConnection;
import java.net.URL;

import org.junit.jupiter.api.Test;

class FaultyServletLiveTest {
    

    @Test
    void testGivenFaultyRequestWithNoQueryString_thenSuccess() throws Exception {
        
        //int port = getServerPort();
        URL u = new URL("http://localhost:8080/sentry-servlet/fault");
        HttpURLConnection conn = (HttpURLConnection)u.openConnection();
        int rc = conn.getResponseCode();
        assertThat(rc)
          .isEqualTo(200);
    }

    @Test
    void testGivenFaultyRequestWithFaultString_thenFail() throws Exception {
        
        //int port = getServerPort();
        URL u = new URL("http://localhost:8080/sentry-servlet/fault?fault=true");
        HttpURLConnection conn = (HttpURLConnection)u.openConnection();
        int rc = conn.getResponseCode();
        assertThat(rc)
          .isEqualTo(500);
    }

    @Test
    void testGivenFaultyRequestWithExceptionString_thenFail() throws Exception {
        
        //int port = getServerPort();
        URL u = new URL("http://localhost:8080/sentry-servlet/fault?exception=true");
        HttpURLConnection conn = (HttpURLConnection)u.openConnection();
        int rc = conn.getResponseCode();
        assertThat(rc)
          .isEqualTo(500);
    }
    
}
