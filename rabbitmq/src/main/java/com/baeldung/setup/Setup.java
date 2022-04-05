package com.baeldung.setup;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeoutException;

public class Setup {

    private static final String ORDERS_QUEUE_NAME = "orders-queue";
    private static final String ORDERS_EXCHANGE_NAME = "orders-direct-exchange";
    private static final String ORDERS_ALTERNATE_EXCHANGE_NAME = "orders-alternate-exchange";
    private static final String ORDERS_ROUTING_KEY = "orders-routing-key";

    public static void main(String[] args) throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        Map<String, Object> exchangeArguments = new HashMap<>();
        exchangeArguments.put("alternate-exchange", ORDERS_ALTERNATE_EXCHANGE_NAME);
        channel.exchangeDeclare(ORDERS_EXCHANGE_NAME, BuiltinExchangeType.DIRECT, true, false, exchangeArguments);

        Map<String, Object> queueArguments = new HashMap<>();
        queueArguments.put("x-message-ttl", 60000);
        queueArguments.put("x-max-priority", 10);
        channel.queueDeclare(ORDERS_QUEUE_NAME, true, false, false, queueArguments);

        channel.queueBind(ORDERS_QUEUE_NAME, ORDERS_EXCHANGE_NAME, ORDERS_ROUTING_KEY);
    }

}
