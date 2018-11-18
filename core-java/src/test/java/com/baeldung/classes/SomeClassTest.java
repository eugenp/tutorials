package com.baeldung.classes;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class SomeClassTest {

    // Retrieving Simple Name
    @Test
    public void givenSomeClass_whenGetSimpleName_thenSomeClass() {
        assertEquals("SomeClass", SomeClass.class.getSimpleName());
    }

    @Test
    public void givenPrimitiveInt_whenGetSimpleName_thenInt() {
        assertEquals("int", int.class.getSimpleName());
    }

    @Test
    public void givenSomeClassArray_whenGetSimpleName_thenSomeClassWithBrackets() {
        assertEquals("SomeClass[]", SomeClass[].class.getSimpleName());
        assertEquals("SomeClass[][]", SomeClass[][].class.getSimpleName());
    }

    @Test
    public void givenAnonymousClass_whenGetSimpleName_thenSomeClassWithBrackets() {
        assertEquals("", new SomeClass() {}.getClass().getSimpleName());
    }

    // Retrieving Other Names
    // - Primitive Types
    @Test
    public void givenPrimitiveInt_whenGetName_thenInt() {
        assertEquals("int", int.class.getName());
    }

    @Test
    public void givenPrimitiveInt_whenGetTypeName_thenInt() {
        assertEquals("int", int.class.getTypeName());
    }

    @Test
    public void givenPrimitiveInt_whenGetCanonicalName_thenInt() {
        assertEquals("int", int.class.getCanonicalName());
    }

    // - Object Types
    @Test
    public void givenSomeClass_whenGetName_thenCanonicalName() {
        assertEquals("com.baeldung.classes.SomeClass", SomeClass.class.getName());
    }

    @Test
    public void givenSomeClass_whenGetTypeName_thenCanonicalName() {
        assertEquals("com.baeldung.classes.SomeClass", SomeClass.class.getTypeName());
    }

    @Test
    public void givenSomeClass_whenGetCanonicalName_thenCanonicalName() {
        assertEquals("com.baeldung.classes.SomeClass", SomeClass.class.getCanonicalName());
    }

    // - Inner Classes
    @Test
    public void givenSomeInnerClass_whenGetName_thenCanonicalNameWithDollarSeparator() {
        assertEquals("com.baeldung.classes.SomeClass$SomeInnerClass", SomeClass.SomeInnerClass.class.getName());
    }

    @Test
    public void givenSomeInnerClass_whenGetTypeName_thenCanonicalNameWithDollarSeparator() {
        assertEquals("com.baeldung.classes.SomeClass$SomeInnerClass", SomeClass.SomeInnerClass.class.getTypeName());
    }

    @Test
    public void givenSomeInnerClass_whenGetCanonicalName_thenCanonicalName() {
        assertEquals("com.baeldung.classes.SomeClass.SomeInnerClass", SomeClass.SomeInnerClass.class.getCanonicalName());
    }

    // - Anonymous Classes
    @Test
    public void givenAnonymousClass_whenGetName_thenCallingClassCanonicalNameWithDollarSeparatorAndCountNumber() {
        // These are the second and third appearences of an anonymous class in SomeClassTest, hence $2 and $3 expectations
        assertEquals("com.baeldung.classes.SomeClassTest$2", new SomeClass() {}.getClass().getName());
        assertEquals("com.baeldung.classes.SomeClassTest$3", new SomeClass() {}.getClass().getName());
    }

    @Test
    public void givenAnonymousClass_whenGetTypeName_thenCallingClassCanonicalNameWithDollarSeparatorAndCountNumber() {
        // These are the fourth and fifth appearences of an anonymous class in SomeClassTest, hence $4 and $5 expectations
        assertEquals("com.baeldung.classes.SomeClassTest$4", new SomeClass() {}.getClass().getTypeName());
        assertEquals("com.baeldung.classes.SomeClassTest$5", new SomeClass() {}.getClass().getTypeName());
    }

    @Test
    public void givenAnonymousClass_whenGetCanonicalName_thenNull() {
        assertNull(new SomeClass() {}.getClass().getCanonicalName());
    }

    // - Arrays
    @Test
    public void givenPrimitiveIntArray_whenGetName_thenOpeningBracketsAndPrimitiveIntLetter() {
        assertEquals("[I", int[].class.getName());
        assertEquals("[[I", int[][].class.getName());
    }

    @Test
    public void givenSomeClassArray_whenGetName_thenOpeningBracketsLetterLAndSomeClassGetName() {
        assertEquals("[Lcom.baeldung.classes.SomeClass;", SomeClass[].class.getName());
        assertEquals("[[Lcom.baeldung.classes.SomeClass;", SomeClass[][].class.getName());
    }

    @Test
    public void givenSomeInnerClassArray_whenGetName_thenOpeningBracketsLetterLAndSomeInnerClassGetName() {
        assertEquals("[Lcom.baeldung.classes.SomeClass$SomeInnerClass;", SomeClass.SomeInnerClass[].class.getName());
        assertEquals("[[Lcom.baeldung.classes.SomeClass$SomeInnerClass;", SomeClass.SomeInnerClass[][].class.getName());
    }

    @Test
    public void givenPrimitiveIntArray_whenGetTypeName_thenPrimitiveIntGetTypeNameWithBrackets() {
        assertEquals("int[]", int[].class.getTypeName());
        assertEquals("int[][]", int[][].class.getTypeName());
    }

    @Test
    public void givenSomeClassArray_whenGetTypeName_thenSomeClassGetTypeNameWithBrackets() {
        assertEquals("com.baeldung.classes.SomeClass[]", SomeClass[].class.getTypeName());
        assertEquals("com.baeldung.classes.SomeClass[][]", SomeClass[][].class.getTypeName());
    }

    @Test
    public void givenSomeInnerClassArray_whenGetTypeName_thenSomeInnerClassGetTypeNameWithBrackets() {
        assertEquals("com.baeldung.classes.SomeClass$SomeInnerClass[]", SomeClass.SomeInnerClass[].class.getTypeName());
        assertEquals("com.baeldung.classes.SomeClass$SomeInnerClass[][]", SomeClass.SomeInnerClass[][].class.getTypeName());
    }

    @Test
    public void givenPrimitiveIntArray_whenGetCanonicalName_thenPrimitiveIntGetCanonicalNameWithBrackets() {
        assertEquals("int[]", int[].class.getCanonicalName());
        assertEquals("int[][]", int[][].class.getCanonicalName());
    }

    @Test
    public void givenSomeClassArray_whenGetCanonicalName_thenSomeClassGetCanonicalNameWithBrackets() {
        assertEquals("com.baeldung.classes.SomeClass[]", SomeClass[].class.getCanonicalName());
        assertEquals("com.baeldung.classes.SomeClass[][]", SomeClass[][].class.getCanonicalName());
    }

    @Test
    public void givenSomeInnerClassArray_whenGetCanonicalName_thenSomeInnerClassGetCanonicalNameWithBrackets() {
        assertEquals("com.baeldung.classes.SomeClass.SomeInnerClass[]", SomeClass.SomeInnerClass[].class.getCanonicalName());
        assertEquals("com.baeldung.classes.SomeClass.SomeInnerClass[][]", SomeClass.SomeInnerClass[][].class.getCanonicalName());
    }

}