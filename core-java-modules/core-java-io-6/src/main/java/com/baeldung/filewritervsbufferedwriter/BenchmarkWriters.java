package com.baeldung.filewritervsbufferedwriter;

import org.openjdk.jmh.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.concurrent.TimeUnit;

@State(Scope.Benchmark)
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@Warmup(iterations = 5, time = 1, timeUnit = TimeUnit.SECONDS)
@Measurement(iterations = 10, time = 10, timeUnit = TimeUnit.SECONDS)
@Fork(1)
public class BenchmarkWriters {

    private static final Logger log = LoggerFactory.getLogger(BenchmarkWriters.class);
    private static final String FILE_PATH = "benchmark.txt";
    private static final String CONTENT = "This is a test line.";
    private static final int BUFSIZE = 4194304; // 4MiB

    @Benchmark
    public void fileWriter1Write() {
        try (FileWriter writer = new FileWriter(FILE_PATH, true)) {
            writer.write(CONTENT);
            writer.close();
        } catch (IOException e) {
            log.error("Error in FileWriter 1 write", e);
        }
    }

    @Benchmark
    public void bufferedWriter1Write() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH, true), BUFSIZE)) {
            writer.write(CONTENT);
            writer.close();
        } catch (IOException e) {
            log.error("Error in BufferedWriter 1 write", e);
        }
    }

    @Benchmark
    public void fileWriter10Writes() {
        try (FileWriter writer = new FileWriter(FILE_PATH, true)) {
            for (int i = 0; i < 10; i++) {
                writer.write(CONTENT);
            }
            writer.close();
        } catch (IOException e) {
            log.error("Error in FileWriter 10 writes", e);
        }
    }

    @Benchmark
    public void bufferedWriter10Writes() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH, true), BUFSIZE)) {
            for (int i = 0; i < 10; i++) {
                writer.write(CONTENT);
            }
            writer.close();
        } catch (IOException e) {
            log.error("Error in BufferedWriter 10 writes", e);
        }
    }

    @Benchmark
    public void fileWriter1000Writes() {
        try (FileWriter writer = new FileWriter(FILE_PATH, true)) {
            for (int i = 0; i < 1000; i++) {
                writer.write(CONTENT);
            }
            writer.close();
        } catch (IOException e) {
            log.error("Error in FileWriter 1000 writes", e);
        }
    }

    @Benchmark
    public void bufferedWriter1000Writes() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH, true), BUFSIZE)) {
            for (int i = 0; i < 1000; i++) {
                writer.write(CONTENT);
            }
            writer.close();
        } catch (IOException e) {
            log.error("Error in BufferedWriter 1000 writes", e);
        }
    }
    
    @Benchmark
    public void fileWriter10000Writes() {
        try (FileWriter writer = new FileWriter(FILE_PATH, true)) {
            for (int i = 0; i < 10000; i++) {
                writer.write(CONTENT);
            }
            writer.close();
        } catch (IOException e) {
            log.error("Error in FileWriter 10000 writes", e);
        }
    }

    @Benchmark
    public void bufferedWriter10000Writes() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH, true), BUFSIZE)) {
            for (int i = 0; i < 10000; i++) {
                writer.write(CONTENT);
            }
            writer.close();
        } catch (IOException e) {
            log.error("Error in BufferedWriter 10000 writes", e);
        }
    }
    
    @Benchmark
    public void fileWriter100000Writes() {
        try (FileWriter writer = new FileWriter(FILE_PATH, true)) {
            for (int i = 0; i < 100000; i++) {
                writer.write(CONTENT);
            }
            writer.close();
        } catch (IOException e) {
            log.error("Error in FileWriter 100000 writes", e);
        }
    }

    @Benchmark
    public void bufferedWriter100000Writes() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH, true), BUFSIZE)) {
            for (int i = 0; i < 100000; i++) {
                writer.write(CONTENT);
            }
            writer.close();
        } catch (IOException e) {
            log.error("Error in BufferedWriter 100000 writes", e);
        }
    }

    public static void main(String[] args) throws Exception {
        Files.deleteIfExists(Paths.get(FILE_PATH));
        org.openjdk.jmh.Main.main(args);
    }
}