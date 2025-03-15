package com.baeldung.compilerApi;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.io.TempDir;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

public class JavaCompilerTest {

    @TempDir
    static Path tempDir;

    private JavaCompilerUtils compilerUtil;
    private final ByteArrayOutputStream outputCaptor = new ByteArrayOutputStream();
    private PrintStream standardOut;

    @BeforeEach
    void setUp() throws Exception {
        // Create a specific output directory for compiled classes
        Path outputDir = tempDir.resolve("classes");
        Files.createDirectories(outputDir);

        // Initialize the compiler util with the output directory
        compilerUtil = new JavaCompilerUtils(outputDir);

        // Set up System.out capture
        standardOut = System.out;
        System.setOut(new PrintStream(outputCaptor));
    }

    @AfterEach
    void tearDown() {
        // Restore System.out
        System.setOut(standardOut);
    }

    @Test
    void testCompileFromString_Success() {
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
    void testCompileFromString_WithPackage() {
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
    void testCompileFromString_CompilationError() {
        // Class with syntax error (missing semicolon)
        String className = "ErrorClass";
        String sourceCode = "public class ErrorClass {\n" +
                "    public static void main(String[] args) {\n" +
                "        System.out.println(\"This has an error\")\n" + // Missing semicolon
                "    }\n" +
                "}";

        boolean result = compilerUtil.compileFromString(className, sourceCode);

        assertFalse(result, "Compilation should fail");
        assertTrue(outputCaptor.toString().contains("';' expected"),
                "Diagnostic should mention missing semicolon");
    }

    @Test
    void testCompileFile_Success() throws Exception {
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
    void testRunClass() throws Exception {
        // Compile a simple class
        String className = "Runner";
        String sourceCode = "public class Runner {\n" +
                "    public static void main(String[] args) {\n" +
                "        System.out.println(\"Running: \" + String.join(\", \", args));\n" +
                "    }\n" +
                "}";

        boolean result = compilerUtil.compileFromString(className, sourceCode);
        assertTrue(result, "Compilation should succeed");

        // Clear the output capture
        outputCaptor.reset();

        // Run the compiled class
        compilerUtil.runClass(className, "arg1", "arg2");

        // Check the output
        assertEquals("Running: arg1, arg2", outputCaptor.toString().trim());
    }

    @Test
    void testCompileFile_FileNotExists() {
        Path nonExistentFile = tempDir.resolve("NonExistent.java");

        assertThrows(IllegalArgumentException.class, () -> {
            compilerUtil.compileFile(nonExistentFile);
        });
    }

}
