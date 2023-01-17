package com.baeldung.queue.dynamic;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class DynamicQueueCreation {

    private static final String QUEUE_NAME = "baeldung-queue";

    private static Connection connection;

    public static void main(String[] args) throws IOException, TimeoutException {

        setUpConnection();

        Channel channel = setUpChannel();

        AMQP.Queue.DeclareOk declareOk = channel.queueDeclare(QUEUE_NAME, true, false, false, null);
        System.out.println(declareOk.getQueue());

        AMQP.Queue.DeclareOk declareOkExists = channel.queueDeclarePassive(QUEUE_NAME);
        System.out.println(declareOkExists.getQueue());
    }

    public static void setUpConnection() throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        connection = factory.newConnection();
    }

    public static Channel setUpChannel() throws IOException {
        return connection.createChannel();
    }

}
