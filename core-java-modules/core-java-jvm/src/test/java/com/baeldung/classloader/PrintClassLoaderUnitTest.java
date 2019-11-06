package com.baeldung.classloader;

import org.junit.Test;

import static org.junit.Assert.*;

public class PrintClassLoaderUnitTest {
    @Test(expected = ClassNotFoundException.class)
    public void givenAppClassLoader_whenParentClassLoader_thenClassNotFoundException() throws Exception {
        PrintClassLoader sampleClassLoader = (PrintClassLoader) Class.forName(PrintClassLoader.class.getName()).newInstance();
        sampleClassLoader.printClassLoaders();
        Class.forName(PrintClassLoader.class.getName(), true, PrintClassLoader.class.getClassLoader().getParent());
    }
}