package com.baeldung.server;

import com.baeldung.api.CabBookingService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.remoting.caucho.BurlapServiceExporter;
import org.springframework.remoting.caucho.HessianServiceExporter;
import org.springframework.remoting.support.RemoteExporter;

import java.util.Collections;

@Configuration @ComponentScan @EnableAutoConfiguration public class Server {

    @Bean CabBookingService bookingService() {
        return new CabBookingServiceImpl();
    }

    @Bean(name = "/booking") RemoteExporter hessianService(CabBookingService service) {
        HessianServiceExporter exporter = new HessianServiceExporter();
        exporter.setService(bookingService());
        exporter.setServiceInterface(CabBookingService.class);
        return exporter;
    }

    @Bean(name = "/b_booking") RemoteExporter burlapService(CabBookingService service) {
        BurlapServiceExporter exporter = new BurlapServiceExporter();
        exporter.setService(bookingService());
        exporter.setServiceInterface(CabBookingService.class);
        return exporter;
    }

    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(Server.class);
        app.setDefaultProperties(Collections.singletonMap("server.port", "8032"));
        app.run(args);
    }

}