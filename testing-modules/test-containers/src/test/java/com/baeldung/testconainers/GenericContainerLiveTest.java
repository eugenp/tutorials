package com.baeldung.testconainers;

import static org.junit.Assert.assertEquals;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;

import org.junit.ClassRule;
import org.junit.Test;
import org.junit.platform.commons.annotation.Testable;
import org.testcontainers.containers.GenericContainer;

@Testable
public class GenericContainerLiveTest {
    @ClassRule
    public static GenericContainer simpleWebServer =
      new GenericContainer("alpine:3.2")
        .withExposedPorts(80)
        .withCommand("/bin/sh", "-c", "while true; do echo "
          + "\"HTTP/1.1 200 OK\n\nHello World!\" | nc -l -p 80; done");

    @Test
    public void givenSimpleWebServerContainer_whenGetReuqest_thenReturnsResponse()
      throws Exception {
        String address = "http://" 
          + simpleWebServer.getContainerIpAddress() 
          + ":" + simpleWebServer.getMappedPort(80);
        String response = simpleGetRequest(address);
        
        assertEquals(response, "Hello World!");
    }

    private String simpleGetRequest(String address) throws Exception {
        URL url = new URI(address).toURL();
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");

        BufferedReader in = new BufferedReader(
          new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer content = new StringBuffer();
        while ((inputLine = in.readLine()) != null) {
            content.append(inputLine);
        }
        in.close();

        return content.toString();
    }
}
