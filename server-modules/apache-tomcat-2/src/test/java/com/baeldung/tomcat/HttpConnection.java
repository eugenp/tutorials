package com.baeldung.tomcat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.util.stream.Collectors;

public class HttpConnection {
     static int getResponseCode(int port) throws Exception {
        HttpURLConnection connection = getConnection(port);
        try {
            return connection.getResponseCode();
        } finally {
            connection.disconnect();
        }
    }

     static String getContent(int port) throws Exception {
        HttpURLConnection connection = getConnection(port);
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
            return reader.lines().collect(Collectors.joining());
        } finally {
            connection.disconnect();
        }
    }

     static HttpURLConnection getConnection(int port) throws IOException {
        URL url = URI.create("http://localhost:" + port + "/").toURL();
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.setConnectTimeout(5000);
        connection.setReadTimeout(5000);
        return connection;
    }
}
