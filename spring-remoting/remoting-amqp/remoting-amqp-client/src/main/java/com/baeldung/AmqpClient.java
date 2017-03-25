package com.baeldung;

import com.baeldung.api.BookingException;
import com.baeldung.api.CabBookingService;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.amqp.remoting.client.AmqpProxyFactoryBean;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import static java.lang.System.out;

@SpringBootApplication
public class AmqpClient {

    @Bean AmqpProxyFactoryBean xxx(AmqpTemplate amqpTemplate) {
        AmqpProxyFactoryBean proxy = new AmqpProxyFactoryBean();
        proxy.setAmqpTemplate(amqpTemplate);
        proxy.setServiceInterface(CabBookingService.class);
        return proxy;
    }

    @Bean
    Queue queue() {
        return new Queue("cicci", false);
    }

    @Bean TopicExchange exchange() {
        return new TopicExchange("spring-boot-exchange");
    }

    @Bean Binding binding(Queue queue, TopicExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with("cicci");
    }


    @Bean SimpleMessageListenerContainer container(ConnectionFactory connectionFactory,
            MessageListenerAdapter listenerAdapter) {
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.setQueueNames("cicci");
        container.setMessageListener(listenerAdapter);
        return container;
    }

    public static void main(String[] args) throws BookingException {
        CabBookingService service = SpringApplication.run(AmqpClient.class, args).getBean(CabBookingService.class);
        out.println(service.bookRide("13 Seagate Blvd, Key Largo, FL 33037"));
    }

}
