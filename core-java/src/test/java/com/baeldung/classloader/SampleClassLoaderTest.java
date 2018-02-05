package com.baeldung.classloader;

import org.junit.Test;

import static org.junit.Assert.*;

public class SampleClassLoaderTest {
        @Test(expected = ClassNotFoundException.class)
        public void givenAppClassLoader_whenParentClassLoader_thenClassNotFoundException() throws Exception {
                SampleClassLoader sampleClassLoader = (SampleClassLoader) Class.forName(SampleClassLoader.class.getName()).newInstance();
                sampleClassLoader.loadClass();

        }
}