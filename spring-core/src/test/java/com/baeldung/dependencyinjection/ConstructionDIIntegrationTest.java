package com.baeldung.dependencyinjection;

import com.baeldung.constructordi.domain.MotorCycle;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import static org.junit.Assert.assertTrue;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(loader = AnnotationConfigContextLoader.class, classes = ConstructorDIConfigTest.class)
public class ConstructionDIIntegrationTest {
    @Autowired private MotorCycle constructorInjectedMotorCycle;

    @Test
    public void testSetterInjectMotorCycle() {
        assertTrue(constructorInjectedMotorCycle
          .toString()
          .contains("v6"));
        assertTrue(constructorInjectedMotorCycle
          .toString()
          .contains("6"));
    }
}