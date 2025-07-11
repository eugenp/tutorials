package com.baeldung.smithy.books.server;

import com.baeldung.smithy.books.server.service.*;
import java.net.URI;
import java.util.logging.Logger;
import software.amazon.smithy.java.server.Server;

public class Main {
    public static void main(String... args) throws Exception {
        Server server = Server.builder()
            .endpoints(URI.create("http://localhost:8888"))
            .addService(
                BookManagementService.builder()
                    .addCreateBookOperation(new CreateBookOperationImpl())
                    .addGetBookOperation(new GetBookOperationImpl())
                    .addListBooksOperation(new ListBooksOperationImpl())
                    .addRecommendBookOperation(new RecommendBookOperationImpl())
                    .build()
            )
            .build();
        System.out.println("Starting server...");

        server.start();

        try {
            Thread.currentThread().join();
        } catch (InterruptedException e) {
            System.out.println("Stopping server...");
            try {
                server.shutdown().get();
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        }
    }
}

