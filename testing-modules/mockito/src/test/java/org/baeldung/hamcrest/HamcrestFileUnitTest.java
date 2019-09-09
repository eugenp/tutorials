package org.baeldung.hamcrest;

import static org.hamcrest.core.StringContains.containsString;
import static org.hamcrest.io.FileMatchers.aFileNamed;
import static org.hamcrest.io.FileMatchers.aFileWithAbsolutePath;
import static org.hamcrest.io.FileMatchers.aFileWithCanonicalPath;
import static org.hamcrest.io.FileMatchers.aFileWithSize;
import static org.hamcrest.io.FileMatchers.aReadableFile;
import static org.hamcrest.io.FileMatchers.aWritableFile;
import static org.hamcrest.io.FileMatchers.anExistingDirectory;
import static org.hamcrest.io.FileMatchers.anExistingFile;
import static org.hamcrest.io.FileMatchers.anExistingFileOrDirectory;
import static org.hamcrest.number.OrderingComparison.greaterThan;
import static org.hamcrest.text.IsEqualIgnoringCase.equalToIgnoringCase;
import static org.junit.Assert.assertThat;

import java.io.File;

import org.junit.Test;

public class HamcrestFileUnitTest {

    @Test
    public final void whenVerifyingFileName_thenCorrect() {
        File file = new File("src/test/resources/test1.in");

        assertThat(file, aFileNamed(equalToIgnoringCase("test1.in")));
    }

    @Test
    public final void whenVerifyingFileOrDirExist_thenCorrect() {
        File file = new File("src/test/resources/test1.in");
        File dir = new File("src/test/resources");
        
        assertThat(file, anExistingFile());
        assertThat(dir, anExistingDirectory());
        assertThat(file, anExistingFileOrDirectory());
        assertThat(dir, anExistingFileOrDirectory());
    }

    @Test
    public final void whenVerifyingFileIsReadableAndWritable_thenCorrect() {
        File file = new File("src/test/resources/test1.in");

        assertThat(file, aReadableFile());
        assertThat(file, aWritableFile());        
    }

    @Test
    public final void whenVerifyingFileSize_thenCorrect() {
        File file = new File("src/test/resources/test1.in");

        assertThat(file, aFileWithSize(11));
        assertThat(file, aFileWithSize(greaterThan(1L)));;
    }

    /*@Test
    public final void whenVerifyingFilePath_thenCorrect() {
        File file = new File("src/test/resources/test1.in");
        
        assertThat(file, aFileWithCanonicalPath(containsString("src/test/resources")));
        assertThat(file, aFileWithAbsolutePath(containsString("src/test/resources")));
    }*/
}
