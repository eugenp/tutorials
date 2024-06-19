package com.baeldung.grpc.orderprocessing.server;

import java.io.IOException;

import com.baeldung.grpc.orderprocessing.orderprocessing.GlobalExceptionInterceptor;
import com.baeldung.grpc.orderprocessing.orderprocessing.LogInterceptor;
import com.baeldung.grpc.orderprocessing.orderprocessing.OrderProcessorImpl;

import io.grpc.Server;
import io.grpc.ServerBuilder;

public class OrderProcessingServer {
    public static void main(String[] args) throws IOException, InterruptedException {
        Server server = ServerBuilder.forPort(8080)
            .addService(new OrderProcessorImpl())
            .intercept(new LogInterceptor())
            .intercept(new GlobalExceptionInterceptor())
            .build();
        server.start();
        server.awaitTermination();
    }
}
