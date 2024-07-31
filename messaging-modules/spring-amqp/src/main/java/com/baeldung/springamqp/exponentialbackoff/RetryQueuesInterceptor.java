package com.baeldung.springamqp.exponentialbackoff;

import java.util.function.BiConsumer;
import java.util.function.Consumer;

import com.rabbitmq.client.Channel;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

public class RetryQueuesInterceptor implements MethodInterceptor {

    private RabbitTemplate rabbitTemplate;

    private RetryQueues retryQueues;

    private Runnable observer;

    public RetryQueuesInterceptor(RabbitTemplate rabbitTemplate, RetryQueues retryQueues) {
        this.rabbitTemplate = rabbitTemplate;
        this.retryQueues = retryQueues;
    }

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        return tryConsume(invocation, this::ack, (mac, e) -> {
            try {
                int retryCount = tryGetRetryCountOrFail(mac, e);
                sendToNextRetryQueue(mac, retryCount);
            } catch (Throwable t) {
                if (observer != null) {
                    observer.run();
                }

                throw new RuntimeException(t);
            }
        });
    }

    void setObserver(Runnable observer) {
        this.observer = observer;
    }

    private Object tryConsume(MethodInvocation invocation, Consumer<MessageAndChannel> successHandler, BiConsumer<MessageAndChannel, Throwable> errorHandler) throws Throwable {
        MessageAndChannel mac = new MessageAndChannel((Message) invocation.getArguments()[1], (Channel) invocation.getArguments()[0]);
        Object ret = null;
        try {
            ret = invocation.proceed();
            successHandler.accept(mac);
        } catch (Throwable e) {
            errorHandler.accept(mac, e);
        }
        return ret;
    }

    private void ack(MessageAndChannel mac) {
        try {
            mac.channel.basicAck(mac.message.getMessageProperties()
                .getDeliveryTag(), false);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private int tryGetRetryCountOrFail(MessageAndChannel mac, Throwable originalError) throws Throwable {
        MessageProperties props = mac.message.getMessageProperties();

        String xRetriedCountHeader = (String) props.getHeader("x-retried-count");
        final int xRetriedCount = xRetriedCountHeader == null ? 0 : Integer.valueOf(xRetriedCountHeader);

        if (retryQueues.retriesExhausted(xRetriedCount)) {
            mac.channel.basicReject(props.getDeliveryTag(), false);

            throw originalError;
        }

        return xRetriedCount;
    }

    private void sendToNextRetryQueue(MessageAndChannel mac, int retryCount) throws Exception {
        String retryQueueName = retryQueues.getQueueName(retryCount);

        rabbitTemplate.convertAndSend(retryQueueName, mac.message, m -> {
            MessageProperties props = m.getMessageProperties();
            props.setExpiration(String.valueOf(retryQueues.getTimeToWait(retryCount)));
            props.setHeader("x-retried-count", String.valueOf(retryCount + 1));
            props.setHeader("x-original-exchange", props.getReceivedExchange());
            props.setHeader("x-original-routing-key", props.getReceivedRoutingKey());

            return m;
        });

        mac.channel.basicReject(mac.message.getMessageProperties()
            .getDeliveryTag(), false);
    }

    private class MessageAndChannel {
        private Message message;
        private Channel channel;

        private MessageAndChannel(Message message, Channel channel) {
            this.message = message;
            this.channel = channel;
        }
    }
}