package com.baeldung.cxf.spring;

import javax.xml.ws.Endpoint;

import org.apache.cxf.Bus;
import org.apache.cxf.bus.spring.SpringBus;
import org.apache.cxf.jaxws.EndpointImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class ServiceConfiguration {
    @Bean(name = Bus.DEFAULT_BUS_ID)
    private SpringBus springBus() {
        return new SpringBus();
    }

    @Bean
    public Endpoint endpoint() {
        EndpointImpl endpoint = new EndpointImpl(springBus(), new BaeldungImpl());
        endpoint.publish("http://localhost:8081/services/baeldung");
        return endpoint;
    }
}