package org.baeldung.httpclient;

import org.apache.http.HttpHost;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.conn.ConnectionRequest;
import org.apache.http.conn.routing.HttpRoute;
import org.apache.http.impl.conn.BasicHttpClientConnectionManager;
import org.junit.Test;

public class HttpClientConnectionManagementTest {

    // tests

    @Test
    public final void whenLowLevelConnectionIsEstablished_thenNoExceptions() {
        final HttpClientContext context = HttpClientContext.create();
        final BasicHttpClientConnectionManager connManager = new BasicHttpClientConnectionManager();
        final HttpRoute route = new HttpRoute(new HttpHost("localhost", 80));
        final ConnectionRequest connRequest = connManager.requestConnection(route, null);
    }

}
