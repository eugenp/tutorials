package com.baeldung.httpclient;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import java.io.IOException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import javax.net.ssl.SSLContext;

import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.cookie.BasicClientCookie;
import org.apache.http.impl.nio.client.CloseableHttpAsyncClient;
import org.apache.http.impl.nio.client.HttpAsyncClients;
import org.apache.http.impl.nio.conn.PoolingNHttpClientConnectionManager;
import org.apache.http.impl.nio.reactor.DefaultConnectingIOReactor;
import org.apache.http.nio.reactor.ConnectingIOReactor;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import org.apache.http.ssl.SSLContexts;
import org.junit.jupiter.api.Test;

import com.baeldung.GetRequestMockServer;

class HttpAsyncClientV4LiveTest extends GetRequestMockServer {

    private static final String HOST = "http://www.google.com";
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
        final CloseableHttpAsyncClient client = HttpAsyncClients.createDefault();
        client.start();
        final HttpGet request = new HttpGet(HOST);

        final Future<HttpResponse> future = client.execute(request, null);
        final HttpResponse response = future.get();

        assertThat(response.getStatusLine().getStatusCode(), equalTo(200));
        client.close();
    }

    @Test
    void whenUseMultipleHttpAsyncClient_thenCorrect() throws Exception {
        final ConnectingIOReactor ioReactor = new DefaultConnectingIOReactor();
        final PoolingNHttpClientConnectionManager cm = new PoolingNHttpClientConnectionManager(ioReactor);
        final CloseableHttpAsyncClient client = HttpAsyncClients.custom().setConnectionManager(cm).build();
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
        final CloseableHttpAsyncClient client = HttpAsyncClients.createDefault();
        client.start();
        final HttpHost proxy = new HttpHost("127.0.0.1", GetRequestMockServer.serverPort);
        final RequestConfig config = RequestConfig.custom().setProxy(proxy).build();
        final HttpGet request = new HttpGet(HOST_WITH_PROXY);
        request.setConfig(config);
        final Future<HttpResponse> future = client.execute(request, null);
        final HttpResponse response = future.get();
        assertThat(response.getStatusLine().getStatusCode(), equalTo(200));
        client.close();
    }

    @Test
    void whenUseSSLWithHttpAsyncClient_thenCorrect() throws Exception {
        final TrustStrategy acceptingTrustStrategy = (certificate, authType) -> true;
        final SSLContext sslContext = SSLContexts.custom().loadTrustMaterial(null, acceptingTrustStrategy).build();

        final CloseableHttpAsyncClient client = HttpAsyncClients.custom().setSSLHostnameVerifier(NoopHostnameVerifier.INSTANCE).setSSLContext(sslContext).build();

        client.start();
        final HttpGet request = new HttpGet(HOST_WITH_SSL);
        final Future<HttpResponse> future = client.execute(request, null);
        final HttpResponse response = future.get();
        assertThat(response.getStatusLine().getStatusCode(), equalTo(200));
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
        final HttpGet request = new HttpGet(HOST_WITH_COOKIE);

        final HttpContext localContext = new BasicHttpContext();
        localContext.setAttribute(HttpClientContext.COOKIE_STORE, cookieStore);

        final Future<HttpResponse> future = client.execute(request, localContext, null);
        final HttpResponse response = future.get();
        assertThat(response.getStatusLine().getStatusCode(), equalTo(200));
        client.close();
    }

    @Test
    void whenUseAuthenticationWithHttpAsyncClient_thenCorrect() throws Exception {
        final CredentialsProvider provider = new BasicCredentialsProvider();
        final UsernamePasswordCredentials creds = new UsernamePasswordCredentials(DEFAULT_USER, DEFAULT_PASS);
        provider.setCredentials(AuthScope.ANY, creds);
        final CloseableHttpAsyncClient client = HttpAsyncClients.custom().setDefaultCredentialsProvider(provider).build();

        final HttpGet request = new HttpGet(URL_SECURED_BY_BASIC_AUTHENTICATION);
        client.start();
        final Future<HttpResponse> future = client.execute(request, null);
        final HttpResponse response = future.get();
        assertThat(response.getStatusLine().getStatusCode(), equalTo(200));
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
                final Future<HttpResponse> future = client.execute(request, context, null);
                final HttpResponse response = future.get();
                assertThat(response.getStatusLine().getStatusCode(), equalTo(200));
            } catch (final Exception ex) {
                System.out.println(ex.getLocalizedMessage());
            }
        }

    }
}