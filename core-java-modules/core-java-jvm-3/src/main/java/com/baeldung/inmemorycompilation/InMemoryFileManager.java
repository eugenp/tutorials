package com.baeldung.inmemorycompilation;

import java.util.Hashtable;
import java.util.Map;
import javax.tools.FileObject;
import javax.tools.ForwardingJavaFileManager;
import javax.tools.JavaFileManager;
import javax.tools.JavaFileObject;
import javax.tools.JavaFileObject.Kind;
import javax.tools.StandardJavaFileManager;

public class InMemoryFileManager extends ForwardingJavaFileManager<JavaFileManager> {

    private final Map<String, JavaClassAsBytes> compiledClasses;
    private final ClassLoader loader;

    public InMemoryFileManager(StandardJavaFileManager standardManager) {
        super(standardManager);
        this.compiledClasses = new Hashtable<>();
        this.loader = new InMemoryClassLoader(this.getClass()
                                                                                   .getClassLoader(),
            this
        );
    }

    /**
     * Used to get the class loader for our compiled class. It creates an anonymous class extending
     * the SecureClassLoader which uses the byte code created by the compiler and stored in the
     * JavaClassObject, and returns the Class for it
     *
     * @param location where to place or search for file objects.
     */
    @Override
    public ClassLoader getClassLoader(Location location) {
        return loader;
    }

    @Override
    public JavaFileObject getJavaFileForOutput(Location location, String className, Kind kind,
        FileObject sibling) {

        JavaClassAsBytes classAsBytes = new JavaClassAsBytes(
            className, kind);
        compiledClasses.put(className, classAsBytes);

        return classAsBytes;
    }

    public Map<String, JavaClassAsBytes> getBytesMap() {
        return compiledClasses;
    }
}
