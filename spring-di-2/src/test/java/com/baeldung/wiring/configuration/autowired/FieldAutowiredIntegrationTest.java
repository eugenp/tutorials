package com.baeldung.wiring.configuration.autowired;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import com.baeldung.dependency.ArbitraryDependency;
import com.baeldung.wiring.configuration.ApplicationContextTestAutowiredType;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(
  loader = AnnotationConfigContextLoader.class,
  classes = ApplicationContextTestAutowiredType.class)
public class FieldAutowiredIntegrationTest {

    @Autowired
    private ArbitraryDependency fieldDependency;

    @Test
    public void givenAutowired_WhenSetOnField_ThenDependencyResolved() {
        assertNotNull(fieldDependency);
        assertEquals("Arbitrary Dependency", fieldDependency.toString());
    }
}
