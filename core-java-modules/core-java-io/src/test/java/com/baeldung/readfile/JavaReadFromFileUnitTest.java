package com.baeldung.readfile;

import org.junit.Test;
import org.junit.Ignore;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class JavaReadFromFileUnitTest {

    private static final Logger LOG = LoggerFactory.getLogger(JavaReadFromFileUnitTest.class);

    @Test
    public void whenReadWithBufferedReader_thenCorrect() throws IOException {
        final String expected_value = "Hello world";

        final BufferedReader reader = new BufferedReader(new FileReader("src/test/resources/test_read.in"));
        final String currentLine = reader.readLine();
        reader.close();

        assertEquals(expected_value, currentLine);
    }

    @Test
    public void whenReadWithScanner_thenCorrect() throws IOException {
        final Scanner scanner = new Scanner(new File("src/test/resources/test_read1.in"));
        scanner.useDelimiter(" ");

        assertTrue(scanner.hasNext());
        assertEquals("Hello", scanner.next());
        assertEquals("world", scanner.next());
        assertEquals(1, scanner.nextInt());

        scanner.close();

    }

    @Test
    public void whenReadWithScannerTwoDelimiters_thenCorrect() throws IOException {
        final Scanner scanner = new Scanner(new File("src/test/resources/test_read2.in"));
        scanner.useDelimiter(",| ");

        assertTrue(scanner.hasNextInt());
        assertEquals(2, scanner.nextInt());
        assertEquals(3, scanner.nextInt());
        assertEquals(4, scanner.nextInt());

        scanner.close();
    }

    @Test
    public void whenReadWithStreamTokenizer_thenCorrectTokens() throws IOException {
        final FileReader reader = new FileReader("src/test/resources/test_read3.in");
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
        final String expected_value = "Hello";

        String result;
        final DataInputStream reader = new DataInputStream(new FileInputStream("src/test/resources/test_read4.in"));
        result = reader.readUTF();
        reader.close();

        assertEquals(expected_value, result);
    }

    public void whenReadTwoFilesWithSequenceInputStream_thenCorrect() throws IOException {
        final int expected_value1 = 2000;
        final int expected_value2 = 5000;

        final FileInputStream stream1 = new FileInputStream("src/test/resources/test_read5.in");
        final FileInputStream stream2 = new FileInputStream("src/test/resources/test_read6.in");

        final SequenceInputStream sequence = new SequenceInputStream(stream1, stream2);
        final DataInputStream reader = new DataInputStream(sequence);

        assertEquals(expected_value1, reader.readInt());
        assertEquals(expected_value2, reader.readInt());

        reader.close();
        stream2.close();
    }

    @Test
    @Ignore // TODO
    public void whenReadUTFEncodedFile_thenCorrect() throws IOException {
        final String expected_value = "青空";
        final BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream("src/test/resources/test_read7.in"), "UTF-8"));
        final String currentLine = reader.readLine();
        reader.close();
        LOG.debug(currentLine);
        
        assertEquals(expected_value, currentLine);
    }

    @Test
    public void whenReadFileContentsIntoString_thenCorrect() throws IOException {
        final String expected_value = "Hello world \n Test line \n";
        final BufferedReader reader = new BufferedReader(new FileReader("src/test/resources/test_read8.in"));
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
        final String expected_value = "Hello world";
        final RandomAccessFile reader = new RandomAccessFile("src/test/resources/test_read.in", "r");
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
        final String expected_value = "Hello world";
        final Path path = Paths.get("src/test/resources/test_read.in");

        final String read = Files.readAllLines(path, Charset.defaultCharset()).get(0);
        assertEquals(expected_value, read);
    }

    @Test
    public void whenReadLargeFileJava7_thenCorrect() throws IOException {
        final String expected_value = "Hello world";

        final Path path = Paths.get("src/test/resources/test_read.in");
        final BufferedReader reader = Files.newBufferedReader(path, Charset.defaultCharset());
        final String line = reader.readLine();
        assertEquals(expected_value, line);
    }

}
