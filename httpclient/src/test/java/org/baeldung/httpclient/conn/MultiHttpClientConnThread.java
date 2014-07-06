package org.baeldung.httpclient.conn;

import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MultiHttpClientConnThread extends Thread {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    private final CloseableHttpClient client;
    private final HttpGet get;

    private PoolingHttpClientConnectionManager connManager;
    private static HttpResponse response;
    public int leasedConn;

    public MultiHttpClientConnThread(final CloseableHttpClient client, final HttpGet get, final PoolingHttpClientConnectionManager connManager) {
        this.client = client;
        this.get = get;
        this.connManager = connManager;
        this.leasedConn = 0;
    }

    public MultiHttpClientConnThread(final CloseableHttpClient client, final HttpGet get) {
        this.client = client;
        this.get = get;
    }

    // API

    public final int getLeasedConn() {
        return leasedConn;
    }

    //

    @Override
    public final void run() {
        try {
            logger.info("Thread Running: " + getName());

            response = client.execute(get);
            if (connManager != null) {
                logger.info("Leased Connections " + connManager.getTotalStats().getLeased());
                leasedConn = connManager.getTotalStats().getLeased();
                logger.info("Available Connections " + connManager.getTotalStats().getAvailable());
            }
            EntityUtils.consume(response.getEntity());
        } catch (final ClientProtocolException ex) {
            logger.error("", ex);
        } catch (final IOException ex) {
            logger.error("", ex);
        }
    }

}
