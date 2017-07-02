package com.baeldung.server;

import com.baeldung.api.CabBookingService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.remoting.rmi.RmiServiceExporter;

@SpringBootApplication public class RmiServer {

    @Bean CabBookingService bookingService() {
        return new CabBookingServiceImpl();
    }

    @Bean RmiServiceExporter exporter(CabBookingService implementation) {
        Class<CabBookingService> serviceInterface = CabBookingService.class;
        RmiServiceExporter exporter = new RmiServiceExporter();
        exporter.setServiceInterface(serviceInterface);
        exporter.setService(implementation);
        exporter.setServiceName(serviceInterface.getSimpleName());
        exporter.setRegistryPort(1099); // 1099 default port
        // rmi://HOST:1199/AccountService
        return exporter;
    }

    public static void main(String[] args) {
        SpringApplication.run(RmiServer.class, args);
    }

}
