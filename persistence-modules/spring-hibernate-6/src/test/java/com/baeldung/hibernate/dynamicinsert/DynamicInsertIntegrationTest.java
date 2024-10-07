package com.baeldung.hibernate.dynamicinsert;

import jakarta.transaction.Transactional;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Commit;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import com.baeldung.hibernate.dynamicinsert.model.Account;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = { DynamicInsertConfig.class }, loader = AnnotationConfigContextLoader.class)
@Transactional
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class DynamicInsertIntegrationTest {

    private static final Integer ACCOUNT_ID = 1;

    @Autowired
    private AccountRepository accountRepository;

    @Test
    @Commit
        // Enable Hibernate's debug logging in logback.xml to see the generated SQL statement.
    void givenEntityWithDynamicInsert_whenSavingEntity_thenOnlyNotNullFieldsAreInserted() {
        Account account = new Account();
        account.setId(ACCOUNT_ID);
        account.setName("account1");
        account.setActive(true);
        accountRepository.save(account);
    }

}
