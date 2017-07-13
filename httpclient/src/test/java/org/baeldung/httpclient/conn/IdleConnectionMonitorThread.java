package org.baeldung.httpclient.conn;

import java.util.concurrent.TimeUnit;

import org.apache.http.conn.HttpClientConnectionManager;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;

public class IdleConnectionMonitorThread extends Thread {
    private final HttpClientConnectionManager connMgr;
    private volatile boolean shutdown;

    public IdleConnectionMonitorThread(final PoolingHttpClientConnectionManager connMgr) {
        super();
        this.connMgr = connMgr;
    }

    // API

    @Override
    public final void run() {
        try {
            while (!shutdown) {
                synchronized (this) {
                    wait(1000);
                    connMgr.closeExpiredConnections();
                    connMgr.closeIdleConnections(30, TimeUnit.SECONDS);
                }
            }
        } catch (final InterruptedException ex) {
            shutdown();
        }
    }

    public final void shutdown() {
        shutdown = true;
        synchronized (this) {
            notifyAll();
        }
    }

}
