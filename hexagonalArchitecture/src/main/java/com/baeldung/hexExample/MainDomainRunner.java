package com.baeldung.hexExample;

import com.baeldung.hexExample.adapters.persistance.BankAccountRepository;
import com.baeldung.hexExample.application.domain.BankAccount;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.math.BigDecimal;

@SpringBootApplication
public class MainDomainRunner {
    public static void main(final String[] args) {
        SpringApplication.run(MainDomainRunner.class, args);
    }

    @Bean
    public CommandLineRunner bootstrapData(BankAccountRepository repository) {
        return (args) -> {
            BigDecimal initialBalance = BigDecimal.valueOf(1000);
            BankAccount bankAccount = new BankAccount(0L, initialBalance);
            repository.save(bankAccount);
        };
    }
}
