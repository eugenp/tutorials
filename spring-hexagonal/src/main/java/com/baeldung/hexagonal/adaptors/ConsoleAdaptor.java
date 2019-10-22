package com.baeldung.hexagonal.adaptors;

import com.baeldung.hexagonal.model.Account;
import com.baeldung.hexagonal.ports.entry.AccountPort;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

import java.math.BigDecimal;

@SpringBootApplication
@ComponentScan("com.baeldung")
public class ConsoleAdaptor {
    private static final Logger log = LoggerFactory.getLogger(ConsoleAdaptor.class);

    public static void main(String[] args) {
        SpringApplication.run(ConsoleAdaptor.class);
    }

    @Bean
    public CommandLineRunner entryAdaptor(AccountPort service) {
        return (args) -> {
            Account newAccount = service.openAccount(BigDecimal.TEN);
            log.info("Account details = " + service.getAccount(newAccount.getId()));
            log.info("Opening balance = " + service.checkBalance(newAccount.getId()));
            log.info("Updated balance = " + service.depositMoney(newAccount.getId(), BigDecimal.valueOf(999.0)));
            log.info("Account details = " + service.getAccount(newAccount.getId()));
        };
    }
}
