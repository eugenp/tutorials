package com.baeldung.guava;

import static org.hamcrest.Matchers.contains;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.List;

import org.junit.Test;

import com.google.common.base.Charsets;
import com.google.common.base.Joiner;
import com.google.common.collect.Lists;
import com.google.common.io.ByteSink;
import com.google.common.io.ByteSource;
import com.google.common.io.ByteStreams;
import com.google.common.io.CharSink;
import com.google.common.io.CharSource;
import com.google.common.io.CharStreams;
import com.google.common.io.Files;
import com.google.common.io.LittleEndianDataInputStream;
import com.google.common.io.LittleEndianDataOutputStream;
import com.google.common.io.Resources;

public class GuavaIOUnitTest {

    @Test
    public void whenWriteUsingFiles_thenWritten() throws IOException {
        final String expectedValue = "Hello world";
        final File file = new File("src/test/resources/test.out");

        Files.write(expectedValue, file, Charsets.UTF_8);

        final String result = Files.toString(file, Charsets.UTF_8);
        assertEquals(expectedValue, result);
    }

    @Test
    public void whenWriteUsingCharSink_thenWritten() throws IOException {
        final String expectedValue = "Hello world";
        final File file = new File("src/test/resources/test.out");
        final CharSink sink = Files.asCharSink(file, Charsets.UTF_8);

        sink.write(expectedValue);

        final String result = Files.toString(file, Charsets.UTF_8);
        assertEquals(expectedValue, result);
    }

    @Test
    public void whenWriteMultipleLinesUsingCharSink_thenWritten() throws IOException {
        final List<String> names = Lists.newArrayList("John", "Jane", "Adam", "Tom");
        final File file = new File("src/test/resources/test.out");
        final CharSink sink = Files.asCharSink(file, Charsets.UTF_8);

        sink.writeLines(names, " ");

        final String result = Files.toString(file, Charsets.UTF_8);
        final String expectedValue = Joiner.on(" ").join(names);
        assertEquals(expectedValue, result.trim());

    }

    @Test
    public void whenWriteUsingByteSink_thenWritten() throws IOException {
        final String expectedValue = "Hello world";
        final File file = new File("src/test/resources/test.out");
        final ByteSink sink = Files.asByteSink(file);

        sink.write(expectedValue.getBytes());

        final String result = Files.toString(file, Charsets.UTF_8);
        assertEquals(expectedValue, result);
    }

    @Test
    public void whenReadUsingFiles_thenRead() throws IOException {
        final String expectedValue = "Hello world";
        final File file = new File("src/test/resources/test1.in");

        final String result = Files.toString(file, Charsets.UTF_8);
        assertEquals(expectedValue, result);
    }

    @Test
    public void whenReadMultipleLinesUsingFiles_thenRead() throws IOException {
        final File file = new File("src/test/resources/test2.in");

        final List<String> result = Files.readLines(file, Charsets.UTF_8);
        assertThat(result, contains("John", "Jane", "Adam", "Tom"));
    }

    @Test
    public void whenReadUsingCharSource_thenRead() throws IOException {
        final String expectedValue = "Hello world";
        final File file = new File("src/test/resources/test1.in");

        final CharSource source = Files.asCharSource(file, Charsets.UTF_8);

        final String result = source.read();
        assertEquals(expectedValue, result);
    }

    @Test
    public void whenReadMultipleCharSources_thenRead() throws IOException {
        final String expectedValue = "Hello worldTest";
        final File file1 = new File("src/test/resources/test1.in");
        final File file2 = new File("src/test/resources/test1_1.in");

        final CharSource source1 = Files.asCharSource(file1, Charsets.UTF_8);
        final CharSource source2 = Files.asCharSource(file2, Charsets.UTF_8);
        final CharSource source = CharSource.concat(source1, source2);

        final String result = source.read();

        assertEquals(expectedValue, result);
    }

    @Test
    public void whenReadUsingCharStream_thenRead() throws IOException {
        final String expectedValue = "Hello world";

        final FileReader reader = new FileReader("src/test/resources/test1.in");
        final String result = CharStreams.toString(reader);

        assertEquals(expectedValue, result);
        reader.close();
    }

    @Test
    public void whenReadUsingByteSource_thenRead() throws IOException {
        final String expectedValue = "Hello world";
        final File file = new File("src/test/resources/test1.in");

        final ByteSource source = Files.asByteSource(file);

        final byte[] result = source.read();
        assertEquals(expectedValue, new String(result));
    }

    @Test
    public void whenReadAfterOffsetUsingByteSource_thenRead() throws IOException {
        final String expectedValue = "lo world";
        final File file = new File("src/test/resources/test1.in");

        final long offset = 3;
        final long length = 1000;
        final ByteSource source = Files.asByteSource(file).slice(offset, length);

        final byte[] result = source.read();
        assertEquals(expectedValue, new String(result));
    }

    @Test
    public void whenReadUsingByteStream_thenRead() throws IOException {
        final String expectedValue = "Hello world";

        final FileInputStream reader = new FileInputStream("src/test/resources/test1.in");
        final byte[] result = ByteStreams.toByteArray(reader);

        assertEquals(expectedValue, new String(result));
        reader.close();
    }

    @Test
    public void whenReadUsingResources_thenRead() throws IOException {
        final String expectedValue = "Hello world";

        final URL url = Resources.getResource("test1.in");
        final String result = Resources.toString(url, Charsets.UTF_8);

        assertEquals(expectedValue, result);
    }

    @Test
    public void whenCopyFileUsingFiles_thenCopied() throws IOException {
        final String expectedValue = "Hello world";

        final File file1 = new File("src/test/resources/test1.in");
        final File file2 = new File("src/test/resources/test_copy.in");

        Files.copy(file1, file2);
        final String result = Files.toString(file2, Charsets.UTF_8);

        assertEquals(expectedValue, result);
    }

    @Test
    public void whenWriteReadLittleEndian_thenCorrect() throws IOException {
        final int value = 100;

        final LittleEndianDataOutputStream writer = new LittleEndianDataOutputStream(new FileOutputStream("src/test/resources/test_le.txt"));
        writer.writeInt(value);
        writer.close();

        final LittleEndianDataInputStream reader = new LittleEndianDataInputStream(new DataInputStream(new FileInputStream("src/test/resources/test_le.txt")));
        final int result = reader.readInt();
        reader.close();

        assertEquals(value, result);
    }

}
