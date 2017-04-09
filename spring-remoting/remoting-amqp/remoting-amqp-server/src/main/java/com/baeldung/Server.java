package com.baeldung;

import com.baeldung.api.CabBookingService;
import com.baeldung.server.CabBookingServiceImpl;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.remoting.service.AmqpInvokerServiceExporter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.remoting.support.RemoteExporter;

@Configuration @ComponentScan @EnableAutoConfiguration public class Server {

    /*
    Please note that
    - CachingConnectionFactory
    - RabbitAdmin
    - AmqpTemplate
    are automatically declared by SpringBoot.
     */

    @Bean CabBookingService bookingService() {
        return new CabBookingServiceImpl();
    }

    @Bean RemoteExporter listener(RabbitTemplate amqTemplate, CabBookingService service) {
        AmqpInvokerServiceExporter exporter = new AmqpInvokerServiceExporter();
        exporter.setService(service);
        exporter.setServiceInterface(CabBookingService.class);
        exporter.setAmqpTemplate(amqTemplate);
        return exporter;
    }

    @Bean Queue queue() {
        Queue name = new Queue("remotingQueue");
        return name;
    }

    @Bean AmqpInvokerServiceExporter exporter(CabBookingService implementation, AmqpTemplate template) {
        AmqpInvokerServiceExporter exporter = new AmqpInvokerServiceExporter();
        exporter.setServiceInterface(CabBookingService.class);
        exporter.setService(implementation);
        exporter.setAmqpTemplate(template);
        return exporter;
    }

    @Bean SimpleMessageListenerContainer listener(ConnectionFactory facotry, AmqpInvokerServiceExporter listener, Queue queue) {
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer(facotry);
        container.setMessageListener(listener);
        container.setQueueNames(queue.getName());
        container.start();
        return container;
    }

    public static void main(String[] args) {
        SpringApplication.run(Server.class, args);
    }

}