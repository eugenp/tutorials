package com.baeldung.timeout;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

public class TcpServerSocket {

    private Socket socket;
    private ServerSocket serverSocket;
    private BufferedReader in;

    public TcpServerSocket(int port) {

        try {
            serverSocket = new ServerSocket(port);
            System.out.println("Server is listening on port :: " + port);
            System.out.println("Waiting for a client ...");

            socket = serverSocket.accept();
            socket.setSoTimeout(40000);
            System.out.println("Client connected !! ");

            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            String line = in.readLine();
            System.out.println(line);

            System.out.println("Closing connection  !!! ");

            socket.close();
            in.close();
        } catch (IOException i) {
            System.out.println(i);
        }
    }

    public static void main(String args[]) {
        TcpServerSocket server = new TcpServerSocket(5000);
    }
}
