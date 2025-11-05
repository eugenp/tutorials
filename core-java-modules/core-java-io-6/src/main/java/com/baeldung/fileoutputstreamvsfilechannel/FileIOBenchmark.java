package com.baeldung.fileoutputstreamvsfilechannel;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;

@State(Scope.Benchmark)
public class FileIOBenchmark {

    private byte[] largeData;

    @Setup
    public void setup() {
        largeData = new byte[1000 * 1024 * 1024]; // 1 GB of data
        Arrays.fill(largeData, (byte) 1);
    }


    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    public void testFileOutputStream() {
        try (FileOutputStream outputStream = new FileOutputStream("largeOutputStream.txt")) {
            outputStream.write(largeData);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    public void testFileChannel() {
        ByteBuffer buffer = ByteBuffer.wrap(largeData);
        try (FileChannel fileChannel = FileChannel.open(Path.of("largeFileChannel.txt"), StandardOpenOption.WRITE, StandardOpenOption.CREATE)) {
            fileChannel.write(buffer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
