package com.baeldung.server;

import com.baeldung.api.CabBookingService;
import org.apache.activemq.command.ActiveMQQueue;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.jms.listener.SimpleMessageListenerContainer;
import org.springframework.jms.remoting.JmsInvokerServiceExporter;

import javax.jms.ConnectionFactory;
import javax.jms.Queue;

@SpringBootApplication public class JmsServer {

    /*
    This server needs to be connected to an ActiveMQ server.
    To quickly spin up an ActiveMQ server, you can use Docker.

    docker run -p 61616:61616 -p 8161:8161 rmohr/activemq:5.14.3
     */

    @Bean CabBookingService bookingService() {
        return new CabBookingServiceImpl();
    }

    @Bean Queue queue() {
    return new ActiveMQQueue("remotingQueue");
}

    @Bean JmsInvokerServiceExporter exporter(CabBookingService implementation) {
        JmsInvokerServiceExporter exporter = new JmsInvokerServiceExporter();
        exporter.setServiceInterface(CabBookingService.class);
        exporter.setService(implementation);
        return exporter;
    }

    @Bean SimpleMessageListenerContainer listener(ConnectionFactory factory, JmsInvokerServiceExporter exporter) {
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
        container.setConnectionFactory(factory);
        container.setDestinationName("remotingQueue");
        container.setConcurrentConsumers(1);
        container.setMessageListener(exporter);
        return container;
    }

    public static void main(String[] args) {
        SpringApplication.run(JmsServer.class, args);
    }

}
