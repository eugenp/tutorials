package com.baeldung.java.nio2.async;

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

public class AsyncFileIntegrationTest {
    @Test
    public void givenPath_whenReadsContentWithFuture_thenCorrect() throws IOException, ExecutionException, InterruptedException {
        Path path = Paths.get(URI.create(this.getClass().getClassLoader().getResource("file.txt").toString()));
        AsynchronousFileChannel fileChannel = AsynchronousFileChannel.open(path, StandardOpenOption.READ);

        ByteBuffer buffer = ByteBuffer.allocate(1024);

        Future<Integer> operation = fileChannel.read(buffer, 0);

        operation.get();

        String fileContent = new String(buffer.array()).trim();
        buffer.clear();

        assertEquals(fileContent, "baeldung.com");
    }

    @Test
    public void givenPath_whenReadsContentWithCompletionHandler_thenCorrect() throws IOException {
        Path path = Paths.get(URI.create(AsyncFileIntegrationTest.class.getResource("/file.txt").toString()));
        AsynchronousFileChannel fileChannel = AsynchronousFileChannel.open(path, StandardOpenOption.READ);

        ByteBuffer buffer = ByteBuffer.allocate(1024);

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
        String fileName = "temp";
        Path path = Paths.get(fileName);
        AsynchronousFileChannel fileChannel = AsynchronousFileChannel.open(path, StandardOpenOption.WRITE, StandardOpenOption.CREATE);

        ByteBuffer buffer = ByteBuffer.allocate(1024);
        long position = 0;

        buffer.put("hello world".getBytes());
        buffer.flip();

        Future<Integer> operation = fileChannel.write(buffer, position);
        buffer.clear();

        operation.get();

        String content = readContent(path);
        assertEquals("hello world", content);
    }

    @Test
    public void givenPathAndContent_whenWritesToFileWithHandler_thenCorrect() throws IOException {
        String fileName = UUID.randomUUID().toString();
        Path path = Paths.get(fileName);
        AsynchronousFileChannel fileChannel = AsynchronousFileChannel.open(path, StandardOpenOption.WRITE, StandardOpenOption.CREATE, StandardOpenOption.DELETE_ON_CLOSE);

        ByteBuffer buffer = ByteBuffer.allocate(1024);
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

    public static String readContent(Path file) throws ExecutionException, InterruptedException {
        AsynchronousFileChannel fileChannel = null;
        try {
            fileChannel = AsynchronousFileChannel.open(file, StandardOpenOption.READ);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        ByteBuffer buffer = ByteBuffer.allocate(1024);

        Future<Integer> operation = fileChannel.read(buffer, 0);

        operation.get();

        String fileContent = new String(buffer.array()).trim();
        buffer.clear();
        return fileContent;
    }
}