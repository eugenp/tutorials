package com.baeldung.filewriterflushandclose;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

public class FileWriterFlushVsCloseUnitTest {

    @Test
    void whenUsingFileWriterWithTryWithResources_thenAutoClosed(@TempDir Path tmpDir) throws IOException {
        Path filePath = tmpDir.resolve("auto-close.txt");
        File file = filePath.toFile();

        try (FileWriter fw = new FileWriter(file)) {
            fw.write("Catch Me If You Can");
        }

        List<String> lines = Files.readAllLines(filePath);
        assertEquals(List.of("Catch Me If You Can"), lines);
    }

    @Test
    void whenUsingFileWriterWithoutFlush_thenDataWontBeWritten(@TempDir Path tmpDir) throws IOException {
        Path filePath = tmpDir.resolve("noFlush.txt");
        File file = filePath.toFile();

        FileWriter fw = new FileWriter(file);
        fw.write("Catch Me If You Can");

        List<String> lines = Files.readAllLines(filePath);
        assertEquals(0, lines.size());
        fw.close(); //close the resource
    }

    @Test
    void whenUsingFileWriterWithFlush_thenGetExpectedResult(@TempDir Path tmpDir) throws IOException {
        Path filePath = tmpDir.resolve("flush1.txt");
        File file = filePath.toFile();

        FileWriter fw = new FileWriter(file);
        fw.write("Catch Me If You Can");
        fw.flush();

        List<String> lines = Files.readAllLines(filePath);
        assertEquals(List.of("Catch Me If You Can"), lines);
        fw.close(); //close the resource
    }

    @Test
    void whenUsingFileWriterWithClose_thenGetExpectedResult(@TempDir Path tmpDir) throws IOException {
        Path filePath = tmpDir.resolve("close1.txt");
        File file = filePath.toFile();
        FileWriter fw = new FileWriter(file);
        fw.write("Catch Me If You Can");
        fw.close();

        List<String> lines = Files.readAllLines(filePath);
        assertEquals(List.of("Catch Me If You Can"), lines);

    }

    @Test
    void whenUsingFileWriterWithFlushMultiTimes_thenGetExpectedResult(@TempDir Path tmpDir) throws IOException {
        List<String> lines = List.of("Catch Me If You Can", "A Man Called Otto", "Saving Private Ryan");
        Path filePath = tmpDir.resolve("flush2.txt");
        File file = filePath.toFile();
        FileWriter fw = new FileWriter(file);
        for (String line : lines) {
            fw.write(line + System.lineSeparator());
            fw.flush();
        }

        List<String> linesInFile = Files.readAllLines(filePath);
        assertEquals(lines, linesInFile);
        fw.close(); //close the resource
    }

    @Test
    void whenUsingFileWriterWithCloseMultiTimes_thenGetIOExpectedException(@TempDir Path tmpDir) throws IOException {
        List<String> lines = List.of("Catch Me If You Can", "A Man Called Otto", "Saving Private Ryan");
        Path filePath = tmpDir.resolve("close2.txt");
        File file = filePath.toFile();
        FileWriter fw = new FileWriter(file);
        //write and close
        fw.write(lines.get(0) + System.lineSeparator());
        fw.close();

        //writing again throws IOException
        Throwable throwable = assertThrows(IOException.class, () -> fw.append(lines.get(1)));
        assertEquals("Stream closed", throwable.getMessage());
    }
}