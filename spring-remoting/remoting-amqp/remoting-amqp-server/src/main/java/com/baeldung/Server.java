package com.baeldung;

import com.baeldung.api.CabBookingService;
import com.baeldung.server.CabBookingServiceImpl;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.remoting.service.AmqpInvokerServiceExporter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.remoting.support.RemoteExporter;

@Configuration @ComponentScan @EnableAutoConfiguration public class Server {

    @Bean CabBookingService bookingService() {
        return new CabBookingServiceImpl();
    }

    @Bean RemoteExporter listener(RabbitTemplate amqTemplate) {
        AmqpInvokerServiceExporter exporter = new AmqpInvokerServiceExporter();
        exporter.setService(bookingService());
        exporter.setServiceInterface(CabBookingService.class);
        exporter.setAmqpTemplate(amqTemplate);
        return exporter;
    }

    public static void main(String[] args) {
        SpringApplication.run(Server.class, args);
    }

}