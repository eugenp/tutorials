package com.baeldung.springamqp.errorhandling.configuration;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static com.baeldung.springamqp.errorhandling.configuration.SimpleDLQAmqpConfiguration.EXCHANGE_MESSAGES;
import static com.baeldung.springamqp.errorhandling.configuration.SimpleDLQAmqpConfiguration.QUEUE_MESSAGES;
import static com.baeldung.springamqp.errorhandling.configuration.SimpleDLQAmqpConfiguration.QUEUE_MESSAGES_DLQ;

@Configuration
@ConditionalOnProperty(
  value = "amqp.configuration.current",
  havingValue = "dlx-custom")
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
