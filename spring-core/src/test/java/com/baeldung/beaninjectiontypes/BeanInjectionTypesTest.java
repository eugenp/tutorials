package com.baeldung.beaninjectiontypes;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class BeanInjectionTypesTest {
    @Test
    public void LibrarySetterInjectionTest() {
        LibrarySetterConfiguration configuration = new LibrarySetterConfiguration();
        LibrarySetterInjection library = (LibrarySetterInjection) configuration.library();
        String name = "Data Structures and Algorithms";
        assertNotNull(library);
        assertTrue(name.equals(library.getBook().getName()));
    }

    @Test
    public void LibraryConstructorInjectionTest() {
        LibraryConstructorConfiguration configuration = new LibraryConstructorConfiguration();
        LibraryConstructorInjection library = (LibraryConstructorInjection) configuration.library();
        String name = "Data Structures and Algorithms";
        assertNotNull(library);
        assertTrue(name.equals(library.getBook().getName()));
    }
}

