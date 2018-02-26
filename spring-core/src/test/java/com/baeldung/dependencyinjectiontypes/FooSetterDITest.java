package com.baeldung.dependencyinjectiontypes;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.stereotype.Repository;

import static org.junit.Assert.assertNotNull;

public class FooSetterDITest {
    private FooSetterDI objUnderTest;
    @Mock Repository repo;

    @Before
    public void setUp() {
        objUnderTest = new FooSetterDI();
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void whenCallSetterThenInject() {
        objUnderTest.setMariaDB(repo);
        assertNotNull(objUnderTest.getMariaDB());
    }
}