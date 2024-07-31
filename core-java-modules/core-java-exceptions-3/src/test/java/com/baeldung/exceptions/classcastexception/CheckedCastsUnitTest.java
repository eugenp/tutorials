package com.baeldung.exceptions.classcastexception;

import org.junit.Test;

import java.io.Serializable;

public class CheckedCastsUnitTest {

    @Test(expected = ClassCastException.class)
    public void givenBaseTypeVariableReferencingChildInstance_whenCastToIncompatibleSubtype_thenClassCastException() {
        Animal animal = new Frog();

        //A checked downcast to Mammal is incompatible from Frog because Frog is not a subtype of Mammal.
        Mammal mammal = (Mammal) animal;
    }

    @Test(expected = ClassCastException.class)
    public void givenBaseTypeVariableReferencingChildInstance_whenCastToIncompatibleInterface_thenClassCastException() {
        Animal animal = new Frog();

        //A checked cast to Serializable is incompatible from Frog because Frog is not a subtype of Serializable.
        Serializable serial = (Serializable) animal;
    }

    @Test(expected = ClassCastException.class)
    public void givenObjectVariableReferencingPrimitiveArray_whenCastToBoxedTypeArray_thenClassCastException() {
        Object primitives = new int[1];

        //A checked cast to Integer[] is incompatible from primitive arrays. Auto-boxing does not work for arrays.
        Integer[] integers = (Integer[]) primitives;
    }

    @Test(expected = ClassCastException.class)
    public void givenObjectVariableReferencingPrimitiveArray_whenCastToPromotedTypeArray_thenClassCastException() {
        Object primitives = new int[1];

        //A checked cast to long[] is incompatible from int[]. Type promotion does not work for arrays.
        long[] longs = (long[]) primitives;
    }
}
