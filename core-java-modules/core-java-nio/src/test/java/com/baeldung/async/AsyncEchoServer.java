package com.baeldung.async;

import java.io.File;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.nio.channels.AsynchronousSocketChannel;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class AsyncEchoServer {
    private AsynchronousServerSocketChannel serverChannel;
    private Future<AsynchronousSocketChannel> acceptResult;
    private AsynchronousSocketChannel clientChannel;

    public AsyncEchoServer() {
        try {
            serverChannel = AsynchronousServerSocketChannel.open();
            InetSocketAddress hostAddress = new InetSocketAddress("localhost", 4999);
            serverChannel.bind(hostAddress);
            acceptResult = serverChannel.accept();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void runServer() {
        try {
            clientChannel = acceptResult.get();
            if ((clientChannel != null) && (clientChannel.isOpen())) {
                while (true) {

                    ByteBuffer buffer = ByteBuffer.allocate(32);
                    Future<Integer> readResult = clientChannel.read(buffer);

                    // do some computation

                    readResult.get();

                    buffer.flip();
                    String message = new String(buffer.array()).trim();
                    if (message.equals("bye")) {
                        break; // while loop
                    }
                    buffer = ByteBuffer.wrap(new String(message).getBytes());
                    Future<Integer> writeResult = clientChannel.write(buffer);

                    // do some computation
                    writeResult.get();
                    buffer.clear();

                } // while()

                clientChannel.close();
                serverChannel.close();

            }
        } catch (InterruptedException | ExecutionException | IOException e) {
            e.printStackTrace();
        }

    }

    public static void main(String[] args) {
        AsyncEchoServer server = new AsyncEchoServer();
        server.runServer();
    }

    public static Process start() throws IOException, InterruptedException {
        String javaHome = System.getProperty("java.home");
        String javaBin = javaHome + File.separator + "bin" + File.separator + "java";
        String classpath = System.getProperty("java.class.path");
        String className = AsyncEchoServer.class.getCanonicalName();

        ProcessBuilder builder = new ProcessBuilder(javaBin, "-cp", classpath, className);

        return builder.start();
    }
}