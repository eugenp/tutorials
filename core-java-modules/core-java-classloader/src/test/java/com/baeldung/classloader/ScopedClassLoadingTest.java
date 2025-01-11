package com.baeldung.classloader;

import static org.junit.Assume.assumeTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.lang.ProcessBuilder.Redirect;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.function.Predicate;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.baeldung.classloader.internal.InternalJdkSupport;
import com.baeldung.classloader.spi.ClasspathResolver;

class ScopedClassLoadingTest {

    final Logger log = LoggerFactory.getLogger(getClass());

    /**
     * Some ides may treat test-classes as a dynamic module-path.
     *
     */
    private void ammendTestClasspath(Set<URL> classpath) {
        var testCp = classpath.stream()
            .filter(url -> Objects.equals(url.getProtocol(), "file") && url.getPath()
                .contains("test-classes"))
            .findFirst()
            .orElse(null);

        if (testCp == null) {
            log.info("Amending test classpath for Eclipse");

            var loc = getClass().getProtectionDomain()
                .getCodeSource()
                .getLocation();

            testCp = toURL(loc.toString());

            classpath.add(testCp);
        }

        log.info("Test Classpath: {}", testCp);
    }

    private Set<URL> createNarrowClasspath(Predicate<URL> filter) {
        assumeTrue(InternalJdkSupport.available());

        var loader = getClass().getClassLoader();

        var full = ClasspathResolver.get()
            .getFullClasspath(loader);

        ammendTestClasspath(full);

        mergeClasspathWithModulePath(full, filter);

        var classpath = full.stream()
            .filter(filter)
            .collect(Collectors.toCollection(HashSet::new));

        log.info("Narrowed Classpath: \n[\n{}\n]", classpath);

        return classpath;
    }

    @Test
    void givenAForkedJVM_whenClassPathIsNarrowed_thenAccessWillBeLimitedToItsScope() throws InterruptedException, IOException {
        var scope = Pattern.compile("(test-classes|slf|logback)");

        var classpath = createNarrowClasspath(url -> scope.matcher(url.toString())
            .find()).stream()
            .map(URL::toString)
            .collect(Collectors.joining(":"));

        var executable = ProcessHandle.current()
            .info()
            .command()
            .orElse("java");

        var pb = new ProcessBuilder(executable, "-cp");
        var command = pb.command();
        command.add(classpath);
        command.add(ForkedService.class.getName());

        pb.redirectOutput(Redirect.INHERIT);
        pb.redirectInput(Redirect.INHERIT);
        pb.redirectError(Redirect.INHERIT);

        log.info("VM at PID {} will fork another JVM with narrowed classpath", ProcessHandle.current()
            .pid());

        var process = pb.start();

        var status = process.waitFor();

        assertEquals(0, status);
    }

    @Test
    void givenScopedClassLoader_whenClasspathIsNarrowed_thenAccessWillBeLimitedToItsScope() throws InterruptedException, IOException,
    ReflectiveOperationException {
        var thread = Thread.currentThread();
        var current = thread.getContextClassLoader();

        assertEquals(current, ForkedService.class.getClassLoader());

        var scope = Pattern.compile("(test-classes|slf|logback)");

        var classpath = createNarrowClasspath(url -> scope.matcher(url.toString())
            .find()).toArray(URL[]::new);

        var loader = new CustomClassLoader(classpath);

        thread.setContextClassLoader(loader);

        try {
            var service = Class.forName(ForkedService.class.getName(), true, Thread.currentThread()
                .getContextClassLoader());

            assertEquals(loader, service.getClassLoader());

            ((Runnable) service.getConstructor()
                .newInstance()).run();
        } finally {
            thread.setContextClassLoader(current);
        }
    }

    private void mergeClasspathWithModulePath(Set<URL> files, Predicate<URL> filter) {
        var modules = System.getProperty("jdk.module.path");

        if (modules != null && !modules.isBlank()) {
            log.info("Converting module-path ({}) to classpath", modules);

            Arrays.stream(modules.split(":"))
            .map(this::toURL)
            .filter(filter)
            .forEach(files::add);
        } else {
            log.info("No module path");
        }
    }

    private URL toURL(String name) {
        if (!name.startsWith("file:")) {
            name = "file://" + name;
        }
        try {
            return URI.create(name)
                .toURL();
        } catch (MalformedURLException e) {
            throw new UncheckedIOException(e);
        }
    }
}