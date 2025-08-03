package com.baeldung.mockito.mockstatic;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mockStatic;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import com.baeldung.mockito.mockedstatic.StaticUtils;

public class StaticMockRegistrationUnitTest {

    private MockedStatic<StaticUtils> mockStatic;

    @Before
    public void setUp() {
        // Registering a static mock for UserService before each test
        mockStatic = mockStatic(StaticUtils.class);
    }

    @After
    public void tearDown() {
        // Closing the mockStatic after each test
        mockStatic.close();
    }

    @Test
    public void givenStaticMockRegistration_whenMocked_thenReturnsMockSuccessfully() {
        // Ensure that the static mock for UserService is registered
        assertTrue(Mockito.mockingDetails(StaticUtils.class).isMock());
    }

    @Test
    public void givenAnotherStaticMockRegistration_whenMocked_thenReturnsMockSuccessfully() {
        // Ensure that the static mock for UserService is registered
        assertTrue(Mockito.mockingDetails(StaticUtils.class).isMock());
    }
}