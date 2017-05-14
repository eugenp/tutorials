package com.baeldung.client;

import com.baeldung.api.Booking;
import com.baeldung.api.BookingException;
import com.baeldung.api.CabBookingService;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQQueue;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jms.JmsAutoConfiguration;
import org.springframework.boot.autoconfigure.jms.activemq.ActiveMQAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.jms.remoting.JmsInvokerProxyFactoryBean;

import javax.jms.ConnectionFactory;

import static java.util.Arrays.asList;

@SpringBootApplication
@EnableAutoConfiguration(exclude = { JmsAutoConfiguration.class, ActiveMQAutoConfiguration.class })
public class JmsClient {

    @Bean ConnectionFactory connectionFactory() {
        ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory();
        factory.setBrokerURL("tcp://192.168.99.100:61616");
        factory.setTrustedPackages(asList("org.springframework.remoting.support", "java.lang", "com.baeldung.api"));
        //factory.setTrustAllPackages(true);
        return factory;
    }

    @Bean
    ActiveMQQueue queue() {
        ActiveMQQueue queue = new ActiveMQQueue("remotingQueue");
        return queue;
    }

    @Bean JmsInvokerProxyFactoryBean invoker(ActiveMQQueue queue, ConnectionFactory factory) {
        JmsInvokerProxyFactoryBean factoryBean = new JmsInvokerProxyFactoryBean();
        //factoryBean.setQueue(queue);
        factoryBean.setConnectionFactory(factory);
        factoryBean.setServiceInterface(CabBookingService.class);
        factoryBean.setQueueName("remotingQueue");
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
