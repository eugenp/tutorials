package com.baeldung.listfiles;

import org.junit.Test;

import java.io.IOException;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ListFilesUnitTest {

    private static final String VALID_DIRECTORY = "src/test/resources/listFilesUnitTestFolder";
    private static final String INVALID_DIRECTORY = "src/test/resources/thisDirectoryDoesNotExist";
    private static final Set<String> EXPECTED_FILE_SET = new HashSet<String>() {
        {
            add("test.xml");
            add("employee.json");
            add("students.json");
            add("country.txt");
        }
    };
    private static final int DEPTH = 1;
    private final ListFiles listFiles = new ListFiles();

    @Test
    public void givenAValidDirectoryWhenUsingJavaIOThenReturnSetOfFileNames() {
        assertThat(listFiles.listFilesUsingJavaIO(VALID_DIRECTORY))
                .isEqualTo(EXPECTED_FILE_SET);
    }

    @Test
    public void givenAInvalidValidDirectoryWhenUsingJavaIOThenThrowsNullPointerExceptino() {
        assertThrows(NullPointerException.class,
                () -> listFiles.listFilesUsingJavaIO(INVALID_DIRECTORY));
    }

    @Test
    public void givenAValidDirectoryWhenUsingFilesListThenReturnSetOfFileNames() throws IOException {
        assertThat(listFiles.listFilesUsingFilesList(VALID_DIRECTORY))
                .isEqualTo(EXPECTED_FILE_SET);
    }

    @Test
    public void givenAValidDirectoryWhenUsingFilesWalkWithDepth1ThenReturnSetOfFileNames() throws IOException {
        assertThat(listFiles.listFilesUsingFileWalk(VALID_DIRECTORY, DEPTH))
                .isEqualTo(EXPECTED_FILE_SET);
    }

    @Test
    public void givenAValidDirectoryWhenUsingFilesWalkFileTreeThenReturnSetOfFileNames() throws IOException {
        assertThat(listFiles.listFilesUsingFileWalkAndVisitor(VALID_DIRECTORY))
                .isEqualTo(EXPECTED_FILE_SET);
    }

    @Test
    public void givenAValidDirectoryWhenUsingDirectoryStreamThenReturnSetOfFileNames() throws IOException {
        assertThat(listFiles.listFilesUsingDirectoryStream(VALID_DIRECTORY))
                .isEqualTo(EXPECTED_FILE_SET);
    }

    @Test
    public void givenAValidFileWhenUsingFilesWalkWithDepth1ThenReturnSetOfTheSameFile() throws IOException {
        Set<String> expectedFileSet = Collections.singleton("test.xml");
        String filePathString = "src/test/resources/listFilesUnitTestFolder/test.xml";
        assertEquals(expectedFileSet, listFiles.listFilesUsingFileWalk(filePathString, DEPTH));
    }
}
