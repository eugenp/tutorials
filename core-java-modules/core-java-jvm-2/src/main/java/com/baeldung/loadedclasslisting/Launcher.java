package com.baeldung.loadedclasslisting;

import java.util.Arrays;

public class Launcher {

    public static void main(String[] args) {
        printClassesLoadedBy("BOOTSTRAP");
        printClassesLoadedBy("SYSTEM");
        printClassesLoadedBy("EXTENSION");
    }

    private static void printClassesLoadedBy(String classLoaderType) {
        Class<?>[] classes = ListLoadedClassesAgent.listLoadedClasses(classLoaderType);
        Arrays.asList(classes)
            .forEach(clazz -> System.out.println(
              classLoaderType + " ClassLoader : " + clazz.getCanonicalName()));
    }
}
