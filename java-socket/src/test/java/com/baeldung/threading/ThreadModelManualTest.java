package com.baeldung.threading;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class ThreadModelManualTest {

    private static final String HOST = "localhost";
    private static final int PORT = 8080;

    @Test
    void whenSendingRequestWithDifferentConnections_responseReceived() throws IOException {
        for (int i = 1; i <= 3; i++) {
            Socket socket = new Socket(HOST, PORT);
            PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            writer.println("Request " + i);
            String response = reader.readLine();
            Assertions.assertNotNull(response);
            socket.close();
        }
    }

    @Test
    void whenSendingRequestWithSameConnection_responseReceived() throws IOException, InterruptedException {
        Socket socket = new Socket(HOST, PORT);
        PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);
        BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        for (int i = 1; i <= 3; i++) {
            writer.println((String) null);
            Thread.sleep(2000);
            String response = reader.readLine();
            Assertions.assertNotNull(response);
        }
        socket.close();
    }
}