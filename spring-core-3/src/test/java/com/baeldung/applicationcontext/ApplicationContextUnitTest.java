package com.baeldung.applicationcontext;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.Ignore;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

public class ApplicationContextUnitTest {

    @Test
    public void givenAnnotationConfigAppContext_whenSpringConfig_thenMappingSuccess() {
        ApplicationContext context = new AnnotationConfigApplicationContext(AccountConfig.class);
        AccountService accountService = context.getBean(AccountService.class);

        assertNotNull(accountService);
        assertNotNull(accountService.getAccountRepository());

        ((AnnotationConfigApplicationContext) context).close();
    }

    @Test
    public void givenClasspathXmlAppContext_whenAnnotationConfig_thenMappingSuccess() {
        ApplicationContext context = new ClassPathXmlApplicationContext("applicationcontext/user-bean-config.xml");
        UserService userService = context.getBean(UserService.class);

        assertNotNull(userService);

        ((ClassPathXmlApplicationContext) context).close();
    }

    @Test
    @Ignore
    public void givenFileXmlAppContext_whenXMLConfig_thenMappingSuccess() {
        String path = "D:/workspaces/Baeldung/tutorials/spring-core-4/src/test/resources/applicationcontext/account-bean-config.xml";

        ApplicationContext context = new FileSystemXmlApplicationContext(path);
        AccountService accountService = context.getBean("accountService", AccountService.class);

        assertNotNull(accountService);
        assertNotNull(accountService.getAccountRepository());

        ((FileSystemXmlApplicationContext) context).close();
    }

    @Test
    public void givenClasspathXmlAppContext_whenXMLConfig_thenMappingSuccess() {
        ApplicationContext context = new ClassPathXmlApplicationContext("applicationcontext/account-bean-config.xml");
        AccountService accountService = context.getBean("accountService", AccountService.class);

        assertNotNull(accountService);
        assertNotNull(accountService.getAccountRepository());

        ((ClassPathXmlApplicationContext) context).close();
    }

    @Test
    public void givenMessagesInFile_whenMessageResourceUsed_thenReadMessage() {
        ApplicationContext context = new AnnotationConfigApplicationContext(AccountConfig.class);
        AccountService accountService = context.getBean(AccountService.class);

        assertEquals("TestAccount", accountService.getAccountName());

        ((AnnotationConfigApplicationContext) context).close();
    }

}
