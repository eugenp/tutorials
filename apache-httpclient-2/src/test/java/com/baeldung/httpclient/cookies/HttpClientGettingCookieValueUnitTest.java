package com.baeldung.httpclient.cookies;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.cookie.BasicCookieStore;
import org.apache.hc.client5.http.cookie.Cookie;
import org.apache.hc.client5.http.cookie.CookieStore;

import org.apache.hc.client5.http.impl.classic.BasicHttpClientResponseHandler;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClientBuilder;

import org.apache.hc.client5.http.impl.cookie.BasicClientCookie;
import org.apache.hc.client5.http.protocol.HttpClientContext;

import org.apache.http.cookie.ClientCookie;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

class HttpClientGettingCookieValueUnitTest {
    private static Logger log = LoggerFactory.getLogger(HttpClientGettingCookieValueUnitTest.class);

    private static final String SAMPLE_URL = "http://www.github.com/";

    @Test
    void whenSettingCustomCookieOnTheRequest_thenGettingTheSameCookieFromTheResponse() throws IOException {

        HttpClientContext context = HttpClientContext.create();
        context.setAttribute(HttpClientContext.COOKIE_STORE, createCustomCookieStore());

        final HttpGet request = new HttpGet(SAMPLE_URL);

        try (CloseableHttpClient client = HttpClientBuilder.create()
            .build()) {
            client.execute(request, context, new BasicHttpClientResponseHandler());
            CookieStore cookieStore = context.getCookieStore();
            Cookie customCookie = cookieStore.getCookies()
                .stream()
                .peek(cookie -> log.info("cookie name:{}", cookie.getName()))
                .filter(cookie -> "custom_cookie".equals(cookie.getName()))
                .findFirst()
                .orElseThrow(IllegalStateException::new);

            assertEquals("test_value", customCookie.getValue());
        }
    }

    private BasicCookieStore createCustomCookieStore() {
        BasicCookieStore cookieStore = new BasicCookieStore();
        BasicClientCookie cookie = new BasicClientCookie("custom_cookie", "test_value");
        cookie.setDomain("github.com");
        cookie.setAttribute(ClientCookie.DOMAIN_ATTR, "github.com");
        cookie.setPath("/");
        cookieStore.addCookie(cookie);
        return cookieStore;
    }
}
