package com.baeldung.concurrent.threadsafety.tests;

import org.junit.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.baeldung.concurrent.threadsafety.callables.MessageServiceCallable;
import com.baeldung.concurrent.threadsafety.services.MessageService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class MessageServiceTest {

    @Test
    public void whenCalledgetMessage_thenCorrect() throws Exception {
        ExecutorService executorService = Executors.newFixedThreadPool(2);
        MessageService messageService = new MessageService("Welcome to Baeldung!");
        Future<String> future1 = (Future<String>) executorService.submit(new MessageServiceCallable(messageService));
        Future<String> future2 = (Future<String>) executorService.submit(new MessageServiceCallable(messageService));

        assertThat(future1.get()).isEqualTo("Welcome to Baeldung!");
        assertThat(future2.get()).isEqualTo("Welcome to Baeldung!");
    }
}
