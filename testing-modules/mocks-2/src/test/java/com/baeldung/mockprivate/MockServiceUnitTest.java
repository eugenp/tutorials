package com.baeldung.mockprivate;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.platform.commons.util.ReflectionUtils;
import org.springframework.test.util.ReflectionTestUtils;

import java.lang.reflect.Field;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class MockServiceUnitTest {

    private Person mockedPerson;

    @BeforeEach
    public void setUp(){
        mockedPerson = mock(Person.class);
    }

    @Test
    void givenNameChangedWithReflection_whenGetName_thenReturnName() throws Exception {
        Class<?> mockServiceClass = Class.forName("com.baeldung.mockprivate.MockService");
        MockService mockService = (MockService) mockServiceClass.getDeclaredConstructor().newInstance();
        Field field = mockServiceClass.getDeclaredField("person");
        field.setAccessible(true);
        field.set(mockService, mockedPerson);

        when(mockedPerson.getName()).thenReturn("Jane Doe");

        Assertions.assertEquals("Jane Doe", mockService.getName());
    }

    @Test
    void givenNameChangedWithReflectionUtils_whenGetName_thenReturnName() throws Exception {
        MockService mockService = new MockService();
        Field field = ReflectionUtils
          .findFields(MockService.class, f -> f.getName().equals("person"),
            ReflectionUtils.HierarchyTraversalMode.TOP_DOWN)
          .get(0);

        field.setAccessible(true);
        field.set(mockService, mockedPerson);

        when(mockedPerson.getName()).thenReturn("Jane Doe");

        Assertions.assertEquals("Jane Doe", mockService.getName());
    }

    @Test
    void givenNameChangedWithReflectionTestUtils_whenGetName_thenReturnName() throws Exception {
        MockService mockService = new MockService();

        ReflectionTestUtils.setField(mockService, "person", mockedPerson);

        when(mockedPerson.getName()).thenReturn("Jane Doe");
        Assertions.assertEquals("Jane Doe", mockService.getName());
    }

}