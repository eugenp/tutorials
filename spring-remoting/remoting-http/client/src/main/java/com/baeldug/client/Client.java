package com.baeldug.client;

import com.baeldung.api.Booking;
import com.baeldung.api.BookingException;
import com.baeldung.api.CabBookingService;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.remoting.httpinvoker.HttpInvokerProxyFactoryBean;

import static java.lang.System.out;

@Configuration
public class Client {

    @Bean
    public HttpInvokerProxyFactoryBean invoker() {
        HttpInvokerProxyFactoryBean invoker = new HttpInvokerProxyFactoryBean();
        invoker.setServiceUrl("http://localhost:8080/booking");
        invoker.setServiceInterface(CabBookingService.class);
        return invoker;
    }

    public static void main(String[] args) throws BookingException {
        ConfigurableApplicationContext ctx = SpringApplication.run(Client.class, args);
        Booking booking = ctx.getBean(CabBookingService.class).bookRide("13 Seagate Blvd, Key Largo, FL 33037");
        out.println(booking);
    }

}
