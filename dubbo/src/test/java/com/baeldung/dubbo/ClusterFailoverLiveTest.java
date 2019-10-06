package com.baeldung.dubbo;

import com.baeldung.dubbo.remote.GreetingsService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * @author aiet
 */
public class ClusterFailoverLiveTest {

    private ExecutorService executorService;

    @Before
    public void initRemote() {
        executorService = Executors.newFixedThreadPool(2);
        executorService.submit(() -> {
            ClassPathXmlApplicationContext backupRemoteContext = new ClassPathXmlApplicationContext("cluster/provider-app-special.xml");
            backupRemoteContext.start();
        });
        executorService.submit(() -> {
            ClassPathXmlApplicationContext remoteContext = new ClassPathXmlApplicationContext("cluster/provider-app-failover.xml");
            remoteContext.start();
        });

    }

    @Test
    public void givenProviderCluster_whenConsumerSaysHi_thenGotFailoverResponse() {
        ClassPathXmlApplicationContext localContext = new ClassPathXmlApplicationContext("cluster/consumer-app-failtest.xml");
        localContext.start();
        GreetingsService greetingsService = (GreetingsService) localContext.getBean("greetingsService");
        String hiMessage = greetingsService.sayHi("baeldung");

        assertNotNull(hiMessage);
        assertEquals("hi, failover baeldung", hiMessage);
    }

    @After
    public void destroy() {
        executorService.shutdown();
    }

}
