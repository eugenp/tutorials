package org.baeldung.httpclient.sec;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

import java.io.IOException;
import java.io.InputStream;

import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.cookie.BasicClientCookie;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class HttpClientCookieLiveTest {

    private CloseableHttpClient instance;

    private CloseableHttpResponse response;

    @Before
    public final void before() {
        instance = HttpClientBuilder.create().build();
    }

    @After
    public final void after() throws IllegalStateException, IOException {
        if (response == null) {
            return;
        }

        try {
            final HttpEntity entity = response.getEntity();
            if (entity != null) {
                final InputStream instream = entity.getContent();
                instream.close();
            }
        } finally {
            response.close();
        }
    }

    // tests

    @Test
    public final void whenSettingCookiesOnARequest_thenCorrect() throws ClientProtocolException, IOException {
        instance = HttpClientBuilder.create().build();
        final HttpGet request = new HttpGet("http://www.github.com");
        request.setHeader("Cookie", "JSESSIONID=1234");

        response = instance.execute(request);

        assertThat(response.getStatusLine().getStatusCode(), equalTo(200));
    }

    @Test
    public final void givenUsingDeprecatedApi_whenSettingCookiesOnTheHttpClient_thenCorrect() throws ClientProtocolException, IOException {
        final BasicCookieStore cookieStore = new BasicCookieStore();
        final BasicClientCookie cookie = new BasicClientCookie("JSESSIONID", "1234");
        cookie.setDomain(".github.com");
        cookie.setPath("/");
        cookieStore.addCookie(cookie);
        final HttpClient client = HttpClientBuilder.create().setDefaultCookieStore(cookieStore).build();

        final HttpGet request = new HttpGet("http://www.github.com");

        response = (CloseableHttpResponse) client.execute(request);

        assertThat(response.getStatusLine().getStatusCode(), equalTo(200));
    }

    @Test
    public final void whenSettingCookiesOnTheHttpClient_thenCookieSentCorrectly() throws ClientProtocolException, IOException {
        final BasicCookieStore cookieStore = new BasicCookieStore();
        final BasicClientCookie cookie = new BasicClientCookie("JSESSIONID", "1234");
        cookie.setDomain(".github.com");
        cookie.setPath("/");
        cookieStore.addCookie(cookie);
        instance = HttpClientBuilder.create().setDefaultCookieStore(cookieStore).build();

        final HttpGet request = new HttpGet("http://www.github.com");

        response = instance.execute(request);

        assertThat(response.getStatusLine().getStatusCode(), equalTo(200));
    }

    @Test
    public final void whenSettingCookiesOnTheRequest_thenCookieSentCorrectly() throws ClientProtocolException, IOException {
        final BasicCookieStore cookieStore = new BasicCookieStore();
        final BasicClientCookie cookie = new BasicClientCookie("JSESSIONID", "1234");
        cookie.setDomain(".github.com");
        cookie.setPath("/");
        cookieStore.addCookie(cookie);
        instance = HttpClientBuilder.create().build();

        final HttpGet request = new HttpGet("http://www.github.com");

        final HttpContext localContext = new BasicHttpContext();
        localContext.setAttribute(HttpClientContext.COOKIE_STORE, cookieStore);
        // localContext.setAttribute(ClientContext.COOKIE_STORE, cookieStore); // before 4.3
        response = instance.execute(request, localContext);

        assertThat(response.getStatusLine().getStatusCode(), equalTo(200));
    }

}
