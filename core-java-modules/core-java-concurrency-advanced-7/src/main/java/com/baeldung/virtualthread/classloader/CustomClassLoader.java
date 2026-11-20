package com.baeldung.virtualthread.classloader;

import java.io.IOException;
import java.nio.file.Path;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

class CustomClassLoader extends ClassLoader {

    private static final Logger LOGGER = LoggerFactory.getLogger(CustomClassLoader.class);
    private final Path classDir;

    public CustomClassLoader(Path classDir) {
        super(ClassLoader.getSystemClassLoader());
        this.classDir = classDir;
    }

    @Override
    protected Class<?> loadClass(String name, boolean resolve) throws ClassNotFoundException {
        LOGGER.info("Load class for {}", name);

        Class<?> clazz = findLoadedClass(name);

        if (clazz == null) {
            try {
                clazz = findClass(name);
            } catch (ClassNotFoundException ex) {
                clazz = super.loadClass(name, resolve);
            }
        }

        if (resolve) {
            resolveClass(clazz);
        }

        return clazz;
    }

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        LOGGER.info("Finding class for {}", name);

        try {
            Path file = classDir.resolve(name.replace('.', '/') + ".class");
            byte[] bytes = java.nio.file.Files.readAllBytes(file);
            Thread.sleep(100);

            return defineClass(name, bytes, 0, bytes.length);
        } catch (InterruptedException | IOException ex) {
            LOGGER.error("Error while finding class file {}", ex.getMessage());
            throw new ClassNotFoundException(ex.getMessage(), ex);
        }
    }
}
