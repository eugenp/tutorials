package com.baeldung.inmemorycompilation;

import static java.util.Objects.requireNonNull;

import java.util.Map;

public class InMemoryClassLoader extends ClassLoader {

    private final InMemoryFileManager manager;

    public InMemoryClassLoader(ClassLoader parent, InMemoryFileManager manager) {
        super(parent);
        this.manager = requireNonNull(manager, "manager must not be null");
    }

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {

        Map<String, JavaClassAsBytes> compiledClasses = manager
            .getBytesMap();

        if (compiledClasses.containsKey(name)) {
            byte[] bytes = compiledClasses.get(name)
                                          .getBytes();
            return defineClass(name, bytes, 0, bytes.length);
        } else {
            throw new ClassNotFoundException();
        }
    }
}