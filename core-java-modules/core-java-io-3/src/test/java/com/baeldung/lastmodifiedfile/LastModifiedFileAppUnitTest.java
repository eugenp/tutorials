package com.baeldung.lastmodifiedfile;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class LastModifiedFileAppUnitTest {

    private final static String SOURCEDIRECTORY = "src/test/resources/lastmodfiles";

    @BeforeAll
    public static void setUpFiles() throws IOException, InterruptedException {
        File srcDir = new File(SOURCEDIRECTORY);
        if (!srcDir.exists()) {
            srcDir.mkdir();
        }

        FileUtils.cleanDirectory(srcDir);

        File file01 = new File(SOURCEDIRECTORY + "/file01.txt");
        file01.createNewFile();

        Thread.sleep(2000);

        File file02 = new File(SOURCEDIRECTORY + "/file02.txt");
        file02.createNewFile();

        Thread.sleep(2000);

        File file03 = new File(SOURCEDIRECTORY + "/file03.txt");
        file03.createNewFile();

        Thread.sleep(2000);

        Files.write(Paths.get(SOURCEDIRECTORY + "/file02.txt"), "Hello File02".getBytes());

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
        assertThat(lastModPath.toFile().getName()).isEqualTo("file02.txt");
    }

    @Test
    public void givenDirectory_whenUsingApacheCommons_thenFindLastModfile() throws IOException {
        File lastModFile = LastModifiedFileApp.findUsingCommonsIO(SOURCEDIRECTORY);

        assertThat(lastModFile).isNotNull();
        assertThat(lastModFile.getName()).isEqualTo("file02.txt");
    }

    @AfterAll
    public static void cleanUp() throws IOException {
        File srcDir = new File(SOURCEDIRECTORY);
        FileUtils.deleteDirectory(srcDir);
    }

}