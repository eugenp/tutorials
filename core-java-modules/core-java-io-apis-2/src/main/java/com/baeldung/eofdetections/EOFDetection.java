package com.baeldung.eofdetections;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class EOFDetection {

    public String readWithFileInputStream(String pathFile) throws IOException {
        try (FileInputStream fis = new FileInputStream(pathFile);
             ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            int data;
            while ((data = fis.read()) != -1) {
                baos.write(data);
            }
            return baos.toString();
        }
    }

    public String readWithBufferedReader(String pathFile) throws IOException {
        try (FileInputStream fis = new FileInputStream(pathFile);
             InputStreamReader isr = new InputStreamReader(fis);
             BufferedReader reader = new BufferedReader(isr)) {
            StringBuilder actualContent = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                actualContent.append(line);
            }
            return actualContent.toString();
        }
    }

    public String readWithScanner(String pathFile) throws IOException {
        File file = new File(pathFile);
        Scanner scanner = new Scanner(file);
        StringBuilder actualContent = new StringBuilder();
        while (scanner.hasNext()) {
            String line = scanner.nextLine();
            actualContent.append(line);
        }
        scanner.close();
        return actualContent.toString();
    }

    public String readFileWithFileChannelAndByteBuffer(String pathFile) throws IOException {
        try (FileInputStream fis = new FileInputStream(pathFile);
             FileChannel channel = fis.getChannel()) {

            ByteBuffer buffer = ByteBuffer.allocate((int) channel.size());
            while (channel.read(buffer) != -1) {
                buffer.flip();
                buffer.clear();
            }
            return StandardCharsets.UTF_8.decode(buffer).toString();
        }
    }
}

