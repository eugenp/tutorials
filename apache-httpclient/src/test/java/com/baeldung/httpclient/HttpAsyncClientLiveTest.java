package com.baeldung.httpclient;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import java.io.IOException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import javax.net.ssl.SSLContext;

import org.apache.hc.client5.http.async.methods.SimpleHttpRequest;
import org.apache.hc.client5.http.async.methods.SimpleHttpResponse;
import org.apache.hc.client5.http.async.methods.SimpleRequestBuilder;
import org.apache.hc.client5.http.auth.AuthScope;
import org.apache.hc.client5.http.auth.UsernamePasswordCredentials;
import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.cookie.BasicCookieStore;
import org.apache.hc.client5.http.impl.async.CloseableHttpAsyncClient;
import org.apache.hc.client5.http.impl.async.HttpAsyncClients;
import org.apache.hc.client5.http.impl.auth.BasicCredentialsProvider;
import org.apache.hc.client5.http.impl.cookie.BasicClientCookie;
import org.apache.hc.client5.http.impl.nio.PoolingAsyncClientConnectionManager;
import org.apache.hc.client5.http.impl.nio.PoolingAsyncClientConnectionManagerBuilder;
import org.apache.hc.client5.http.impl.routing.DefaultProxyRoutePlanner;
import org.apache.hc.client5.http.protocol.HttpClientContext;
import org.apache.hc.client5.http.ssl.ClientTlsStrategyBuilder;
import org.apache.hc.core5.http.HttpHost;
import org.apache.hc.core5.http.HttpResponse;
import org.apache.hc.core5.http.nio.ssl.TlsStrategy;
import org.apache.hc.core5.http.protocol.BasicHttpContext;
import org.apache.hc.core5.http.protocol.HttpContext;
import org.apache.hc.core5.reactor.IOReactorConfig;
import org.apache.hc.core5.ssl.SSLContexts;
import org.apache.hc.core5.ssl.TrustStrategy;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.junit.jupiter.api.Test;


class HttpAsyncClientLiveTest extends GetRequestMockServer {
    private static final String HOST_WITH_SSL = "https://mms.nw.ru/";
    private static final String HOST_WITH_PROXY = "http://httpbin.org/";
    private static final String URL_SECURED_BY_BASIC_AUTHENTICATION = "http://browserspy.dk/password-ok.php";// "http://localhost:8080/spring-security-rest-basic-auth/api/foos/1";
    private static final String DEFAULT_USER = "test";// "user1";
    private static final String DEFAULT_PASS = "test";// "user1Pass";

    private static final String HOST_WITH_COOKIE = "http://yuilibrary.com/yui/docs/cookie/cookie-simple-example.html"; // "http://github.com";
    private static final String COOKIE_DOMAIN = ".yuilibrary.com"; // ".github.com";
    private static final String COOKIE_NAME = "example"; // "JSESSIONID";

    // tests

    @Test
    void whenUseHttpAsyncClient_thenCorrect() throws InterruptedException, ExecutionException, IOException {
        final SimpleHttpRequest request = SimpleRequestBuilder.get(HOST_WITH_COOKIE)
            .build();
        final CloseableHttpAsyncClient client = HttpAsyncClients.custom()
            .build();
        client.start();


        final Future<SimpleHttpResponse> future = client.execute(request, null);
        final HttpResponse response = future.get();

        assertThat(response.getCode(), equalTo(200));
        client.close();
    }

    @Test
    void whenUseMultipleHttpAsyncClient_thenCorrect() throws Exception {
        final IOReactorConfig ioReactorConfig = IOReactorConfig
            .custom()
            .build();

        final CloseableHttpAsyncClient client = HttpAsyncClients.custom()
            .setIOReactorConfig(ioReactorConfig)
            .build();

        client.start();
        final String[] toGet = { "http://www.google.com/", "http://www.apache.org/", "http://www.bing.com/" };

        final GetThread[] threads = new GetThread[toGet.length];
        for (int i = 0; i < threads.length; i++) {
            final HttpGet request = new HttpGet(toGet[i]);
            threads[i] = new GetThread(client, request);
        }

        for (final GetThread thread : threads) {
            thread.start();
        }

        for (final GetThread thread : threads) {
            thread.join();
        }

    }

    @Test
    void whenUseProxyWithHttpClient_thenCorrect() throws Exception {
        final HttpHost proxy = new HttpHost("127.0.0.1", GetRequestMockServer.serverPort);
        DefaultProxyRoutePlanner routePlanner = new DefaultProxyRoutePlanner(proxy);
        final CloseableHttpAsyncClient client = HttpAsyncClients.custom()
            .setRoutePlanner(routePlanner)
            .build();
        client.start();

        final SimpleHttpRequest request = new SimpleHttpRequest("GET" ,HOST_WITH_PROXY);
        final Future<SimpleHttpResponse> future = client.execute(request, null);
        final HttpResponse  response = future.get();
        assertThat(response.getCode(), equalTo(200));
        client.close();
    }

    @Test
    void whenUseSSLWithHttpAsyncClient_thenCorrect() throws Exception {
        final TrustStrategy acceptingTrustStrategy = (certificate, authType) -> true;

        final SSLContext sslContext = SSLContexts.custom()
            .loadTrustMaterial(null, acceptingTrustStrategy)
            .build();

        final TlsStrategy tlsStrategy = ClientTlsStrategyBuilder.create()
            .setHostnameVerifier(SSLConnectionSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER)
            .setSslContext(sslContext)
            .build();

        final PoolingAsyncClientConnectionManager cm = PoolingAsyncClientConnectionManagerBuilder.create()
            .setTlsStrategy(tlsStrategy)
            .build();

        final CloseableHttpAsyncClient client = HttpAsyncClients.custom()
            .setConnectionManager(cm)
            .build();

        client.start();

        final SimpleHttpRequest request = new SimpleHttpRequest("GET", HOST_WITH_SSL);
        final Future<SimpleHttpResponse> future = client.execute(request, null);

        final HttpResponse response = future.get();
        assertThat(response.getCode(), equalTo(200));
        client.close();
    }

    @Test
    void whenUseCookiesWithHttpAsyncClient_thenCorrect() throws Exception {
        final BasicCookieStore cookieStore = new BasicCookieStore();
        final BasicClientCookie cookie = new BasicClientCookie(COOKIE_NAME, "1234");
        cookie.setDomain(COOKIE_DOMAIN);
        cookie.setPath("/");
        cookieStore.addCookie(cookie);
        final CloseableHttpAsyncClient client = HttpAsyncClients.custom().build();
        client.start();
        final SimpleHttpRequest request = new SimpleHttpRequest("GET" ,HOST_WITH_COOKIE);

        final HttpContext localContext = new BasicHttpContext();
        localContext.setAttribute(HttpClientContext.COOKIE_STORE, cookieStore);

        final Future<SimpleHttpResponse> future = client.execute(request, localContext, null);

        final HttpResponse response = future.get();
        assertThat(response.getCode(), equalTo(200));
        client.close();
    }

    @Test
    void whenUseAuthenticationWithHttpAsyncClient_thenCorrect() throws Exception {
        final BasicCredentialsProvider credsProvider = new BasicCredentialsProvider();
        final UsernamePasswordCredentials credentials =
            new UsernamePasswordCredentials(DEFAULT_USER, DEFAULT_PASS.toCharArray());
        credsProvider.setCredentials(new AuthScope(URL_SECURED_BY_BASIC_AUTHENTICATION, 80) ,credentials);
        final CloseableHttpAsyncClient client = HttpAsyncClients
            .custom()
            .setDefaultCredentialsProvider(credsProvider).build();

        final SimpleHttpRequest request = new SimpleHttpRequest("GET" ,URL_SECURED_BY_BASIC_AUTHENTICATION);

        client.start();

        final Future<SimpleHttpResponse> future = client.execute(request, null);

        final HttpResponse response = future.get();
        assertThat(response.getCode(), equalTo(200));
        client.close();
    }

    static class GetThread extends Thread {

        private final CloseableHttpAsyncClient client;
        private final HttpContext context;
        private final HttpGet request;

        GetThread(final CloseableHttpAsyncClient client, final HttpGet request) {
            this.client = client;
            context = HttpClientContext.create();
            this.request = request;
        }

        @Override
        public void run() {
            try {
                final Future<SimpleHttpResponse> future = client.execute(SimpleRequestBuilder.copy(request).build(), context, null);
                final HttpResponse response = future.get();
                assertThat(response.getCode(), equalTo(200));
            } catch (final Exception ex) {
                System.out.println(ex.getLocalizedMessage());
            }
        }

    }
}
