package com.baeldung.inject;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import javax.inject.Inject;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.baeldung.dependency.ArbitraryDependency;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={
		"/applicationContextTest-@Inject-Qualifier.xml"})
public class FieldQualifierInjectDemo {

    @Inject
    @Qualifier("defaultFile")
    private ArbitraryDependency defaultDependency;

    @Inject
    @Qualifier("namedFile")
    private ArbitraryDependency namedDependency;

    @Test
    public void defaultDependency_MUST_BE_INJECTED_Successfully() {
        assertNotNull(defaultDependency);
        assertEquals("Arbitrary Dependency", defaultDependency.toString());
    }

    @Test
    public void namedDependency_MUST_BE_INJECTED_Correctly() {
        assertNotNull(defaultDependency);
        assertEquals("Another Arbitrary Dependency", namedDependency.toString());
    }
}
