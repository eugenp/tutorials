package com.baeldung.lastmodifiedfile;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class LastModifiedFileAppUnitTest {

    private final static String SOURCEDIRECTORY = "src/test/resources/lastmodfiles";

    @BeforeAll
    public static void setUpFiles() throws IOException {
        File srcDir = new File(SOURCEDIRECTORY);
        if (!srcDir.exists())
            srcDir.mkdir();

        FileUtils.cleanDirectory(srcDir);
        
        Path file01 = Paths.get(SOURCEDIRECTORY + "/file01.txt");
        Files.createFile(file01);
        Files.write(file01, "Hello File01".getBytes());
        
        Path file02 = Paths.get(SOURCEDIRECTORY + "/file02.txt");
        Files.createFile(file02);
        Files.write(file02, "Hello File02".getBytes());
        
        Path file03 = Paths.get(SOURCEDIRECTORY + "/file03.txt");
        Files.createFile(file03);
        Files.write(file03, "Hello File03".getBytes());
        
    }
    
    @BeforeEach
    public void modifyFile() throws IOException {
        Path file02 = Paths.get(SOURCEDIRECTORY + "/file02.txt");
        Files.write(file02, "Change File02 Content".getBytes());
    }

    @Test
    public void givenDirectory_whenUsingIoApi_thenFindLastModfile() throws IOException {
        File lastModFile = LastModifiedFileApp.findUsingIOApi(SOURCEDIRECTORY);

        assertThat(lastModFile).isNotNull();
        assertThat(lastModFile.getName()).isEqualTo("file02.txt");
    }

    @Test
    public void givenDirectory_whenUsingNioApi_thenFindLastModfile() throws IOException {
        Path lastModPath = LastModifiedFileApp.findUsingNIOApi(SOURCEDIRECTORY);

        assertThat(lastModPath).isNotNull();
        assertThat(lastModPath.toFile()
            .getName()).isEqualTo("file02.txt");
    }

    @Test
    public void givenDirectory_whenUsingApacheCommons_thenFindLastModfile() throws IOException {
        File lastModFile = LastModifiedFileApp.findUsingCommonsIO(SOURCEDIRECTORY);

        assertThat(lastModFile).isNotNull();
        assertThat(lastModFile.getName()).isEqualTo("file02.txt");
    }

}
