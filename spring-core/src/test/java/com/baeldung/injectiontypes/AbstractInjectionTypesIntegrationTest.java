package com.baeldung.injectiontypes;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Collection;

import static org.junit.Assert.assertSame;

@RunWith(SpringJUnit4ClassRunner.class)
@Ignore
public class AbstractInjectionTypesIntegrationTest {

    @Autowired
    Dependency1 dep1;

    @Autowired
    Dependency2 dep2;

    @Autowired
    MyService myService;

    @Autowired
    Collection<MyService2> myServices2;

    @Autowired
    MyService3 myService3;

    @Test
    public void whenContextIsProperlyConfigured_thenConstructorInjectionWorksAsExpected() {
        assertSame(myService.getD1(), dep1);
        assertSame(myService.getD2(), dep2);

        myServices2.forEach(service -> {
            assertSame(service.getD1(), dep1);
            assertSame(service.getD2(), dep2);
        });
    }

    @Test
    public void whenContextIsProperlyConfigured_thenSetterInjectionWorksAsExpected() {
        assertSame(myService3.getDependency(), dep1);
    }
}
