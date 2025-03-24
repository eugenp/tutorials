package com.baeldung.rest;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import java.io.*;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class RestApiServer implements HttpHandler {
    private final List<String> users = new ArrayList<>();

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String method = exchange.getRequestMethod();
        switch (method) {
            case "GET" -> handleGet(exchange);
            case "POST" -> handlePost(exchange);
            case "PUT" -> handlePut(exchange);
            case "DELETE" -> handleDelete(exchange);
            default -> sendResponse(exchange, 405, "Method Not Allowed");
        }
    }

    private void handleGet(HttpExchange exchange) throws IOException {
        sendResponse(exchange, 200, users.toString());
    }

    private void handlePost(HttpExchange exchange) throws IOException {
        String newUser = readRequestBody(exchange);
        if (!newUser.isBlank()) {
            users.add(newUser);
            sendResponse(exchange, 201, "User added: " + newUser);
        } else {
            sendResponse(exchange, 400, "Invalid user data");
        }
    }

    private void handlePut(HttpExchange exchange) throws IOException {
        String body = readRequestBody(exchange);
        String[] parts = body.split(":", 2);
        if (parts.length == 2) {
            int index = Integer.parseInt(parts[0]);
            String newName = parts[1];
            if (index >= 0 && index < users.size()) {
                users.set(index, newName);
                sendResponse(exchange, 200, "User updated: " + newName);
            } else {
                sendResponse(exchange, 404, "User not found");
            }
        } else {
            sendResponse(exchange, 400, "Invalid input format");
        }
    }

    private void handleDelete(HttpExchange exchange) throws IOException {
        String body = readRequestBody(exchange);
        int index;
        try {
            index = Integer.parseInt(body);
            if (index >= 0 && index < users.size()) {
                String removedUser = users.remove(index);
                sendResponse(exchange, 200, "User deleted: " + removedUser);
            } else {
                sendResponse(exchange, 404, "User not found");
            }
        } catch (NumberFormatException e) {
            sendResponse(exchange, 400, "Invalid index");
        }
    }

    private void sendResponse(HttpExchange exchange, int statusCode, String response) throws IOException {
        exchange.sendResponseHeaders(statusCode, response.length());
        OutputStream os = exchange.getResponseBody();
        os.write(response.getBytes(StandardCharsets.UTF_8));
        os.close();
    }

    private String readRequestBody(HttpExchange exchange) throws IOException {
        InputStream is = exchange.getRequestBody();
        return new String(is.readAllBytes(), StandardCharsets.UTF_8);
    }

    // Helper methods for testing
    public List<String> getUsers() {
        return new ArrayList<>(users); // Return a copy to prevent external modification
    }

    public void addUser(String user) {
        users.add(user);
    }

    public void clearUsers() {
        users.clear();
    }

    public static void main(String[] args) throws IOException {
        HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0);
        server.createContext("/users", new RestApiServer());
        server.setExecutor(null);
        System.out.println("Server started at http://localhost:8080/users");
        server.start();
    }
}