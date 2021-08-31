package com.baeldung.httpclient.cookies;

import org.apache.http.client.CookieStore;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.cookie.ClientCookie;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.cookie.BasicClientCookie;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

import static org.junit.Assert.assertEquals;


public class HttpClientGettingCookieValueUnitTest {
    private static Logger log = LoggerFactory.getLogger(HttpClientGettingCookieValueUnitTest.class);

    private static final String SAMPLE_URL = "http://www.baeldung.com/";

    @Test
    public final void whenSettingCustomCookieOnTheRequest_thenGettingTheSameCookieFromTheResponse() throws IOException {
        HttpClientContext context = HttpClientContext.create();
        context.setAttribute(HttpClientContext.COOKIE_STORE, createCustomCookieStore());

        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            try (CloseableHttpResponse response = httpClient.execute(new HttpGet(SAMPLE_URL), context)) {
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
    }

    private BasicCookieStore createCustomCookieStore() {
        BasicCookieStore cookieStore = new BasicCookieStore();
        BasicClientCookie cookie = new BasicClientCookie("custom_cookie", "test_value");
        cookie.setDomain("baeldung.com");
        cookie.setAttribute(ClientCookie.DOMAIN_ATTR, "true");
        cookie.setPath("/");
        cookieStore.addCookie(cookie);
        return cookieStore;
    }
}
