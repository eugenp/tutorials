package org.baeldung.client;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.security.cert.X509Certificate;
import java.util.Timer;
import java.util.TimerTask;

import javax.net.ssl.SSLPeerUnverifiedException;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.params.ClientPNames;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.PoolingClientConnectionManager;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.junit.Test;

public class RawClientLiveTest {

    // tests

    @Test
    public final void whenSecuredRestApiIsConsumed_then200OK() throws ClientProtocolException, IOException {
        final DefaultHttpClient httpClient = new DefaultHttpClient();

        final int timeout = 20; // seconds
        final HttpParams httpParams = httpClient.getParams();
        configureViaRawApi(timeout, httpParams);
        // configureViaHighLevelApi(timeout, httpParams);

        final HttpGet getMethod = new HttpGet("http://localhost:8080/spring-security-rest-basic-auth/api/bars/1");

        final int hardTimeout = 5; // seconds
        final TimerTask task = new TimerTask() {
            @Override
            public void run() {
                if (getMethod != null) {
                    getMethod.abort();
                }
            }
        };
        new Timer(true).schedule(task, hardTimeout * 1000);

        final HttpResponse response = httpClient.execute(getMethod);
        System.out.println("HTTP Status of response: " + response.getStatusLine().getStatusCode());
    }

    @Test(expected = SSLPeerUnverifiedException.class)
    public final void whenHttpsUrlIsConsumed_thenException() throws ClientProtocolException, IOException {
        final DefaultHttpClient httpClient = new DefaultHttpClient();

        final String urlOverHttps = "https://localhost:8443/spring-security-rest-basic-auth/api/bars/1";
        final HttpGet getMethod = new HttpGet(urlOverHttps);
        final HttpResponse response = httpClient.execute(getMethod);
        assertThat(response.getStatusLine().getStatusCode(), equalTo(200));
    }

    @Test
    public final void givenAcceptingAllCertificates_whenHttpsUrlIsConsumed_thenException() throws IOException, GeneralSecurityException {
        final TrustStrategy acceptingTrustStrategy = new TrustStrategy() {
            @Override
            public final boolean isTrusted(final X509Certificate[] certificate, final String authType) {
                return true;
            }
        };
        final SSLSocketFactory sf = new SSLSocketFactory(acceptingTrustStrategy, SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
        final SchemeRegistry registry = new SchemeRegistry();
        registry.register(new Scheme("https", 8443, sf));
        final ClientConnectionManager ccm = new PoolingClientConnectionManager(registry);

        final DefaultHttpClient httpClient = new DefaultHttpClient(ccm);

        final String urlOverHttps = "https://localhost:8443/spring-security-rest-basic-auth/api/bars/1";
        final HttpGet getMethod = new HttpGet(urlOverHttps);
        final HttpResponse response = httpClient.execute(getMethod);
        assertThat(response.getStatusLine().getStatusCode(), equalTo(200));
    }

    // util

    final void configureViaHighLevelApi(final int timeout, final HttpParams httpParams) {
        HttpConnectionParams.setConnectionTimeout(httpParams, timeout * 1000); // http.connection.timeout
        HttpConnectionParams.setSoTimeout(httpParams, timeout * 1000); // http.socket.timeout
        httpParams.setParameter(ClientPNames.CONN_MANAGER_TIMEOUT, new Long(timeout * 1000));
    }

    final void configureViaRawApi(final int timeout, final HttpParams httpParams) {
        httpParams.setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, timeout * 1000);
        httpParams.setParameter(CoreConnectionPNames.SO_TIMEOUT, timeout * 1000);
        httpParams.setParameter(ClientPNames.CONN_MANAGER_TIMEOUT, new Long(timeout * 1000));
    }
}
