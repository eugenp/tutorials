package com.baeldung.streams.parallel;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import org.apache.commons.io.FileUtils;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Level;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.TearDown;

@State(Scope.Benchmark)
public class FileSearchCost {

	private final static String FILE_NAME = "src/main/resources/Test";

	@Setup(Level.Trial)
	public void setup() throws IOException {
		for (int i = 0; i < 1500; i++) {
			File targetFile = new File(FILE_NAME + i);
			FileUtils.writeStringToFile(targetFile, "Test", "UTF8");
		}
	}

	@TearDown(Level.Trial)
	public void tearDown() {
		for (int i = 0; i < 1500; i++) {
			File fileToDelete = new File(FILE_NAME + i);
			fileToDelete.delete();
		}
	}

	@Benchmark
	@BenchmarkMode(Mode.AverageTime)
	@OutputTimeUnit(TimeUnit.NANOSECONDS)
	public static void textFileSearchSequential() throws IOException {
		Files.walk(Paths.get("src/main/resources/")).map(Path::normalize).filter(Files::isRegularFile)
				.filter(path -> path.getFileName().toString().endsWith(".txt")).collect(Collectors.toList());
	}

	@Benchmark
	@BenchmarkMode(Mode.AverageTime)
	@OutputTimeUnit(TimeUnit.NANOSECONDS)
	public static void textFileSearchParallel() throws IOException {
		Files.walk(Paths.get("src/main/resources/")).parallel().map(Path::normalize).filter(Files::isRegularFile)
				.filter(path -> path.getFileName().toString().endsWith(".txt")).collect(Collectors.toList());
	}

}

