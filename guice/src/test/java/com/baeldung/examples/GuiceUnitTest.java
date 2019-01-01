package com.baeldung.examples;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;

import com.baeldung.examples.common.BookService;
import com.baeldung.examples.guice.FooGenerator;
import com.baeldung.examples.guice.GuicePersonService;
import com.baeldung.examples.guice.GuiceUser;
import com.baeldung.examples.guice.GuiceUserService;
import com.baeldung.examples.guice.Person;
import com.baeldung.examples.guice.modules.GuiceModule;
import com.google.inject.Guice;
import com.google.inject.Injector;

public class GuiceUnitTest {

    @Test
    public void givenAccountInjectedInGuiceUser_WhenGetAccountInvoked_ThenReturnValueIsNotNull() {
        Injector injector = Guice.createInjector(new GuiceModule());
        GuiceUser guiceUser = injector.getInstance(GuiceUser.class);
        assertNotNull(guiceUser.getAccount());
    }

    @Test
    public void givenPersonDaoInjectedInGuicePersonService_WhenGetPersonDaoInvoked_ThenReturnValueIsNotNull() {
        Injector injector = Guice.createInjector(new GuiceModule());
        GuicePersonService personService = injector.getInstance(GuicePersonService.class);
        assertNotNull(personService);
        assertNotNull(personService.getPersonDao());
    }

    @Test
    public void givenAccountServiceInjectedInGuiceUserService_WhenGetAccountServiceInvoked_ThenReturnValueIsNotNull() {
        Injector injector = Guice.createInjector(new GuiceModule());
        GuiceUserService guiceUserService = injector.getInstance(GuiceUserService.class);
        assertNotNull(guiceUserService.getAccountService());
    }

    @Test
    public void givenBookServiceIsRegisteredInModule_WhenBookServiceIsInjected_ThenReturnValueIsNotNull() {
        Injector injector = Guice.createInjector(new GuiceModule());
        BookService bookService = injector.getInstance(BookService.class);
        assertNotNull(bookService);
    }

    @Test
    public void givenFooGeneratorConstructorParameterIsNotNullable_WhenFooGeneratorIsInjected_ThenTestFailsByProvisionException() {
        Injector injector = Guice.createInjector(new GuiceModule());
        FooGenerator fooGenerator = injector.getInstance(FooGenerator.class);
        assertNotNull(fooGenerator);
    }

    @Test
    public void givenMultipleBindingsForPerson_WhenPersonIsInjected_ThenTestFailsByProvisionException() {
        Injector injector = Guice.createInjector(new GuiceModule());
        Person person = injector.getInstance(Person.class);
        assertNotNull(person);
    }

    @Test
    public void givenGuicePersonServiceConstructorAnnotatedByInject_WhenGuicePersonServiceIsInjected_ThenInstanceWillBeCreatedFromTheConstructor() {
        Injector injector = Guice.createInjector(new GuiceModule());
        GuicePersonService personService = injector.getInstance(GuicePersonService.class);
        assertNotNull(personService);
    }

    @Test
    public void givenPersonDaoInjectedToGuicePersonServiceBySetterInjection_WhenGuicePersonServiceIsInjected_ThenPersonDaoInitializedByTheSetter() {
        Injector injector = Guice.createInjector(new GuiceModule());
        GuicePersonService personService = injector.getInstance(GuicePersonService.class);
        assertNotNull(personService);
        assertNotNull(personService.getPersonDao());
    }

}
