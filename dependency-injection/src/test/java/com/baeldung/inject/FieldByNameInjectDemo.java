package com.baeldung.inject;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import javax.inject.Inject;
import javax.inject.Named;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.baeldung.dependency.ArbitraryDependency;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={
		"/applicationContextTest-@Inject-Name.xml"})
public class FieldByNameInjectDemo {

    @Inject
    @Named("yetAnotherFieldInjectDependency")
    private ArbitraryDependency yetAnotherFieldInjectDependency;

    @Test
    public void yetAnotherFieldInjectDependency_MUST_BE_INJECTED_Correctly() {
        assertNotNull(yetAnotherFieldInjectDependency);
        assertEquals("Yet Another Arbitrary Dependency", yetAnotherFieldInjectDependency.toString());
    }
}
