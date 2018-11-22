package com.baeldung.className;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class RetrievingClassNameUnitTest {

    // Retrieving Simple Name
    @Test
    public void givenRetrievingClassName_whenGetSimpleName_thenRetrievingClassName() {
        assertEquals("RetrievingClassName", RetrievingClassName.class.getSimpleName());
    }

    @Test
    public void givenPrimitiveInt_whenGetSimpleName_thenInt() {
        assertEquals("int", int.class.getSimpleName());
    }

    @Test
    public void givenRetrievingClassNameArray_whenGetSimpleName_thenRetrievingClassNameWithBrackets() {
        assertEquals("RetrievingClassName[]", RetrievingClassName[].class.getSimpleName());
        assertEquals("RetrievingClassName[][]", RetrievingClassName[][].class.getSimpleName());
    }

    @Test
    public void givenAnonymousClass_whenGetSimpleName_thenEmptyString() {
        assertEquals("", new RetrievingClassName() {}.getClass().getSimpleName());
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
    public void givenRetrievingClassName_whenGetName_thenCanonicalName() {
        assertEquals("com.baeldung.className.RetrievingClassName", RetrievingClassName.class.getName());
    }

    @Test
    public void givenRetrievingClassName_whenGetTypeName_thenCanonicalName() {
        assertEquals("com.baeldung.className.RetrievingClassName", RetrievingClassName.class.getTypeName());
    }

    @Test
    public void givenRetrievingClassName_whenGetCanonicalName_thenCanonicalName() {
        assertEquals("com.baeldung.className.RetrievingClassName", RetrievingClassName.class.getCanonicalName());
    }

    // - Inner Classes
    @Test
    public void givenRetrievingClassNameInnerClass_whenGetName_thenCanonicalNameWithDollarSeparator() {
        assertEquals("com.baeldung.className.RetrievingClassName$InnerClass", RetrievingClassName.InnerClass.class.getName());
    }

    @Test
    public void givenRetrievingClassNameInnerClass_whenGetTypeName_thenCanonicalNameWithDollarSeparator() {
        assertEquals("com.baeldung.className.RetrievingClassName$InnerClass", RetrievingClassName.InnerClass.class.getTypeName());
    }

    @Test
    public void givenRetrievingClassNameInnerClass_whenGetCanonicalName_thenCanonicalName() {
        assertEquals("com.baeldung.className.RetrievingClassName.InnerClass", RetrievingClassName.InnerClass.class.getCanonicalName());
    }

    // - Anonymous Classes
    @Test
    public void givenAnonymousClass_whenGetName_thenCallingClassCanonicalNameWithDollarSeparatorAndCountNumber() {
        // These are the second and third appearences of an anonymous class in RetrievingClassNameUnitTest, hence $2 and $3 expectations
        assertEquals("com.baeldung.className.RetrievingClassNameUnitTest$2", new RetrievingClassName() {}.getClass().getName());
        assertEquals("com.baeldung.className.RetrievingClassNameUnitTest$3", new RetrievingClassName() {}.getClass().getName());
    }

    @Test
    public void givenAnonymousClass_whenGetTypeName_thenCallingClassCanonicalNameWithDollarSeparatorAndCountNumber() {
        // These are the fourth and fifth appearences of an anonymous class in RetrievingClassNameUnitTest, hence $4 and $5 expectations
        assertEquals("com.baeldung.className.RetrievingClassNameUnitTest$4", new RetrievingClassName() {}.getClass().getTypeName());
        assertEquals("com.baeldung.className.RetrievingClassNameUnitTest$5", new RetrievingClassName() {}.getClass().getTypeName());
    }

    @Test
    public void givenAnonymousClass_whenGetCanonicalName_thenNull() {
        assertNull(new RetrievingClassName() {}.getClass().getCanonicalName());
    }

    // - Arrays
    @Test
    public void givenPrimitiveIntArray_whenGetName_thenOpeningBracketsAndPrimitiveIntLetter() {
        assertEquals("[I", int[].class.getName());
        assertEquals("[[I", int[][].class.getName());
    }

    @Test
    public void givenRetrievingClassNameArray_whenGetName_thenOpeningBracketsLetterLAndRetrievingClassNameGetName() {
        assertEquals("[Lcom.baeldung.className.RetrievingClassName;", RetrievingClassName[].class.getName());
        assertEquals("[[Lcom.baeldung.className.RetrievingClassName;", RetrievingClassName[][].class.getName());
    }

    @Test
    public void givenRetrievingClassNameInnerClassArray_whenGetName_thenOpeningBracketsLetterLAndRetrievingClassNameInnerClassGetName() {
        assertEquals("[Lcom.baeldung.className.RetrievingClassName$InnerClass;", RetrievingClassName.InnerClass[].class.getName());
        assertEquals("[[Lcom.baeldung.className.RetrievingClassName$InnerClass;", RetrievingClassName.InnerClass[][].class.getName());
    }

    @Test
    public void givenPrimitiveIntArray_whenGetTypeName_thenPrimitiveIntGetTypeNameWithBrackets() {
        assertEquals("int[]", int[].class.getTypeName());
        assertEquals("int[][]", int[][].class.getTypeName());
    }

    @Test
    public void givenRetrievingClassNameArray_whenGetTypeName_thenRetrievingClassNameGetTypeNameWithBrackets() {
        assertEquals("com.baeldung.className.RetrievingClassName[]", RetrievingClassName[].class.getTypeName());
        assertEquals("com.baeldung.className.RetrievingClassName[][]", RetrievingClassName[][].class.getTypeName());
    }

    @Test
    public void givenRetrievingClassNameInnerClassArray_whenGetTypeName_thenRetrievingClassNameInnerClassGetTypeNameWithBrackets() {
        assertEquals("com.baeldung.className.RetrievingClassName$InnerClass[]", RetrievingClassName.InnerClass[].class.getTypeName());
        assertEquals("com.baeldung.className.RetrievingClassName$InnerClass[][]", RetrievingClassName.InnerClass[][].class.getTypeName());
    }

    @Test
    public void givenPrimitiveIntArray_whenGetCanonicalName_thenPrimitiveIntGetCanonicalNameWithBrackets() {
        assertEquals("int[]", int[].class.getCanonicalName());
        assertEquals("int[][]", int[][].class.getCanonicalName());
    }

    @Test
    public void givenRetrievingClassNameArray_whenGetCanonicalName_thenRetrievingClassNameGetCanonicalNameWithBrackets() {
        assertEquals("com.baeldung.className.RetrievingClassName[]", RetrievingClassName[].class.getCanonicalName());
        assertEquals("com.baeldung.className.RetrievingClassName[][]", RetrievingClassName[][].class.getCanonicalName());
    }

    @Test
    public void givenRetrievingClassNameInnerClassArray_whenGetCanonicalName_thenRetrievingClassNameInnerClassGetCanonicalNameWithBrackets() {
        assertEquals("com.baeldung.className.RetrievingClassName.InnerClass[]", RetrievingClassName.InnerClass[].class.getCanonicalName());
        assertEquals("com.baeldung.className.RetrievingClassName.InnerClass[][]", RetrievingClassName.InnerClass[][].class.getCanonicalName());
    }

}