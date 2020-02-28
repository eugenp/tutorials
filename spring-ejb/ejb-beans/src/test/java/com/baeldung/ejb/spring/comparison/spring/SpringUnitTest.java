package com.baeldung.ejb.spring.comparison.spring;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import javax.naming.NamingException;

import org.apache.activemq.junit.EmbeddedActiveMQBroker;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.baeldung.ejb.spring.comparison.spring.config.ApplicationConfig;
import com.baeldung.ejb.spring.comparison.spring.messagedriven.Producer;
import com.baeldung.ejb.spring.comparison.spring.singleton.CounterBean;
import com.baeldung.ejb.spring.comparison.spring.stateful.ShoppingCartBean;

public class SpringUnitTest {

    private static AnnotationConfigApplicationContext context = null;

    @ClassRule
    public static EmbeddedActiveMQBroker broker = new EmbeddedActiveMQBroker() {
        @Override
        protected void configure() {
            this.getBrokerService()
                .setUseJmx(true);
            try {
                this.getBrokerService()
                    .addConnector("tcp://localhost:61616");
            } catch (Exception e) {
                Assert.fail(e.getMessage());
            }
        }
    };

    @BeforeClass
    public static void init() {
        context = new AnnotationConfigApplicationContext(ApplicationConfig.class);
    }

    @Test
    public void whenCounterInvoked_thenCountIsIncremented() throws NamingException {

        CounterBean firstCounter = context.getBean(CounterBean.class);
        firstCounter.setName("first");
        int count = 0;
        for (int i = 0; i < 10; i++) {
            count = firstCounter.count();
        }

        assertEquals(10, count);
        assertEquals("first", firstCounter.getName());

        CounterBean secondCounter = context.getBean(CounterBean.class);

        int count2 = 0;
        for (int i = 0; i < 10; i++) {
            count2 = secondCounter.count();
        }

        assertEquals(20, count2);
        assertEquals("first", secondCounter.getName());

    }

    @Test
    public void whenBathingCartWithThreeItemsAdded_thenItemsSizeIsThree() throws NamingException {
        ShoppingCartBean bathingCart = context.getBean(ShoppingCartBean.class);

        bathingCart.setName("bathingCart");

        bathingCart.addItem("soap");
        bathingCart.addItem("shampoo");
        bathingCart.addItem("oil");

        assertEquals(3, bathingCart.getItems()
            .size());

        assertEquals("bathingCart", bathingCart.getName());

        ShoppingCartBean fruitCart = context.getBean(ShoppingCartBean.class);

        fruitCart.addItem("apples");
        fruitCart.addItem("oranges");

        assertEquals(2, fruitCart.getItems()
            .size());
        assertNull(fruitCart.getName());
    }

    @Test
    public void givenJMSBean_whenMessageSent_thenAcknowledgementReceived() throws NamingException {
        Producer producer = context.getBean(Producer.class);
        producer.sendMessageToDefaultDestination("marco");

        assertEquals("polo", producer.receiveAck());
    }

    @AfterClass
    public static void closeContext() throws NamingException {
        context.close();
    }

}
