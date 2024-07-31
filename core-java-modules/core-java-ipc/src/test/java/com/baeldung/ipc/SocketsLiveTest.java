package com.baeldung.ipc;

import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class SocketsLiveTest {
    @Test
    public void consumer() throws Exception {
        try (ServerSocket serverSocket = new ServerSocket(1234)) {
            Socket clientSocket = serverSocket.accept();

            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

            String line;
            while ((line = in.readLine()) != null) {
                System.out.println("Received message: " + line);
            }
        }
    }

    @Test
    public void producer() throws Exception {
        try (Socket clientSocket = new Socket("localhost", 1234)) {
            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

            out.println("Hello");
            
            String response = in.readLine();
        }
    }
}