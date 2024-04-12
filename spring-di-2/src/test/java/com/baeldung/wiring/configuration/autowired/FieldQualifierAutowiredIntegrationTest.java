package com.baeldung.wiring.configuration.autowired;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import com.baeldung.dependency.ArbitraryDependency;
import com.baeldung.wiring.configuration.ApplicationContextTestAutowiredQualifier;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(
  loader = AnnotationConfigContextLoader.class,
  classes = ApplicationContextTestAutowiredQualifier.class)
public class FieldQualifierAutowiredIntegrationTest {

    @Autowired
    @Qualifier("autowiredFieldDependency")
    private ArbitraryDependency fieldDependency1;

    @Autowired
    @Qualifier("anotherAutowiredFieldDependency")
    private ArbitraryDependency fieldDependency2;

    @Test
    public void givenAutowiredQualifier_WhenOnField_ThenDep1Valid() {
        assertNotNull(fieldDependency1);
        assertEquals("Arbitrary Dependency", fieldDependency1.toString());
    }

    @Test
    public void givenAutowiredQualifier_WhenOnField_ThenDep2Valid() {
        assertNotNull(fieldDependency2);
        assertEquals("Another Arbitrary Dependency", fieldDependency2.toString());
    }
}
