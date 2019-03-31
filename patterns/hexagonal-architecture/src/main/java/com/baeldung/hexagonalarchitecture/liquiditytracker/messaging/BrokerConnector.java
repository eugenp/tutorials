package com.baeldung.hexagonalarchitecture.liquiditytracker.messaging;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.baeldung.hexagonalarchitecture.liquiditytracker.config.MessagingConfigValues;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

/**
 * @author Víctor Gil
 *
 * since March 2019
 */
public class BrokerConnector{
    private static final Logger log = LoggerFactory.getLogger(BrokerConnector.class);

    private MessagingConfigValues configValues;
    private Connection connection;
    private MessageListener messageListener;
    private SenderImpl sender;    
    
    public void start() throws Exception {
        connect(configValues.getBrokerHostname(), configValues.getUsername(), configValues.getPassword());
        
        messageListener.setQueueName(configValues.getIncomingMessagesQueue());
        messageListener.setConnection(connection);
        
        sender.setConnection(connection);
        sender.setQueueName(configValues.getOutgoingMessagesQueue());
    }
    
    public void connect(String brokerHost, String username, String password) throws Exception{
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(brokerHost);
        factory.setUsername(username);
        factory.setPassword(password);
        
        try{
            connection = factory.newConnection();
        } catch(IOException | TimeoutException ex){
            log.error("FATAL: Unable to create connection: " + ex, ex);            
            throw ex;
        }
    }
    
    public void setMessageListener(MessageListener messageListener) {
        this.messageListener = messageListener;
    }

    public void setConfigValues(MessagingConfigValues configValues) {
        this.configValues = configValues;
    }
    
    public void setSender(SenderImpl sender) {
        this.sender = sender;
    }
}
