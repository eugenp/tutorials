package com.baeldung.differenttypesofbeaninjection;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {DifferentTypesOfBeanInjectionConfig.class})
public class DifferentTypesOfBeanInjectionTest {

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private BeerService beerService;

    @Autowired
    private UserService userService;

    @Test
    public void givenAutowiredAnnotation_WhenSetOnConstructor_ThenDependencyValid() {
        String salaryDetails = employeeService.process();

        assertThat("100 transferred", is(equalTo(salaryDetails)));
    }

    @Test
    public void givenAutowiredAnnotation_WhenSetOnSetter_ThenDependencyValid() {
        String order = beerService.order();

        assertThat("Order for Beer received", is(equalTo(order)));
    }

    @Test
    public void givenAutowiredAnnotation_WhenSetOnField_ThenDependencyValid() {
        String address = userService.getUserAddress();

        assertThat("USA", is(equalTo(address)));
    }
}
