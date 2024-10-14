package com.baeldung.classloader.spi;

import java.net.URL;
import java.util.HashSet;
import java.util.Set;

import com.baeldung.classloader.internal.BasicClasspathResolver;
import com.baeldung.classloader.internal.InternalClasspathResolver;
import com.baeldung.classloader.internal.InternalJdkSupport;

public interface ClasspathResolver {

    public static ClasspathResolver get() {
        return InternalJdkSupport.available() ? new InternalClasspathResolver() : new BasicClasspathResolver();
    }

    void collectClasspath(ClassLoader loader, Set<URL> result);

    default Set<URL> getClasspath(ClassLoader loader) {
        var result = new HashSet<URL>();
        collectClasspath(loader, result);
        return result;
    }

    default Set<URL> getFullClasspath(ClassLoader loader) {
        var result = new HashSet<URL>();
        collectClasspath(loader, result);
        loader = loader.getParent();

        while (loader != null) {
            collectClasspath(loader, result);
            loader = loader.getParent();
        }
        return result;
    }

}