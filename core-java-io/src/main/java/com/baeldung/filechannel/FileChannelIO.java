package com.baeldung.filechannel;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

class FileChannelIO implements AutoCloseable {
    private static final Charset UTF8 = Charset.forName("UTF-8");
    private FileChannel channel;

    FileChannelIO(FileChannel channel) {
        this.channel = channel;
    }

    String read() throws IOException {
        StringBuilder content = new StringBuilder();
        ByteBuffer buffer = ByteBuffer.allocate(8);

        while (channel.position() < channel.size()) {
            channel.read(buffer);
            buffer.flip();
            CharBuffer charBuffer = UTF8.decode(buffer);
            content.append(charBuffer.array());
            buffer.flip();
        }

        buffer.clear();
        return content.toString();
    }

    void write(String content) throws IOException {
        byte[] bytes = content.getBytes(UTF8);
        ByteBuffer buffer = ByteBuffer.allocate(8);

        int offset = 0, length = buffer.capacity();
        while (offset < bytes.length) {
            buffer.put(bytes, offset, length);
            buffer.flip();
            channel.write(buffer);
            buffer.flip();
            offset += buffer.capacity();
            length = bytes.length - offset > buffer.capacity() ? buffer.capacity() : bytes.length - offset;
        }

        buffer.clear();
    }

    @Override
    public void close() throws IOException {
        channel.close();
    }

    static String verifiedRead(Path path) throws IOException {
        try (BufferedReader br = Files.newBufferedReader(path)) {
            return br.readLine();
        }
    }

    static String verifiedRead(String path) throws IOException {
        try (BufferedReader br = Files.newBufferedReader(Paths.get(path))) {
            return br.readLine();
        }
    }

    static void clearContent(String path) throws IOException {
        new RandomAccessFile(path, "rw").setLength(0);
    }
}