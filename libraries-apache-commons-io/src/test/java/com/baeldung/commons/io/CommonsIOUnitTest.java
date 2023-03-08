package com.baeldung.commons.io;

import org.apache.commons.io.FileSystemUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOCase;
import org.apache.commons.io.comparator.PathFileComparator;
import org.apache.commons.io.comparator.SizeFileComparator;
import org.apache.commons.io.filefilter.AndFileFilter;
import org.apache.commons.io.filefilter.NameFileFilter;
import org.apache.commons.io.filefilter.SuffixFileFilter;
import org.apache.commons.io.filefilter.WildcardFileFilter;
import org.apache.commons.io.input.TeeInputStream;
import org.apache.commons.io.output.TeeOutputStream;
import org.junit.Assert;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FilterOutputStream;
import java.io.IOException;
import java.nio.charset.Charset;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CommonsIOUnitTest {

    @Test
    public void whenCopyANDReadFileTesttxt_thenMatchExpectedData() throws IOException {

        String expectedData = "Hello World from fileTest.txt!!!";

        File file = FileUtils.getFile(getClass().getClassLoader().getResource("fileTest.txt").getPath());
        File tempDir = FileUtils.getTempDirectory();
        FileUtils.copyFileToDirectory(file, tempDir);
        File newTempFile = FileUtils.getFile(tempDir, file.getName());
        String data = FileUtils.readFileToString(newTempFile, Charset.defaultCharset());

        Assert.assertEquals(expectedData, data.trim());
    }

    @Test
    public void whenUsingFileNameUtils_thenshowdifferentFileOperations() throws IOException {

        String path = getClass().getClassLoader().getResource("fileTest.txt").getPath();

        String fullPath = FilenameUtils.getFullPath(path);
        String extension = FilenameUtils.getExtension(path);
        String baseName = FilenameUtils.getBaseName(path);

        log.debug("full path: " + fullPath);
        log.debug("Extension: " + extension);
        log.debug("Base name: " + baseName);
    }

    @Test
    public void whenUsingFileSystemUtils_thenDriveFreeSpace() throws IOException {

        long freeSpace = FileSystemUtils.freeSpaceKb("/");
    }

    @SuppressWarnings("resource")
    @Test
    public void whenUsingTeeInputOutputStream_thenWriteto2OutputStreams() throws IOException {

        final String str = "Hello World.";
        ByteArrayInputStream inputStream = new ByteArrayInputStream(str.getBytes());
        ByteArrayOutputStream outputStream1 = new ByteArrayOutputStream();
        ByteArrayOutputStream outputStream2 = new ByteArrayOutputStream();

        FilterOutputStream teeOutputStream = new TeeOutputStream(outputStream1, outputStream2);
        new TeeInputStream(inputStream, teeOutputStream, true).read(new byte[str.length()]);

        Assert.assertEquals(str, String.valueOf(outputStream1));
        Assert.assertEquals(str, String.valueOf(outputStream2));
    }

    @Test
    public void whenGetFilewithNameFileFilter_thenFindfileTesttxt() throws IOException {

        final String testFile = "fileTest.txt";

        String path = getClass().getClassLoader().getResource(testFile).getPath();
        File dir = FileUtils.getFile(FilenameUtils.getFullPath(path));

        String[] possibleNames = { "NotThisOne", testFile };

        Assert.assertEquals(testFile, dir.list(new NameFileFilter(possibleNames, IOCase.INSENSITIVE))[0]);
    }

    @Test
    public void whenGetFilewith_ANDFileFilter_thenFindsampletxt() throws IOException {

        String path = getClass().getClassLoader().getResource("fileTest.txt").getPath();
        File dir = FileUtils.getFile(FilenameUtils.getFullPath(path));

        Assert.assertEquals("sample.txt", dir.list(new AndFileFilter(new WildcardFileFilter("*ple*", IOCase.INSENSITIVE), new SuffixFileFilter("txt")))[0]);
    }

    @Test
    public void whenSortDirWithPathFileComparator_thenFirstFileaaatxt() throws IOException {

        PathFileComparator pathFileComparator = new PathFileComparator(IOCase.INSENSITIVE);
        String path = FilenameUtils.getFullPath(getClass().getClassLoader().getResource("fileTest.txt").getPath());
        File dir = new File(path);
        File[] files = dir.listFiles();

        pathFileComparator.sort(files);

        Assert.assertEquals("aaa.txt", files[0].getName());
    }

    @Test
    public void whenSizeFileComparator_thenLargerFile() throws IOException {

        SizeFileComparator sizeFileComparator = new SizeFileComparator();
        File largerFile = FileUtils.getFile(getClass().getClassLoader().getResource("fileTest.txt").getPath());
        File smallerFile = FileUtils.getFile(getClass().getClassLoader().getResource("sample.txt").getPath());

        int i = sizeFileComparator.compare(largerFile, smallerFile);

        Assert.assertTrue(i > 0);
    }
}