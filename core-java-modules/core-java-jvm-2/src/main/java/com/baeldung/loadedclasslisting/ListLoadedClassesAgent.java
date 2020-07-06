package com.baeldung.loadedclasslisting;

import java.lang.instrument.Instrumentation;

public class ListLoadedClassesAgent {

    private static Instrumentation instrumentation;

    public static void premain(String agentArgs, Instrumentation instrumentation) {
        ListLoadedClassesAgent.instrumentation = instrumentation;
    }

    public static Class<?>[] listLoadedClasses(ClassLoaderType classLoaderType) {
        if (instrumentation == null) {
            throw new IllegalStateException(
              "ListLoadedClassesAgent is not initialized.");
        }
        return instrumentation.getInitiatedClasses(
          getClassLoader(classLoaderType));
    }

    public static Class<?>[] listLoadedClasses(ClassLoader classLoader) {
        if (instrumentation == null) {
            throw new IllegalStateException(
              "ListLoadedClassesAgent is not initialized.");
        }
        return instrumentation.getInitiatedClasses(classLoader);
    }

    private static ClassLoader getClassLoader(ClassLoaderType classLoaderType) {
        ClassLoader classLoader = null;
        switch (classLoaderType) {
        case SYSTEM:
            classLoader = ClassLoader.getSystemClassLoader();
            break;
        case EXTENSION:
            classLoader = ClassLoader.getSystemClassLoader().getParent();
            break;
        case BOOTSTRAP:
            break;
        default:
            break;
        }
        return classLoader;
    }
}
