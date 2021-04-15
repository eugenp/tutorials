package com.baeldung.pattern.portsAndAdapters.runner;


import com.baeldung.pattern.portsAndAdapters.adapter.TransactionServiceImpl;
import com.baeldung.pattern.portsAndAdapters.controllers.TransactionController;
import com.baeldung.pattern.portsAndAdapters.core.ports.TransactionService;
import com.baeldung.pattern.portsAndAdapters.core.service.TransactionProcessor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class PortsAndAdaptersRunner {

    public static void main(String[] args) {
        SpringApplication.run(PortsAndAdaptersRunner.class, args);
    }

    @Bean
    public TransactionProcessor transactionProcessor(){
        return new TransactionProcessor();
    }

    @Bean
    public TransactionService transactionService(TransactionProcessor processor){
        return new TransactionServiceImpl(processor);
    }

    @Bean
    public TransactionController transactionController(TransactionService transactionService){
        return new TransactionController(transactionService);
    }
}
