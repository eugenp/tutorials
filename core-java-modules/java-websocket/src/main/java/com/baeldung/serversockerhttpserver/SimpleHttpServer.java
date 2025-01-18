package com.baeldung.serversockerhttpserver;

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

import java.io.IOException;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.time.LocalDateTime;

public class SimpleHttpServer {

    int port;
    Logger logger = LoggerFactory.getLogger(SimpleHttpServer.class);

    public SimpleHttpServer(int port) {
        this.port = port;
    }

    public void start() throws IOException {
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            logger.info("Server started on port: " + port);

            while (true) {

                Socket clientSocket = serverSocket.accept();

                try (BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream())); BufferedWriter out = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()))) {

                    String clientInputLine;
                    while ((clientInputLine = in.readLine()) != null) {
                        logger.info(clientInputLine);

                        if (clientInputLine.isEmpty()) {
                            break;
                        }

                        String body = """
                            <html>
                                <head>
                                    <title> Baeldung Home </title>
                                </head>
                                <body>
                                    <h1> Baeldung Home Page </h1>
                                    <p> Java Tutorials </p>
                                    <ul>
                                        <li> <a href="/java"> Java </a> </li>
                                        <li> <a href="/spring"> Spring </a> </li>
                                        <li> <a href="/hibernate"> Hibernate </a> </li>
                                    </ul>
                                </body>
                            </html>
                            """;

                        int length = body.length();

                        out.write("HTTP/1.0 200 OK\r\n");
                        out.write("Date: " + LocalDateTime.now() + "\r\n");
                        out.write("Server: Custom Server\r\n");
                        out.write("Content-Type: text/html\r\n");
                        out.write("Content-Length: " + length + "\r\n");
                        out.write("\r\n");
                        out.write(body);

                    }
                }
            }
        }

    }

    public static void main(String [] args) throws IOException {
        SimpleHttpServer simpleHttpServer = new SimpleHttpServer(8080);
        simpleHttpServer.start();
    }
}
