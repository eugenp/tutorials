package com.baeldung.compilerApi;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.tools.*;
import java.io.*;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class JavaCompilerUtils {

    private final JavaCompiler compiler;
    private final StandardJavaFileManager standardFileManager;
    private final Path outputDirectory;

    private static final Logger logger = LoggerFactory.getLogger(JavaCompilerUtils.class);

    public JavaCompilerUtils(Path outputDirectory) throws IOException {
        this.outputDirectory = outputDirectory;
        this.compiler = ToolProvider.getSystemJavaCompiler();

        if (compiler == null) {
            throw new IllegalStateException("Java compiler not available. Ensure you're using JDK, not JRE.");
        }

        this.standardFileManager = compiler.getStandardFileManager(null, null, StandardCharsets.UTF_8);

        if (!Files.exists(outputDirectory)) {
            Files.createDirectories(outputDirectory);
        }

        standardFileManager.setLocation(StandardLocation.CLASS_OUTPUT, Collections.singleton(outputDirectory.toFile()));
    }

    public boolean compileFile(Path sourceFile) {
        if (!Files.exists(sourceFile)) {
            throw new IllegalArgumentException("Source file does not exist: " + sourceFile);
        }

        try {
            Iterable<? extends JavaFileObject> compilationUnits =
                    standardFileManager.getJavaFileObjectsFromFiles(Collections.singletonList(sourceFile.toFile()));
            return compile(compilationUnits);
        } catch (Exception e) {
            logger.error("Compilation failed: ", e);
            return false;
        }
    }

    public boolean compileFromString(String className, String sourceCode) {
        JavaFileObject sourceObject = new InMemoryJavaFile(className, sourceCode);
        return compile(Collections.singletonList(sourceObject));
    }

    private boolean compile(Iterable<? extends JavaFileObject> compilationUnits) {
        DiagnosticCollector<JavaFileObject> diagnostics = new DiagnosticCollector<>();

        JavaCompiler.CompilationTask task = compiler.getTask(
                null,
                standardFileManager,
                diagnostics,
                null,
                null,
                compilationUnits
        );

        boolean success = task.call();

        for (Diagnostic<? extends JavaFileObject> diagnostic : diagnostics.getDiagnostics()) {
            logger.debug(diagnostic.getMessage(null));
        }

        return success;
    }

    public void runClass(String className, String... args) throws Exception {
        try (URLClassLoader classLoader = new URLClassLoader(new URL[]{outputDirectory.toUri().toURL()})) {
            Class<?> loadedClass = classLoader.loadClass(className);
            loadedClass.getMethod("main", String[].class).invoke(null, (Object) args);
        }
    }

    public Path getOutputDirectory() {
        return outputDirectory;
    }
}