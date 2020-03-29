package com.baeldung.readfile;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.hamcrest.CoreMatchers;
import org.hamcrest.Matchers;
import org.junit.Test;

import java.io.*;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class JavaReadFromFileUnitTest {

    @Test
    public void whenReadWithBufferedReader_thenCorrect() throws IOException {
        final String expected_value = "Hello, world!";

        final BufferedReader reader = new BufferedReader(new FileReader("src/test/resources/fileTest.txt"));
        final String currentLine = reader.readLine();
        reader.close();

        assertEquals(expected_value, currentLine);
    }

    @Test
    public void givenFileName_whenUsingClassloader_thenFileData() throws IOException {
        String expectedData = "Hello, world!";

        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(classLoader.getResource("fileTest.txt").getFile());
        InputStream inputStream = new FileInputStream(file);
        String data = readFromInputStream(inputStream);

        assertEquals(expectedData, data.trim());
    }

    @Test
    public void givenFileNameAsAbsolutePath_whenUsingClasspath_thenFileData() throws IOException {
        String expectedData = "Hello, world!";

        Class clazz = JavaReadFromFileUnitTest.class;
        InputStream inputStream = clazz.getResourceAsStream("/fileTest.txt");
        String data = readFromInputStream(inputStream);

        assertEquals(expectedData, data.trim());
    }

    @Test
    public void givenFileName_whenUsingJarFile_thenFileData() throws IOException {
        String expectedData = "BSD License";

        Class clazz = Matchers.class;
        InputStream inputStream = clazz.getResourceAsStream("/LICENSE.txt");
        String data = readFromInputStream(inputStream);

        assertThat(data.trim(), CoreMatchers.containsString(expectedData));
    }

    @Test
    public void givenURLName_whenUsingURL_thenFileData() throws IOException {
        String expectedData = "Example Domain";

        URL urlObject = new URL("http://www.example.com/");

        URLConnection urlConnection = urlObject.openConnection();

        InputStream inputStream = urlConnection.getInputStream();
        String data = readFromInputStream(inputStream);

        assertThat(data.trim(), CoreMatchers.containsString(expectedData));
    }

    @Test
    public void givenFileName_whenUsingFileUtils_thenFileData() throws IOException {
        String expectedData = "Hello, world!";

        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(classLoader.getResource("fileTest.txt").getFile());
        String data = FileUtils.readFileToString(file, "UTF-8");

        assertEquals(expectedData, data.trim());
    }

    @Test
    public void givenFilePath_whenUsingFilesReadAllBytes_thenFileData() throws IOException, URISyntaxException {
        String expectedData = "Hello, world!";

        Path path = Paths.get(getClass().getClassLoader().getResource("fileTest.txt").toURI());

        byte[] fileBytes = Files.readAllBytes(path);
        String data = new String(fileBytes);

        assertEquals(expectedData, data.trim());
    }

    @Test
    public void givenFilePath_whenUsingFilesLines_thenFileData() throws IOException, URISyntaxException {
        String expectedData = "Hello, world!";

        Path path = Paths.get(getClass().getClassLoader().getResource("fileTest.txt").toURI());

        Stream<String> lines = Files.lines(path);
        String data = lines.collect(Collectors.joining("\n"));
        lines.close();

        assertEquals(expectedData, data.trim());
    }

    @Test
    public void givenFileName_whenUsingIOUtils_thenFileData() throws IOException {
        String expectedData = "Hello, world!";

        FileInputStream fis = new FileInputStream("src/test/resources/fileTest.txt");
        String data = IOUtils.toString(fis, "UTF-8");

        assertEquals(expectedData, data.trim());
    }

    @Test
    public void whenReadWithScanner_thenCorrect() throws IOException {
        final Scanner scanner = new Scanner(new File("src/test/resources/fileTest.txt"));
        scanner.useDelimiter(" ");

        assertTrue(scanner.hasNext());
        assertEquals("Hello,", scanner.next());
        assertEquals("world!", scanner.next());

        scanner.close();
    }

    @Test
    public void whenReadWithScannerTwoDelimiters_thenCorrect() throws IOException {
        final Scanner scanner = new Scanner(new File("src/test/resources/fileTest.txt"));
        scanner.useDelimiter("\\s|,");

        assertTrue(scanner.hasNext());
        assertEquals("Hello", scanner.next());
        assertEquals("", scanner.next());
        assertEquals("world!", scanner.next());

        scanner.close();
    }

    @Test
    public void whenReadWithStreamTokenizer_thenCorrectTokens() throws IOException {
        final FileReader reader = new FileReader("src/test/resources/fileTestTokenizer.txt");
        final StreamTokenizer tokenizer = new StreamTokenizer(reader);

        tokenizer.nextToken();
        assertEquals(StreamTokenizer.TT_WORD, tokenizer.ttype);
        assertEquals("Hello", tokenizer.sval);
        tokenizer.nextToken();
        assertEquals(StreamTokenizer.TT_NUMBER, tokenizer.ttype);
        assertEquals(1, tokenizer.nval, 0.0000001);

        tokenizer.nextToken();
        assertEquals(StreamTokenizer.TT_EOF, tokenizer.ttype);
        reader.close();
    }

    @Test
    public void whenReadWithDataInputStream_thenCorrect() throws IOException {
        String expectedValue = "Hello, world!";
        String file ="src/test/resources/fileTest.txt";

        String result = null;

        DataInputStream reader = new DataInputStream(new FileInputStream(file));
        int nBytesToRead = reader.available();
        if(nBytesToRead > 0) {
            byte[] bytes = new byte[nBytesToRead];
            reader.read(bytes);
            result = new String(bytes);
        }

        assertEquals(expectedValue, result);
    }

    @Test
    public void whenReadUTFEncodedFile_thenCorrect() throws IOException {
        final String expected_value = "青空";
        final BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream("src/test/resources/fileTestUtf8.txt"), "UTF-8"));
        final String currentLine = reader.readLine();
        reader.close();

        assertEquals(expected_value, currentLine);
    }

    @Test
    public void whenReadFileContentsIntoString_thenCorrect() throws IOException {
        final String expected_value = "Hello, world!\n";
        final BufferedReader reader = new BufferedReader(new FileReader("src/test/resources/fileTest.txt"));
        final StringBuilder builder = new StringBuilder();
        String currentLine = reader.readLine();
        while (currentLine != null) {
            builder.append(currentLine);
            builder.append("\n");
            currentLine = reader.readLine();
        }

        reader.close();

        assertEquals(expected_value, builder.toString());
    }

    @Test
    public void whenReadWithFileChannel_thenCorrect() throws IOException {
        final String expected_value = "Hello, world!";
        final RandomAccessFile reader = new RandomAccessFile("src/test/resources/fileTest.txt", "r");
        final FileChannel channel = reader.getChannel();

        int bufferSize = 1024;
        if (bufferSize > channel.size()) {
            bufferSize = (int) channel.size();
        }
        final ByteBuffer buff = ByteBuffer.allocate(bufferSize);
        channel.read(buff);
        buff.flip();
        assertEquals(expected_value, new String(buff.array()));
        channel.close();
        reader.close();
    }

    @Test
    public void whenReadSmallFileJava7_thenCorrect() throws IOException {
        final String expected_value = "Hello, world!";
        final Path path = Paths.get("src/test/resources/fileTest.txt");

        final String read = Files.readAllLines(path, Charset.defaultCharset()).get(0);
        assertEquals(expected_value, read);
    }

    @Test
    public void whenReadLargeFileJava7_thenCorrect() throws IOException {
        final String expected_value = "Hello, world!";

        final Path path = Paths.get("src/test/resources/fileTest.txt");
        final BufferedReader reader = Files.newBufferedReader(path, Charset.defaultCharset());
        final String line = reader.readLine();
        assertEquals(expected_value, line);
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

}
