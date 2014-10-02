package org.baeldung.java.io;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.RandomAccessFile;
import java.io.SequenceInputStream;
import java.io.StreamTokenizer;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Enumeration;
import java.util.Scanner;
import java.util.Vector;

import org.junit.Test;


/*
MappedByteBuffer

 */
public class TestReader {

    @Test
    public void whenReadWithBufferedReader_thenCorrect() throws IOException {
        final String str = "Hello world";

        final BufferedWriter writer = new BufferedWriter(new FileWriter("src/test/resources/test_read.txt"));
        writer.write(str);
        writer.close();

        final BufferedReader reader = new BufferedReader(new FileReader("src/test/resources/test_read.txt"));
        final String currentLine = reader.readLine();
        reader.close();

        assertEquals(str, currentLine);
    }

    @Test
    public void whenReadWithScanner_thenCorrect() throws IOException {
        final String str = "Hello world 1 2,3,4";
        final BufferedWriter writer = new BufferedWriter(new FileWriter("src/test/resources/test_read.txt"));
        writer.write(str);
        writer.close();

        final Scanner scanner = new Scanner(new File("src/test/resources/test_read.txt"));
        scanner.useDelimiter(" ");

        assertTrue(scanner.hasNext());
        assertEquals("Hello", scanner.next());
        assertEquals("world", scanner.next());
        assertEquals(1, scanner.nextInt());

        scanner.useDelimiter(",| ");

        assertTrue(scanner.hasNextInt());
        assertEquals(2, scanner.nextInt());
        assertEquals(3, scanner.nextInt());
        assertEquals(4, scanner.nextInt());

        scanner.close();

    }

    @Test
    public void whenReadWithStreamTokenizer_thenCorrectTokens() throws IOException {
        final String str = "Hello 1";
        final BufferedWriter writer = new BufferedWriter(new FileWriter("src/test/resources/test_read.txt"));
        writer.write(str);
        writer.close();

        final FileReader reader = new FileReader("src/test/resources/test_read.txt");
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
        final String value = "Hello";
        final DataOutputStream outStream = new DataOutputStream(new BufferedOutputStream(new FileOutputStream("src/test/resources/test_read.txt")));
        outStream.writeUTF(value);
        outStream.close();

        String result;
        final DataInputStream reader = new DataInputStream(new FileInputStream("src/test/resources/test_read.txt"));
        result = reader.readUTF();
        reader.close();

        assertEquals(value, result);
    }

    public void whenReadTwoFilesWithSequenceInputStream_thenCorrect() throws IOException {
        final int value1 = 2000;
        final int value2 = 5000;
        FileOutputStream writer = new FileOutputStream("src/test/resources/test_read.txt");
        writer.write(value1);
        writer.close();
        writer = new FileOutputStream("src/test/resources/test_read1.txt");
        writer.write(value2);
        writer.close();

        final FileInputStream stream1 = new FileInputStream("src/test/resources/test_read.txt");
        final FileInputStream stream2 = new FileInputStream("src/test/resources/test_read1.txt");

        final SequenceInputStream sequence = new SequenceInputStream(stream1, stream2);
        final DataInputStream reader = new DataInputStream(sequence);

        assertEquals(value1, reader.readInt());
        assertEquals(value2, reader.readInt());

        reader.close();
        stream2.close();
    }

    @Test
    public void whenReadAllFilesInFolder_thenCorrect() throws IOException {

        final File dir = new File("src/test/resources");
        final File[] allFiles = dir.listFiles();
        final int noOfFiles = allFiles.length;

        final Vector<FileInputStream> allStreams = new Vector<FileInputStream>();

        for (int i = 0; i < noOfFiles; i++) {
            allStreams.add(new FileInputStream(allFiles[i]));
        }

        final Enumeration<FileInputStream> enu = allStreams.elements();
        final SequenceInputStream sequence = new SequenceInputStream(enu);

        final BufferedReader reader = new BufferedReader(new InputStreamReader(sequence));

        String line = reader.readLine();
        while (line != null) {
            line = reader.readLine();
        }
        reader.close();
        sequence.close();
    }

    @Test
    public void whenReadUTFEncodedFile_thenCorrect() throws IOException {
        final String str = "青空";
        final BufferedWriter writer = new BufferedWriter(new FileWriter("src/test/resources/test_read.txt"));
        writer.write(str);
        writer.close();

        final BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream("src/test/resources/test_read.txt"), "UTF-8"));
        final String currentLine = reader.readLine();
        reader.close();

        assertEquals(str, currentLine);
    }

    @Test
    public void whenReadFileContentsIntoString_thenCorrect() throws IOException {
        final String str = "Hello world \n Test line \n";
        final BufferedWriter writer = new BufferedWriter(new FileWriter("src/test/resources/test_read.txt"));
        writer.write(str);
        writer.close();

        final BufferedReader reader = new BufferedReader(new FileReader("src/test/resources/test_read.txt"));
        final StringBuilder builder = new StringBuilder();
        String currentLine = reader.readLine();
        while (currentLine != null) {
            builder.append(currentLine);
            builder.append("\n");
            currentLine = reader.readLine();
        }

        reader.close();

        assertEquals(str, builder.toString());
    }

    @Test
    public void whenReadWithFileChannel_thenCorrect() throws IOException {
        final String str = "Hello world";
        final BufferedWriter writer = new BufferedWriter(new FileWriter("src/test/resources/test_read.txt"));
        writer.write(str);
        writer.close();

        final RandomAccessFile reader = new RandomAccessFile("src/test/resources/test_read.txt", "r");
        final FileChannel channel = reader.getChannel();

        int bufferSize = 1024;
        if (bufferSize > channel.size())
            bufferSize = (int) channel.size();
        final ByteBuffer buff = ByteBuffer.allocate(bufferSize);
        channel.read(buff);
        buff.flip();
        assertEquals(str, new String(buff.array()));
        channel.close();
        reader.close();
    }

    @Test
    public void whenReadSmallFileJava7_thenCorrect() throws IOException {
        final String str = "Hello world";

        final Path path = Paths.get("src/test/resources/test_read.txt");
        final byte[] strToBytes = str.getBytes();
        Files.write(path, strToBytes);

        final String read = Files.readAllLines(path).get(0);
        assertEquals(str, read);
    }

    @Test
    public void whenReadLargeFileJava7_thenCorrect() throws IOException {
        final String str = "Hello world";

        final Path path = Paths.get("src/test/resources/test_read.txt");
        final byte[] strToBytes = str.getBytes();
        Files.write(path, strToBytes);

        final BufferedReader reader = Files.newBufferedReader(path);
        final String line = reader.readLine();
        assertEquals(str, line);
    }

}
