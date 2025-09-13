package com.baeldung.socketchannel;

import java.io.*;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;
import java.util.Set;

public class NonBlockingServer {
    private static final int PORT = 6000;

    public static void main(String[] args) throws IOException {
        ServerSocketChannel serverChannel = ServerSocketChannel.open();
        serverChannel.bind(new InetSocketAddress(PORT));
        serverChannel.configureBlocking(false);

        Selector selector = Selector.open();
        serverChannel.register(selector, SelectionKey.OP_ACCEPT);

        System.out.println("Non-blocking Server listening on port " + PORT);

        while (true) {
            selector.select();
            Set<SelectionKey> keys = selector.selectedKeys();
            Iterator<SelectionKey> iter = keys.iterator();

            while (iter.hasNext()) {
                SelectionKey key = iter.next();
                iter.remove();

                if (key.isAcceptable()) {
                    SocketChannel client = serverChannel.accept();
                    client.configureBlocking(false);
                    client.register(selector, SelectionKey.OP_READ);
                    System.out.println("New client connected");
                }
                else if (key.isReadable()) {
                    SocketChannel client = (SocketChannel) key.channel();
                    try {
                        MyObject obj = receiveObject(client);
                        if (obj != null) {
                            System.out.println("Received: " + obj.getName() + ", " + obj.getAge());
                        }
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                        client.close();
                    }
                }
            }
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

        if (bytesRead == -1) {
            channel.close();
            return null;
        }

        byte[] bytes = byteStream.toByteArray();
        try (ObjectInputStream objIn = new ObjectInputStream(new ByteArrayInputStream(bytes))) {
            return (MyObject) objIn.readObject();
        }
    }
}