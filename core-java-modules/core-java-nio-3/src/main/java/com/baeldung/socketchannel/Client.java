package com.baeldung.socketchannel;

import java.io.*;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

public class Client {
    private static final String SERVER_ADDRESS = "localhost";
    private static final int SERVER_PORT = 6000;

    public static void main(String[] args) {
        try (SocketChannel socketChannel = SocketChannel.open()) {
            socketChannel.connect(new InetSocketAddress(SERVER_ADDRESS, SERVER_PORT));
            System.out.println("Connected to server");

            MyObject objectToSend = new MyObject("Alice", 25);
            sendObject(socketChannel, objectToSend);
            System.out.println("Object sent");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void sendObject(SocketChannel channel, MyObject obj)
        throws IOException {
        ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
        try (ObjectOutputStream objOut = new ObjectOutputStream(byteStream)) {
            objOut.writeObject(obj);
        }
        byte[] bytes = byteStream.toByteArray();

        ByteBuffer buffer = ByteBuffer.wrap(bytes);
        while (buffer.hasRemaining()) {
            channel.write(buffer);
        }
    }
}