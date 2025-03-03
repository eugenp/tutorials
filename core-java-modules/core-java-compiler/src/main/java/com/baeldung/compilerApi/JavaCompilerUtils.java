package com.baeldung.compilerApi;

import javax.tools.*;
import java.io.*;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

/**
 * A utility class for compiling Java source code using the Java Compiler API.
 * This class provides methods to compile Java code from strings or files.
 */
public class JavaCompilerUtils {

    private final JavaCompiler compiler;
    private final StandardJavaFileManager standardFileManager;
    private final Path outputDirectory;

    /**
     * Constructs a new JavaCompilerUtil instance.
     *
     * @param outputDirectory The directory where compiled classes will be stored
     * @throws IOException If there's an error creating the output directory
     */
    public JavaCompilerUtils(Path outputDirectory) throws IOException {
        this.outputDirectory = outputDirectory;
        this.compiler = ToolProvider.getSystemJavaCompiler();

        if (compiler == null) {
            throw new IllegalStateException("Java compiler not available. Ensure you're using JDK, not JRE.");
        }

        this.standardFileManager = compiler.getStandardFileManager(null, null, StandardCharsets.UTF_8);

        // Create output directory if it doesn't exist
        if (!Files.exists(outputDirectory)) {
            Files.createDirectories(outputDirectory);
        }

        // Set output directory for compiled classes
        standardFileManager.setLocation(StandardLocation.CLASS_OUTPUT, Collections.singleton(outputDirectory.toFile()));
    }

    /**
     * Compiles a Java source file.
     *
     * @param sourceFile The Java source file to compile
     * @return true if compilation was successful, false otherwise
     */
    public boolean compileFile(Path sourceFile) {
        if (!Files.exists(sourceFile)) {
            throw new IllegalArgumentException("Source file does not exist: " + sourceFile);
        }

        try {
            Iterable<? extends JavaFileObject> compilationUnits =
                    standardFileManager.getJavaFileObjectsFromFiles(Collections.singletonList(sourceFile.toFile()));
            return compile(compilationUnits);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Compiles Java source code from a string.
     *
     * @param className The name of the class (including package if any)
     * @param sourceCode The Java source code as a string
     * @return true if compilation was successful, false otherwise
     */
    public boolean compileFromString(String className, String sourceCode) {
        JavaFileObject sourceObject = new InMemoryJavaFile(className, sourceCode);
        return compile(Collections.singletonList(sourceObject));
    }

    /**
     * Common compilation method used by both compileFile and compileFromString.
     *
     * @param compilationUnits The compilation units to compile
     * @return true if compilation was successful, false otherwise
     */
    private boolean compile(Iterable<? extends JavaFileObject> compilationUnits) {
        DiagnosticCollector<JavaFileObject> diagnostics = new DiagnosticCollector<>();


        JavaCompiler.CompilationTask task = compiler.getTask(
                null,                       // Writer for compiler output
                standardFileManager,        // File manager
                diagnostics,                // Diagnostic listener
                null,                    // Compiler options
                null,                       // Classes to be processed by annotation processors
                compilationUnits            // Compilation units
        );

        boolean success = task.call();

        // Print compilation diagnostics
        for (Diagnostic<? extends JavaFileObject> diagnostic : diagnostics.getDiagnostics()) {
            System.out.println(diagnostic.getMessage(null));
        }

        return success;
    }

    /**
     * Loads and executes the main method of a compiled class.
     *
     * @param className The fully qualified name of the class to run
     * @param args Arguments to pass to the main method
     * @throws Exception If there's an error loading or executing the class
     */
    public void runClass(String className, String... args) throws Exception {
        try (URLClassLoader classLoader = new URLClassLoader(new URL[]{outputDirectory.toUri().toURL()})) {
            Class<?> loadedClass = classLoader.loadClass(className);
            loadedClass.getMethod("main", String[].class).invoke(null, (Object) args);
        }
    }


    /**
     * Returns the output directory where compiled classes are stored.
     *
     * @return The output directory path
     */
    public Path getOutputDirectory() {
        return outputDirectory;
    }
}