package org.baeldung.httpclient;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

import org.apache.http.HttpException;
import org.apache.http.HttpHost;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.conn.ConnectionRequest;
import org.apache.http.conn.routing.HttpRoute;
import org.apache.http.impl.conn.BasicHttpClientConnectionManager;
import org.junit.Test;

@SuppressWarnings("unused")
public class HttpClientConnectionManagementTest {

    // tests

    @Test
    public final void whenLowLevelConnectionIsEstablished_thenNoExceptions() throws IOException, HttpException, InterruptedException, ExecutionException {
        final HttpClientContext context = HttpClientContext.create();
        final BasicHttpClientConnectionManager connManager = new BasicHttpClientConnectionManager();
        final HttpRoute route = new HttpRoute(new HttpHost("localhost", 80));
        final ConnectionRequest connRequest = connManager.requestConnection(route, null);

        connManager.shutdown();
    }

}
