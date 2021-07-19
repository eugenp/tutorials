package com.baeldung.rce;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.SocketException;
import java.net.URL;

import static org.junit.Assert.assertTrue;

/**
 * Unit test which demonstrates a remote code exploit against the {@link App}
 * server. Sends an XML request containing an attack payload to the {@code POST}
 * endpoint.
 */
public final class AppUnitTest {

    private App app;

    /** start a new web server */
    @Before
    public void before() throws IOException {
        app = App.createVulnerable(0);
        app.start();
    }

    /** stop the web server */
    @After
    public void after() {
        if (app != null)
            app.stop();
    }

    /**
     * Test passes when an {@link IOException} is thrown because this indicates that
     * the attacker caused the application to fail in some way. This does not
     * actually confirm that the exploit took place, because the RCE is a
     * side-effect that is difficult to observe.
     */
    @Test(expected = SocketException.class)
    public void givenAppIsVulneable_whenExecuteRemoteCodeWhichThrowsException_thenThrowsException() throws IOException {
        // POST the attack.xml to the application's /persons endpoint
        final URL url = new URL("http://localhost:" + app.port() + "/persons");
        final HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("POST");
        connection.setDoOutput(true);
        connection.setUseCaches(false);
        connection.setRequestProperty("Content-Type", "application/xml");
        connection.connect();
        try (OutputStream os = connection.getOutputStream(); InputStream is = AppUnitTest.class.getResourceAsStream("/attack.xml")) {
            byte[] buffer = new byte[1024];
            while (is.read(buffer) > 0) {
                os.write(buffer);
            }
        }
        final int rc = connection.getResponseCode();
        connection.disconnect();
        assertTrue(rc >= 400);
    }
}
