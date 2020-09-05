package com.baeldung.lastmodifiedfile;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;

import org.junit.Test;

public class LastModifiedFileUnitTest {

    private final String DIRECTORY = "src/test/resources/lastmodifiedfile";

    @Test
    public void givenDirecory_whenUsingIOApi_thenFindLastModifiedFile() {

        File lastmodfile = LastModifiedFile.findUsingIOApi(DIRECTORY);

        assertThat(lastmodfile).isNotNull();
        assertThat(lastmodfile.getName()).isEqualTo("file02.txt");

    }

    @Test
    public void givenDirecory_whenUsingNIOApi_thenFindLastModifiedFile() throws IOException {

        Path lastmodpath = LastModifiedFile.findUsingNIOApi(DIRECTORY);

        assertThat(lastmodpath).isNotNull();
        assertThat(lastmodpath.getFileName()
            .toString()).isEqualTo("file02.txt");

    }

    @Test
    public void givenDirecory_whenUsingCommonsIO_thenFindLastModifiedFile() {

        File lastmodfile = LastModifiedFile.findUsingCommonsIO(DIRECTORY);

        assertThat(lastmodfile).isNotNull();
        assertThat(lastmodfile.getName()).isEqualTo("file02.txt");

    }

}
