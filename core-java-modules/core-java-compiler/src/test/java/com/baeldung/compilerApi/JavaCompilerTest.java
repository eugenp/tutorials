package com.baeldung.compilerApi;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.io.TempDir;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static com.github.stefanbirkner.systemlambda.SystemLambda.tapSystemErr;
import static com.github.stefanbirkner.systemlambda.SystemLambda.tapSystemOut;
import static org.junit.jupiter.api.Assertions.*;

public class JavaCompilerTest {

    @TempDir
    static Path tempDir;

    private JavaCompilerUtils compilerUtil;

    @BeforeEach
    void setUp() throws Exception {
        // Create a specific output directory for compiled classes
        Path outputDir = tempDir.resolve("classes");
        Files.createDirectories(outputDir);

        // Initialize the compiler util with the output directory
        compilerUtil = new JavaCompilerUtils(outputDir);
    }

    @Test
    void given_simpleHelloWorldClass_when_compiledFromString_then_compilationSucceeds() {
        // Simple "Hello World" class
        String className = "HelloWorld";
        String sourceCode = "public class HelloWorld {\n" +
                "    public static void main(String[] args) {\n" +
                "        System.out.println(\"Hello, World!\");\n" +
                "    }\n" +
                "}";

        boolean result = compilerUtil.compileFromString(className, sourceCode);

        assertTrue(result, "Compilation should succeed");

        // Check if the class file was created
        Path classFile = compilerUtil.getOutputDirectory().resolve(className + ".class");
        assertTrue(Files.exists(classFile), "Class file should be created");
    }

    @Test
    void given_classWithPackage_when_compiledFromString_then_compilationSucceedsInPackageDirectory() {
        // Class with a package
        String className = "com.example.PackagedClass";
        String sourceCode = "package com.example;\n\n" +
                "public class PackagedClass {\n" +
                "    public static void main(String[] args) {\n" +
                "        System.out.println(\"Hello from packaged class!\");\n" +
                "    }\n" +
                "}";

        boolean result = compilerUtil.compileFromString(className, sourceCode);

        assertTrue(result, "Compilation should succeed");

        // Check if the class file was created in the correct package directory
        Path classFile = compilerUtil.getOutputDirectory().resolve(
                Paths.get("com", "example", "PackagedClass.class"));
        assertTrue(Files.exists(classFile), "Class file should be created in the package directory");
    }

    @Test
    void given_classWithSyntaxError_when_compiledFromString_then_compilationFails() {
        // Class with syntax error (missing semicolon)
        String className = "ErrorClass";
        String sourceCode = "public class ErrorClass {\n" +
                "    public static void main(String[] args) {\n" +
                "        System.out.println(\"This has an error\")\n" + // Missing semicolon
                "    }\n" +
                "}";

        // Just verify compilation fails and no class file is created
        boolean result = compilerUtil.compileFromString(className, sourceCode);
        assertFalse(result, "Compilation should fail due to syntax error");

        // Check that no class file was created
        Path classFile = compilerUtil.getOutputDirectory().resolve(className + ".class");
        assertFalse(Files.exists(classFile), "No class file should be created for failed compilation");
    }

    @Test
    void given_javaSourceFile_when_compiled_then_compilationSucceeds() throws Exception {
        // Create a temporary Java file
        String className = "FileTest";
        String sourceCode = "public class FileTest {\n" +
                "    public static void main(String[] args) {\n" +
                "        System.out.println(\"Hello from file!\");\n" +
                "    }\n" +
                "}";

        Path sourceFile = tempDir.resolve(className + ".java");
        Files.write(sourceFile, sourceCode.getBytes());

        boolean result = compilerUtil.compileFile(sourceFile);

        assertTrue(result, "Compilation should succeed");

        // Check if the class file was created
        Path classFile = compilerUtil.getOutputDirectory().resolve(className + ".class");
        assertTrue(Files.exists(classFile), "Class file should be created");
    }

    @Test
    void given_compiledClass_when_runWithArguments_then_outputsExpectedResult() throws Exception {
        // Compile a simple class
        String className = "Runner";
        String sourceCode = "public class Runner {\n" +
                "    public static void main(String[] args) {\n" +
                "        System.out.println(\"Running: \" + String.join(\", \", args));\n" +
                "    }\n" +
                "}";

        boolean result = compilerUtil.compileFromString(className, sourceCode);
        assertTrue(result, "Compilation should succeed");

        // Use system-lambda to capture the output
        String output = tapSystemOut(() -> {
            compilerUtil.runClass(className, "arg1", "arg2");
        });

        // Check the output
        assertEquals("Running: arg1, arg2", output.trim());
    }

    @Test
    void when_compilingNonExistentFile_then_throwsIllegalArgumentException() {
        Path nonExistentFile = tempDir.resolve("NonExistent.java");

        assertThrows(IllegalArgumentException.class, () -> {
            compilerUtil.compileFile(nonExistentFile);
        });
    }
}