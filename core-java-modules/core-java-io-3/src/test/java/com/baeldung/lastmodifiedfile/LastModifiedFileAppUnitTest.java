package com.baeldung.lastmodifiedfile;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class LastModifiedFileAppUnitTest {

    private final static String SOURCEDIRECTORY = "src/test/resources/lastmodfiles";

    private final String lastModifiedFile = "file02.txt";

    @BeforeAll
    public static void setUpFiles() throws IOException {
        Path pathToDir = Paths.get(SOURCEDIRECTORY);
        Files.walk(pathToDir)
            .map(Path::toFile)
            .forEach(File::delete);

        Path file01 = Paths.get(SOURCEDIRECTORY + "/file01.txt");
        Files.createFile(file01);
        Path file02 = Paths.get(SOURCEDIRECTORY + "/file02.txt");
        Files.createFile(file02);
        Path file03 = Paths.get(SOURCEDIRECTORY + "/file03.txt");
        Files.createFile(file03);
    }
    
    @BeforeEach
    public void beforeEach() throws IOException {
        String str = "Hello From Baeldung";
        Files.write(Paths.get(SOURCEDIRECTORY + "/file02.txt"), str.getBytes());
    }

    @Test
    public void givenDirectory_whenUsingIoApi_thenFindLastModfile() {
        File lastModFile = LastModifiedFileApp.findUsingIOApi(SOURCEDIRECTORY);

        assertThat(lastModFile).isNotNull();
        assertThat(lastModFile.getName()).isEqualTo(lastModifiedFile);
    }

    @Test
    public void givenDirectory_whenUsingNioApi_thenFindLastModfile() throws IOException {
        Path lastModPath = LastModifiedFileApp.findUsingNIOApi(SOURCEDIRECTORY);

        assertThat(lastModPath).isNotNull();
        assertThat(lastModPath.getFileName()
            .toString()).isEqualTo(lastModifiedFile);
    }

    @Test
    public void givenDirectory_whenUsingApacheCommons_thenFindLastModfile() {
        File lastModFile = LastModifiedFileApp.findUsingCommonsIO(SOURCEDIRECTORY);

        assertThat(lastModFile).isNotNull();
        assertThat(lastModFile.getName()).isEqualTo(lastModifiedFile);
    }

}
