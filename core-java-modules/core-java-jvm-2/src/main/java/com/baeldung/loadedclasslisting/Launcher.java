package com.baeldung.loadedclasslisting;

import java.lang.reflect.Method;
import java.util.Arrays;

import com.baeldung.loadedclasslisting.ListLoadedClassesAgent.ClassLoaderType;
import com.baeldung.loadedclasslisting.customLoader.ClassLoaderInfo;
import com.baeldung.loadedclasslisting.customLoader.CustomClassLoader;

public class Launcher {

    private static ClassLoader customClassLoader;

    public static void main(String[] args) {
        printClassesLoadedBy(ClassLoaderType.BOOTSTRAP);
        printClassesLoadedBy(ClassLoaderType.SYSTEM);
        printClassesLoadedBy(ClassLoaderType.EXTENSION);
        printClassesLoadedBy(ClassLoaderType.CUSTOM);
    }

    private static void printClassesLoadedBy(ClassLoaderType classLoaderType) {
        Class<?>[] classes;
        if (classLoaderType.equals(ClassLoaderType.CUSTOM)) {
            customClassLoader = customClassLoading();
            classes = ListLoadedClassesAgent.listLoadedClasses(customClassLoader);
        } else {
            classes = ListLoadedClassesAgent.listLoadedClasses(classLoaderType);
        }
        Arrays.asList(classes)
            .forEach(clazz -> System.out.println(
              classLoaderType + " ClassLoader : " + clazz.getCanonicalName()));
    }
    
    private static CustomClassLoader customClassLoading() {
        CustomClassLoader customClassLoader = new CustomClassLoader();
        Class<?> c;
        try {
            c = customClassLoader.findClass(ClassLoaderInfo.class.getName());
            Object ob = c.getDeclaredConstructor()
                .newInstance();
            Method md = c.getMethod("printClassLoaders");
            md.invoke(ob);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return customClassLoader;
    }
}
