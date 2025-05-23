package com.baeldung.replacewordinfile;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.Test;

public class ReplaceWordInFileUnitTest {

    private static final String FILE_PATH = "src/test/resources/data.txt";
    private static final String FILE_OUTPUT_PATH = "src/test/resources/data_output.txt";
    private static final String OUTPUT_TO_VERIFY = "This is a test file."+System.lineSeparator()+"This is a test file."+System.lineSeparator()+"This is a test file.";

    @Test
    public void givenFile_whenUsingBufferedReader_thenReplacedWordCorrect() throws IOException {
        StringBuilder fileContent = new StringBuilder();
        try (BufferedReader br = Files.newBufferedReader(Paths.get(FILE_PATH))) {
            String line;
            while ((line = br.readLine()) != null){
                fileContent.append(line).append(System.lineSeparator());
            }
            String replacedContent = fileContent.toString().replace("sample","test").trim();
            try (FileWriter fw = new FileWriter(FILE_OUTPUT_PATH)) {
                fw.write(replacedContent);
            }

            assertEquals(OUTPUT_TO_VERIFY,replacedContent);
        }
    }

    @Test
    public void givenFile_whenUsingScanner_thenReplacedWordCorrect() throws IOException {
        StringBuilder fileContent = new StringBuilder();
        try (Scanner scanner = new Scanner(new File(FILE_PATH))) {
            while (scanner.hasNextLine()){
                fileContent.append(scanner.nextLine()).append(System.lineSeparator());
            }
            String replacedContent = fileContent.toString().replace("sample","test").trim();
            try (FileWriter fw = new FileWriter(FILE_OUTPUT_PATH)) {
                fw.write(replacedContent);
            }

            assertEquals(OUTPUT_TO_VERIFY,replacedContent);
        }
    }

    @Test
    public void givenFile_whenUsingFilesAPI_thenReplacedWordCorrect() throws IOException{
        try (Stream<String> lines = Files.lines(Paths.get(FILE_PATH))) {
            List<String> list = lines.map(line -> line.replace("sample", "test"))
              .collect(Collectors.toList());
            Files.write(Paths.get(FILE_OUTPUT_PATH), list, StandardCharsets.UTF_8);

            assertEquals(OUTPUT_TO_VERIFY,String.join(System.lineSeparator(), list));
        }
    }

    @Test
    public void givenFile_whenUsingFileUtils_thenReplacedWordCorrect() throws IOException{
        StringBuilder fileContent = new StringBuilder();
        List<String> lines = FileUtils.readLines(new File(FILE_PATH), "UTF-8");
        lines.forEach(line -> fileContent.append(line).append(System.lineSeparator()));
        String replacedContent = fileContent.toString().replace("sample","test").trim();
        try (FileWriter fw = new FileWriter(FILE_OUTPUT_PATH)) {
            fw.write(replacedContent);
        }

        assertEquals(OUTPUT_TO_VERIFY,replacedContent);
    }

    @Test
    public void givenLargeFile_whenUsingFilesAPI_thenReplacedWordCorrect() throws IOException{
        try (Stream<String> lines = Files.lines(Paths.get(FILE_PATH))) {
            Files.writeString(Paths.get(FILE_OUTPUT_PATH), "",StandardCharsets.UTF_8, StandardOpenOption.CREATE,StandardOpenOption.TRUNCATE_EXISTING);
            lines.forEach(line -> {
                line = line.replace("sample", "test") + System.lineSeparator();
                try {
                    Files.writeString(Paths.get(FILE_OUTPUT_PATH),line,StandardCharsets.UTF_8, StandardOpenOption.APPEND);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });

            assertEquals(OUTPUT_TO_VERIFY,Files.readString(Paths.get(FILE_OUTPUT_PATH)).trim());
        }
    }
}
