package com.baeldung.dubbo;

import com.baeldung.dubbo.remote.GreetingsService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * @author aiet
 */
public class MulticastRegistryLiveTest {

    private ClassPathXmlApplicationContext remoteContext;

    @Before
    public void initRemote() {
        remoteContext = new ClassPathXmlApplicationContext("multicast/provider-app.xml");
        remoteContext.start();
    }

    @Test
    public void givenProvider_whenConsumerSaysHi_thenGotResponse() {
        ClassPathXmlApplicationContext localContext = new ClassPathXmlApplicationContext("multicast/consumer-app.xml");
        localContext.start();
        GreetingsService greetingsService = (GreetingsService) localContext.getBean("greetingsService");
        String hiMessage = greetingsService.sayHi("baeldung");

        assertNotNull(hiMessage);
        assertEquals("hi, baeldung", hiMessage);
    }

}
