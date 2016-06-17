package com.baeldung.autowired;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.baeldung.dependency.ArbitraryDependency;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={
		"/applicationContextTest-@Autowired-Qualifier.xml"})
public class FieldQualifierAutowiredDemo {

    @Autowired
    @Qualifier("autowiredFieldDependency")
    private ArbitraryDependency fieldDependency1;

    @Autowired
    @Qualifier("anotherAutowiredFieldDependency")
    private ArbitraryDependency fieldDependency2;

    @Test
    public void fieldDependency1_MUST_BE_AUTOWIRED_Correctly() {
        assertNotNull(fieldDependency1);
        assertEquals("Arbitrary Dependency", fieldDependency1.toString());
    }

    @Test
    public void fieldDependency2_MUST_BE_AUTOWIRED_Correctly() {
        assertNotNull(fieldDependency2);
        assertEquals("Another Arbitrary Dependency", fieldDependency2.toString());
    }
}
