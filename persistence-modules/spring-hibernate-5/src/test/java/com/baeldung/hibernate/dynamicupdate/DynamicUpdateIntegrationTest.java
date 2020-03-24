package com.baeldung.hibernate.dynamicupdate;

import javax.transaction.Transactional;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Commit;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import com.baeldung.hibernate.dynamicupdate.model.Account;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = DynamicUpdateConfig.class, loader = AnnotationConfigContextLoader.class)
@Transactional
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class DynamicUpdateIntegrationTest {

    private static final Integer ACCOUNT_ID = 1;

    @Autowired
    private AccountRepository accountRepository;

    @Test
    @Commit
    public void testA_whenTestAccountIsSaved_thenSuccess() {
        Account account = new Account(ACCOUNT_ID, "account1", "regional", true);
        accountRepository.save(account);
    }

    @Test
    @Commit
    // Enable Hibernate's debug logging in logback.xml to see the generated SQL statement.
    public void testB_whenAccountNameUpdated_thenSuccess() {
        Account account = accountRepository.findOne(ACCOUNT_ID);
        account.setName("Test Account");
        accountRepository.save(account);
    }

}
