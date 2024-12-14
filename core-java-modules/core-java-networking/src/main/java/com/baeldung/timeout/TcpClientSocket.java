package com.baeldung.timeout;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.net.UnknownHostException;

public class TcpClientSocket {

    private Socket socket;
    private PrintStream out;
    private BufferedReader in;

    public void connect(String host, int port) {
        try {
            socket = new Socket(host, port);
            socket.setSoTimeout(30000);
            System.out.println("connected to " + host + " on port " + port);

            out = new PrintStream(socket.getOutputStream(), true);
            System.out.println("Sending message ... ");
            out.println("Hello world");

            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out.println(in.readLine());

            System.out.println("Closing connection !!! ");
            in.close();
            out.close();
            socket.close();

        } catch (UnknownHostException e) {
            System.err.println(e);
        } catch (IOException e) {
            System.err.println(e);
        }
    }

    public static void main(String[] args) throws IOException {
        TcpClientSocket client = new TcpClientSocket();
        client.connect("127.0.0.1", 5000);
    }

}
