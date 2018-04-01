package com.rabbitmq.lihongjie.demo.controller;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Example of rabbitmq.com
 */
public class Send {

  static Logger log = LoggerFactory.getLogger(Send.class);

  private static final String QUEUE_NAME = "hello";


  public static void main(String[] argv) throws Exception {

    ConnectionFactory factory = new ConnectionFactory();
    factory.setHost("localhost");
    Connection connection = factory.newConnection();
    Channel channel = connection.createChannel();
    
    channel.queueDeclare(QUEUE_NAME, false, false, false, null);


    String message = "hello";

    channel.basicPublish("", QUEUE_NAME, null, message.getBytes("UTF-8"));



    channel.close();
    connection.close();
  }
}
