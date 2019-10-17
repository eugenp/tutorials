package com.baeldung.springamqp.errorhandling.configuration;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;

import static com.baeldung.springamqp.errorhandling.configuration.SimpleDLQAmqpConfiguration.*;

//@Configuration
public class DLXCustomAmqpConfiguration {
    public static final String DLX_EXCHANGE_MESSAGES = QUEUE_MESSAGES + ".dlx";

    @Bean
    Queue messagesQueue() {
        return QueueBuilder.durable(QUEUE_MESSAGES)
                .withArgument("x-dead-letter-exchange", DLX_EXCHANGE_MESSAGES)
                .build();
    }

    @Bean
    FanoutExchange deadLetterExchange() {
        return new FanoutExchange(DLX_EXCHANGE_MESSAGES);
    }

    @Bean
    Queue deadLetterQueue() {
        return QueueBuilder.durable(QUEUE_MESSAGES_DLQ).build();
    }

    @Bean
    Binding deadLetterBinding() {
        return BindingBuilder.bind(deadLetterQueue()).to(deadLetterExchange());
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
