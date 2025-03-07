package com.baeldung.compilerApi;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Demonstration of the JavaCompilerUtil class.
 */
public class JavaCompilerApiDemo {
    private static final Logger logger = LoggerFactory.getLogger(JavaCompilerApiDemo.class);

    public static void main(String[] args) {
        try {
            // Create output directory for compiled classes
            Path outputDir = Paths.get("compiled-classes");

            JavaCompilerUtils compilerUtil = new JavaCompilerUtils(outputDir);
            logger.debug("Java compiler initialized with output directory: {}", outputDir.toAbsolutePath());

            // Example 1: Compile from string
            compileFromStringExample(compilerUtil);

            // Example 2: Compile from file
            compileFromFileExample(compilerUtil);

        } catch (Exception e) {
            logger.error("Compilation failed {}", e.getMessage(), e);
        }
    }

    private static void compileFromStringExample(JavaCompilerUtils compilerUtil) throws Exception {
        logger.debug("\n--- Example 1: Compile from String ---");

        // Define a simple class
        String className = "HelloWorld";
        String sourceCode = "public class HelloWorld {\n" +
                "    public static void main(String[] args) {\n" +
                "        System.out.println(\"Hello, compiled from string!\");\n" +
                "    }\n" +
                "}";

        // Compile the source code
        boolean success = compilerUtil.compileFromString(className, sourceCode);

        if (success) {
            logger.debug("Compilation successful!");
            logger.debug("Running the compiled class:");

            // Run the compiled class
            logger.debug("----- Output from HelloWorld -----");
            compilerUtil.runClass(className, "arg1", "arg2");
            logger.debug("---------------------------------");
        } else {
            logger.error("Compilation failed.");
        }
    }

    private static void compileFromFileExample(JavaCompilerUtils compilerUtil) throws Exception {
        logger.debug("\n--- Example 2: Compile from File ---");

        // Create a temporary Java file
        Path tempFile = Paths.get("Calculator.java");

        // Write source code to the file
        String sourceCode = "public class Calculator {\n" +
                "    public static void main(String[] args) {\n" +
                "        System.out.println(\"Calculator, compiled from file!\");\n" +
                "        \n" +
                "        if (args.length >= 2) {\n" +
                "            try {\n" +
                "                int a = Integer.parseInt(args[0]);\n" +
                "                int b = Integer.parseInt(args[1]);\n" +
                "                System.out.println(a + \" + \" + b + \" = \" + (a + b));\n" +
                "                System.out.println(a + \" * \" + b + \" = \" + (a * b));\n" +
                "            } catch (NumberFormatException e) {\n" +
                "                System.out.println(\"Arguments must be numbers.\");\n" +
                "            }\n" +
                "        } else {\n" +
                "            System.out.println(\"Please provide two numbers as arguments.\");\n" +
                "        }\n" +
                "    }\n" +
                "}";
        Files.write(tempFile, sourceCode.getBytes());

        logger.debug("Created temporary Java file: {}", tempFile);

        // Compile the file
        boolean success = compilerUtil.compileFile(tempFile);

        if (success) {
            logger.debug("Compilation successful!");
            logger.debug("Running the compiled class:");

            // Run the compiled class
            logger.debug("----- Output from Calculator -----");
            compilerUtil.runClass("Calculator", "5", "7");
            logger.debug("----------------------------------");
        } else {
            System.out.println("Compilation failed.");
        }

        // Clean up the temporary file
        Files.delete(tempFile);
        logger.debug("Deleted temporary file: {}", tempFile);
    }

}
