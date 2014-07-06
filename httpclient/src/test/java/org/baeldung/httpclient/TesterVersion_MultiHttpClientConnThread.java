package org.baeldung.httpclient;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;

public class TesterVersion_MultiHttpClientConnThread extends Thread {
    private final CloseableHttpClient client;
    private final HttpGet get;
    private PoolingHttpClientConnectionManager connManager = null;
    private Logger logger;
    public int leasedConn;

    public TesterVersion_MultiHttpClientConnThread(final CloseableHttpClient client, final HttpGet get, final PoolingHttpClientConnectionManager connManager) {
        this.client = client;
        this.get = get;
        this.connManager = connManager;
        logger = Logger.getLogger(TesterVersion_MultiHttpClientConnThread.class.getName());
        leasedConn = 0;
    }

    public int getLeasedConn() {
        return leasedConn;
    }

    @Override
    public void run() {
        try {
            if (this != null)
                logger.log(Level.SEVERE, "Thread Running: " + getName());
            client.execute(get);
            if (connManager != null) {
                logger.log(Level.SEVERE, "Leased Connections " + connManager.getTotalStats().getLeased());
                leasedConn = connManager.getTotalStats().getLeased();
                logger.log(Level.SEVERE, "Available Connections " + connManager.getTotalStats().getAvailable());
            }

        } catch (final ClientProtocolException ex) {

        } catch (final IOException ex) {

        }
    }

}
