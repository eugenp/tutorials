package executerservicecustomthreadname;

import static org.junit.Assert.assertTrue;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

import org.apache.commons.lang3.concurrent.BasicThreadFactory;
import org.junit.jupiter.api.Test;

import com.baeldung.executerservicecustomthreadname.CustomThreadFactory;
import com.google.common.util.concurrent.ThreadFactoryBuilder;

public class ExecuterServiceCustomThreadNameUnitTest {

    @Test
    void whenUsingDefaultThreadFactory_thenCustomThreadName() {
        ExecutorService executorService = Executors.newFixedThreadPool(3);
        for (int i = 0; i < 5; i++) {
            executorService.execute(() -> assertTrue(
                    Thread.currentThread().getName().startsWith("MyCustomThread")));
        }
        executorService.shutdown();
    }

    @Test
    void whenUsingCustomThreadFactory_thenCustomThreadName() {
        CustomThreadFactory myThreadFactory = new CustomThreadFactory("MyCustomThread-");
        ExecutorService executorService = Executors.newFixedThreadPool(3, myThreadFactory);
        for (int i = 0; i < 5; i++) {
            executorService.execute(() -> assertTrue(
                    Thread.currentThread().getName().startsWith("MyCustomThread")));
        }
        executorService.shutdown();
    }

    @Test
    void whenUsingCommonsLangThreadFactory_thenCustomThreadName() {
        BasicThreadFactory factory = new BasicThreadFactory.Builder()
                .namingPattern("MyCustomThread-%d").priority(Thread.MAX_PRIORITY).build();
        ExecutorService executorService = Executors.newFixedThreadPool(3, factory);
        for (int i = 0; i < 5; i++) {
            executorService.execute(() -> assertTrue(
                    Thread.currentThread().getName().startsWith("MyCustomThread")));
        }
        executorService.shutdown();
    }

    @Test
    void whenUsingGuavaThreadFactory_thenCustomThreadName() {
        ThreadFactory namedThreadFactory = new ThreadFactoryBuilder()
                .setNameFormat("MyCustomThread-%d").build();
        ExecutorService executorService = Executors.newFixedThreadPool(3, namedThreadFactory);
        for (int i = 0; i < 5; i++) {
            executorService.execute(() -> assertTrue(
                    Thread.currentThread().getName().startsWith("MyCustomThread")));
        }
        executorService.shutdown();
    }
}
