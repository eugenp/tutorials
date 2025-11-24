package com.baeldung.async;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import java.util.concurrent.CompletableFuture;

@Component
public class AsyncComponent {

    @Async
    public void asyncMethodWithVoidReturnType() {
        System.out.println("Execute method asynchronously. " 
          + Thread.currentThread().getName());
    }

    @Async
    public CompletableFuture<String> asyncMethodWithReturnType() {
        System.out.println("Execute method asynchronously - " 
          + Thread.currentThread().getName());

        try {
                Thread.sleep(5000);
                return CompletableFuture.completedFuture("hello world !!!!");
        } catch (InterruptedException e) {
              return CompletableFuture.failedFuture(e);
          }
    }

    @Async("threadPoolTaskExecutor")
    public void asyncMethodWithConfiguredExecutor() {
        System.out.println("Execute method with configured executor - "
          + Thread.currentThread().getName());
    }

    @Async
    public void asyncMethodWithExceptions() throws Exception {
        throw new Exception("Throw message from asynchronous method. ");
    }
}
