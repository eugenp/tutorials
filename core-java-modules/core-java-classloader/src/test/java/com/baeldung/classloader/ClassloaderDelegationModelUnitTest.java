package com.baeldung.classloader;

import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertSame;
import static org.junit.Assume.assumeNotNull;
import static org.junit.Assume.assumeTrue;

import java.io.IOException;
import java.net.URL;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.baeldung.classloader.internal.InternalClasspathResolver;
import com.baeldung.classloader.internal.InternalJdkSupport;

class ClassloaderDelegationModelUnitTest {

    private static final String CLASS_TO_LOAD = "com.google.common.base.Function";

    final Logger logger = LoggerFactory.getLogger(getClass());

    @Test
    void givenURLClassLoader_whenChildOfAppClassLoader_thenWillLoadNotNewInstanceOfClassWithSameName() throws ClassNotFoundException, IOException {
        var scope = scope();
        var parent = getClass().getClassLoader();

        try (var loaderOne = new CustomClassLoader(parent, scope); var loaderTwo = new CustomClassLoader(parent, scope)) {
            var functionOne = loaderOne.loadClass(CLASS_TO_LOAD);
            var functionTwo = loaderTwo.loadClass(CLASS_TO_LOAD);

            assertSame(functionOne, functionTwo);
            assertSame(functionOne, parent.loadClass(CLASS_TO_LOAD));
        }
    }

    @Test
    void givenURLClassLoader_whenIsolatedFromAppClassLoader_thenWillLoadNewInstanceOfClassWithSameName() throws ClassNotFoundException, IOException {
        var scope = scope();
        var parent = getClass().getClassLoader();

        try (var loaderOne = new CustomClassLoader(scope); var loaderTwo = new CustomClassLoader(scope)) {
            var functionOne = loaderOne.loadClass(CLASS_TO_LOAD);
            var functionTwo = loaderTwo.loadClass(CLASS_TO_LOAD);

            assertNotSame(functionOne, functionTwo);
            assertNotSame(functionOne, parent.loadClass(CLASS_TO_LOAD));
        }
    }

    URL[] scope() {
        assumeTrue(InternalJdkSupport.available());

        var resolver = new InternalClasspathResolver();

        var loader = getClass().getClassLoader();

        var urls = resolver.getFullClasspath(loader);

        var guava = urls.stream()
            .filter(url -> url.toString()
                .contains("guava"))
            .findFirst()
            .orElse(null);

        assumeNotNull(guava);

        return new URL[] { guava };
    }
}