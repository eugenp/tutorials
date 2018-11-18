package com.baeldung.classes;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class SomeClassTest {

    // Retrieving Simple Name
    @Test
    public void givenSomeClassWhenGetSimpleNameThenSomeClass() {
        assertEquals("SomeClass", SomeClass.class.getSimpleName());
    }

    @Test
    public void givenPrimitiveIntWhenGetSimpleNameThenInt() {
        assertEquals("int", int.class.getSimpleName());
    }

    @Test
    public void givenSomeClassArrayWhenGetSimpleNameThenSomeClassWithBrackets() {
        assertEquals("SomeClass[]", SomeClass[].class.getSimpleName());
        assertEquals("SomeClass[][]", SomeClass[][].class.getSimpleName());
    }

    @Test
    public void givenAnonymousClassWhenGetSimpleNameThenSomeClassWithBrackets() {
        assertEquals("", new SomeClass() {}.getClass().getSimpleName());
    }

    // Retrieving Other Names
    // - Primitive Types
    @Test
    public void givenPrimitiveIntWhenGetNameThenInt() {
        assertEquals("int", int.class.getName());
    }

    @Test
    public void givenPrimitiveIntWhenGetTypeNameThenInt() {
        assertEquals("int", int.class.getTypeName());
    }

    @Test
    public void givenPrimitiveIntWhenGetCanonicalNameThenInt() {
        assertEquals("int", int.class.getCanonicalName());
    }

    // - Object Types
    @Test
    public void givenSomeClassWhenGetNameThenCanonicalName() {
        assertEquals("com.baeldung.classes.SomeClass", SomeClass.class.getName());
    }

    @Test
    public void givenSomeClassWhenGetTypeNameThenCanonicalName() {
        assertEquals("com.baeldung.classes.SomeClass", SomeClass.class.getTypeName());
    }

    @Test
    public void givenSomeClassWhenGetCanonicalNameThenCanonicalName() {
        assertEquals("com.baeldung.classes.SomeClass", SomeClass.class.getCanonicalName());
    }

    // - Inner Classes
    @Test
    public void givenSomeInnerClassWhenGetNameThenCanonicalNameWithDollarSeparator() {
        assertEquals("com.baeldung.classes.SomeClass$SomeInnerClass", SomeClass.SomeInnerClass.class.getName());
    }

    @Test
    public void givenSomeInnerClassWhenGetTypeNameThenCanonicalNameWithDollarSeparator() {
        assertEquals("com.baeldung.classes.SomeClass$SomeInnerClass", SomeClass.SomeInnerClass.class.getTypeName());
    }

    @Test
    public void givenSomeInnerClassWhenGetCanonicalNameThenCanonicalName() {
        assertEquals("com.baeldung.classes.SomeClass.SomeInnerClass", SomeClass.SomeInnerClass.class.getCanonicalName());
    }

    // - Anonymous Classes
    @Test
    public void givenAnonymousClassWhenGetNameThenCallingClassCanonicalNameWithDollarSeparatorAndCountNumber() {
        // These are the second and third appearences of an anonymous class in SomeClassTest, hence $2 and $3 expectations
        assertEquals("com.baeldung.classes.SomeClassTest$2", new SomeClass() {}.getClass().getName());
        assertEquals("com.baeldung.classes.SomeClassTest$3", new SomeClass() {}.getClass().getName());
    }

    @Test
    public void givenAnonymousClassWhenGetTypeNameThenCallingClassCanonicalNameWithDollarSeparatorAndCountNumber() {
        // These are the fourth and fifth appearences of an anonymous class in SomeClassTest, hence $4 and $5 expectations
        assertEquals("com.baeldung.classes.SomeClassTest$4", new SomeClass() {}.getClass().getTypeName());
        assertEquals("com.baeldung.classes.SomeClassTest$5", new SomeClass() {}.getClass().getTypeName());
    }

    @Test
    public void givenAnonymousClassWhenGetCanonicalNameThenNull() {
        assertNull(new SomeClass() {}.getClass().getCanonicalName());
    }

    // - Arrays
    @Test
    public void givenPrimitiveIntArrayWhenGetNameThenOpeningBracketsAndPrimitiveIntLetter() {
        assertEquals("[I", int[].class.getName());
        assertEquals("[[I", int[][].class.getName());
    }

    @Test
    public void givenSomeClassArrayWhenGetNameThenOpeningBracketsLetterLAndSomeClassGetName() {
        assertEquals("[Lcom.baeldung.classes.SomeClass;", SomeClass[].class.getName());
        assertEquals("[[Lcom.baeldung.classes.SomeClass;", SomeClass[][].class.getName());
    }

    @Test
    public void givenSomeInnerClassArrayWhenGetNameThenOpeningBracketsLetterLAndSomeInnerClassGetName() {
        assertEquals("[Lcom.baeldung.classes.SomeClass$SomeInnerClass;", SomeClass.SomeInnerClass[].class.getName());
        assertEquals("[[Lcom.baeldung.classes.SomeClass$SomeInnerClass;", SomeClass.SomeInnerClass[][].class.getName());
    }

    @Test
    public void givenPrimitiveIntArrayWhenGetTypeNameThenPrimitiveIntGetTypeNameWithBrackets() {
        assertEquals("int[]", int[].class.getTypeName());
        assertEquals("int[][]", int[][].class.getTypeName());
    }

    @Test
    public void givenSomeClassArrayWhenGetTypeNameThenSomeClassGetTypeNameWithBrackets() {
        assertEquals("com.baeldung.classes.SomeClass[]", SomeClass[].class.getTypeName());
        assertEquals("com.baeldung.classes.SomeClass[][]", SomeClass[][].class.getTypeName());
    }

    @Test
    public void givenSomeInnerClassArrayWhenGetTypeNameThenSomeInnerClassGetTypeNameWithBrackets() {
        assertEquals("com.baeldung.classes.SomeClass$SomeInnerClass[]", SomeClass.SomeInnerClass[].class.getTypeName());
        assertEquals("com.baeldung.classes.SomeClass$SomeInnerClass[][]", SomeClass.SomeInnerClass[][].class.getTypeName());
    }

    @Test
    public void givenPrimitiveIntArrayWhenGetCanonicalNameThenPrimitiveIntGetCanonicalNameWithBrackets() {
        assertEquals("int[]", int[].class.getCanonicalName());
        assertEquals("int[][]", int[][].class.getCanonicalName());
    }

    @Test
    public void givenSomeClassArrayWhenGetCanonicalNameThenSomeClassGetCanonicalNameWithBrackets() {
        assertEquals("com.baeldung.classes.SomeClass[]", SomeClass[].class.getCanonicalName());
        assertEquals("com.baeldung.classes.SomeClass[][]", SomeClass[][].class.getCanonicalName());
    }

    @Test
    public void givenSomeInnerClassArrayWhenGetCanonicalNameThenSomeInnerClassGetCanonicalNameWithBrackets() {
        assertEquals("com.baeldung.classes.SomeClass.SomeInnerClass[]", SomeClass.SomeInnerClass[].class.getCanonicalName());
        assertEquals("com.baeldung.classes.SomeClass.SomeInnerClass[][]", SomeClass.SomeInnerClass[][].class.getCanonicalName());
    }

}