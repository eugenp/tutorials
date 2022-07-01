package com.baeldung.client;

import com.baeldung.api.BookingException;
import com.baeldung.api.CabBookingService;
import org.springframework.boot.SpringApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.remoting.caucho.HessianProxyFactoryBean;

import static java.lang.System.out;

@Configuration
public class HessianClient {

    @Bean
    public HessianProxyFactoryBean hessianInvoker() {
        HessianProxyFactoryBean invoker = new HessianProxyFactoryBean();
        invoker.setServiceUrl("http://localhost:8032/booking");
        invoker.setServiceInterface(CabBookingService.class);
        return invoker;
    }

    public static void main(String[] args) throws BookingException {
        CabBookingService service = SpringApplication.run(HessianClient.class, args).getBean(CabBookingService.class);
        out.println(service.bookRide("13 Seagate Blvd, Key Largo, FL 33037"));
    }

}
