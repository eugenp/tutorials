package com.baeldung.staticgc;

import java.io.File;
import java.io.IOException;
import org.apache.commons.io.IOUtils;

public class CustomClassloader extends ClassLoader {

    public static final String PREFIX = "com.baeldung.classloader";

    public CustomClassloader(ClassLoader parent) {
        super(parent);
    }

    @Override
    public Class<?> loadClass(String name) throws ClassNotFoundException {
        if (name.startsWith(PREFIX)) {
            return getClass(name);
        } else {
            return super.loadClass(name);
        }
    }

    private Class<?> getClass(String name) {
        String fileName = name.replace('.', File.separatorChar) + ".class";
        try {
            byte[] byteArr = IOUtils.toByteArray(getClass().getClassLoader().getResourceAsStream(fileName));
            Class<?> c = defineClass(name, byteArr, 0, byteArr.length);
            resolveClass(c);
            return c;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}