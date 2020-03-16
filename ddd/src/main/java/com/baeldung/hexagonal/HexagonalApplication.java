package com.baeldung.hexagonal;

import java.math.BigDecimal;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.baeldung.hexagonal.application.repository.BankAccountRepository;
import com.baeldung.hexagonal.domain.BankAccount;

@SpringBootApplication(scanBasePackages = "com.baeldung.hexagonal")
public class HexagonalApplication {

    public static void main(final String[] args) {
        SpringApplication.run(HexagonalApplication.class, args);
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