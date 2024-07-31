package com.baeldung.reflection.access.packages;

import java.io.IOException;
import java.util.Set;

import org.junit.Rule;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.rules.ExpectedException;

@SuppressWarnings("rawtypes")
public class AccessingAllClassesInPackageUnitTest {
    @Rule
    public final ExpectedException exception = ExpectedException.none();

    private static final String PACKAGE_NAME = "com.baeldung.reflection.access.packages.search";

    @Test
    public void when_findAllClassesUsingClassLoader_thenSuccess() {
        AccessingAllClassesInPackage instance = new AccessingAllClassesInPackage();
        Set<Class> classes = instance.findAllClassesUsingClassLoader(PACKAGE_NAME);
        Assertions.assertEquals(5, classes.size());
    }

    @Test
    public void when_findAllClassesUsingReflectionsLibrary_thenSuccess() {
        AccessingAllClassesInPackage instance = new AccessingAllClassesInPackage();
        Set<Class> classes = instance.findAllClassesUsingReflectionsLibrary(PACKAGE_NAME);
        Assertions.assertEquals(5, classes.size());
    }

    @Test
    public void when_findAllClassesUsingGoogleGuice_thenSuccess() throws IOException {
        AccessingAllClassesInPackage instance = new AccessingAllClassesInPackage();
        Set<Class> classes = instance.findAllClassesUsingGoogleGuice(PACKAGE_NAME);
        Assertions.assertEquals(5, classes.size());
    }

}
