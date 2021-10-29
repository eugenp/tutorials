package com.baeldung.loadedclasslisting;

import java.io.IOException;
import java.util.Set;
import java.util.stream.Collectors;

import org.reflections.Reflections;
import org.reflections.scanners.SubTypesScanner;

import com.google.common.collect.ImmutableSet;
import com.google.common.reflect.ClassPath;
import com.google.common.reflect.ClassPath.ClassInfo;

public class ListLoadedClass {

    public ImmutableSet<ClassInfo> listClassLoaded() throws IOException {
        return ClassPath.from(ListLoadedClass.class.getClassLoader())
                .getAllClasses();
    }

    public Set<Class> listClassLoaded(String packageName) throws IOException {
        return ClassPath.from(ClassLoader.getSystemClassLoader()).getAllClasses().stream()
                .filter(clazz -> clazz.getPackageName().equals(packageName))
                .map(ClassInfo::load)
                .collect(Collectors.toSet());
    }

    public Set<Class> findAllClassesUsingReflectionsLibrary(String packageName) {
        Reflections reflections = new Reflections(packageName, new SubTypesScanner(false));
        return reflections.getSubTypesOf(Object.class)
               .stream()
               .collect(Collectors.toSet());
    }
    
}
