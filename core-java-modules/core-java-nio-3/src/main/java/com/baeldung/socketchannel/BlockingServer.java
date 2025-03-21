package com.baeldung.socketchannel;

import java.io.*;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

public class BlockingServer {
    private static final int PORT = 6000;

    public static void main(String[] args) {
        try (ServerSocketChannel serverSocket = ServerSocketChannel.open()) {
            serverSocket.bind(new InetSocketAddress(PORT));
            System.out.println("Blocking Server listening on port " + PORT);

            while (true) {
                try (SocketChannel clientSocket = serverSocket.accept()) {
                    System.out.println("Client connected");
                    MyObject obj = receiveObject(clientSocket);
                    System.out.println("Received: " + obj.getName() + ", " + obj.getAge());
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static MyObject receiveObject(SocketChannel channel)
        throws IOException, ClassNotFoundException {
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        ByteArrayOutputStream byteStream = new ByteArrayOutputStream();

        int bytesRead;
        while ((bytesRead = channel.read(buffer)) > 0) {
            buffer.flip();
            byteStream.write(buffer.array(), 0, buffer.limit());
            buffer.clear();
        }

        byte[] bytes = byteStream.toByteArray();
        try (ObjectInputStream objIn = new ObjectInputStream(new ByteArrayInputStream(bytes))) {
            return (MyObject) objIn.readObject();
        }
    }
}