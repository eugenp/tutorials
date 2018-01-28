package com.baeldung.stream;

import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;

import org.junit.Test;

public class FileCopyTest {

    @Test
    public void whenUsingStream_thenCopyFile() throws IOException {
        File src = new File("src" + File.separator + "test" + File.separator + "resources" + File.separator + "copyTest" + File.separator + "src" + File.separator + "test_stream.txt");
        File dest = new File("src" + File.separator + "test" + File.separator + "resources" + File.separator + "copyTest" + File.separator + "dest" + File.separator + "test_stream.txt");
        FileCopy.copyFileUsingStream(src, dest);
        assertTrue(dest.exists());
        dest.delete();
    }

    @Test
    public void whenUsingFiles_thenCopyFile() throws IOException {
        File src = new File("src" + File.separator + "test" + File.separator + "resources" + File.separator + "copyTest" + File.separator + "src" + File.separator + "test_files.txt");
        File dest = new File("src" + File.separator + "test" + File.separator + "resources" + File.separator + "copyTest" + File.separator + "dest" + File.separator + "test_files.txt");
        FileCopy.copyFileUsingJavaFiles(src, dest);
        assertTrue(dest.exists());
        dest.delete();
    }

    @Test
    public void whenUsingChannel_thenCopyFile() throws IOException {
        File src = new File("src" + File.separator + "test" + File.separator + "resources" + File.separator + "copyTest" + File.separator + "src" + File.separator + "test_channel.txt");
        File dest = new File("src" + File.separator + "test" + File.separator + "resources" + File.separator + "copyTest" + File.separator + "dest" + File.separator + "test_channel.txt");
        FileCopy.copyFileUsingChannel(src, dest);
        assertTrue(dest.exists());
        dest.delete();
    }

    @Test
    public void whenUsingApache_thenCopyFile() throws IOException {
        File src = new File("src" + File.separator + "test" + File.separator + "resources" + File.separator + "copyTest" + File.separator + "src" + File.separator + "test_apache.txt");
        File dest = new File("src" + File.separator + "test" + File.separator + "resources" + File.separator + "copyTest" + File.separator + "dest" + File.separator + "test_apache.txt");
        FileCopy.copyFileUsingApacheCommonsIO(src, dest);
        assertTrue(dest.exists());
        dest.delete();
    }
}
