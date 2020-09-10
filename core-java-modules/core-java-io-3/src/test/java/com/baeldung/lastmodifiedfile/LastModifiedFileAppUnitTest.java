package com.baeldung.lastmodifiedfile;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;

import org.junit.jupiter.api.Test;

public class LastModifiedFileAppUnitTest {

    private final String sourceDirectoryLocation = "src/test/resources/lastmodfiles";

    @Test
    public void givenDirectory_whenUsingIoApi_thenFindLastModfile() {
        File lastModFile = LastModifiedFileApp.findUsingIOApi(sourceDirectoryLocation);

        assertThat(lastModFile).isNotNull();
        assertThat(lastModFile.getName()).isEqualTo("file02.txt");
    }

    @Test
    public void givenDirectory_whenUsingNioApi_thenFindLastModfile() throws IOException {
        Path lastModPath = LastModifiedFileApp.findUsingNIOApi(sourceDirectoryLocation);

        assertThat(lastModPath).isNotNull();
        assertThat(lastModPath.getFileName()
            .toString()).isEqualTo("file02.txt");
    }

    @Test
    public void givenDirectory_whenUsingApacheCommons_thenFindLastModfile() {
        File lastModFile = LastModifiedFileApp.findUsingCommonsIO(sourceDirectoryLocation);

        assertThat(lastModFile).isNotNull();
        assertThat(lastModFile.getName()).isEqualTo("file02.txt");
    }

}
