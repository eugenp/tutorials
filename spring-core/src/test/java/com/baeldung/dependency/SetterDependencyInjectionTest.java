package com.baeldung.dependency;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:dependency-injection.xml" })
public class SetterDependencyInjectionTest {

    @Autowired
    private SetterDependencyInjection setterDependencyInjectionXML;

    @Autowired
    private SetterDependencyInjection setterDependencyInjectionAnnotation;

    @Test
    public void testSetterDependencyXMLConfiguration() {
        assertThat(setterDependencyInjectionXML.add(5, 6), equalTo(11));
    }

    @Test
    public void testSetterDependencyAnnotation() {
        assertThat(setterDependencyInjectionAnnotation.add(4, 6), equalTo(10));
    }
}
