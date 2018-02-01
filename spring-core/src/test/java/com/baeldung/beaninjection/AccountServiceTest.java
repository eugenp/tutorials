package com.baeldung.beaninjection;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.*;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {
        AccountController.class,
        AccountService.class,
        AccountRepository.class})
public class AccountServiceTest {

    @Autowired
    private AccountService accountService;

    @Test
    public void accountService_WhenInstantiated_ShouldInjectDependencies() throws Exception {
        assertNotNull(accountService);
        assertNotNull(accountService.getAccountRepository());
    }
}