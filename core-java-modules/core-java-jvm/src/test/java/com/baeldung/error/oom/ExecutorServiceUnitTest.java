package com.baeldung.error.oom;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

import org.junit.jupiter.api.Test;

public class ExecutorServiceUnitTest {

    @Test
    public void givenAnExecutorService_WhenMoreTasksSubmitted_ThenAdditionalTasksWait() {

        // Given
        int noOfThreads = 5;
        ExecutorService executorService = Executors.newFixedThreadPool(noOfThreads);

        Runnable runnableTask = () -> {
            try {
                TimeUnit.HOURS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        };

        // When
        IntStream.rangeClosed(1, 10)
            .forEach(i -> executorService.submit(runnableTask));

        // Then
        assertThat(((ThreadPoolExecutor) executorService).getQueue()
            .size(), is(equalTo(5)));
    }

    @Test
    public void givenAnExecutorService() throws Exception {

        while (true) {
            TimeUnit.SECONDS.sleep(5);
            new Thread(() -> {
                try {
                    TimeUnit.HOURS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }).start();
        }
    }
}
