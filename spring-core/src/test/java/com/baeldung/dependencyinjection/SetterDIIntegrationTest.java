package com.baeldung.dependencyinjection;

import com.baeldung.setterdi.domain.MotorCycle;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import static org.junit.Assert.assertTrue;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(loader = AnnotationConfigContextLoader.class, classes = SetterDIConfigTest.class)
public class SetterDIIntegrationTest {
    @Autowired private MotorCycle setterInjectedMotorCycle;

    @Test
    public void testSetterInjectMotorCycle() {
        assertTrue(setterInjectedMotorCycle
          .toString()
          .contains("v6"));
        assertTrue(setterInjectedMotorCycle
          .toString()
          .contains("6"));
    }
}