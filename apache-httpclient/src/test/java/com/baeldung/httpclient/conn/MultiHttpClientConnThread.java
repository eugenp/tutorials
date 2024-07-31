package com.baeldung.httpclient.conn;

import java.io.IOException;

import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.io.PoolingHttpClientConnectionManager;
import org.apache.hc.core5.http.HttpEntity;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MultiHttpClientConnThread extends Thread {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    private final CloseableHttpClient client;
    private final HttpGet get;

    private PoolingHttpClientConnectionManager connManager;
    private int leasedConn;

    MultiHttpClientConnThread(final CloseableHttpClient client, final HttpGet get, final PoolingHttpClientConnectionManager connManager) {
        this.client = client;
        this.get = get;
        this.connManager = connManager;
        leasedConn = 0;
    }

    MultiHttpClientConnThread(final CloseableHttpClient client, final HttpGet get) {
        this.client = client;
        this.get = get;
    }

    // API

    final int getLeasedConn() {
        return leasedConn;
    }

    //

    @Override
    public final void run() {
        try {
            logger.debug("Thread Running: " + getName());


            if (connManager != null) {
                logger.info("Before - Leased Connections = " + connManager.getTotalStats().getLeased());
                logger.info("Before - Available Connections = " + connManager.getTotalStats().getAvailable());
            }

            HttpEntity entity = client.execute(get).getEntity();

            if (connManager != null) {
                leasedConn = connManager.getTotalStats().getLeased();
                logger.info("After - Leased Connections = " + connManager.getTotalStats().getLeased());
                logger.info("After - Available Connections = " + connManager.getTotalStats().getAvailable());
            }
            EntityUtils.consume(entity);

        } catch (final IOException ex) {
            logger.error("", ex);
        }
    }

}
