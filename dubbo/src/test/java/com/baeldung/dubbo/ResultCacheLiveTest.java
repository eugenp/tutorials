package com.baeldung.dubbo;

import com.baeldung.dubbo.remote.GreetingsService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import static org.junit.Assert.*;

/**
 * @author aiet
 */
public class ResultCacheLiveTest {

    private ClassPathXmlApplicationContext remoteContext;

    @Before
    public void initRemote() {
        remoteContext = new ClassPathXmlApplicationContext("multicast/provider-app-special.xml");
        remoteContext.start();
    }

    @Test
    public void givenProvider_whenConsumerSaysHi_thenGotResponse() {
        ClassPathXmlApplicationContext localContext = new ClassPathXmlApplicationContext("multicast/consumer-app.xml");
        localContext.start();
        GreetingsService greetingsService = (GreetingsService) localContext.getBean("greetingsService");

        long before = System.currentTimeMillis();
        String hiMessage = greetingsService.sayHi("baeldung");

        long timeElapsed = System.currentTimeMillis() - before;
        assertTrue(timeElapsed > 5000);
        assertNotNull(hiMessage);
        assertEquals("hi, baeldung", hiMessage);


        before = System.currentTimeMillis();
        hiMessage = greetingsService.sayHi("baeldung");
        timeElapsed = System.currentTimeMillis() - before;
        assertTrue(timeElapsed < 1000);
        assertNotNull(hiMessage);
        assertEquals("hi, baeldung", hiMessage);
    }

}
