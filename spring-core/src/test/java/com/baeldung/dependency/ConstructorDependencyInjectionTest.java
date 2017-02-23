package com.baeldung.dependency;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:dependency-injection.xml" })
public class ConstructorDependencyInjectionTest {

    @Autowired
    private ConstructorDependencyInjection constructorDependencyInjectionXML;

    @Autowired
    private ConstructorDependencyInjection constructorDependencyInjectionAnnotation;
;
    @Test
    public void testConstructorDependencyXMLConfiguration() {
        assertThat(constructorDependencyInjectionXML.add(5, 6), equalTo(11));
    }

    @Test
    public void testConstructorDependencyAnnotation() {
        assertThat(constructorDependencyInjectionAnnotation.add(4, 6), equalTo(10));
    }
}