package com.baeldung;

import org.apache.qpid.server.Broker;
import org.apache.qpid.server.BrokerOptions;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.rules.ExternalResource;
import org.junit.runner.RunWith;
import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.rabbit.connection.SimpleRoutingConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
public class AmqpTest {

    @Autowired ApplicationContext ctx;

    @ClassRule
    public static final ExternalResource embeddedAMQPBroker = new ExternalResource() {
        Broker broker;

        @Override
        protected void before() throws Throwable {
            BrokerOptions brokerOptions = new BrokerOptions();
            brokerOptions.setConfigProperty("qpid.amqp_port", "55672");
            broker = new Broker();
            broker.startup(brokerOptions);
        }

        @Override
        protected void after() {
            broker.shutdown();
        }
    };

    @Configuration
    public static class MyConfig {
        @Bean
        public static String aBean() {
            System.out.println("===============================");
            return "hello";
        }

        @Bean
        public AmqpAdmin amqpAdmin() {
            SimpleRoutingConnectionFactory cf = new SimpleRoutingConnectionFactory();
            return new RabbitAdmin(cf);
        }

        @Component
        public static class MyBean {

            private final AmqpAdmin amqpAdmin;
            private final AmqpTemplate amqpTemplate;

            @Autowired
            public MyBean(AmqpAdmin amqpAdmin, AmqpTemplate amqpTemplate) {
                this.amqpAdmin = amqpAdmin;
                this.amqpTemplate = amqpTemplate;
            }

        }
    }

    @Test public void tryAConnection() throws InterruptedException {
        Object bean = ctx.getBean(MyConfig.MyBean.class);
        assertNotNull(bean);
    }

}
