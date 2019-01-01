package com.baeldung.examples;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import com.baeldung.examples.common.BookService;
import com.baeldung.examples.spring.AppConfig;
import com.baeldung.examples.spring.SpringPersonService;
import com.baeldung.examples.spring.SpringUser;
import com.baeldung.examples.spring.UserService;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = { AppConfig.class })
public class SpringUnitTest {
    @Autowired
    ApplicationContext context;

    @Test
    public void givenAccountAutowiredToSpringUser_WhenGetAccountInvoked_ThenReturnValueIsNotNull() {
        SpringUser springUser = context.getBean(SpringUser.class);
        assertNotNull(springUser.getAccount());
    }

    @Test
    public void givenPersonDaoAutowiredToSpringPersonService_WhenGetPersonDaoInvoked_ThenReturnValueIsNotNull() {
        SpringPersonService personService = context.getBean(SpringPersonService.class);
        assertNotNull(personService);
        assertNotNull(personService.getPersonDao());
    }

    @Test
    public void givenAccountServiceAutowiredToUserService_WhenGetAccountServiceInvoked_ThenReturnValueIsNotNull() {
        UserService userService = context.getBean(UserService.class);
        assertNotNull(userService.getAccountService());
    }

    @Test
    public void givenBookServiceIsRegisteredAsBeanInContext_WhenBookServiceIsRetrievedFromContext_ThenReturnValueIsNotNull() {
        BookService bookService = context.getBean(BookService.class);
        assertNotNull(bookService);
    }

    @Test
    public void givenSpringPersonServiceConstructorAnnotatedByAutowired_WhenSpringPersonServiceIsRetrievedFromContext_ThenInstanceWillBeCreatedFromTheConstructor() {
        SpringPersonService personService = context.getBean(SpringPersonService.class);
        assertNotNull(personService);
    }

    @Test
    public void givenPersonDaoAutowiredToSpringPersonServiceBySetterInjection_WhenSpringPersonServiceRetrievedFromContext_ThenPersonDaoInitializedByTheSetter() {
        SpringPersonService personService = context.getBean(SpringPersonService.class);
        assertNotNull(personService);
        assertNotNull(personService.getPersonDao());
    }

}
