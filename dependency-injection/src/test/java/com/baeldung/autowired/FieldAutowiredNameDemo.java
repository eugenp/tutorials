package com.baeldung.autowired;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.baeldung.dependency.ArbitraryDependency;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={
		"/applicationContextTest-@Autowired-Name.xml"})
public class FieldAutowiredNameDemo {

    @Autowired
    private ArbitraryDependency autowiredFieldDependency;

    @Test
    public void autowiredFieldDependency_MUST_BE_AUTOWIRED_Correctly() {
        assertNotNull(autowiredFieldDependency);
        assertEquals("Arbitrary Dependency", autowiredFieldDependency.toString());
	}
}
