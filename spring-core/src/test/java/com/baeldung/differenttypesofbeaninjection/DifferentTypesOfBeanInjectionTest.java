package com.baeldung.differenttypesofbeaninjection;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class DifferentTypesOfBeanInjectionTest {

    private ApplicationContext context;

    @Before
    public void setUp() {
        context = new ClassPathXmlApplicationContext("differenttypesofbeaninjection-context.xml");
    }

    @Test
    public void givenAutowiredAnnotation_WhenSetOnConstructor_ThenDependencyValid() {
        EmployeeService employeeService = (EmployeeService) context.getBean("employeeServiceConstructorInjectionBean");
        String salaryDetails = employeeService.process();

        assertThat("100 transferred", is(equalTo(salaryDetails)));
    }

    @Test
    public void givenAutowiredAnnotation_WhenSetOnSetter_ThenDependencyValid() {
        BeerService beerService = (BeerService) context.getBean("beerServiceSetterInjectionBean");
        String order = beerService.order();

        assertThat("Order for Beer received", is(equalTo(order)));
    }

    @Test
    public void givenAutowiredAnnotation_WhenSetOnField_ThenDependencyValid() {
        UserService userService = (UserService) context.getBean("userServiceFieldInjectionBean");
        String address = userService.getUserAddress();

        assertThat("USA", is(equalTo(address)));
    }
}
