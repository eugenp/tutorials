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
import java.io.IOException;
import java.nio.charset.Charset;

public class CommonsIOUnitTest {

    public static final String FILE_TEST_TXT = "fileTest.txt";

    @Test
    public void givenFileName_whenUsingFileUtils_thenCopyAndReadFileData() throws IOException {

        String expectedData = "Hello World from fileTest.txt!!!";

        File file = FileUtils.getFile(getClass().getClassLoader()
            .getResource("fileTest.txt")
            .getPath());
        File tempDir = FileUtils.getTempDirectory();
        FileUtils.copyFileToDirectory(file, tempDir);
        File newTempFile = FileUtils.getFile(tempDir, file.getName());
        String data = FileUtils.readFileToString(newTempFile, Charset.defaultCharset());

        Assert.assertEquals(expectedData, data.trim());
    }

    @Test
    public void whenUsingFileNameUtils_showdifferentOperations() throws IOException {

        String path = getClass().getClassLoader()
            .getResource("fileTest.txt")
            .getPath();

        System.out.println("full path" + FilenameUtils.getFullPath(path));
        System.out.println("path     " + FilenameUtils.getPath(path));
        System.out.println("name     " + FilenameUtils.getName(path));
        System.out.println("Extension" + FilenameUtils.getExtension(path));
        System.out.println("Base name" + FilenameUtils.getBaseName(path));
    }

    @Test
    public void whenUsingFileSystemUtils_showDriveFreeSpace() throws IOException {
        System.out.println("You have " + (FileSystemUtils.freeSpaceKb("/") / 1024) / 1024 + "GBs free on your drive.");
    }

    @SuppressWarnings("resource")
    @Test
    public void givenUsingTeeInputOutputStream_thenWriteto2OutputStreams() throws IOException {

        final String str = "Hello World.";

        ByteArrayOutputStream outputStream1 = new ByteArrayOutputStream();
        ByteArrayOutputStream outputStream2 = new ByteArrayOutputStream();
        new TeeInputStream(new ByteArrayInputStream(str.getBytes()), new TeeOutputStream(outputStream1, outputStream2), true).read(new byte[str.length()]);

        Assert.assertEquals(str, String.valueOf(outputStream1));
        Assert.assertEquals(str, String.valueOf(outputStream2));
    }

    @Test
    public void givenDirectory_whenFilters_thenFindSpecificTextFile1() throws IOException {

        String path = getClass().getClassLoader()
            .getResource(FILE_TEST_TXT)
            .getPath();
        File dir = FileUtils.getFile(FilenameUtils.getFullPath(path));

        String[] possibleNames = { "NotThisOne", FILE_TEST_TXT };

        Assert.assertEquals(FILE_TEST_TXT, dir.list(new NameFileFilter(possibleNames, IOCase.INSENSITIVE))[0]);
    }

    @Test
    public void givenDirectory_whenFilters_thenFindSpecificTextFile2() throws IOException {

        String path = getClass().getClassLoader()
            .getResource(FILE_TEST_TXT)
            .getPath();
        File dir = FileUtils.getFile(FilenameUtils.getFullPath(path));

        Assert.assertEquals("sample.txt", dir.list(new AndFileFilter(new WildcardFileFilter("*ple*", IOCase.INSENSITIVE), new SuffixFileFilter("txt")))[0]);
    }

    @Test
    public void whenPathFileComparator_thenCompareDifferentFiles() throws IOException {

        PathFileComparator pathFileComparator = new PathFileComparator();
        String path = getClass().getClassLoader()
            .getResource(FILE_TEST_TXT)
            .getPath();
        File fileA = FileUtils.getFile(path);
        FileUtils.copyFileToDirectory(fileA, FileUtils.getTempDirectory());
        File fileB = FileUtils.getFile(FileUtils.getTempDirectory(), FILE_TEST_TXT);

        int i = pathFileComparator.compare(fileA, fileB);

        Assert.assertTrue(i < 0);
    }

    @Test
    public void whenSizeFileComparator_thenCompareDifferentFiles() throws IOException {

        SizeFileComparator sizeFileComparator = new SizeFileComparator();
        File fileA = FileUtils.getFile(getClass().getClassLoader()
            .getResource(FILE_TEST_TXT)
            .getPath());
        File fileB = FileUtils.getFile(getClass().getClassLoader()
            .getResource("sample.txt")
            .getPath());

        int i = sizeFileComparator.compare(fileA, fileB);

        Assert.assertTrue(i > 0);
    }
}