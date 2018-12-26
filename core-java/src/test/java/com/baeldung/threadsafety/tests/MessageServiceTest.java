package com.baeldung.threadsafety.tests;

import com.baeldung.threadsafety.services.MessageService;
import com.baeldung.threadsafety.threads.ThreadD;
import org.junit.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class MessageServiceTest {
    
    @Test
    public void whenCalledGetMessage_thenCorrect() throws InterruptedException {
        MessageService messageService = new MessageService("Welcome to Baeldung!");
        ThreadD thread1 = new ThreadD(messageService);
        ThreadD thread2 = new ThreadD(messageService);
        thread1.start();
        thread2.start();
        thread1.join();
        thread2.join();
        
        assertThat(thread1.getMessageService().getMesssage()).isEqualTo("Welcome to Baeldung!");
        assertThat(thread2.getMessageService().getMesssage()).isEqualTo("Welcome to Baeldung!");
    }
}
