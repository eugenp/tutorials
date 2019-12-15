package com.baeldung.httpclient.conn;

import java.io.IOException;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Preconditions;

public class TesterVersion_MultiHttpClientConnThread extends Thread {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    private final CloseableHttpClient client;
    private final HttpGet get;
    private PoolingHttpClientConnectionManager connManager;

    TesterVersion_MultiHttpClientConnThread(final CloseableHttpClient client, final HttpGet get, final PoolingHttpClientConnectionManager connManager) {
        this.client = client;
        this.get = get;
        this.connManager = Preconditions.checkNotNull(connManager);
    }

    //

    @Override
    public final void run() {
        try {
            logger.debug("Thread Running: " + getName());

            logger.info("Before - Leased Connections = " + connManager.getTotalStats().getLeased());
            logger.info("Before - Available Connections = " + connManager.getTotalStats().getAvailable());

            client.execute(get);

            logger.info("After - Leased Connections = " + connManager.getTotalStats().getLeased());
            logger.info("After - Available Connections = " + connManager.getTotalStats().getAvailable());
        } catch (final IOException ex) {
            logger.error("", ex);
        }
    }

}
