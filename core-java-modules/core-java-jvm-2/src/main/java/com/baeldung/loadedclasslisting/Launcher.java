package com.baeldung.loadedclasslisting;

import java.util.Arrays;

public class Launcher {

    public static void main(String[] args) {
        printClassesLoadedBy("BOOTSTRAP");
        printClassesLoadedBy("SYSTEM");
        printClassesLoadedBy("EXTENSION");
    }

    private static void printClassesLoadedBy(String classLoaderType) {
        System.out.println(classLoaderType + " ClassLoader : ");
        Class<?>[] classes = ListLoadedClassesAgent.listLoadedClasses(classLoaderType);
        Arrays.asList(classes)
            .forEach(clazz -> System.out.println(clazz.getCanonicalName()));
    }
}
