package com.baeldung.constructors.exception;

import java.io.File;
import java.io.IOException;

public class Animal {

    public Animal() throws InstantiationException {
        throw new InstantiationException("Cannot be instantiated");
    }

    public Animal(String id, int age) {
        if (id == null)
            throw new NullPointerException("Id cannot be null");
        if (age < 0)
            throw new IllegalArgumentException("Age cannot be negative");
    }

    public Animal(File file) throws SecurityException, IOException {
        // Avoiding Path traversal attacks
        if (file.isAbsolute()) {
            throw new SecurityException("Traversal attack - absolute path not allowed");
        }
        // Avoiding directory traversal
        // a/../..b/
        if (!file.getCanonicalPath()
            .equals(file.getAbsolutePath())) {
            throw new SecurityException("Directory traversal attempt?");
        }
    }

}
