package org.baeldung.spring43.ctor;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

import static org.junit.Assert.assertNotNull;

@ContextConfiguration("classpath:implicit-ctor-context.xml")
public class ImplicitConstructorIntegrationTest extends AbstractJUnit4SpringContextTests {

    @Autowired
    private FooService fooService;

    @Test
    public void whenBeanWithoutAutowiredCtor_thenInjectIntoSingleCtor() {
        assertNotNull(fooService.getRepository());
    }

}
