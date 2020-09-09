package com.baeldung.checkclassexistence;

import org.junit.Test;

public class CheckClassExistenceUnitTest {

    public static class InitializingClass {
        static {
            if (true) { //enable throwing of an exception in a static initialization block
                throw new RuntimeException();
            }
        }
    }

    @Test(expected = ClassNotFoundException.class) //thrown when class does not exist
    public void givenNonExistingClass_whenUsingForName_thenClassNotFound() throws ClassNotFoundException {
        Class.forName("class.that.does.not.exist");
    }

    @Test
    public void givenExistingClass_whenUsingForName_noException() throws ClassNotFoundException {
        Class.forName("java.lang.String");
    }

    @Test(expected = ExceptionInInitializerError.class) //thrown when exception occurs inside of a static initialization block
    public void givenInitializingClass_whenUsingForName_initializationError() throws ClassNotFoundException {
        Class.forName("com.baeldung.checkclassexistence.CheckClassExistenceUnitTest$InitializingClass");
    }

    @Test
    public void givenInitializingClass_whenUsingForNameWithoutInitialization_noException() throws ClassNotFoundException {
        Class.forName("com.baeldung.checkclassexistence.CheckClassExistenceUnitTest$InitializingClass", false, getClass().getClassLoader());
    }
}
