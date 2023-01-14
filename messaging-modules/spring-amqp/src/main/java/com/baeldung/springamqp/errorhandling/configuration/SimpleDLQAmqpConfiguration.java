package com.baeldung.springamqp.errorhandling.configuration;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnProperty(
  value = "amqp.configuration.current",
  havingValue = "simple-dlq")
public class SimpleDLQAmqpConfiguration {
    public static final String QUEUE_MESSAGES = "baeldung-messages-queue";
    public static final String QUEUE_MESSAGES_DLQ = QUEUE_MESSAGES + ".dlq";
    public static final String EXCHANGE_MESSAGES = "baeldung-messages-exchange";

    @Bean
    Queue messagesQueue() {
        return QueueBuilder.durable(QUEUE_MESSAGES)
          .withArgument("x-dead-letter-exchange", "")
          .withArgument("x-dead-letter-routing-key", QUEUE_MESSAGES_DLQ)
          .build();
    }

    @Bean
    Queue deadLetterQueue() {
        return QueueBuilder.durable(QUEUE_MESSAGES_DLQ).build();
    }

    @Bean
    DirectExchange messagesExchange() {
        return new DirectExchange(EXCHANGE_MESSAGES);
    }

    @Bean
    Binding bindingMessages() {
        return BindingBuilder.bind(messagesQueue()).to(messagesExchange()).with(QUEUE_MESSAGES);
    }
}
