package com.baeldung.streams.parallel;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;

public class FileSearchCost {

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.NANOSECONDS)
	public static void textFileSearchSequential() throws IOException {
		Files.walk(Paths.get("src/main/resources/filesearch/")).map(Path::normalize).filter(Files::isRegularFile)
				.filter(path -> path.getFileName().toString().endsWith(".txt")).collect(Collectors.toList());
	}

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.NANOSECONDS)
	public static void textFileSearchParallel() throws IOException {
		Files.walk(Paths.get("src/main/resources/filesearch/")).parallel().map(Path::normalize).filter(Files::isRegularFile)
				.filter(path -> path.getFileName().toString().endsWith(".txt")).collect(Collectors.toList());
	}

}

