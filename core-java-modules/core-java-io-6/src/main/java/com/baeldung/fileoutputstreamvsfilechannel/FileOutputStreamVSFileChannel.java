package com.baeldung.fileoutputstreamvsfilechannel;

import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousFileChannel;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.Arrays;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.baeldung.filewritervsbufferedwriter.BenchmarkWriters;

public class FileOutputStreamVSFileChannel {
    private static final Logger log = LoggerFactory.getLogger(FileOutputStreamVSFileChannel.class);


    private static final Object lock = new Object();
    private static final String outputFile = "output.txt";
    private static final String largeOutputFile = "large_output.txt";

    private static byte[] data = "This is some data to write".getBytes();
    private static byte[] data1 = "This is the first line.\n".getBytes();
    private static byte[] data2 = "This is the second line.\n".getBytes();
    private static byte[] largeData = new byte[1000 * 1024 * 1024];

    public static void writeFileUsingFileOutputStream() {
        try (FileOutputStream outputStream = new FileOutputStream(outputFile)) {
            outputStream.write(data);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void writeFileUsingFileChannel() {
        ByteBuffer buffer = ByteBuffer.wrap(data);
        try (FileChannel fileChannel = FileChannel.open(Path.of(outputFile), StandardOpenOption.WRITE, StandardOpenOption.CREATE)) {
            fileChannel.write(buffer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void writeFileInSequentialUsingFileOutputStream() {
        try (FileOutputStream outputStream = new FileOutputStream("output.txt")) {
            outputStream.write(data1);
            outputStream.write(data2);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void writeFileInRandomUsingFileChannel() {
        ByteBuffer buffer1 = ByteBuffer.wrap(data1);
        ByteBuffer buffer2 = ByteBuffer.wrap(data2);
        try (FileChannel fileChannel = FileChannel.open(Path.of("output.txt"), StandardOpenOption.WRITE, StandardOpenOption.CREATE)) {
            fileChannel.write(buffer1);
            fileChannel.position(10);
            fileChannel.write(buffer2);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void writeToFile(String fileName, byte[] data) {
        synchronized (lock) {
            try (FileOutputStream outputStream = new FileOutputStream(fileName, true)) {
                outputStream.write(data);
                log.info("Data written by " + Thread.currentThread()
                    .getName());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void writeFileWithMultThreadUsingFileOutputStream() {
        Thread thread1 = new Thread(() -> writeToFile(outputFile, data1));
        Thread thread2 = new Thread(() -> writeToFile(outputFile, data2));

        thread1.start();
        thread2.start();
    }

    private static void writeToFileWithLock(String fileName, ByteBuffer buffer, int position) {
        try (FileChannel fileChannel = FileChannel.open(Path.of(fileName), StandardOpenOption.WRITE, StandardOpenOption.CREATE)) {
            try (FileLock lock = fileChannel.lock(position, buffer.remaining(), false)) {
                fileChannel.position(position);
                fileChannel.write(buffer);
                log.info("Data written by " + Thread.currentThread()
                    .getName() + " at position " + position);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void writeFileWithMultiThreadUsingFileChannel() {
        ByteBuffer buffer1 = ByteBuffer.wrap(data1);
        ByteBuffer buffer2 = ByteBuffer.wrap(data2);

        Thread thread1 = new Thread(() -> writeToFileWithLock(outputFile, buffer1, 0));
        Thread thread2 = new Thread(() -> writeToFileWithLock(outputFile, buffer2, 20));

        thread1.start();
        thread2.start();
    }

    public static void performanceComparisonUsingJMH() throws RunnerException {
        Options opt = new OptionsBuilder()
            .include(FileIOBenchmark.class.getSimpleName())
            .forks(1)
            .build();

        new Runner(opt).run();
    }

    public static void main(String [] args) throws RunnerException {
        performanceComparisonUsingJMH();
    }
}
