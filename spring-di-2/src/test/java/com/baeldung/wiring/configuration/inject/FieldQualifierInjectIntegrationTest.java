package com.baeldung.wiring.configuration.inject;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import jakarta.inject.Inject;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import com.baeldung.dependency.ArbitraryDependency;
import com.baeldung.wiring.configuration.ApplicationContextTestInjectQualifier;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(loader = AnnotationConfigContextLoader.class,
  classes = ApplicationContextTestInjectQualifier.class)
public class FieldQualifierInjectIntegrationTest {

    @Inject
    @Qualifier("defaultFile")
    private ArbitraryDependency defaultDependency;

    @Inject
    @Qualifier("namedFile")
    private ArbitraryDependency namedDependency;

    @Test
    public void givenInjectQualifier_WhenOnField_ThenDefaultFileValid() {
        assertNotNull(defaultDependency);
        assertEquals("Arbitrary Dependency", defaultDependency.toString());
    }

    @Test
    public void givenInjectQualifier_WhenOnField_ThenNamedFileValid() {
        assertNotNull(defaultDependency);
        assertEquals("Another Arbitrary Dependency", namedDependency.toString());
    }
}
