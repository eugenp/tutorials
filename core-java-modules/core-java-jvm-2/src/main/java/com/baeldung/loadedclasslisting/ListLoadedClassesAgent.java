package com.baeldung.loadedclasslisting;

import java.lang.instrument.Instrumentation;

public class ListLoadedClassesAgent {

    private static Instrumentation instrumentation;

    public static void premain(String agentArgs, Instrumentation instrumentation) {
        ListLoadedClassesAgent.instrumentation = instrumentation;
    }

    public static Class<?>[] listLoadedClasses(String classLoaderType) {
        if (instrumentation == null) {
            throw new IllegalStateException(
              "ListLoadedClassesAgent is not initialized.");
        }
        return instrumentation.getInitiatedClasses(
          getClassLoader(classLoaderType));
    }

    private static ClassLoader getClassLoader(String classLoaderType) {
        ClassLoader classLoader = null;
        switch (classLoaderType) {
        case "SYSTEM":
            classLoader = ClassLoader.getSystemClassLoader();
            break;
        case "EXTENSION":
            classLoader = ClassLoader.getSystemClassLoader().getParent();
            break;
        // passing a null value to the Instrumentation : getInitiatedClasses method
        // defaults to the bootstrap class loader
        case "BOOTSTRAP":
            break;
        default:
            break;
        }
        return classLoader;
    }
}
