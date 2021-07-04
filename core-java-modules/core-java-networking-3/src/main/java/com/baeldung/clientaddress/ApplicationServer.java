package com.baeldung.clientaddress;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class ApplicationServer {

    private ServerSocket serverSocket;
    private Socket conncetedSocket;
    private PrintWriter out;
    private BufferedReader in;

    public void startServer(int port) {
        try {
            serverSocket = new ServerSocket(port);
            conncetedSocket = serverSocket.accept();

            InetSocketAddress socketAddress = (InetSocketAddress) conncetedSocket.getRemoteSocketAddress();
            String clientIpAddress = socketAddress.getAddress()
                .getHostAddress();
            System.out.println("IP address of the connected client :: " + clientIpAddress);

            out = new PrintWriter(conncetedSocket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(conncetedSocket.getInputStream()));

            while (in.readLine() != null) {
                String msg = in.readLine();
                if (msg.contains("Hello")) {
                    System.out.println(msg);
                    out.println("Hello Client !!");
                } else {
                    out.println("Not valid message !! ");
                    break;
                }
            }
            closeIO();
            stopServer();
        } catch (IOException e) {

        }
    }

    private void closeIO() throws IOException {
        in.close();
        out.close();
    }

    private void stopServer() throws IOException {
        conncetedSocket.close();
        serverSocket.close();
    }
}
