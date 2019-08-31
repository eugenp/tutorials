package com.baeldung.restart;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.net.ServerSocket;

import org.junit.Test;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

public class RestartApplicationIntegrationTest {

    private TestRestTemplate restTemplate = new TestRestTemplate();

    @Test
    public void givenBootApp_whenRestart_thenOk() throws Exception {
        Integer port = findRandomOpenPortOnAllLocalInterfaces();
        Application.main(new String[] { String.format("--server.port=%d", port) });

        ResponseEntity response = restTemplate.exchange(String.format("http://localhost:%d/restart", port),
           HttpMethod.POST, null, Object.class);
        
        assertEquals(200, response.getStatusCode().value());
    }
    
    @Test
    public void givenBootApp_whenRestartUsingActuator_thenOk() throws Exception {
        Integer port = findRandomOpenPortOnAllLocalInterfaces();
        Application.main(new String[] { String.format("--server.port=%d", port) });

        ResponseEntity response = restTemplate.exchange(String.format("http://localhost:%d/restartApp", port),
           HttpMethod.POST, null, Object.class);
        
        assertEquals(200, response.getStatusCode().value());
    }
    
    private Integer findRandomOpenPortOnAllLocalInterfaces() throws IOException {
        try (ServerSocket socket = new ServerSocket(0);) {
            return socket.getLocalPort();
        }
    }
}