package com.baeldung.replacewordinfile;

import static org.junit.jupiter.api.Assertions.assertLinesMatch;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.regex.Pattern;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

public class ModifyFileByPatternUnitTest {

    @TempDir
    private File fileDir;

    private File myFile;

    private static final List<String> ORIGINAL_LINES = List.of(
        //@formatter:off
      "Both JAVA and KOTLIN applications can run on the JVM.",
      "But python is a simpler language",
      "PYTHON application is also platform independent.",
      "java and kotlin are statically typed languages.",
      "On the other hand, python is a dynamically typed language.");
    //@formatter:on

    private static final List<String> EXPECTED_LINES = List.of(
        //@formatter:off
      "Both Java and Kotlin applications can run on the JVM (Java Virtual Machine).",
      "Python application is also platform independent.",
      "Java and Kotlin are statically typed languages.",
      "On the other hand, Python is a dynamically typed language.");
    //@formatter:on

    @BeforeEach
    void initFile() throws IOException {
        myFile = new File(fileDir, "myFile.txt");
        Files.write(myFile.toPath(), ORIGINAL_LINES);
    }

    @Test
    void whenLoadTheFileContentToMemModifyThenWriteBack_thenCorrect() throws IOException {
        assertTrue(Files.exists(myFile.toPath()));
        List<String> lines = Files.readAllLines(myFile.toPath());
        lines.remove(1); // remove the 2nd line
        List<String> newLines = lines.stream()
            .map(line -> line.replaceAll("(?i)java", "Java")
                .replaceAll("(?i)kotlin", "Kotlin")
                .replaceAll("(?i)python", "Python")
                .replaceAll("JVM", "$0 (Java Virtual Machine)"))
            .toList();

        Files.write(myFile.toPath(), newLines);
        assertLinesMatch(EXPECTED_LINES, Files.readAllLines(myFile.toPath()));
    }

    @Test
    void whenUsingBufferedReaderAndModifyViaTempFile_thenCorrect(@TempDir Path tempDir) throws IOException {

        Pattern javaPat = Pattern.compile("(?i)java");
        Pattern kotlinPat = Pattern.compile("(?i)kotlin");
        Pattern pythonPat = Pattern.compile("(?i)python");
        Pattern jvmPat = Pattern.compile("JVM");

        Path modifiedFile = tempDir.resolve("modified.txt");
        //@formatter:off
        try ( BufferedReader reader = Files.newBufferedReader(myFile.toPath());
              BufferedWriter writer = Files.newBufferedWriter(modifiedFile)) {
            //@formatter:on

            int lineNumber = 0;
            String line;
            while ((line = reader.readLine()) != null) {
                lineNumber++;
                if (lineNumber == 2) {
                    continue; // skip the 2nd line
                }

                String replaced = line;
                replaced = javaPat.matcher(replaced)
                    .replaceAll("Java");
                replaced = kotlinPat.matcher(replaced)
                    .replaceAll("Kotlin");
                replaced = pythonPat.matcher(replaced)
                    .replaceAll("Python");
                replaced = jvmPat.matcher(replaced)
                    .replaceAll("JVM (Java Virtual Machine)");

                writer.write(replaced);
                writer.newLine();
            }
        }

        Files.move(modifiedFile, myFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
        assertTrue(myFile.exists());
        assertLinesMatch(EXPECTED_LINES, Files.readAllLines(myFile.toPath()));

    }
}