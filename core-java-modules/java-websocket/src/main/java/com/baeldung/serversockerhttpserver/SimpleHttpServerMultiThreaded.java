package com.baeldung.serversockerhttpserver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.time.LocalDateTime;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SimpleHttpServerMultiThreaded {

    private final int port;
    private static final Logger logger = LoggerFactory.getLogger(SimpleHttpServerMultiThreaded.class);
    private static final int THREAD_POOL_SIZE = 10;

    public SimpleHttpServerMultiThreaded(int port) {
        this.port = port;
    }

    public void start() throws IOException {

        try (ExecutorService threadPool = Executors.newFixedThreadPool(THREAD_POOL_SIZE); ServerSocket serverSocket = new ServerSocket(port)) {
            logger.info("Server started on port: {}", port);

            while (true) {
                Socket clientSocket = serverSocket.accept();
                threadPool.execute(() -> handleClient(clientSocket));
            }
        }
    }

    private void handleClient(Socket clientSocket) {
        try (BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream())); BufferedWriter out = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()))) {

            String clientInputLine;
            while ((clientInputLine = in.readLine()) != null) {
                logger.info("Client Request: {}", clientInputLine);
                if (clientInputLine.isEmpty()) {
                    break;
                }
            }

            String body = """
                <html>
                    <head><title>Baeldung Home</title></head>
                    <body>
                        <h1>Baeldung Home Page</h1>
                        <p>Java Tutorials</p>
                        <ul>
                            <li><a href="https://www.baeldung.com/get-started-with-java-series">Java</a></li>
                            <li><a href="https://www.baeldung.com/spring-boot">Spring</a></li>
                            <li><a href="https://www.baeldung.com/learn-jpa-hibernate">Hibernate</a></li>
                        </ul>
                    </body>
                </html>
                """;

            int length = body.length();
            LocalDateTime now = LocalDateTime.now();

            out.write("HTTP/1.0 200 OK\r\n");
            out.write("Date: " + now + "\r\n");
            out.write("Server: Custom Server\r\n");
            out.write("Content-Type: text/html\r\n");
            out.write("Content-Length: " + length + "\r\n");
            out.write("\r\n");
            out.write(body);

        } catch (IOException e) {
            logger.error("Error handling client", e);
        } finally {
            try {
                clientSocket.close();
            } catch (IOException e) {
                logger.error("Error closing client socket", e);
            }
        }
    }

    public static void main(String[] args) throws IOException {
        int port = 8080;
        SimpleHttpServerMultiThreaded server = new SimpleHttpServerMultiThreaded(port);
        server.start();
    }
}
