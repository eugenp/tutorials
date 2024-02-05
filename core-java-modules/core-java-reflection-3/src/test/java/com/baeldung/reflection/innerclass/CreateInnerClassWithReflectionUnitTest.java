package com.baeldung.reflection.innerclass;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CreateInnerClassWithReflectionUnitTest {

    static Logger logger = LoggerFactory.getLogger(CreateInnerClassWithReflectionUnitTest.class);

    @Test
    void givenInnerClass_whenUseReflection_thenShowConstructors() {
        final String personBuilderClassName = "com.baeldung.reflection.innerclass.Person$Builder";
        final String personAddressClassName = "com.baeldung.reflection.innerclass.Person$Address";
        assertDoesNotThrow(() -> logConstructors(Class.forName(personAddressClassName)));
        assertDoesNotThrow(() -> logConstructors(Class.forName(personBuilderClassName)));
    }

    private static void logConstructors(Class<?> clazz) {
        Arrays.stream(clazz.getDeclaredConstructors())
            .map(c -> formatConstructorSignature(c))
            .forEach(logger::info);
    }

    private static String formatConstructorSignature(Constructor<?> constructor) {
        String params = Arrays.stream(constructor.getParameters())
            .map(parameter -> parameter.getType().getSimpleName() + " " + parameter.getName())
            .collect(Collectors.joining(", "));
        return constructor.getName() + "(" + params + ")";
    }

    @Test
    void givenStaticInnerClass_whenUseReflection_thenInstantiate()
        throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException,
            InstantiationException, IllegalAccessException {
        final String personBuilderClassName = "com.baeldung.reflection.innerclass.Person$Builder";
        Class<Person.Builder> personBuilderClass = (Class<Person.Builder>) Class.forName(personBuilderClassName);
        Person.Builder personBuilderObj = personBuilderClass.getDeclaredConstructor().newInstance();
        assertTrue(personBuilderObj instanceof Person.Builder);
    }

    @Test
    void givenNonStaticInnerClass_whenUseReflection_thenInstantiate()
        throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException,
            InstantiationException, IllegalAccessException {
        final String personClassName = "com.baeldung.reflection.innerclass.Person";
        final String personAddressClassName = "com.baeldung.reflection.innerclass.Person$Address";

        Class<Person> personClass = (Class<Person>) Class.forName(personClassName);
        Person personObj = personClass.getConstructor().newInstance();

        Class<Person.Address> personAddressClass = (Class<Person.Address>) Class.forName(personAddressClassName);

        assertThrows(NoSuchMethodException.class, () -> personAddressClass.getDeclaredConstructor(String.class));

        Constructor<Person.Address> constructorOfPersonAddress = personAddressClass.getDeclaredConstructor(Person.class, String.class);
        Person.Address personAddressObj = constructorOfPersonAddress.newInstance(personObj, "751003");
        assertTrue(personAddressObj instanceof Person.Address);
    }

}
