package com.baeldung.clientaddress;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ApplicationClient {

    private Socket clientSocket;
    private PrintWriter out;
    private BufferedReader in;

    public void connect(String ip, int port) throws IOException {
        clientSocket = new Socket(ip, port);
        out = new PrintWriter(clientSocket.getOutputStream(), true);
        in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
    }

    public void sendGreetings(String msg) throws IOException {
        out.println(msg);
        String reply = in.readLine();
        System.out.println("Reply received from the server :: " + reply);
    }

    public void disconnect() throws IOException {
        in.close();
        out.close();
        clientSocket.close();
    }

    public static void main(String[] args) throws IOException {
        ApplicationClient client = new ApplicationClient();
        client.connect(args[0], Integer.parseInt(args[1])); // IP address and port number of the server
        client.sendGreetings(args[2]); // greetings message
        client.disconnect();
    }
}
