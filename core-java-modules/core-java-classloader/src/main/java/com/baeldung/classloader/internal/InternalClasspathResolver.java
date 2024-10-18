package com.baeldung.classloader.internal;

import java.net.URL;
import java.net.URLClassLoader;
import java.util.Arrays;
import java.util.Collections;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.baeldung.classloader.spi.ClasspathResolver;

import jdk.internal.loader.BuiltinClassLoader;
import jdk.internal.loader.URLClassPath;

public final class InternalClasspathResolver implements ClasspathResolver {

    final Logger log = LoggerFactory.getLogger(getClass());

    @Override
    public void collectClasspath(ClassLoader loader, Set<URL> result) {
        log.info("Collecting URLs from {}", loader.getClass()
            .getSimpleName());

        var urls = switch (loader) {
            case URLClassLoader ucl -> Arrays.asList(ucl.getURLs());
            case BuiltinClassLoader bcl -> {
                URLClassPath ucp = (URLClassPath) InternalJdkSupport.getURLClassPath(loader);

                yield ucp == null ? Collections.<URL> emptyList() : Arrays.asList(ucp.getURLs());
            }
            default -> {
                log.warn("Cannot handle {}", loader);
                yield Collections.<URL> emptyList();
            }
        };

        log.info("Collected {} URLs from {}", urls.size(), loader.getClass()
            .getSimpleName());

        result.addAll(urls);
    }

}