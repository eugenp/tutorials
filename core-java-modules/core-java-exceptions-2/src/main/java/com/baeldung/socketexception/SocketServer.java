package com.baeldung.socketexception;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class SocketServer {

    private ServerSocket serverSocket;
    private Socket clientSocket;
    private PrintWriter out;
    private BufferedReader in;

    public void start(int port) {
        try {
            serverSocket = new ServerSocket(port);
            clientSocket = serverSocket.accept();
            out = new PrintWriter(clientSocket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            String msg = in.readLine();
            if (msg.contains("hi"))
                out.println("hi");
            else
                out.println("didn't understand");
            close();
            stop();
        } catch (IOException e) {
            
        }
    }

    private void close() throws IOException {
        in.close();
        out.close();
    }

    private void stop() throws IOException {
        clientSocket.close();
        serverSocket.close();
    }

}
