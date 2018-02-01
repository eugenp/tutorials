package com.baeldung.beaninjection;

import static org.junit.Assert.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {
        AccountController.class,
        AccountService.class,
        AccountRepository.class})
public class AccountControllerTest {

    @Autowired
    private AccountController accountController;

    @Test
    public void accountController_WhenInstantiated_ShouldInjectDependencies() throws Exception {
        assertNotNull(accountController);
        assertNotNull(accountController.getAccountService());
    }
}