package com.baeldung.file.content.comparison;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.nio.file.Path;

public class CompareFileContents {

    public static long filesCompareByByte(Path path1, Path path2) throws IOException {

        if (path1.getFileSystem()
            .provider()
            .isSameFile(path1, path2)) {
            return -1;
        }

        try (BufferedInputStream fis1 = new BufferedInputStream(new FileInputStream(path1.toFile())); 
            BufferedInputStream fis2 = new BufferedInputStream(new FileInputStream(path2.toFile()))) {
            int ch = 0;
            long pos = 1;
            while ((ch = fis1.read()) != -1) {
                if (ch != fis2.read()) {
                    return pos;
                }
                pos++;
            }
            if (fis2.read() == -1) {
                return -1;
            } else {
                return pos;
            }
        }
    }

    public static long filesCompareByLine(Path path1, Path path2) throws IOException {

        if (path1.getFileSystem()
            .provider()
            .isSameFile(path1, path2)) {
            return -1;
        }

        try (BufferedReader bf1 = Files.newBufferedReader(path1); 
            BufferedReader bf2 = Files.newBufferedReader(path2)) {
            
            long lineNumber = 1;
            String line1 = "", line2 = "";
            while ((line1 = bf1.readLine()) != null) {
                line2 = bf2.readLine();
                if (line2 == null || !line1.equals(line2)) {
                    return lineNumber;
                }
                lineNumber++;
            }
            if (bf2.readLine() == null) {
                return -1;
            } else {
                return lineNumber;
            }
        }
    }

    public static boolean compareByMemoryMappedFiles(Path path1, Path path2) throws IOException {
    
        try (RandomAccessFile randomAccessFile1 = new RandomAccessFile(path1.toFile(), "r"); 
            RandomAccessFile randomAccessFile2 = new RandomAccessFile(path2.toFile(), "r")) {
            
            FileChannel ch1 = randomAccessFile1.getChannel();
            FileChannel ch2 = randomAccessFile2.getChannel();
            if (ch1.size() != ch2.size()) {
    
                return false;
            }
            long size = ch1.size();
            MappedByteBuffer m1 = ch1.map(FileChannel.MapMode.READ_ONLY, 0L, size);
            MappedByteBuffer m2 = ch2.map(FileChannel.MapMode.READ_ONLY, 0L, size);
    
            return m1.equals(m2);
        }
    }
}