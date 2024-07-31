package com.baeldung.reflection.check.staticmethods;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class StaticUtilityUnitTest {

    @Test
    void whenCheckStaticMethod_ThenSuccess() throws Exception {
        Method method = StaticUtility.class.getMethod("getAuthorName", null);
        Assertions.assertTrue(Modifier.isStatic(method.getModifiers()));
    }

    @Test
    void whenCheckAllStaticMethods_thenSuccess() {
        List<Method> methodList = Arrays.asList(StaticUtility.class.getMethods())
          .stream()
          .filter(method -> Modifier.isStatic(method.getModifiers()))
          .collect(Collectors.toList());
        Assertions.assertEquals(3, methodList.size());
    }

}
