package com.baeldung.file;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

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
import org.hamcrest.CoreMatchers;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Test;
public class FileOperationsManualTest {

	public static final String FILE_TEST_TXT = "fileTest.txt";
	
    @Test
    public void givenFileName_whenUsingClassloader_thenFileData() throws IOException {
        String expectedData = "Hello World from fileTest.txt!!!";

        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(classLoader.getResource("fileTest.txt").getFile());
        InputStream inputStream = new FileInputStream(file);
        String data = readFromInputStream(inputStream);

        Assert.assertEquals(expectedData, data.trim());
    }

    @Test
    public void givenFileNameAsAbsolutePath_whenUsingClasspath_thenFileData() throws IOException {
        String expectedData = "Hello World from fileTest.txt!!!";

        Class clazz = FileOperationsManualTest.class;
        InputStream inputStream = clazz.getResourceAsStream("/fileTest.txt");
        String data = readFromInputStream(inputStream);

        Assert.assertEquals(expectedData, data.trim());
    }

    @Test
    public void givenFileName_whenUsingJarFile_thenFileData() throws IOException {
        String expectedData = "BSD License";

        Class clazz = Matchers.class;
        InputStream inputStream = clazz.getResourceAsStream("/LICENSE.txt");
        String data = readFromInputStream(inputStream);

        Assert.assertThat(data.trim(), CoreMatchers.containsString(expectedData));
    }

    @Test
    public void givenURLName_whenUsingURL_thenFileData() throws IOException {
        String expectedData = "Example Domain";

        URL urlObject = new URL("http://www.example.com/");

        URLConnection urlConnection = urlObject.openConnection();

        InputStream inputStream = urlConnection.getInputStream();
        String data = readFromInputStream(inputStream);

        Assert.assertThat(data.trim(), CoreMatchers.containsString(expectedData));
    }

    @Test
    public void givenFileName_whenUsingFileUtils_thenFileData() throws IOException {
        String expectedData = "Hello World from fileTest.txt!!!";

        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(classLoader.getResource("fileTest.txt").getFile());
        String data = FileUtils.readFileToString(file);

        Assert.assertEquals(expectedData, data.trim());
    }

    @Test
    public void givenFilePath_whenUsingFilesReadAllBytes_thenFileData() throws IOException, URISyntaxException {
        String expectedData = "Hello World from fileTest.txt!!!";

        Path path = Paths.get(getClass().getClassLoader().getResource("fileTest.txt").toURI());

        byte[] fileBytes = Files.readAllBytes(path);
        String data = new String(fileBytes);

        Assert.assertEquals(expectedData, data.trim());
    }

    @Test
    public void givenFilePath_whenUsingFilesLines_thenFileData() throws IOException, URISyntaxException {
        String expectedData = "Hello World from fileTest.txt!!!";

        Path path = Paths.get(getClass().getClassLoader().getResource("fileTest.txt").toURI());

        StringBuilder data = new StringBuilder();
        Stream<String> lines = Files.lines(path);
        lines.forEach(line -> data.append(line).append("\n"));
        lines.close();

        Assert.assertEquals(expectedData, data.toString().trim());
    }

    private String readFromInputStream(InputStream inputStream) throws IOException {
        StringBuilder resultStringBuilder = new StringBuilder();
        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream))) {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                resultStringBuilder.append(line).append("\n");
            }
        }

        return resultStringBuilder.toString();
    }

    @Test
    public void givenFileName_whenUsingFileUtils_thenCopyAndReadFileData() throws IOException {
        String expectedData = "Hello World from fileTest.txt!!!";

        File file = FileUtils.getFile(getClass().getClassLoader().getResource("fileTest.txt").getPath());

        File tempDir = FileUtils.getTempDirectory();

        FileUtils.copyFileToDirectory(file, tempDir);

        File newTempFile = FileUtils.getFile(tempDir, file.getName());

        String data = FileUtils.readFileToString(newTempFile, Charset.defaultCharset());

        Assert.assertEquals(expectedData, data.trim());
    }

    @Test
    public void givenFileName_whenUsingFileUtils_thenFileNameExtracts() {

        String PATH = getClass().getClassLoader().getResource("fileTest.txt").getPath();

        System.out.println("Full full PATH =" + FilenameUtils.getFullPath(PATH));

        System.out.println("Full PATH      =" + FilenameUtils.getPath(PATH));

        System.out.println("Full name      =" + FilenameUtils.getName(PATH));

        System.out.println("Extension      =" + FilenameUtils.getExtension(PATH));

        System.out.println("Base name      =" + FilenameUtils.getBaseName(PATH));
    }

    @Test
    public void givenFileName_whenUsingFileSystemUtils_thenCheckFreeSpaceOnDisk() throws IOException {
        System.out.println("You have " + (FileSystemUtils.freeSpaceKb("/") / 1024) / 1024 + " GBs free on your drive.");
    }

    @SuppressWarnings("resource")
    @Test
    public void givenTeeInputStream_whenUsingTeeOutputStream_thenWriteIntoBothOutputStreams() throws IOException {

        final String STR = "Hello World.";

        ByteArrayOutputStream outputStream1 = new ByteArrayOutputStream();
        ByteArrayOutputStream outputStream2 = new ByteArrayOutputStream();

        new TeeInputStream(new ByteArrayInputStream(STR.getBytes()), new TeeOutputStream(outputStream1, outputStream2),
                true).read(new byte[STR.length()]);

        Assert.assertEquals(STR, String.valueOf(outputStream1));
        Assert.assertEquals(STR, String.valueOf(outputStream2));
    }

    @Test
    public void givenDirectory_whenFilters_thenFindSpecificTextFile1() throws IOException {

        String PATH = getClass().getClassLoader().getResource(FILE_TEST_TXT).getPath();
        File dir = FileUtils.getFile(FilenameUtils.getFullPath(PATH));
        String[] possibleNames = { "NotThisOne", FILE_TEST_TXT };

        Assert.assertEquals(FILE_TEST_TXT, dir.list(new NameFileFilter(possibleNames, IOCase.INSENSITIVE))[0]);
    }

    @Test
    public void givenDirectory_whenFilters_thenFindSpecificTextFile2() throws IOException {

        String PATH = getClass().getClassLoader().getResource(FILE_TEST_TXT).getPath();
        File dir = FileUtils.getFile(FilenameUtils.getFullPath(PATH));

        Assert.assertEquals("sample.txt",
                dir.list(new AndFileFilter(new WildcardFileFilter("*ple*", IOCase.INSENSITIVE),
                        new SuffixFileFilter("txt")))[0]);
    }

    @Test
    public void givenDirectories_whenPathFileComparator_thenCompareDifferentFiles() throws IOException {
        PathFileComparator pathFileComparator = new PathFileComparator();

        String PATH = getClass().getClassLoader().getResource(FILE_TEST_TXT).getPath();
        File fileA = FileUtils.getFile(PATH);

        FileUtils.copyFileToDirectory(fileA, FileUtils.getTempDirectory());
        File fileB = FileUtils.getFile(FileUtils.getTempDirectory(), FILE_TEST_TXT);

        int i = pathFileComparator.compare(fileA, fileB);
        Assert.assertTrue(i < 0);

        i = pathFileComparator.compare(fileB, fileA);
        Assert.assertTrue(i > 0);

        i = pathFileComparator.compare(fileB, fileB);
        Assert.assertTrue(i == 0);
    }

    @Test
    public void givenDirectories_whenSizeFileComparator_thenCompareDifferentFiles() throws IOException {
        SizeFileComparator sizeFileComparator = new SizeFileComparator();

        File fileA = FileUtils.getFile(getClass().getClassLoader().getResource(FILE_TEST_TXT).getPath());

        File fileB = FileUtils.getFile(getClass().getClassLoader().getResource("sample.txt").getPath());

        int i = sizeFileComparator.compare(fileA, fileB);
        Assert.assertTrue(i > 0);

        i = sizeFileComparator.compare(fileB, fileA);
        Assert.assertTrue(i < 0);

        i = sizeFileComparator.compare(fileB, fileB);
        Assert.assertTrue(i == 0);
    }
}