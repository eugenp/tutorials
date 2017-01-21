package com.baeldung.settervsconstructordi;

import com.baeldung.configuration.ApplicationContextTestSetterVsGetterInjection;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import static org.junit.Assert.assertNotNull;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(loader = AnnotationConfigContextLoader.class, classes = ApplicationContextTestSetterVsGetterInjection.class)
public class ConstructorVsFieldInjectionIntegrationTest {

    @Autowired
    private CarService carService;

    @Autowired
    private BusService busService;

    @Test
    public void givenInjectQualifier_WhenSetOnField_ThenDependencyValid() {
        assertNotNull("EngineService must not be null", carService);
    }

    @Test
    public void test_2() {
        assertNotNull("EngineService must not be null", busService);
    }
}