package com.baeldung.dubbo;

import com.baeldung.dubbo.remote.GreetingsService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.ArrayList;
import java.util.List;
import java.util.OptionalDouble;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static java.util.concurrent.TimeUnit.SECONDS;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * @author aiet
 */
public class ClusterDynamicLoadBalanceLiveTest {

    private ExecutorService executorService;

    @Before
    public void initRemote() {
        executorService = Executors.newFixedThreadPool(2);
        executorService.submit(() -> {
            ClassPathXmlApplicationContext remoteContext = new ClassPathXmlApplicationContext("cluster/provider-app-default.xml");
            remoteContext.start();
        });
        executorService.submit(() -> {
            SECONDS.sleep(2);
            ClassPathXmlApplicationContext backupRemoteContext = new ClassPathXmlApplicationContext("cluster/provider-app-special.xml");
            backupRemoteContext.start();
            return null;
        });
    }

    @Test
    public void givenProviderCluster_whenConsumerSaysHi_thenResponseBalanced() throws InterruptedException {
        ClassPathXmlApplicationContext localContext = new ClassPathXmlApplicationContext("cluster/consumer-app-lb.xml");
        localContext.start();
        GreetingsService greetingsService = (GreetingsService) localContext.getBean("greetingsService");
        List<Long> elapseList = new ArrayList<>(6);
        for (int i = 0; i < 6; i++) {
            long current = System.currentTimeMillis();
            String hiMessage = greetingsService.sayHi("baeldung");
            assertNotNull(hiMessage);
            elapseList.add(System.currentTimeMillis() - current);
            SECONDS.sleep(1);
        }

        OptionalDouble avgElapse = elapseList
          .stream()
          .mapToLong(e -> e)
          .average();
        assertTrue(avgElapse.isPresent());
        assertTrue(avgElapse.getAsDouble() > 1666.0);

    }

    @After
    public void destroy() {
        executorService.shutdown();
    }

}
