package com.baeldung.annotations.parser;

import com.baeldung.annotations.MethodDescription;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class MethodDescriptionParser {

    /**
     * @return method names annotated with @MethodDescription in @param klass
     */
    public static List<String> listMethodDescriptions(Class klass) {
        List<String> methodNames = new ArrayList<>();

        for (Method method : klass.getDeclaredMethods()) {
            method.setAccessible(true);
            if (method.isAnnotationPresent(MethodDescription.class)) {
                methodNames.add(method.getName());
            }
        }

        return methodNames;
    }
}
