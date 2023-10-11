package com.baeldung.hibernate.dynamicupdate;

import java.util.Optional;

import jakarta.transaction.Transactional;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Commit;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import com.baeldung.hibernate.dynamicupdate.model.Account;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = { DynamicUpdateConfig.class }, loader = AnnotationConfigContextLoader.class)
@Transactional
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class DynamicUpdateIntegrationTest {

    private static final Integer ACCOUNT_ID = 1;

    @Autowired
    private AccountRepository accountRepository;

    @Test
    @Commit
    @Order(1)
    void testA_whenTestAccountIsSaved_thenSuccess() {
        Account account = new Account(ACCOUNT_ID, "account1", "regional", true);
        accountRepository.save(account);
    }

    @Test
    @Commit
    @Order(2)
    // Enable Hibernate's debug logging in logback.xml to see the generated SQL statement.
    void testB_whenAccountNameUpdated_thenSuccess() {
        Optional<Account> account = accountRepository.findById(ACCOUNT_ID);
        if(account.isPresent()){
            account.get().setName("Test Account");
            accountRepository.save(account.get());
        }
    }

}
