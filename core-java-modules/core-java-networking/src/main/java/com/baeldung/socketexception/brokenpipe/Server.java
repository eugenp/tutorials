package com.baeldung.socketexception.brokenpipe;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    public static void main(String[] args) {
        try {
            ServerSocket serverSocket = new ServerSocket(1234);
            System.out.println("Server listening on port 1234...");

            Socket clientSocket = serverSocket.accept();
            System.out.println("Client connected: " + clientSocket.getInetAddress());
            //Add some delay for reading from client
            Thread.sleep(2000);
            InputStream in = clientSocket.getInputStream();
            System.out.println("Reading from client:" + in.read());
            in.close();
            clientSocket.close();
            serverSocket.close();
        }
        catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
