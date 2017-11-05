package com.baeldung.autowired;

import com.baeldung.constructordi.PersonController;
import com.baeldung.constructordi.PersonService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import static org.junit.Assert.assertNotNull;

@RunWith(MockitoJUnitRunner.class)
public class ConstructorInjectionTest {


    @Mock
    PersonService personService;
    @InjectMocks
    PersonController personController;
    ApplicationContext applicationContext;

    @Before
    public void setUp() throws Exception {
        applicationContext = new ClassPathXmlApplicationContext("constructordi.xml");
    }


    @Test
    public void testAnnotationConstructorInjection() {
        assertNotNull(personController);
        assertNotNull(personController.getPersonService());
    }

    @Test
    public void testXmlConstructorInjection() {
        PersonController personController = applicationContext.getBean("personController", PersonController.class);
        assertNotNull(personController);
        assertNotNull(personController.getPersonService());
    }

}
