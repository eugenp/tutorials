package com.baeldung.client;

import com.baeldung.api.Booking;
import com.baeldung.api.BookingException;
import com.baeldung.api.CabBookingService;
import org.apache.activemq.command.ActiveMQQueue;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.jms.remoting.JmsInvokerProxyFactoryBean;

import javax.jms.ConnectionFactory;
import javax.jms.Queue;

@SpringBootApplication
public class JmsClient {

    @Bean Queue queue() {
    return new ActiveMQQueue("remotingQueue");
}

    @Bean FactoryBean invoker(ConnectionFactory factory, Queue queue) {
        JmsInvokerProxyFactoryBean factoryBean = new JmsInvokerProxyFactoryBean();
        factoryBean.setConnectionFactory(factory);
        factoryBean.setServiceInterface(CabBookingService.class);
        factoryBean.setQueue(queue);
        return factoryBean;
    }

    public static void main(String[] args) throws BookingException {
        CabBookingService service = SpringApplication.run(JmsClient.class, args).getBean(CabBookingService.class);
        System.out.println("here");
        Booking bookingOutcome = service.bookRide("13 Seagate Blvd, Key Largo, FL 33037");
        System.out.println("there");
        System.out.println(bookingOutcome);
    }

}
