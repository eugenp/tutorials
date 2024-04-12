package com.baeldung.loadedclasslisting;

import java.io.IOException;
import java.util.Set;

import org.junit.Test;
import org.junit.jupiter.api.Assertions;

import com.baeldung.loadedclasslisting.ListLoadedClass;
import com.google.common.collect.ImmutableSet;
import com.google.common.reflect.ClassPath;
import com.google.common.reflect.ClassPath.ClassInfo;

public class ListLoadedClassUnitTest {

    private static final String PACKAGE_NAME = "com.baeldung.loadedclasslisting";

    @Test
    public void when_findAllClassesUsingReflectionsLibrary_thenSuccess() {
        ListLoadedClass instance = new ListLoadedClass();
        Set<Class> classes = instance.findAllClassesUsingReflectionsLibrary(PACKAGE_NAME);

        Assertions.assertEquals(4, classes.size());
    }

    @Test
    public void when_findAllClassesUsingGuavaLibrary_InPackage_thenSuccess() throws IOException {
        ListLoadedClass instance = new ListLoadedClass();
        Set<Class> classes = instance.listClassLoaded(PACKAGE_NAME);

        Assertions.assertEquals(4, classes.size());
    }

    @Test
    public void when_findAllClassesUsingGuavaLibrary_thenSuccess() throws IOException {
        ListLoadedClass instance = new ListLoadedClass();
        Set<ClassInfo> classes = instance.listClassLoaded();

        Assertions.assertTrue(4<classes.size());
    }
}
