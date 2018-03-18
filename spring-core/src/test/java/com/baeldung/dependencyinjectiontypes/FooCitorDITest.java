package com.baeldung.dependencyinjectiontypes;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.stereotype.Repository;

import static org.junit.Assert.assertNotNull;


public class FooCitorDITest {
    private FooCitorDI objUnderTest;
    @Mock Repository repo;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void whenCreateThenInject() {
        objUnderTest = new FooCitorDI(repo);
        assertNotNull(objUnderTest.getMariaDB());
    }
}