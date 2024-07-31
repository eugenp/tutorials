package com.baeldung.spring43.defaultmethods;

import java.time.LocalDate;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

import static org.junit.Assert.assertEquals;

@ContextConfiguration("classpath:defaultmethods-context.xml")
public class DefaultMethodsInjectionIntegrationTest extends AbstractJUnit4SpringContextTests {

    @Autowired
    private IDateHolder dateHolder;

    @Test
    public void whenInjectingToDefaultInterfaceMethod_thenInjectionShouldHappen() {
        assertEquals(LocalDate.of(1982, 10, 15), dateHolder.getLocalDate());
    }

}
