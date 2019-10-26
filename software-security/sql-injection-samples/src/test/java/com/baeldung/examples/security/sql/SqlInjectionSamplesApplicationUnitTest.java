package com.baeldung.examples.security.sql;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.baeldung.examples.security.sql.AccountDAO;
import com.baeldung.examples.security.sql.AccountDTO;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles({ "test" })
public class SqlInjectionSamplesApplicationUnitTest {

    @Autowired
    private AccountDAO target;

    @Test
    public void givenAVulnerableMethod_whenValidCustomerId_thenReturnSingleAccount() {

        List<AccountDTO> accounts = target.unsafeFindAccountsByCustomerId("C1");
        assertThat(accounts).isNotNull();
        assertThat(accounts).isNotEmpty();
        assertThat(accounts).hasSize(1);
    }

    @Test
    public void givenAVulnerableMethod_whenHackedCustomerId_thenReturnAllAccounts() {

        List<AccountDTO> accounts = target.unsafeFindAccountsByCustomerId("C1' or '1'='1");
        assertThat(accounts).isNotNull();
        assertThat(accounts).isNotEmpty();
        assertThat(accounts).hasSize(3);
    }

    @Test
    public void givenAVulnerableJpaMethod_whenHackedCustomerId_thenReturnAllAccounts() {

        List<AccountDTO> accounts = target.unsafeJpaFindAccountsByCustomerId("C1' or '1'='1");
        assertThat(accounts).isNotNull();
        assertThat(accounts).isNotEmpty();
        assertThat(accounts).hasSize(3);
    }
    
    @Test
    public void givenASafeMethod_whenHackedCustomerId_thenReturnNoAccounts() {

        List<AccountDTO> accounts = target.safeFindAccountsByCustomerId("C1' or '1'='1");
        assertThat(accounts).isNotNull();
        assertThat(accounts).isEmpty();
    }

    @Test
    public void givenASafeJpaMethod_whenHackedCustomerId_thenReturnNoAccounts() {

        List<AccountDTO> accounts = target.safeJpaFindAccountsByCustomerId("C1' or '1'='1");
        assertThat(accounts).isNotNull();
        assertThat(accounts).isEmpty();
    }


    @Test
    public void givenASafeJpaCriteriaMethod_whenHackedCustomerId_thenReturnNoAccounts() {

        List<AccountDTO> accounts = target.safeJpaCriteriaFindAccountsByCustomerId("C1' or '1'='1");
        assertThat(accounts).isNotNull();
        assertThat(accounts).isEmpty();
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void givenASafeMethod_whenInvalidOrderBy_thenThroweException() {
        target.safeFindAccountsByCustomerId("C1", "INVALID");
    }

    @Test(expected = Exception.class)
    public void givenWrongPlaceholderUsageMethod_whenNormalCall_thenThrowsException() {        
        target.wrongCountRecordsByTableName("Accounts");
    }

    @Test(expected = Exception.class)
    public void givenWrongJpaPlaceholderUsageMethod_whenNormalCall_thenThrowsException() {        
        target.wrongJpaCountRecordsByTableName("Accounts");
    }
    
}
