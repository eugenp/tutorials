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
    void givenSimpleHelloWorldClass_whenCompiledFromString_thenCompilationSucceeds() {
        String className = "HelloWorld";
        String sourceCode = "public class HelloWorld {\n" +
                "    public static void main(String[] args) {\n" +
                "        System.out.println(\"Hello, World!\");\n" +
                "    }\n" +
                "}";

        boolean result = compilerUtil.compileFromString(className, sourceCode);

        assertTrue(result, "Compilation should succeed");

        Path classFile = compilerUtil.getOutputDirectory().resolve(className + ".class");
        assertTrue(Files.exists(classFile), "Class file should be created");
    }

    @Test
    void givenClassWithPackage_whenCompiledFromString_thenCompilationSucceedsInPackageDirectory() {
        String className = "com.example.PackagedClass";
        String sourceCode = "package com.example;\n\n" +
                "public class PackagedClass {\n" +
                "    public static void main(String[] args) {\n" +
                "        System.out.println(\"Hello from packaged class!\");\n" +
                "    }\n" +
                "}";

        boolean result = compilerUtil.compileFromString(className, sourceCode);

        assertTrue(result, "Compilation should succeed");

        Path classFile = compilerUtil.getOutputDirectory().resolve(
                Paths.get("com", "example", "PackagedClass.class"));
        assertTrue(Files.exists(classFile), "Class file should be created in the package directory");
    }

    @Test
    void givenClassWithSyntaxError_whenCompiledFromString_thenCompilationFails() {
        String className = "ErrorClass";
        String sourceCode = "public class ErrorClass {\n" +
                "    public static void main(String[] args) {\n" +
                "        System.out.println(\"This has an error\")\n" +
                "    }\n" +
                "}";

        boolean result = compilerUtil.compileFromString(className, sourceCode);
        assertFalse(result, "Compilation should fail due to syntax error");

        Path classFile = compilerUtil.getOutputDirectory().resolve(className + ".class");
        assertFalse(Files.exists(classFile), "No class file should be created for failed compilation");
    }

    @Test
    void givenJavaSourceFile_whenCompiled_thenCompilationSucceeds() throws Exception {
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

        Path classFile = compilerUtil.getOutputDirectory().resolve(className + ".class");
        assertTrue(Files.exists(classFile), "Class file should be created");
    }

    @Test
    void givenCompiledClass_whenRunWithArguments_thenOutputsExpectedResult() throws Exception {
        String className = "Runner";
        String sourceCode = "public class Runner {\n" +
                "    public static void main(String[] args) {\n" +
                "        System.out.println(\"Running: \" + String.join(\", \", args));\n" +
                "    }\n" +
                "}";

        boolean result = compilerUtil.compileFromString(className, sourceCode);
        assertTrue(result, "Compilation should succeed");

        String output = tapSystemOut(() -> {
            compilerUtil.runClass(className, "arg1", "arg2");
        });

        assertEquals("Running: arg1, arg2", output.trim());
    }

    @Test
    void whenCompilingNonExistentFile_thenThrowsIllegalArgumentException() {
        Path nonExistentFile = tempDir.resolve("NonExistent.java");

        assertThrows(IllegalArgumentException.class, () -> {
            compilerUtil.compileFile(nonExistentFile);
        });
    }
}