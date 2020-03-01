package com.baeldung.ejb.spring.comparison.ejb;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.embeddable.EJBContainer;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.MessageConsumer;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.naming.Context;
import javax.naming.NamingException;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.baeldung.ejb.spring.comparison.ejb.singleton.CounterEJBRemote;
import com.baeldung.ejb.spring.comparison.ejb.stateful.ShoppingCartEJBRemote;
import com.baeldung.ejb.spring.comparison.ejb.stateless.FinderEJBRemote;

public class EJBUnitTest {

    private static EJBContainer ejbContainer = null;

    private static Context context = null;

    @Resource
    private ConnectionFactory connectionFactory;

    @EJB
    private FinderEJBRemote alphabetFinder;

    @Resource(name = "myQueue")
    private Queue myQueue;

    @Resource(name = "ackQueue")
    private Queue ackQueue;

    @BeforeClass
    public static void start() throws NamingException {
        ejbContainer = EJBContainer.createEJBContainer();
    }

    @Before
    public void initializeContext() throws NamingException {
        context = ejbContainer.getContext();
        context.bind("inject", this);
    }

    @Test
    public void givenSingletonBean_whenCounterInvoked_thenCountIsIncremented() throws NamingException {

        int count = 0;
        CounterEJBRemote firstCounter = (CounterEJBRemote) context.lookup("java:global/ejb-beans/CounterEJB");
        firstCounter.setName("first");
        
        for (int i = 0; i < 10; i++) {
            count = firstCounter.count();
        }
        
        assertEquals(10, count);
        assertEquals("first", firstCounter.getName());

        CounterEJBRemote secondCounter = (CounterEJBRemote) context.lookup("java:global/ejb-beans/CounterEJB");

        int count2 = 0;
        for (int i = 0; i < 10; i++) {
            count2 = secondCounter.count();
        }

        assertEquals(20, count2);
        assertEquals("first", secondCounter.getName());

    }

    @Test
    public void givenStatefulBean_whenBathingCartWithThreeItemsAdded_thenItemsSizeIsThree() throws NamingException {
        ShoppingCartEJBRemote bathingCart = (ShoppingCartEJBRemote) context.lookup("java:global/ejb-beans/ShoppingCartEJB");

        bathingCart.setName("bathingCart");

        bathingCart.addItem("soap");
        bathingCart.addItem("shampoo");
        bathingCart.addItem("oil");

        assertEquals(3, bathingCart.getItems()
            .size());
        assertEquals("bathingCart", bathingCart.getName());

        ShoppingCartEJBRemote fruitCart = (ShoppingCartEJBRemote) context.lookup("java:global/ejb-beans/ShoppingCartEJB");

        fruitCart.addItem("apples");
        fruitCart.addItem("oranges");

        assertEquals(2, fruitCart.getItems()
            .size());
        assertNull(fruitCart.getName());
    }

    @Test
    public void givenStatelessBean_whenSearchForA_thenApple() throws NamingException {

        assertEquals("Apple", alphabetFinder.search("A"));
    }

    @Test
    public void givenMDB_whenMessageSent_thenAcknowledgementReceived() throws InterruptedException, JMSException, NamingException {

        Connection connection = connectionFactory.createConnection();
        connection.start();
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        MessageProducer producer = session.createProducer(myQueue);
        producer.send(session.createTextMessage("marco"));
        MessageConsumer response = session.createConsumer(ackQueue);

        assertEquals("polo", ((TextMessage) response.receive(1000)).getText());

    }

    @After
    public void reset() throws NamingException {
        context.unbind("inject");
    }

    @AfterClass
    public static void closeContext() throws NamingException {
        context.close();
        ejbContainer.close();
    }

}
