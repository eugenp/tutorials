package com.baeldung.ejb.client;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.baeldung.ejb.tutorial.HelloWorldRemote;

public class EJBClientTest {

    @Test
    public void testEJBClient() {
        Client ejbClient = new Client();
        HelloWorldRemote bean = ejbClient.doLookup();
        assertEquals(bean.getHelloWorld(), "Hello World");
    }

}
