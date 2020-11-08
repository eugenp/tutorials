package com.baeldung.schemageneration;

import com.baeldung.schemageneration.model.Account;
import com.baeldung.schemageneration.model.AccountSetting;
import com.baeldung.schemageneration.repository.AccountRepository;
import com.baeldung.schemageneration.repository.AccountSettingRepository;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = AccountApplication.class)
public class AccountRepositoryIntegrationTest {

    private static final String USER_NAME = "Eduard";
    private static final String USER_EMAIL_ADDRESS = "eduard@gmx.com";
    private static final String ACCOUNT_SETTING_NAME = "Timezone";
    private static final String ACCOUNT_SETTING_VALUE = "UTC+02";

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private AccountSettingRepository accountSettingRepository;

    @After
    public void tearDown() {
        accountRepository.deleteAll();
    }

    @Test
    public void givenNewAccount_whenSave_thenSuccess() {
        Account account = new Account(USER_NAME, USER_EMAIL_ADDRESS);
        accountRepository.save(account);

        assertEquals(1, accountRepository.count());
    }

    @Test
    public void givenSavedAccount_whenFindByName_thenFound() {
        Account account = new Account(USER_NAME, USER_EMAIL_ADDRESS);
        accountRepository.save(account);

        Account accountFound = accountRepository.findByName(USER_NAME);

        assertNotNull(accountFound);
        assertEquals(USER_NAME, accountFound.getName());
        assertEquals(USER_EMAIL_ADDRESS, accountFound.getEmailAddress());
    }

    @Test
    public void givenSavedAccount_whenAccountSettingIsAdded_thenPersisted() {
        Account account = new Account(USER_NAME, USER_EMAIL_ADDRESS);
        account.addAccountSetting(new AccountSetting(ACCOUNT_SETTING_NAME, ACCOUNT_SETTING_VALUE));
        accountRepository.save(account);

        Account accountFound = accountRepository.findByName(USER_NAME);
        assertNotNull(accountFound);
        AccountSetting accountSetting = accountSettingRepository.findByAccountId(accountFound.getId());

        assertNotNull(accountSetting);
        assertEquals(ACCOUNT_SETTING_NAME, accountSetting.getSettingName());
        assertEquals(ACCOUNT_SETTING_VALUE, accountSetting.getSettingValue());
    }

}
