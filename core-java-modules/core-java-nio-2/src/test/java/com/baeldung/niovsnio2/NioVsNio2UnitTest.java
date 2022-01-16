package com.baeldung.niovsnio2;

import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

public class NioVsNio2UnitTest {

    @Test
    public void readFromFileUsingFileIO() throws Exception {
        File file = new File("src/test/resources/nio-vs-nio2.txt");
        FileInputStream in = new FileInputStream(file);
        StringBuilder content = new StringBuilder();
        int data = in.read();
        while (data != -1) {
            content.append((char) data);
            data = in.read();
        }
        in.close();
        assertThat(content.toString()).isEqualTo("Hello from file!");
    }

    @Test
    public void readFromFileUsingFileChannel() throws Exception {
        RandomAccessFile file = new RandomAccessFile("src/test/resources/nio-vs-nio2.txt", "r");
        FileChannel channel = file.getChannel();
        StringBuilder content = new StringBuilder();

        ByteBuffer buffer = ByteBuffer.allocate(256);
        int bytesRead = channel.read(buffer);
        while (bytesRead != -1) {
            buffer.flip();

            while (buffer.hasRemaining()) {
                content.append((char) buffer.get());
            }

            buffer.clear();
            bytesRead = channel.read(buffer);
        }
        file.close();

        assertThat(content.toString()).isEqualTo("Hello from file!");
    }

    @Test
    public void readFromFileUsingNIO2() throws Exception {
        List<String> strings = Files.readAllLines(Paths.get("src/test/resources/nio-vs-nio2.txt"));

        assertThat(strings.get(0)).isEqualTo("Hello from file!");
    }

    @Test
    public void listFilesUsingWalk() throws Exception {
        Path path = Paths.get("src/test");
        Stream<Path> walk = Files.walk(path);
        walk.forEach(System.out::println);
    }
}
