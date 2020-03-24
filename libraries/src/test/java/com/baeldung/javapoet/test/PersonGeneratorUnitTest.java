package com.baeldung.javapoet.test;

import com.baeldung.javapoet.PersonGenerator;
import org.apache.commons.io.FileUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

@RunWith(JUnit4.class)
public class PersonGeneratorUnitTest {

    private PersonGenerator generator;
    private Path generatedFolderPath;
    private Path expectedFolderPath;

    @Before
    public void setUp() {
        String packagePath = this
          .getClass()
          .getPackage()
          .getName()
          .replace(".", "/") + "/person";
        generator = new PersonGenerator();
        generatedFolderPath = generator
          .getOutputPath()
          .resolve(packagePath);
        expectedFolderPath = Paths.get(new File(".").getAbsolutePath() + "/src/test/java/" + packagePath);
    }

    @After
    public void tearDown() throws Exception {
        FileUtils.deleteDirectory(new File(generator
          .getOutputPath()
          .toUri()));
    }

    @Test
    public void whenGenerateGenderEnum_thenGenerateGenderEnumAndWriteToFile() throws IOException {
        generator.generateGenderEnum();
        String fileName = "Gender.java";
        assertThatFileIsGeneratedAsExpected(fileName);
        deleteGeneratedFile(fileName);
    }

    @Test
    public void whenGeneratePersonInterface_thenGeneratePersonInterfaceAndWriteToFile() throws IOException {
        generator.generatePersonInterface();
        String fileName = "Person.java";
        assertThatFileIsGeneratedAsExpected(fileName);
        deleteGeneratedFile(fileName);
    }

    @Test
    public void whenGenerateStudentClass_thenGenerateStudentClassAndWriteToFile() throws IOException {
        generator.generateStudentClass();
        String fileName = "Student.java";
        assertThatFileIsGeneratedAsExpected(fileName);
        deleteGeneratedFile(fileName);
    }

    private void assertThatFileIsGeneratedAsExpected(String fileName) throws IOException {
        String generatedFileContent = extractFileContent(generatedFolderPath.resolve(fileName));
        String expectedFileContent = extractFileContent(expectedFolderPath.resolve(fileName));

        assertThat("Generated file is identical to the file with the expected content", generatedFileContent, is(equalTo(expectedFileContent)));

    }

    private void deleteGeneratedFile(String fileName) throws IOException {
        Path generatedFilePath = generatedFolderPath.resolve(fileName);
        Files.delete(generatedFilePath);
    }

    private String extractFileContent(Path filePath) throws IOException {
        byte[] fileContentAsBytes = Files.readAllBytes(filePath);
        String fileContentAsString = new String(fileContentAsBytes, StandardCharsets.UTF_8);

        if (!fileContentAsString.contains("\r\n")) {
            // file is not in DOS format
            // convert it first, so that the content comparison will be relevant
            return fileContentAsString.replaceAll("\n", "\r\n");
        }
        return fileContentAsString;
    }

}
