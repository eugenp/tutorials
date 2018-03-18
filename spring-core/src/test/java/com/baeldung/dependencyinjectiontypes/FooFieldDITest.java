package com.baeldung.dependencyinjectiontypes;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.stereotype.Repository;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;

import static org.junit.Assert.assertNotNull;

/**
 * There are two options to test a field DI:
 * <ul>
 *     <li>@InjectMocks</li>
 *     <li>Reflection</li>
 * </ul>
 */
public class FooFieldDITest {
    private FooFieldDI objUderTest;
    @Mock Repository repo;

    @Before
    public void setUp() {
        objUderTest = new FooFieldDI();
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void whenCreateThenInject() {
        objUderTest = new FooFieldDI();
        Field mariaDBField = ReflectionUtils.findField(FooFieldDI.class, "mariaDB");
        ReflectionUtils.makeAccessible(mariaDBField);
        ReflectionUtils.setField(mariaDBField, objUderTest, repo);

        assertNotNull(objUderTest.getMariaDB());
    }
}