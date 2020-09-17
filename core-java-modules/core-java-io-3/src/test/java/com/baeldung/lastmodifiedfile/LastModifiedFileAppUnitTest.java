package com.baeldung.lastmodifiedfile;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;

import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.BeforeAll;
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

        Path file02 = Paths.get(SOURCEDIRECTORY + "/file02.txt");
        Files.createFile(file02);

        Path file03 = Paths.get(SOURCEDIRECTORY + "/file03.txt");
        Files.createFile(file03);

    }

    @Test
    public void givenDirectory_whenUsingIoApi_thenFindLastModfile() throws IOException {
        File file04 = new File(SOURCEDIRECTORY + "/file04.txt");
        file04.createNewFile();
        file04.setLastModified(new Date().getTime());

        File lastModFile = LastModifiedFileApp.findUsingIOApi(SOURCEDIRECTORY);

        assertThat(lastModFile).isNotNull();
        assertThat(lastModFile.getName()).isEqualTo("file04.txt");
    }

    @Test
    public void givenDirectory_whenUsingNioApi_thenFindLastModfile() throws IOException {
        File file05 = new File(SOURCEDIRECTORY + "/file05.txt");
        file05.createNewFile();
        file05.setLastModified(new Date().getTime());

        Path lastModPath = LastModifiedFileApp.findUsingNIOApi(SOURCEDIRECTORY);

        assertThat(lastModPath).isNotNull();
        assertThat(lastModPath.toFile()
            .getName()).isEqualTo("file05.txt");
    }

    @Test
    public void givenDirectory_whenUsingApacheCommons_thenFindLastModfile() throws IOException {
        File file06 = new File(SOURCEDIRECTORY + "/file06.txt");
        file06.createNewFile();
        file06.setLastModified(new Date().getTime());

        File lastModFile = LastModifiedFileApp.findUsingCommonsIO(SOURCEDIRECTORY);

        assertThat(lastModFile).isNotNull();
        assertThat(lastModFile.getName()).isEqualTo("file06.txt");
    }

}
