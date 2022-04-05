package com.baeldung.reflections;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.Set;
import java.util.regex.Pattern;

import org.reflections.Reflections;
import org.reflections.scanners.MethodAnnotationsScanner;
import org.reflections.scanners.MethodParameterScanner;
import org.reflections.scanners.ResourcesScanner;
import org.reflections.scanners.Scanner;
import org.reflections.scanners.SubTypesScanner;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;

public class ReflectionsApp {

    public Set<Class<? extends Scanner>> getReflectionsSubTypes() {
        Reflections reflections = new Reflections("org.reflections");
        Set<Class<? extends Scanner>> scannersSet = reflections.getSubTypesOf(Scanner.class);
        return scannersSet;
    }

    public Set<Class<?>> getJDKFunctinalInterfaces() {
        Reflections reflections = new Reflections("java.util.function");
        Set<Class<?>> typesSet = reflections.getTypesAnnotatedWith(FunctionalInterface.class);
        return typesSet;
    }

    public Set<Method> getDateDeprecatedMethods() {
        Reflections reflections = new Reflections(java.util.Date.class, new MethodAnnotationsScanner());
        Set<Method> deprecatedMethodsSet = reflections.getMethodsAnnotatedWith(Deprecated.class);
        return deprecatedMethodsSet;
    }

    @SuppressWarnings("rawtypes")
    public Set<Constructor> getDateDeprecatedConstructors() {
        Reflections reflections = new Reflections(java.util.Date.class, new MethodAnnotationsScanner());
        Set<Constructor> constructorsSet = reflections.getConstructorsAnnotatedWith(Deprecated.class);
        return constructorsSet;
    }

    public Set<Method> getMethodsWithDateParam() {
        Reflections reflections = new Reflections(java.text.SimpleDateFormat.class, new MethodParameterScanner());
        Set<Method> methodsSet = reflections.getMethodsMatchParams(Date.class);
        return methodsSet;
    }

    public Set<Method> getMethodsWithVoidReturn() {
        Reflections reflections = new Reflections(java.text.SimpleDateFormat.class, new MethodParameterScanner());
        Set<Method> methodsSet = reflections.getMethodsReturn(void.class);
        return methodsSet;
    }

    public Set<String> getPomXmlPaths() {
        Reflections reflections = new Reflections(new ResourcesScanner());
        Set<String> resourcesSet = reflections.getResources(Pattern.compile(".*pom\\.xml"));
        return resourcesSet;
    }

    public Set<Class<? extends Scanner>> getReflectionsSubTypesUsingBuilder() {
        Reflections reflections = new Reflections(new ConfigurationBuilder().setUrls(ClasspathHelper.forPackage("org.reflections"))
            .setScanners(new SubTypesScanner()));

        Set<Class<? extends Scanner>> scannersSet = reflections.getSubTypesOf(Scanner.class);
        return scannersSet;
    }

}
