package com.baeldung.threading;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

// Note: ThreadPerConnectionServer or ThreadPerRequestServer needs to be started externally in order to execute this test.
class ThreadModelManualTest {

    private static final String HOST = "localhost";
    private static final int PORT = 8080;

    @Test
    void whenSendingRequestWithDifferentConnections_thenResponseReceived() throws IOException {
        for (int i = 1; i <= 3; i++) {
            try (Socket socket = new Socket(HOST, PORT);
                 PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);
                 BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {
                String request = "Request " + i;
                writer.println(request);
                String response = reader.readLine();
                Assertions.assertEquals("HTTP/1.1 200 OK - Processed request: " + request, response);
            }
        }
    }

    @Test
    void whenSendingRequestWithSameConnection_thenResponseReceived() throws IOException, InterruptedException {
        try (Socket socket = new Socket(HOST, PORT);
             PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {
            for (int i = 1; i <= 3; i++) {
                String request = "Request " + i;
                writer.println(request);
                Thread.sleep(2000); // simulate gap between client requests
                String response = reader.readLine();

                Assertions.assertEquals("HTTP/1.1 200 OK - Processed request: " + request, response);
            }
        }
    }
}