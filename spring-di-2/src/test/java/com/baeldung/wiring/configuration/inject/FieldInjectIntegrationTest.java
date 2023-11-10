package com.baeldung.wiring.configuration.inject;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import jakarta.inject.Inject;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import com.baeldung.dependency.ArbitraryDependency;
import com.baeldung.wiring.configuration.ApplicationContextTestInjectType;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(
  loader = AnnotationConfigContextLoader.class,
  classes = ApplicationContextTestInjectType.class)
public class FieldInjectIntegrationTest {

    @Inject
    private ArbitraryDependency fieldInjectDependency;

    @Test
    public void givenInjectAnnotation_WhenOnField_ThenValidDependency() {
        assertNotNull(fieldInjectDependency);
        assertEquals("Arbitrary Dependency", fieldInjectDependency.toString());
    }
}
