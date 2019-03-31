package com.baeldung.hexagonalarchitecture.liquiditytracker.messaging;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.baeldung.hexagonalarchitecture.liquiditytracker.protoapi.LiquidityTrackerOutgoingMessage;
import com.baeldung.hexagonalarchitecture.liquiditytracker.protoapi.OutgoingMessageType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

/**
 * @author Víctor Gil
 *
 * since March 2019
 */
public class SenderImpl implements Sender {
    private static final Logger log = LoggerFactory.getLogger(SenderImpl.class);

    private Connection connection;
    private Channel channel;
    private String queueName;

    public void start() throws IOException {
        try {
            channel = connection.createChannel();
        } catch (IOException ex) {
            log.error("Unable to create channel.", ex);
            throw ex;
        }

        try {
            channel.queueDeclare(queueName, true, false, false, null);
        } catch (IOException ex) {
            log.error("Unable to declare the queue: " + ex.toString(), ex);
            closeChannelAndConnection();
            return;
        }
    }

    @Override
    public void sendLiquidityLimit(long value) {
        sendMessage(OutgoingMessageType.LIQUIDITY_LIMIT, value);
    }

    @Override
    public void sendAvailableLiquidity(long value) {
        sendMessage(OutgoingMessageType.AVAILABLE_LIQUIDITY, value);
    }

    @Override
    public void sendUtilizedLiquidity(long value) {
        sendMessage(OutgoingMessageType.UTILIZED_LIQUIDITY, value);
    }

    @Override
    public void sendSetLiquidityLimitError(String errorMessage) {
        sendErrorMessage(OutgoingMessageType.LIQUIDITY_LIMIT, errorMessage);

    }

    @Override
    public void increaseUtilizedLiquidityError(String errorMessage) {
        sendErrorMessage(OutgoingMessageType.UTILIZED_LIQUIDITY, errorMessage);
    }

    private void sendMessage(OutgoingMessageType type, long value) {
        LiquidityTrackerOutgoingMessage.Builder builder = LiquidityTrackerOutgoingMessage.newBuilder();
        builder.setType(type);
        builder.setValue(value);
        LiquidityTrackerOutgoingMessage message = builder.build();

        byte[] data = builder.build().toByteArray();
        try {
            channel.basicPublish("", queueName, null, data);
        } catch (IOException ex) {
            log.error("Unable to send message:\n" + message, ex);
        }
    }

    private void sendErrorMessage(OutgoingMessageType type, String errorMessage) {
        LiquidityTrackerOutgoingMessage.Builder builder = LiquidityTrackerOutgoingMessage.newBuilder();
        builder.setType(type);
        builder.setErrorMessage(errorMessage);
        LiquidityTrackerOutgoingMessage message = builder.build();

        byte[] data = builder.build().toByteArray();
        try {
            channel.basicPublish("", queueName, null, data);
        } catch (IOException ex) {
            log.error("Unable to send message:\n" + message, ex);
        }
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    public void setQueueName(String queueName) {
        this.queueName = queueName;
    }

    private void closeChannelAndConnection() {
        log.info("Closing the channel and the connection (if required).");
        if (channel != null) {
            try {
                channel.close();
            } catch (IOException | TimeoutException ex) {
                log.error("Unabel to close the channel: " + ex.toString(), ex);
            }
        }
        if (connection != null) {
            try {
                connection.close();
            } catch (IOException ex) {
                log.error("Unable to close the connection: " + ex.toString(), ex);
            }
        }
    }
}
