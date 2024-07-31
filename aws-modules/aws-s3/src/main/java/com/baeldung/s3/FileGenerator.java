package com.baeldung.s3;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class FileGenerator {

    public static List<File> generateFiles(int fileCount, int fileSize) {
        List<File> files = new ArrayList<>();
        for (int i = 0; i < fileCount; i++) {
            String fileName = "file_" + i + ".txt";
            ByteBuffer fileContent = generateRandomBytes(fileSize);
            files.add(new File(fileName, fileContent));
        }
        return files;
    }

    private static ByteBuffer generateRandomBytes(int size) {
        byte[] array = new byte[size];
        new Random().nextBytes(array);
        return ByteBuffer.wrap(array);
    }


}
