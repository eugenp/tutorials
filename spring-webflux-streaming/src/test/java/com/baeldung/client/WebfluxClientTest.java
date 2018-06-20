package com.baeldung.client;

import static org.junit.Assert.*;

import com.baeldung.server.WebfluxServer;
import java.net.HttpURLConnection;
import java.net.URL;
import org.junit.Test;

/**
 * Created by PhysicsSam on 20-Jun-18.
 */
public class WebfluxClientTest {

    @Test public void whenDataEndpointCalled_thenCorrectResponseMessage() throws Exception {
        String[] args = new String[]{};

        WebfluxServer.main(args);
        WebfluxClient.main(args);

        HttpURLConnection http = (HttpURLConnection) new URL("http://localhost:8080/data").openConnection();
        http.connect();

        assertEquals(http.getResponseMessage(), "OK");

        http.disconnect();

    }

}