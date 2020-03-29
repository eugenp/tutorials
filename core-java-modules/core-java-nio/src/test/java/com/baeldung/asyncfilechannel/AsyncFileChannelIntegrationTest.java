package com.baeldung.asyncfilechannel;

import org.junit.Test;

import java.io.IOException;
import java.net.URI;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousFileChannel;
import java.nio.channels.CompletionHandler;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.UUID;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import static org.junit.Assert.assertEquals;

public class AsyncFileChannelIntegrationTest {

    @Test
    public void givenPath_whenReadsContentWithFuture_thenCorrect() throws IOException, ExecutionException, InterruptedException {
        final Path path = Paths.get(URI.create(this.getClass().getClassLoader().getResource("file.txt").toString()));
        final AsynchronousFileChannel fileChannel = AsynchronousFileChannel.open(path, StandardOpenOption.READ);

        final ByteBuffer buffer = ByteBuffer.allocate(1024);

        final Future<Integer> operation = fileChannel.read(buffer, 0);

        operation.get();

        final String fileContent = new String(buffer.array()).trim();
        buffer.clear();

        assertEquals(fileContent, "baeldung.com");
    }

    @Test
    public void givenPath_whenReadsContentWithCompletionHandler_thenCorrect() throws IOException {
        final Path path = Paths.get(URI.create(this.getClass().getClassLoader().getResource("file.txt").toString()));
        final AsynchronousFileChannel fileChannel = AsynchronousFileChannel.open(path, StandardOpenOption.READ);

        final ByteBuffer buffer = ByteBuffer.allocate(1024);

        fileChannel.read(buffer, 0, buffer, new CompletionHandler<Integer, ByteBuffer>() {
            @Override
            public void completed(Integer result, ByteBuffer attachment) {
                // result is number of bytes read
                // attachment is the buffer
            }

            @Override
            public void failed(Throwable exc, ByteBuffer attachment) {

            }
        });
    }

    @Test
    public void givenPathAndContent_whenWritesToFileWithFuture_thenCorrect() throws IOException, ExecutionException, InterruptedException {
        final String fileName = "temp";
        final Path path = Paths.get(fileName);
        final AsynchronousFileChannel fileChannel = AsynchronousFileChannel.open(path, StandardOpenOption.WRITE, StandardOpenOption.CREATE);

        final ByteBuffer buffer = ByteBuffer.allocate(1024);
        final long position = 0;

        buffer.put("hello world".getBytes());
        buffer.flip();

        final Future<Integer> operation = fileChannel.write(buffer, position);
        buffer.clear();

        operation.get();

        final String content = readContent(path);
        assertEquals("hello world", content);
    }

    @Test
    public void givenPathAndContent_whenWritesToFileWithHandler_thenCorrect() throws IOException {
        final String fileName = UUID.randomUUID().toString();
        final Path path = Paths.get(fileName);
        final AsynchronousFileChannel fileChannel = AsynchronousFileChannel.open(path, StandardOpenOption.WRITE, StandardOpenOption.CREATE, StandardOpenOption.DELETE_ON_CLOSE);

        final ByteBuffer buffer = ByteBuffer.allocate(1024);
        buffer.put("hello world".getBytes());
        buffer.flip();

        fileChannel.write(buffer, 0, buffer, new CompletionHandler<Integer, ByteBuffer>() {
            @Override
            public void completed(Integer result, ByteBuffer attachment) {
                // result is number of bytes written
                // attachment is the buffer
            }

            @Override
            public void failed(Throwable exc, ByteBuffer attachment) {

            }
        });
    }

    //

    private String readContent(Path file) throws ExecutionException, InterruptedException {
        AsynchronousFileChannel fileChannel = null;
        try {
            fileChannel = AsynchronousFileChannel.open(file, StandardOpenOption.READ);
        } catch (IOException e) {
            e.printStackTrace();
        }

        final ByteBuffer buffer = ByteBuffer.allocate(1024);

        final Future<Integer> operation = fileChannel.read(buffer, 0);

        operation.get();

        final String fileContent = new String(buffer.array()).trim();
        buffer.clear();
        return fileContent;
    }

}