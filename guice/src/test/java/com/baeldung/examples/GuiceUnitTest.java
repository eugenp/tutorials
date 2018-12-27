package com.baeldung.examples;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;

import com.baeldung.examples.common.BookService;
import com.baeldung.examples.guice.Employee;
import com.baeldung.examples.guice.FooGenerator;
import com.baeldung.examples.guice.GuiceUser;
import com.baeldung.examples.guice.GuiceUserService;
import com.baeldung.examples.guice.Person;
import com.baeldung.examples.guice.modules.GuiceModule;
import com.google.inject.Guice;
import com.google.inject.Injector;

public class GuiceUnitTest {

    @Test
    public void givenAccountFieldInjectedInGuiceUser_WhenGetAccountInvoked_ThenReturnValueIsNotNull() {
        Injector injector = Guice.createInjector(new GuiceModule());
        GuiceUser guiceUser = injector.getInstance(GuiceUser.class);
        assertNotNull(guiceUser.getAccount());
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
    public void givenEmployeeConstructorAnnotatedByInject_WhenEmployeeIsInjected_ThenInstanceWillBeCreatedFromTheConstructor() {
        Injector injector = Guice.createInjector(new GuiceModule());
        Employee employee = injector.getInstance(Employee.class);
        assertNotNull(employee);
        assertEquals("Default", employee.getLastName());
    }

    @Test
    public void givenAddressAutowiredToGuiceUserBySetterInjection_WhenGuiceUserIsInjected_ThenAddressInitializedByTheSetter() {
        Injector injector = Guice.createInjector(new GuiceModule());
        GuiceUser guiceUser = injector.getInstance(GuiceUser.class);
        assertNotNull(guiceUser);
        assertNotNull(guiceUser.getAddress());
        assertEquals("Default", guiceUser.getAddress().getCity());
    }

}
