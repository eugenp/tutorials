package com.baeldung.accessflag;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.lang.reflect.AccessFlag;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Set;

import org.junit.jupiter.api.Test;

class AccessFlagUnitTest {

    @Test
    void givenStaticFinalMethod_whenGetAccessFlag_thenReturnCorrectFlags() throws Exception {
        Class<?> clazz = Class.forName(AccessFlagDemo.class.getName());
        Method method = clazz.getMethod("staticFinalMethod");

        Set<AccessFlag> accessFlagSet = method.accessFlags();

        assertEquals(3, accessFlagSet.size());
        assertTrue(accessFlagSet.contains(AccessFlag.PUBLIC));
        assertTrue(accessFlagSet.contains(AccessFlag.STATIC));
        assertTrue(accessFlagSet.contains(AccessFlag.FINAL));
    }

    @Test
    void givenStaticFinalMethod_whenGetModifiers_thenReturnIsStaticTrue() throws Exception {
        Class<?> clazz = Class.forName(AccessFlagDemo.class.getName());
        Method method = clazz.getMethod("staticFinalMethod");

        int methodModifiers = method.getModifiers();

        assertEquals(25, methodModifiers);
        assertTrue(Modifier.isStatic(methodModifiers));
    }

    @Test
    void givenVarArgsMethod_whenGetModifiers_thenReturnPublicTransientModifiers() throws Exception {
        Class<?> clazz = Class.forName(AccessFlagDemo.class.getName());
        Method method = clazz.getMethod("varArgsMethod", String[].class);

        int methodModifiers = method.getModifiers();

        assertEquals("public transient", Modifier.toString(methodModifiers));
    }

    @Test
    void givenVarArgsMethod_whenGetAccessFlag_thenReturnPublicVarArgsFlags() throws Exception {
        Class<?> clazz = Class.forName(AccessFlagDemo.class.getName());
        Method method = clazz.getMethod("varArgsMethod", String[].class);

        Set<AccessFlag> accessFlagSet = method.accessFlags();

        assertEquals("[PUBLIC, VARARGS]", accessFlagSet.toString());
    }

    @Test
    void givenStrictfpMethod_whenGetAccessFlag_thenReturnOnlyPublicFlag() throws Exception {
        Class<?> clazz = Class.forName(AccessFlagDemo.class.getName());
        Method method = clazz.getMethod("strictfpMethod");

        Set<AccessFlag> accessFlagSet = method.accessFlags();

        assertEquals(1, accessFlagSet.size());
        assertTrue(accessFlagSet.contains(AccessFlag.PUBLIC));
    }

}
