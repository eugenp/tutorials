package com.baeldung.java9.methodhandles;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.invoke.WrongMethodTypeException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;

/**
 * Test case for the {@link MethodHandles} API
 */
public class MethodHandlesUnitTest {

    @Test
    public void givenConcatMethodHandle_whenInvoked_thenCorrectlyConcatenated() throws Throwable {

        MethodHandles.Lookup publicLookup = MethodHandles.publicLookup();
        MethodType mt = MethodType.methodType(String.class, String.class);
        MethodHandle concatMH = publicLookup.findVirtual(String.class, "concat", mt);

        String output = (String) concatMH.invoke("Effective ", "Java");

        assertEquals("Effective Java", output);
    }

    @Test
    public void givenAsListMethodHandle_whenInvokingWithArguments_thenCorrectlyInvoked() throws Throwable {
        MethodHandles.Lookup publicLookup = MethodHandles.publicLookup();
        MethodType mt = MethodType.methodType(List.class, Object[].class);
        MethodHandle asListMH = publicLookup.findStatic(Arrays.class, "asList", mt);

        List<Integer> list = (List<Integer>) asListMH.invokeWithArguments(1, 2);

        assertThat(Arrays.asList(1, 2), is(list));
    }

    @Test
    public void givenConstructorMethodHandle_whenInvoked_thenObjectCreatedCorrectly() throws Throwable {
        MethodHandles.Lookup publicLookup = MethodHandles.publicLookup();
        MethodType mt = MethodType.methodType(void.class, String.class);
        MethodHandle newIntegerMH = publicLookup.findConstructor(Integer.class, mt);

        Integer integer = (Integer) newIntegerMH.invoke("1");

        assertEquals(1, integer.intValue());
    }

    @Test
    public void givenAFieldWithoutGetter_whenCreatingAGetter_thenCorrectlyInvoked() throws Throwable {
        MethodHandles.Lookup lookup = MethodHandles.lookup();
        MethodHandle getTitleMH = lookup.findGetter(Book.class, "title", String.class);

        Book book = new Book("ISBN-1234", "Effective Java");

        assertEquals("Effective Java", getTitleMH.invoke(book));
    }

    @Test
    public void givenPrivateMethod_whenCreatingItsMethodHandle_thenCorrectlyInvoked() throws Throwable {
        MethodHandles.Lookup lookup = MethodHandles.lookup();
        Method formatBookMethod = Book.class.getDeclaredMethod("formatBook");
        formatBookMethod.setAccessible(true);

        MethodHandle formatBookMH = lookup.unreflect(formatBookMethod);

        Book book = new Book("ISBN-123", "Java in Action");

        assertEquals("ISBN-123 > Java in Action", formatBookMH.invoke(book));
    }

    @Test
    public void givenReplaceMethod_whenUsingReflectionAndInvoked_thenCorrectlyReplaced() throws Throwable {
        Method replaceMethod = String.class.getMethod("replace", char.class, char.class);

        String string = (String) replaceMethod.invoke("jovo", 'o', 'a');

        assertEquals("java", string);
    }

    @Test
    public void givenReplaceMethodHandle_whenInvoked_thenCorrectlyReplaced() throws Throwable {
        MethodHandles.Lookup publicLookup = MethodHandles.publicLookup();
        MethodType mt = MethodType.methodType(String.class, char.class, char.class);
        MethodHandle replaceMH = publicLookup.findVirtual(String.class, "replace", mt);

        String replacedString = (String) replaceMH.invoke("jovo", Character.valueOf('o'), 'a');

        assertEquals("java", replacedString);
    }

    @Test
    public void givenReplaceMethodHandle_whenInvokingExact_thenCorrectlyReplaced() throws Throwable {
        MethodHandles.Lookup lookup = MethodHandles.lookup();
        MethodType mt = MethodType.methodType(String.class, char.class, char.class);
        MethodHandle replaceMH = lookup.findVirtual(String.class, "replace", mt);

        String s = (String) replaceMH.invokeExact("jovo", 'o', 'a');

        assertEquals("java", s);
    }

    @Test
    public void givenSumMethodHandle_whenInvokingExact_thenSumIsCorrect() throws Throwable {
        MethodHandles.Lookup lookup = MethodHandles.lookup();
        MethodType mt = MethodType.methodType(int.class, int.class, int.class);
        MethodHandle sumMH = lookup.findStatic(Integer.class, "sum", mt);

        int sum = (int) sumMH.invokeExact(1, 11);

        assertEquals(12, sum);
    }

    @Test(expected = WrongMethodTypeException.class)
    public void givenSumMethodHandleAndIncompatibleArguments_whenInvokingExact_thenException() throws Throwable {
        MethodHandles.Lookup lookup = MethodHandles.lookup();
        MethodType mt = MethodType.methodType(int.class, int.class, int.class);
        MethodHandle sumMH = lookup.findStatic(Integer.class, "sum", mt);

        sumMH.invokeExact(Integer.valueOf(1), 11);
    }

    @Test
    public void givenSpreadedEqualsMethodHandle_whenInvokedOnArray_thenCorrectlyEvaluated() throws Throwable {
        MethodHandles.Lookup publicLookup = MethodHandles.publicLookup();
        MethodType mt = MethodType.methodType(boolean.class, Object.class);
        MethodHandle equalsMH = publicLookup.findVirtual(String.class, "equals", mt);

        MethodHandle methodHandle = equalsMH.asSpreader(Object[].class, 2);

        assertTrue((boolean) methodHandle.invoke(new Object[] { "java", "java" }));
        assertFalse((boolean) methodHandle.invoke(new Object[] { "java", "jova" }));
    }

    @Test
    public void givenConcatMethodHandle_whenBindToAString_thenCorrectlyConcatenated() throws Throwable {
        MethodHandles.Lookup publicLookup = MethodHandles.publicLookup();
        MethodType mt = MethodType.methodType(String.class, String.class);
        MethodHandle concatMH = publicLookup.findVirtual(String.class, "concat", mt);

        MethodHandle bindedConcatMH = concatMH.bindTo("Hello ");

        assertEquals("Hello World!", bindedConcatMH.invoke("World!"));
    }
}
