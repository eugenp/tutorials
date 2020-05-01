package com.baeldung.httpclient.conn;

import org.apache.http.HeaderElement;
import org.apache.http.HeaderElementIterator;
import org.apache.http.HttpClientConnection;
import org.apache.http.HttpException;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.config.SocketConfig;
import org.apache.http.conn.ConnectionKeepAliveStrategy;
import org.apache.http.conn.ConnectionRequest;
import org.apache.http.conn.routing.HttpRoute;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.BasicHttpClientConnectionManager;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicHeaderElementIterator;
import org.apache.http.protocol.HTTP;
import org.apache.http.protocol.HttpContext;
import org.apache.http.protocol.HttpCoreContext;
import org.apache.http.protocol.HttpRequestExecutor;
import org.apache.http.util.EntityUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.io.IOException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertTrue;

public class HttpClientConnectionManagementLiveTest {
    private static final String SERVER1 = "http://www.petrikainulainen.net/";
    private static final String SERVER7 = "http://www.baeldung.com/";

    private BasicHttpClientConnectionManager basicConnManager;
    private PoolingHttpClientConnectionManager poolingConnManager;

    private HttpClientContext context;
    private HttpRoute route;
    private HttpClientConnection conn1;
    private HttpClientConnection conn;
    private HttpClientConnection conn2;

    private CloseableHttpResponse response;
    private HttpGet get1;
    private HttpGet get2;

    private CloseableHttpClient client;

    @Before
    public final void before() {
        get1 = new HttpGet(SERVER1);
        get2 = new HttpGet(SERVER7);
        route = new HttpRoute(new HttpHost("www.baeldung.com", 80));
    }

    @After
    public final void after() throws IllegalStateException, IOException {
        if (conn != null) {
            conn.close();
        }
        if (conn1 != null) {
            conn1.close();
        }
        if (conn2 != null) {
            conn2.close();
        }
        if (poolingConnManager != null) {
            poolingConnManager.shutdown();
        }
        if (basicConnManager != null) {
            basicConnManager.shutdown();
        }
        if (client != null) {
            client.close();
        }
        if (response != null) {
            response.close();
        }
    }

    // 2

    @Test
    // 2.1 IN ARTCLE
    public final void whenLowLevelConnectionIsEstablished_thenNoExceptions() throws IOException, HttpException, InterruptedException, ExecutionException {
        basicConnManager = new BasicHttpClientConnectionManager();
        final ConnectionRequest connRequest = basicConnManager.requestConnection(route, null);
        assertTrue(connRequest.get(1000, TimeUnit.SECONDS) != null);
    }

    @Test
    // @Ignore
    // 2.2 IN ARTICLE
    public final void whenOpeningLowLevelConnectionWithSocketTimeout_thenNoExceptions() throws InterruptedException, ExecutionException, IOException, HttpException {
        basicConnManager = new BasicHttpClientConnectionManager();
        context = HttpClientContext.create();
        final ConnectionRequest connRequest = basicConnManager.requestConnection(route, null);
        conn = connRequest.get(1000, TimeUnit.SECONDS);
        if (!conn.isOpen()) {
            basicConnManager.connect(conn, route, 1000, context);
        }
        conn.setSocketTimeout(30000);

        assertTrue(conn.getSocketTimeout() == 30000);
        assertTrue(conn.isOpen());
    }

    // 3

    @Test
    // Example 3.1.
    public final void whenPollingConnectionManagerIsConfiguredOnHttpClient_thenNoExceptions() throws InterruptedException, ClientProtocolException, IOException {
        poolingConnManager = new PoolingHttpClientConnectionManager();
        client = HttpClients.custom().setConnectionManager(poolingConnManager).build();
        client.execute(get1);

        assertTrue(poolingConnManager.getTotalStats().getLeased() == 1);
    }

    @Test
    // @Ignore
    // Example 3.2. TESTER VERSION
    /*tester*/ public final void whenTwoConnectionsForTwoRequests_thenTwoConnectionsAreLeased() throws InterruptedException {
        poolingConnManager = new PoolingHttpClientConnectionManager();
        final CloseableHttpClient client1 = HttpClients.custom().setConnectionManager(poolingConnManager).build();
        final CloseableHttpClient client2 = HttpClients.custom().setConnectionManager(poolingConnManager).build();
        final TesterVersion_MultiHttpClientConnThread thread1 = new TesterVersion_MultiHttpClientConnThread(client1, get1, poolingConnManager);
        final TesterVersion_MultiHttpClientConnThread thread2 = new TesterVersion_MultiHttpClientConnThread(client2, get2, poolingConnManager);
        thread1.start();
        thread2.start();
        thread1.join();
        thread2.join(1000);
        assertTrue(poolingConnManager.getTotalStats().getLeased() == 2);
    }

    @Test
    // @Ignore
    // Example 3.2. ARTICLE VERSION
    public final void whenTwoConnectionsForTwoRequests_thenNoExceptions() throws InterruptedException {
        poolingConnManager = new PoolingHttpClientConnectionManager();
        final CloseableHttpClient client1 = HttpClients.custom().setConnectionManager(poolingConnManager).build();
        final CloseableHttpClient client2 = HttpClients.custom().setConnectionManager(poolingConnManager).build();
        final MultiHttpClientConnThread thread1 = new MultiHttpClientConnThread(client1, get1);
        final MultiHttpClientConnThread thread2 = new MultiHttpClientConnThread(client2, get2);
        thread1.start();
        thread2.start();
        thread1.join();
        thread2.join();
    }

    // 4

    @Test
    // Example 4.1
    public final void whenIncreasingConnectionPool_thenNoEceptions() {
        poolingConnManager = new PoolingHttpClientConnectionManager();
        poolingConnManager.setMaxTotal(5);
        poolingConnManager.setDefaultMaxPerRoute(4);

        final HttpHost localhost = new HttpHost("locahost", 80);
        poolingConnManager.setMaxPerRoute(new HttpRoute(localhost), 5);
    }

    @Test
    // @Ignore
    // 4.2 Tester Version
    /*tester*/ public final void whenExecutingSameRequestsInDifferentThreads_thenUseDefaultConnLimit() throws InterruptedException, IOException {
        poolingConnManager = new PoolingHttpClientConnectionManager();
        client = HttpClients.custom().setConnectionManager(poolingConnManager).build();
        final TesterVersion_MultiHttpClientConnThread thread1 = new TesterVersion_MultiHttpClientConnThread(client, new HttpGet("http://www.google.com"), poolingConnManager);
        final TesterVersion_MultiHttpClientConnThread thread2 = new TesterVersion_MultiHttpClientConnThread(client, new HttpGet("http://www.google.com"), poolingConnManager);
        final TesterVersion_MultiHttpClientConnThread thread3 = new TesterVersion_MultiHttpClientConnThread(client, new HttpGet("http://www.google.com"), poolingConnManager);
        thread1.start();
        thread2.start();
        thread3.start();
        thread1.join(10000);
        thread2.join(10000);
        thread3.join(10000);
    }

    @Test
    // 4.2 Article version
    public final void whenExecutingSameRequestsInDifferentThreads_thenExecuteReuqest() throws InterruptedException {
        final HttpGet get = new HttpGet("http://www.google.com");
        poolingConnManager = new PoolingHttpClientConnectionManager();
        client = HttpClients.custom().setConnectionManager(poolingConnManager).build();
        final MultiHttpClientConnThread thread1 = new MultiHttpClientConnThread(client, get);
        final MultiHttpClientConnThread thread2 = new MultiHttpClientConnThread(client, get);
        final MultiHttpClientConnThread thread3 = new MultiHttpClientConnThread(client, get);
        thread1.start();
        thread2.start();
        thread3.start();
        thread1.join();
        thread2.join();
        thread3.join();
    }

    // 5

    @Test
    // @Ignore
    // 5.1
    public final void whenCustomizingKeepAliveStrategy_thenNoExceptions() throws ClientProtocolException, IOException {
        final ConnectionKeepAliveStrategy myStrategy = new ConnectionKeepAliveStrategy() {
            @Override
            public long getKeepAliveDuration(final HttpResponse myResponse, final HttpContext myContext) {
                final HeaderElementIterator it = new BasicHeaderElementIterator(myResponse.headerIterator(HTTP.CONN_KEEP_ALIVE));
                while (it.hasNext()) {
                    final HeaderElement he = it.nextElement();
                    final String param = he.getName();
                    final String value = he.getValue();
                    if ((value != null) && param.equalsIgnoreCase("timeout")) {
                        return Long.parseLong(value) * 1000;
                    }
                }
                final HttpHost target = (HttpHost) myContext.getAttribute(HttpCoreContext.HTTP_TARGET_HOST);
                if ("localhost".equalsIgnoreCase(target.getHostName())) {
                    return 10 * 1000;
                } else {
                    return 5 * 1000;
                }
            }

        };
        client = HttpClients.custom().setKeepAliveStrategy(myStrategy).setConnectionManager(poolingConnManager).build();
        client.execute(get1);
        client.execute(get2);
    }

    // 6

    @Test
    // @Ignore
    // 6.1
    public final void givenBasicHttpClientConnManager_whenConnectionReuse_thenNoExceptions() throws InterruptedException, ExecutionException, IOException, HttpException {
        basicConnManager = new BasicHttpClientConnectionManager();
        context = HttpClientContext.create();

        final ConnectionRequest connRequest = basicConnManager.requestConnection(route, null);
        conn = connRequest.get(10, TimeUnit.SECONDS);

        basicConnManager.connect(conn, route, 1000, context);
        basicConnManager.routeComplete(conn, route, context);
        final HttpRequestExecutor exeRequest = new HttpRequestExecutor();
        context.setTargetHost((new HttpHost("http://httpbin.org", 80)));

        final HttpGet get = new HttpGet("http://httpbin.org");
        exeRequest.execute(get, conn, context);
        conn.isResponseAvailable(1000);
        basicConnManager.releaseConnection(conn, null, 1, TimeUnit.SECONDS);

        //
        client = HttpClients.custom().setConnectionManager(basicConnManager).build();
        client.execute(get);
    }

    @Test
    // @Ignore
    // 6.2 TESTER VERSION
    /*tester*/ public final void whenConnectionsNeededGreaterThanMaxTotal_thenReuseConnections() throws InterruptedException {
        poolingConnManager = new PoolingHttpClientConnectionManager();
        poolingConnManager.setDefaultMaxPerRoute(5);
        poolingConnManager.setMaxTotal(5);
        client = HttpClients.custom().setConnectionManager(poolingConnManager).build();
        final MultiHttpClientConnThread[] threads = new MultiHttpClientConnThread[10];
        int countConnMade = 0;
        for (int i = 0; i < threads.length; i++) {
            threads[i] = new MultiHttpClientConnThread(client, get1, poolingConnManager);
        }
        for (final MultiHttpClientConnThread thread : threads) {
            thread.start();
        }
        for (final MultiHttpClientConnThread thread : threads) {
            thread.join(10000);
            countConnMade++;
            if (countConnMade == 0) {
                assertTrue(thread.getLeasedConn() == 5);
            }
        }
    }

    @Test
    // 6.2 ARTICLE VERSION
    // @Ignore
    public final void whenConnectionsNeededGreaterThanMaxTotal_thenLeaseMasTotalandReuse() throws InterruptedException {
        final HttpGet get = new HttpGet("http://echo.200please.com");
        poolingConnManager = new PoolingHttpClientConnectionManager();
        poolingConnManager.setDefaultMaxPerRoute(5);
        poolingConnManager.setMaxTotal(5);
        client = HttpClients.custom().setConnectionManager(poolingConnManager).build();
        final MultiHttpClientConnThread[] threads = new MultiHttpClientConnThread[10];
        for (int i = 0; i < threads.length; i++) {
            threads[i] = new MultiHttpClientConnThread(client, get, poolingConnManager);
        }
        for (final MultiHttpClientConnThread thread : threads) {
            thread.start();
        }
        for (final MultiHttpClientConnThread thread : threads) {
            thread.join(10000);
        }
    }

    // 7

    @Test
    // 7.1
    public final void whenConfiguringTimeOut_thenNoExceptions() {
        route = new HttpRoute(new HttpHost("localhost", 80));
        poolingConnManager = new PoolingHttpClientConnectionManager();
        poolingConnManager.setSocketConfig(route.getTargetHost(), SocketConfig.custom().setSoTimeout(5000).build());
        assertTrue(poolingConnManager.getSocketConfig(route.getTargetHost()).getSoTimeout() == 5000);
    }

    // 8

    @Test
    // @Ignore
    // 8.1
    public final void whenHttpClientChecksStaleConns_thenNoExceptions() {
        poolingConnManager = new PoolingHttpClientConnectionManager();
        poolingConnManager.setValidateAfterInactivity(1000);
        client = HttpClients.custom().setDefaultRequestConfig(RequestConfig.custom().build()).setConnectionManager(poolingConnManager).build();
    }

    @Test
    @Ignore("Very Long Running")
    // 8.2 TESTER VERSION
    /*tester*/ public final void whenCustomizedIdleConnMonitor_thenEliminateIdleConns() throws InterruptedException, IOException {
        poolingConnManager = new PoolingHttpClientConnectionManager();
        client = HttpClients.custom().setConnectionManager(poolingConnManager).build();
        final IdleConnectionMonitorThread staleMonitor = new IdleConnectionMonitorThread(poolingConnManager);
        final HttpGet get = new HttpGet("http://google.com");
        // test this with new HttpGet("http://iotechperu.com")----First test will fail b/c there is redirect connection at that site
        final TesterVersion_MultiHttpClientConnThread thread1 = new TesterVersion_MultiHttpClientConnThread(client, get, poolingConnManager);
        final TesterVersion_MultiHttpClientConnThread thread2 = new TesterVersion_MultiHttpClientConnThread(client, get, poolingConnManager);
        final TesterVersion_MultiHttpClientConnThread thread3 = new TesterVersion_MultiHttpClientConnThread(client, get, poolingConnManager);
        staleMonitor.start();
        thread1.start();
        thread1.join();
        thread2.start();
        thread2.join();
        thread3.start();
        assertTrue(poolingConnManager.getTotalStats().getAvailable() == 1);
        thread3.join(32000);
        assertTrue(poolingConnManager.getTotalStats().getAvailable() == 0);
    }

    @Test
    // @Ignore
    // 8.2 ARTICLE VERSION
    public final void whenCustomizedIdleConnMonitor_thenNoExceptions() throws InterruptedException, IOException {
        new HttpGet("http://google.com");
        poolingConnManager = new PoolingHttpClientConnectionManager();
        client = HttpClients.custom().setConnectionManager(poolingConnManager).build();
        final IdleConnectionMonitorThread staleMonitor = new IdleConnectionMonitorThread(poolingConnManager);
        staleMonitor.start();
        staleMonitor.join(1000);
    }

    // 9

    @Test(expected = IllegalStateException.class)
    // @Ignore
    // 9.1
    public final void whenClosingConnectionsandManager_thenCloseWithNoExceptions1() throws InterruptedException, ExecutionException, IOException, HttpException {
        poolingConnManager = new PoolingHttpClientConnectionManager();
        client = HttpClients.custom().setConnectionManager(poolingConnManager).build();
        final HttpGet get = new HttpGet("http://google.com");
        response = client.execute(get);

        EntityUtils.consume(response.getEntity());
        response.close();
        client.close();
        poolingConnManager.close();
        poolingConnManager.shutdown();

        client.execute(get);

        assertTrue(response.getEntity() == null);
    }

}
