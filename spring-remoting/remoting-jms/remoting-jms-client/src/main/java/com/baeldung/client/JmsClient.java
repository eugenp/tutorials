package com.baeldung.client;

import com.baeldung.api.BookingException;
import com.baeldung.api.CabBookingService;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQQueue;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.jms.remoting.JmsInvokerProxyFactoryBean;

import javax.jms.ConnectionFactory;
import javax.jms.Queue;

@SpringBootApplication public class JmsClient {

    @Bean ConnectionFactory connectionFactory() {
        ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory();
        factory.setBrokerURL("tcp://ep-t43:61616");
        return factory;
    }

    @Bean @Qualifier("queueName") String queueName() {
        return "remotingQueue";
    }

    @Bean
    ActiveMQQueue queue(@Qualifier("queueName") String queueName) {
        ActiveMQQueue queue = new ActiveMQQueue(queueName);
        return queue;
    }

    @Bean JmsInvokerProxyFactoryBean amqpFactoryBean(ConnectionFactory connectionFactory, Queue queue) {
        JmsInvokerProxyFactoryBean factoryBean = new JmsInvokerProxyFactoryBean();
        factoryBean.setConnectionFactory(connectionFactory);
        factoryBean.setQueue(queue);
        return factoryBean;
    }

    public static void main(String[] args) throws BookingException {
        CabBookingService service = SpringApplication.run(JmsClient.class, args).getBean(CabBookingService.class);
        System.out.println(service.bookRide("13 Seagate Blvd, Key Largo, FL 33037"));
    }

}
