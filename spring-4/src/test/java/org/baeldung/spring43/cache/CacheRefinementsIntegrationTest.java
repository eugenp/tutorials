package org.baeldung.spring43.cache;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

import static org.junit.Assert.assertEquals;

@ContextConfiguration(classes = CacheRefinementsConfiguration.class)
public class CacheRefinementsIntegrationTest extends AbstractJUnit4SpringContextTests {

    private ExecutorService executorService = Executors.newFixedThreadPool(10);

    @Autowired
    private FooService service;

    @Test
    public void whenMultipleThreadsExecuteCacheableMethodWithSyncTrue_thenMethodIsExecutedOnlyOnce() throws InterruptedException {
        for (int i = 0; i < 10; i++) {
            executorService.execute(() -> service.getFoo("test").printInstanceNumber());
        }
        executorService.awaitTermination(1, TimeUnit.SECONDS);
        assertEquals(1, Foo.getInstanceCount());
    }

}
