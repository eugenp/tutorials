package com.baeldung.httpclient.conn;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Iterator;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import org.apache.hc.client5.http.ConnectionKeepAliveStrategy;
import org.apache.hc.client5.http.HttpRoute;
import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.config.ConnectionConfig;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.client5.http.impl.io.BasicHttpClientConnectionManager;
import org.apache.hc.client5.http.impl.io.PoolingHttpClientConnectionManager;
import org.apache.hc.client5.http.io.ConnectionEndpoint;
import org.apache.hc.client5.http.io.LeaseRequest;
import org.apache.hc.core5.http.HeaderElement;
import org.apache.hc.core5.http.HeaderElements;
import org.apache.hc.core5.http.HttpHost;
import org.apache.hc.core5.http.HttpResponse;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import org.apache.hc.core5.http.message.MessageSupport;
import org.apache.hc.core5.http.message.StatusLine;
import org.apache.hc.core5.http.protocol.BasicHttpContext;
import org.apache.hc.core5.http.protocol.HttpContext;
import org.apache.hc.core5.pool.PoolStats;
import org.apache.hc.core5.util.Args;
import org.apache.hc.core5.util.TimeValue;
import org.apache.hc.core5.util.Timeout;
import org.junit.jupiter.api.Test;

class HttpClientConnectionManagementLiveTest {

    // Example 2.1. Getting a Connection Request for a Low Level Connection (HttpClientConnection)
    @Test
    final void whenLowLevelConnectionIsEstablished_thenNoExceptions() throws ExecutionException, InterruptedException, TimeoutException {
        BasicHttpClientConnectionManager connMgr = new BasicHttpClientConnectionManager();
        HttpRoute route = new HttpRoute(new HttpHost("www.baeldung.com", 443));
        final LeaseRequest connRequest = connMgr.lease("some-id", route, null);
        assertNotNull(connRequest.get(Timeout.ZERO_MILLISECONDS));
        connMgr.close();
    }

    // Example 3.1. Setting the PoolingHttpClientConnectionManager on a HttpClient
    @Test
    final void whenPollingConnectionManagerIsConfiguredOnHttpClient_thenNoExceptions() throws IOException {
        PoolingHttpClientConnectionManager poolingConnManager = new PoolingHttpClientConnectionManager();
        CloseableHttpClient client = HttpClients.custom()
            .setConnectionManager(poolingConnManager)
            .build();
        client.execute(new HttpGet("https://www.baeldung.com"));
        assertTrue(poolingConnManager.getTotalStats()
            .getLeased() == 1);
        client.close();
        poolingConnManager.close();
    }

    // Example 3.2. Using Two HttpClients to Connect to One Target Host Each
    @Test
    final void whenTwoConnectionsForTwoRequests_thenNoExceptions() throws InterruptedException, IOException {
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

        assertEquals(0, connManager.getTotalStats().getLeased());
        client1.close();
        client2.close();
        connManager.close();
    }

    // Example 4.1. Increasing the Number of Connections that Can be Open and Managed Beyond the default Limits
    @Test
    final void whenIncreasingConnectionPool_thenNoExceptions() {
        PoolingHttpClientConnectionManager connManager = new PoolingHttpClientConnectionManager();
        connManager.setMaxTotal(5);
        connManager.setDefaultMaxPerRoute(4);
        HttpHost host = new HttpHost("www.baeldung.com", 80);
        connManager.setMaxPerRoute(new HttpRoute(host), 5);
        connManager.close();
    }

    // Example 4.2. Using Threads to Execute Connections
    @Test
    final void whenExecutingSameRequestsInDifferentThreads_thenExecuteRequest() throws InterruptedException, IOException {
        HttpGet get = new HttpGet("http://www.baeldung.com");
        PoolingHttpClientConnectionManager connManager = new PoolingHttpClientConnectionManager();
        CloseableHttpClient client = HttpClients.custom()
            .setConnectionManager(connManager)
            .build();
        MultiHttpClientConnThread thread1 = new MultiHttpClientConnThread(client, get, connManager);
        MultiHttpClientConnThread thread2 = new MultiHttpClientConnThread(client, get, connManager);
        MultiHttpClientConnThread thread3 = new MultiHttpClientConnThread(client, get, connManager);
        MultiHttpClientConnThread thread4 = new MultiHttpClientConnThread(client, get, connManager);
        MultiHttpClientConnThread thread5 = new MultiHttpClientConnThread(client, get, connManager);
        MultiHttpClientConnThread thread6 = new MultiHttpClientConnThread(client, get, connManager);
        thread1.start();
        thread2.start();
        thread3.start();
        thread4.start();
        thread5.start();
        thread6.start();
        thread1.join();
        thread2.join();
        thread3.join();
        thread4.join();
        thread5.join();
        thread6.join();
        client.close();
        connManager.close();
    }

    // Example 5.1. A Custom Keep Alive Strategy
    @Test
    final void whenCustomizingKeepAliveStrategy_thenNoExceptions() {
        final ConnectionKeepAliveStrategy myStrategy = new ConnectionKeepAliveStrategy() {
            @Override
            public TimeValue getKeepAliveDuration(HttpResponse response, HttpContext context) {
                Args.notNull(response, "HTTP response");
                final Iterator<HeaderElement> it = MessageSupport.iterate(response, HeaderElements.KEEP_ALIVE);
                final HeaderElement he = it.next();
                final String param = he.getName();
                final String value = he.getValue();
                if (value != null && param.equalsIgnoreCase("timeout")) {
                    try {
                        return TimeValue.ofSeconds(Long.parseLong(value));
                    } catch (final NumberFormatException ignore) {
                    }
                }
                return TimeValue.ofSeconds(5);
            }

        };
        PoolingHttpClientConnectionManager connManager = new PoolingHttpClientConnectionManager();
        HttpClients.custom()
            .setKeepAliveStrategy(myStrategy)
            .setConnectionManager(connManager)
            .build();
        connManager.close();
    }

    //Example 6.1. BasicHttpClientConnectionManager Connection Reuse
    @Test
    final void givenBasicHttpClientConnManager_whenConnectionReuse_thenNoExceptions() throws InterruptedException, ExecutionException, TimeoutException, IOException, URISyntaxException {
        BasicHttpClientConnectionManager connMgr = new BasicHttpClientConnectionManager();
        HttpRoute route = new HttpRoute(new HttpHost("www.baeldung.com", 443));
        final HttpContext context = new BasicHttpContext();

        final LeaseRequest connRequest = connMgr.lease("some-id", route, null);
        final ConnectionEndpoint endpoint = connRequest.get(Timeout.ZERO_MILLISECONDS);
        connMgr.connect(endpoint, Timeout.ZERO_MILLISECONDS, context);

        connMgr.release(endpoint, null, TimeValue.ZERO_MILLISECONDS);

        CloseableHttpClient client = HttpClients.custom()
            .setConnectionManager(connMgr)
            .build();
        HttpGet httpGet = new HttpGet("https://www.example.com");
        client.execute(httpGet, context, response -> response);
        client.close();
        connMgr.close();
    }

    // Example 6.2. PoolingHttpClientConnectionManager: Re-Using Connections with Threads
    @Test
    final void whenConnectionsNeededGreaterThanMaxTotal_thenLeaseMasTotalandReuse() throws InterruptedException, IOException {
        HttpGet get = new HttpGet("http://www.baeldung.com");
        PoolingHttpClientConnectionManager connManager = new PoolingHttpClientConnectionManager();
        connManager.setDefaultMaxPerRoute(6);
        connManager.setMaxTotal(6);
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
        client.close();
        connManager.close();
    }

    // Example 7.1. Setting Socket Timeout to 5 Seconds
    @Test
    final void whenConfiguringTimeOut_thenNoExceptions() throws ExecutionException, InterruptedException, TimeoutException, IOException {
        final HttpRoute route = new HttpRoute(new HttpHost("www.baeldung.com", 80));
        final HttpContext context = new BasicHttpContext();
        final PoolingHttpClientConnectionManager connManager = new PoolingHttpClientConnectionManager();

        final ConnectionConfig connConfig = ConnectionConfig.custom()
            .setSocketTimeout(5, TimeUnit.SECONDS)
            .build();

        connManager.setDefaultConnectionConfig(connConfig);

        final LeaseRequest leaseRequest = connManager.lease("id1", route, null);
        final ConnectionEndpoint endpoint = leaseRequest.get(Timeout.ZERO_MILLISECONDS);
        connManager.connect(endpoint, null, context);
        connManager.close();
    }

    // Example 8.1. Setting the HttpClient to Check for Stale Connections
    @Test
    final void whenEvictIdealConn_thenNoExceptions() throws InterruptedException, IOException {
        final PoolingHttpClientConnectionManager connManager = new PoolingHttpClientConnectionManager();
        connManager.setMaxTotal(100);
        try (final CloseableHttpClient httpclient = HttpClients.custom()
            .setConnectionManager(connManager)
            .evictExpiredConnections()
            .evictIdleConnections(TimeValue.ofSeconds(2))
            .build()) {
            // create an array of URIs to perform GETs on
            final String[] urisToGet = { "http://hc.apache.org/", "http://hc.apache.org/httpcomponents-core-ga/"};

            for (final String requestURI : urisToGet) {
                final HttpGet request = new HttpGet(requestURI);

                System.out.println("Executing request " + request.getMethod() + " " + request.getRequestUri());

                httpclient.execute(request, response -> {
                    System.out.println("----------------------------------------");
                    System.out.println(request + "->" + new StatusLine(response));
                    EntityUtils.consume(response.getEntity());
                    return null;
                });
            }

            final PoolStats stats1 = connManager.getTotalStats();
            System.out.println("Connections kept alive: " + stats1.getAvailable());

            // Sleep 10 sec and let the connection evict or do its job
            Thread.sleep(4000);

            final PoolStats stats2 = connManager.getTotalStats();
            System.out.println("Connections kept alive: " + stats2.getAvailable());

            connManager.close();
        }
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
    }

    @Test
    // Example 3.2. TESTER VERSION
    final void whenTwoConnectionsForTwoRequests_thenTwoConnectionsAreLeased() throws InterruptedException, IOException {
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
        assertEquals(2, poolingConnManager.getTotalStats().getLeased());

        client1.close();
        client2.close();
        poolingConnManager.close();
    }
}
