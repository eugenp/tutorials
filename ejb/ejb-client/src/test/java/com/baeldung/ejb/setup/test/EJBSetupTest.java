package com.baeldung.ejb.setup.test;

import static org.junit.Assert.*;
import org.junit.Test;
import com.baeldung.ejb.client.EJBClient;
import com.baeldung.ejb.tutorial.HelloWorldBean;

public class EJBSetupTest {

    @Test
    public void testEJBClient() {
        EJBClient ejbClient = new EJBClient();
        HelloWorldBean bean = new HelloWorldBean();
        assertEquals(bean.getHelloWorld(), ejbClient.getEJBRemoteMessage());
    }
}
