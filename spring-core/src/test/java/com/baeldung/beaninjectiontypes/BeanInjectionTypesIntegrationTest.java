package com.baeldung.beaninjectiontypes;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(loader = AnnotationConfigContextLoader.class,
        classes = TestConfig.class)
public class BeanInjectionTypesIntegrationTest {

    @Autowired
    Coach coach;

    @Autowired
    Sedan sedan;

    @Autowired
    Truck truck;


    @Test
    public void whenConstructorInjection_thenCorrectHorsePower() {

        assertEquals(290, coach.getHorsePower());
    }

    @Test
    public void whenSetterInjection_thenCorrectHorsePower() {

        assertEquals(290, sedan.getHorsePower());
    }

    @Test
    public void whenFieldInjection_thenCorrectHorsePower() {

        assertEquals(290, truck.getHorsePower());
    }
}