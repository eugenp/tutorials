package com.baeldung.producer;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class ProductService {

    private static final String EXCHANGE_NAME = "products";

    public static void main(String[]args) throws IOException, TimeoutException {
        publish();
    }

    public static void publish () throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        String specialProductMessage = "Special product details";
        String acceptedProductMessage = "Accepted product details";
        String rejectedProductMessage = "Rejected product details";

        channel.exchangeDeclare("products", "topic");


        channel.basicPublish(EXCHANGE_NAME, "accepted.special", null, specialProductMessage.getBytes());
        System.out.println("Sent message '" + specialProductMessage + "'");
        channel.basicPublish(EXCHANGE_NAME, "accepted.", null, acceptedProductMessage.getBytes());
        System.out.println("Sent message '" + acceptedProductMessage + "'");
        channel.basicPublish(EXCHANGE_NAME, "rejected.", null, rejectedProductMessage.getBytes());
        System.out.println("Sent message '" + rejectedProductMessage + "'");


        channel.close();
        connection.close();
    }
}
