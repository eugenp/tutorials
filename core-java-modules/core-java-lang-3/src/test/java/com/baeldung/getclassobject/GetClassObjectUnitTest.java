package com.baeldung.getclassobject;

import org.junit.Test;

import static org.junit.Assert.*;

public class GetClassObjectUnitTest {
    @Test
    public void givenObjectAndType_whenGettingClassObject_thenTwoMethodsHaveTheSameResult() {
        String str = "I am an object of the String class";
        Class fromStrObject = str.getClass();
        Class clazz = String.class;
        assertSame(fromStrObject, clazz);
    }

    @Test
    public void givenClassInheritance_whenGettingRuntimeTypeAndStaticType_thenGetDifferentResult() {
        Animal animal = new Monkey();
        Class runtimeType = animal.getClass();
        Class staticType = Animal.class;
        //Not equals
        assertNotEquals(staticType, runtimeType);

        Class monkeyClass = Monkey.class;
        assertSame(runtimeType, monkeyClass);
    }

    @Test
    public void givenPrimitiveType_whenGettingClassObject_thenOnlyStaticTypeWorks() {
        int number = 7;
        // Class numberClass = number.getClass();   <-- compilation error
        Class intType = int.class;

        assertNotNull(intType);
        assertEquals("int", intType.getName());
        assertTrue(intType.isPrimitive());
    }

    @Test
    public void givenTypeCannotInstantiate_whenGetTypeStatically_thenGetTypesSuccefully() {
        Class interfaceType = SomeInterface.class;
        Class abstractClassType = SomeAbstractClass.class;
        Class utilClassType = SomeUtils.class;

        assertNotNull(interfaceType);
        assertTrue(interfaceType.isInterface());
        assertEquals("SomeInterface", interfaceType.getSimpleName());

        assertNotNull(abstractClassType);
        assertEquals("SomeAbstractClass", abstractClassType.getSimpleName());

        assertNotNull(utilClassType);
        assertEquals("SomeUtils", utilClassType.getSimpleName());
    }
}
