package com.baeldung.inject;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import javax.inject.Inject;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.baeldung.dependency.ArbitraryDependency;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={
		"/applicationContextTest-@Inject-Type.xml"})
public class FieldInjectDemo {

    @Inject
    private ArbitraryDependency inject1Dependency;

    @Test
    public void fieldDependency_MUST_BE_INJECTED_Successfully() {
        assertNotNull(inject1Dependency);
        assertEquals("Arbitrary Dependency", inject1Dependency.toString());
    }
}
