package com.baeldung.server;

import com.baeldung.api.CabBookingService;
import org.apache.activemq.command.ActiveMQQueue;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.jms.listener.SimpleMessageListenerContainer;
import org.springframework.jms.remoting.JmsInvokerServiceExporter;

import javax.jms.ConnectionFactory;

@SpringBootApplication public class JmsServer {

    /*
    This server needs to be connected to an ActiveMQ server.
    To quickly spin up an ActiveMQ server, you can use Docker.

    docker run -p 61616:61616 -p 8161:8161 rmohr/activemq:5.14.3
     */

//    @Bean ConnectionFactory connectionFactory() {
//        ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory();
//        factory.setBrokerURL("tcp://192.168.99.100:61616");
//        factory.setTrustedPackages(asList("org.springframework.remoting.support", "java.lang", "com.baeldung.api"));
//        //factory.setTrustAllPackages(true);
//        return factory;
//    }

    @Bean @Qualifier("queueName") String queueName() {
        return "remotingQueue";
    }

    @Bean
    ActiveMQQueue queue(@Qualifier("queueName") String queueName) {
        ActiveMQQueue queue = new ActiveMQQueue(queueName);
        return queue;
    }

    @Bean JmsInvokerServiceExporter exporter(CabBookingService implementation) {
        JmsInvokerServiceExporter exporter = new JmsInvokerServiceExporter();
        exporter.setServiceInterface(CabBookingService.class);
        exporter.setService(implementation);
        return exporter;
    }

    @Bean SimpleMessageListenerContainer listener(ConnectionFactory factory, @Qualifier("queueName") String queueName, JmsInvokerServiceExporter exporter) {
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
        container.setConnectionFactory(factory);
        container.setDestinationName(queueName);
        container.setConcurrentConsumers(1);
        container.setMessageListener(exporter);
        return container;
    }

    @Bean CabBookingService bookingService() {
        return new CabBookingServiceImpl();
    }

    public static void main(String[] args) {
        SpringApplication.run(JmsServer.class, args);
    }

}
