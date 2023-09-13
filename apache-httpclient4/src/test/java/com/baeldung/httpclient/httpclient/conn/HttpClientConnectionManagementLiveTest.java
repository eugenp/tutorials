package com.baeldung.httpclient.httpclient.conn;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.IOException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

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
import org.apache.http.conn.ConnectionPoolTimeoutException;
import org.apache.http.conn.ConnectionRequest;
import org.apache.http.conn.routing.HttpRoute;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.BasicHttpClientConnectionManager;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicHeaderElementIterator;
import org.apache.http.protocol.HTTP;
import org.apache.http.protocol.HttpContext;
import org.apache.http.protocol.HttpCoreContext;
import org.apache.http.protocol.HttpRequestExecutor;
import org.apache.http.util.EntityUtils;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

class HttpClientConnectionManagementLiveTest {

    // Example 2.1. Getting a Connection Request for a Low Level Connection (HttpClientConnection)
    @Test
    final void whenLowLevelConnectionIsEstablished_thenNoExceptions() throws ConnectionPoolTimeoutException, InterruptedException, ExecutionException {
        try (BasicHttpClientConnectionManager connManager = new BasicHttpClientConnectionManager()) {
            HttpRoute route = new HttpRoute(new HttpHost("www.baeldung.com", 80));
            final ConnectionRequest connRequest = connManager.requestConnection(route, null);
            assertNotNull(connRequest.get(1000, TimeUnit.SECONDS));
        }
    }

    // Example 3.1. Setting the PoolingHttpClientConnectionManager on a HttpClient
    @Test
    final void whenPollingConnectionManagerIsConfiguredOnHttpClient_thenNoExceptions() throws ClientProtocolException, IOException {
        PoolingHttpClientConnectionManager poolingConnManager = new PoolingHttpClientConnectionManager();
        CloseableHttpClient client = HttpClients.custom()
            .setConnectionManager(poolingConnManager)
            .build();
        client.execute(new HttpGet("https://www.baeldung.com"));

        assertEquals(1, poolingConnManager.getTotalStats()
            .getLeased());
    }

    // Example 3.2. Using Two HttpClients to Connect to One Target Host Each
    @Test
    final void whenTwoConnectionsForTwoRequests_thenNoExceptions() throws InterruptedException {
        HttpGet get1 = new HttpGet("https://www.baeldung.com");
        HttpGet get2 = new HttpGet("https://www.google.com");
        PoolingHttpClientConnectionManager connManager = new PoolingHttpClientConnectionManager();
        CloseableHttpClient client1 = HttpClients.custom()
            .setConnectionManager(connManager)
            .build();
        CloseableHttpClient client2 = HttpClients.custom()
            .setConnectionManager(connManager)
            .build();

        MultiHttpClientConnThread thread1 = new MultiHttpClientConnThread(client1, get1);
        MultiHttpClientConnThread thread2 = new MultiHttpClientConnThread(client2, get2);
        thread1.start();
        thread2.start();
        thread1.join();
        thread2.join();

        assertEquals(0, connManager.getTotalStats()
            .getLeased());
    }

    // Example 4.1. Increasing the Number of Connections that Can be Open and Managed Beyond the default Limits
    @Test
    final void whenIncreasingConnectionPool_thenNoEceptions() {
        try (PoolingHttpClientConnectionManager connManager = new PoolingHttpClientConnectionManager()) {
            connManager.setMaxTotal(5);
            connManager.setDefaultMaxPerRoute(4);
            HttpHost host = new HttpHost("www.baeldung.com", 80);
            connManager.setMaxPerRoute(new HttpRoute(host), 5);
        }
    }

    // Example 4.2. Using Threads to Execute Connections
    @Test
    final void whenExecutingSameRequestsInDifferentThreads_thenExecuteReuqest() throws InterruptedException {
        HttpGet get = new HttpGet("http://www.baeldung.com");
        PoolingHttpClientConnectionManager connManager = new PoolingHttpClientConnectionManager();
        CloseableHttpClient client = HttpClients.custom()
            .setConnectionManager(connManager)
            .build();
        MultiHttpClientConnThread thread1 = new MultiHttpClientConnThread(client, get);
        MultiHttpClientConnThread thread2 = new MultiHttpClientConnThread(client, get);
        MultiHttpClientConnThread thread3 = new MultiHttpClientConnThread(client, get);
        thread1.start();
        thread2.start();
        thread3.start();
        thread1.join();
        thread2.join();
        thread3.join();
    }

    // Example 5.1. A Custom Keep Alive Strategy
    @Test
    final void whenCustomizingKeepAliveStrategy_thenNoExceptions() {
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
        PoolingHttpClientConnectionManager connManager = new PoolingHttpClientConnectionManager();
        HttpClients.custom()
            .setKeepAliveStrategy(myStrategy)
            .setConnectionManager(connManager)
            .build();
    }

    // Example 6.1. BasicHttpClientConnectionManager Connection Reuse
    @Test
    final void givenBasicHttpClientConnManager_whenConnectionReuse_thenNoExceptions() throws IOException, HttpException, InterruptedException, ExecutionException {
        BasicHttpClientConnectionManager basicConnManager = new BasicHttpClientConnectionManager();
        HttpClientContext context = HttpClientContext.create();

        // low level
        HttpRoute route = new HttpRoute(new HttpHost("www.baeldung.com", 443));
        ConnectionRequest connRequest = basicConnManager.requestConnection(route, null);
        HttpClientConnection conn = connRequest.get(10, TimeUnit.SECONDS);
        basicConnManager.connect(conn, route, 1000, context);
        basicConnManager.routeComplete(conn, route, context);

        HttpRequestExecutor exeRequest = new HttpRequestExecutor();
        context.setTargetHost((new HttpHost("www.baeldung.com", 80)));
        HttpGet get = new HttpGet("http://www.baeldung.com");
        exeRequest.execute(get, conn, context);

        basicConnManager.releaseConnection(conn, null, 1, TimeUnit.SECONDS);

        // high level
        CloseableHttpClient client = HttpClients.custom()
            .setConnectionManager(basicConnManager)
            .build();
        client.execute(get);
    }

    // Example 6.2. PoolingHttpClientConnectionManager: Re-Using Connections with Threads
    @Test
    final void whenConnectionsNeededGreaterThanMaxTotal_thenLeaseMasTotalandReuse() throws InterruptedException {
        HttpGet get = new HttpGet("http://echo.200please.com");
        PoolingHttpClientConnectionManager connManager = new PoolingHttpClientConnectionManager();
        connManager.setDefaultMaxPerRoute(5);
        connManager.setMaxTotal(5);
        CloseableHttpClient client = HttpClients.custom()
            .setConnectionManager(connManager)
            .build();
        MultiHttpClientConnThread[] threads = new MultiHttpClientConnThread[10];
        for (int i = 0; i < threads.length; i++) {
            threads[i] = new MultiHttpClientConnThread(client, get, connManager);
        }
        for (MultiHttpClientConnThread thread : threads) {
            thread.start();
        }
        for (MultiHttpClientConnThread thread : threads) {
            thread.join(1000);
        }
    }

    // Example 7.1. Setting Socket Timeout to 5 Seconds
    @Test
    final void whenConfiguringTimeOut_thenNoExceptions() {
        HttpRoute route = new HttpRoute(new HttpHost("www.baeldung.com", 80));
        try (PoolingHttpClientConnectionManager connManager = new PoolingHttpClientConnectionManager()) {
            connManager.setSocketConfig(route.getTargetHost(), SocketConfig.custom()
                .setSoTimeout(5000)
                .build());
            assertEquals(5000, connManager.getSocketConfig(route.getTargetHost())
                .getSoTimeout());
        }
    }

    // Example 8.1. Setting the HttpClient to Check for Stale Connections
    @Test
    final void whenHttpClientChecksStaleConns_thenNoExceptions() {
        PoolingHttpClientConnectionManager connManager = new PoolingHttpClientConnectionManager();
        HttpClients.custom()
            .setDefaultRequestConfig(RequestConfig.custom()
                .setStaleConnectionCheckEnabled(true)
                .build())
            .setConnectionManager(connManager)
            .build();
    }

    // Example 8.2. Using a Stale Connection Monitor Thread
    @Test
    final void whenCustomizedIdleConnMonitor_thenNoExceptions() throws InterruptedException {
        PoolingHttpClientConnectionManager connManager = new PoolingHttpClientConnectionManager();
        HttpClients.custom()
            .setConnectionManager(connManager)
            .build();
        IdleConnectionMonitorThread staleMonitor = new IdleConnectionMonitorThread(connManager);
        staleMonitor.start();
        staleMonitor.join(1000);
    }

    // Example 9.1. Closing Connection and Releasing Resources
    @Test
    final void whenClosingConnectionsAndManager_thenCloseWithNoExceptions1() throws IOException {
        PoolingHttpClientConnectionManager connManager = new PoolingHttpClientConnectionManager();
        CloseableHttpClient client = HttpClients.custom()
            .setConnectionManager(connManager)
            .build();
        final HttpGet get = new HttpGet("http://google.com");
        CloseableHttpResponse response = client.execute(get);

        EntityUtils.consume(response.getEntity());
        response.close();
        client.close();
        connManager.close();
        connManager.shutdown();

        assertThrows(IllegalStateException.class, () -> {
            client.execute(get);
        });
    }

    @Test
    // Example 3.2. TESTER VERSION
    final void whenTwoConnectionsForTwoRequests_thenTwoConnectionsAreLeased() throws InterruptedException {
        HttpGet get1 = new HttpGet("https://www.baeldung.com");
        HttpGet get2 = new HttpGet("https://www.google.com");

        PoolingHttpClientConnectionManager poolingConnManager = new PoolingHttpClientConnectionManager();
        final CloseableHttpClient client1 = HttpClients.custom()
            .setConnectionManager(poolingConnManager)
            .build();
        final CloseableHttpClient client2 = HttpClients.custom()
            .setConnectionManager(poolingConnManager)
            .build();

        final TesterVersion_MultiHttpClientConnThread thread1 = new TesterVersion_MultiHttpClientConnThread(client1, get1, poolingConnManager);
        final TesterVersion_MultiHttpClientConnThread thread2 = new TesterVersion_MultiHttpClientConnThread(client2, get2, poolingConnManager);
        thread1.start();
        thread2.start();
        thread1.join();
        thread2.join(1000);
        assertEquals(2, poolingConnManager.getTotalStats()
            .getLeased());
    }

    @Test
    // Example 4.2 Tester Version
    final void whenExecutingSameRequestsInDifferentThreads_thenUseDefaultConnLimit() throws InterruptedException {
        PoolingHttpClientConnectionManager poolingConnManager = new PoolingHttpClientConnectionManager();
        CloseableHttpClient client = HttpClients.custom()
            .setConnectionManager(poolingConnManager)
            .build();
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
    // 6.2 TESTER VERSION
    final void whenConnectionsNeededGreaterThanMaxTotal_thenReuseConnections() throws InterruptedException {
        PoolingHttpClientConnectionManager poolingConnManager = new PoolingHttpClientConnectionManager();
        poolingConnManager.setDefaultMaxPerRoute(5);
        poolingConnManager.setMaxTotal(5);
        CloseableHttpClient client = HttpClients.custom()
            .setConnectionManager(poolingConnManager)
            .build();
        final MultiHttpClientConnThread[] threads = new MultiHttpClientConnThread[10];
        int countConnMade = 0;
        for (int i = 0; i < threads.length; i++) {
            threads[i] = new MultiHttpClientConnThread(client, new HttpGet("http://www.baeldung.com/"), poolingConnManager);
        }
        for (final MultiHttpClientConnThread thread : threads) {
            thread.start();
        }
        for (final MultiHttpClientConnThread thread : threads) {
            thread.join(10000);
            countConnMade++;
            if (countConnMade == 0) {
                assertEquals(5, thread.getLeasedConn());
            }
        }
    }

    @Test
    @Disabled("Very Long Running")
    // 8.2 TESTER VERSION
    final void whenCustomizedIdleConnMonitor_thenEliminateIdleConns() throws InterruptedException {
        PoolingHttpClientConnectionManager poolingConnManager = new PoolingHttpClientConnectionManager();
        CloseableHttpClient client = HttpClients.custom()
            .setConnectionManager(poolingConnManager)
            .build();
        final IdleConnectionMonitorThread staleMonitor = new IdleConnectionMonitorThread(poolingConnManager);
        final HttpGet get = new HttpGet("http://google.com");
        final TesterVersion_MultiHttpClientConnThread thread1 = new TesterVersion_MultiHttpClientConnThread(client, get, poolingConnManager);
        final TesterVersion_MultiHttpClientConnThread thread2 = new TesterVersion_MultiHttpClientConnThread(client, get, poolingConnManager);
        final TesterVersion_MultiHttpClientConnThread thread3 = new TesterVersion_MultiHttpClientConnThread(client, get, poolingConnManager);
        staleMonitor.start();
        thread1.start();
        thread1.join();
        thread2.start();
        thread2.join();
        thread3.start();
        assertEquals(1, poolingConnManager.getTotalStats()
            .getAvailable());
        thread3.join(32000);
        assertEquals(0, poolingConnManager.getTotalStats()
            .getAvailable());
    }
}
