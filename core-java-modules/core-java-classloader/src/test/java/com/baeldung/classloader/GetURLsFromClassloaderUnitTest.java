package com.baeldung.classloader;

import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertTrue;
import static org.junit.Assume.assumeFalse;
import static org.junit.Assume.assumeTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URLClassLoader;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.baeldung.classloader.internal.BasicClasspathResolver;
import com.baeldung.classloader.internal.InternalClasspathResolver;
import com.baeldung.classloader.internal.InternalJdkSupport;

class GetURLsFromClassloaderUnitTest {

    final Logger log = LoggerFactory.getLogger(getClass());

    @Test
    void givenApplicationClassLoader_whenInspectingBasicResolver_thenURLsCannotBeDetermined() {
        var loader = getClass().getClassLoader();

        assumeFalse(loader instanceof URLClassLoader);

        var resolver = new BasicClasspathResolver();

        var classpath = resolver.getFullClasspath(loader);

        assertTrue(classpath.isEmpty());
    }

    @Test
    void givenApplicationClassLoader_whenInspectingInternalResolver_thenURLsCanBeDetermined() {
        assumeTrue(InternalJdkSupport.available());

        var resolver = new InternalClasspathResolver();
        var loader = getClass().getClassLoader();

        assumeFalse(loader instanceof URLClassLoader);

        var classpath = resolver.getFullClasspath(loader);

        assertFalse(classpath.isEmpty());
    }

    @Test
    void givenCustomClassLoader_whenInspectingInternalResolver_thenURLsCanBeDetermined() throws MalformedURLException {
        var url = URI.create("https://repo1.maven.org/maven2/com/google/code/findbugs/jsr305/3.0.2/jsr305-3.0.2.jar")
            .toURL();

        try (var loader = new CustomClassLoader(url)) {
            Class<?> nonNull = loader.loadClass(javax.annotation.Nonnull.class.getName());

            assertNotSame(javax.annotation.Nonnull.class, nonNull);

            var resolver = new InternalClasspathResolver();

            var classpath = resolver.getFullClasspath(loader);

            assertEquals(1, classpath.size());

            assertTrue(classpath.contains(url));
        } catch (IOException | ClassNotFoundException e) {
            log.warn("Download of {} failed", url);
            log.warn(e.getMessage(), e);
        }
    }
}
