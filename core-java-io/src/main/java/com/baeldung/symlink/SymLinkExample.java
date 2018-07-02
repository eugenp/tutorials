package com.baeldung.symlink;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Optional;
import java.util.stream.IntStream;

public class SymLinkExample {

	public Optional<Path> createSymbolicLink(String linkPath, Path target) {
		try {
			Path link = Paths.get(linkPath);
			if (Files.exists(link))
				Files.delete(link);
			Files.createSymbolicLink(link, target);
			return Optional.of(link);
		} catch (IOException e) {
			// process IO error
		}
		return Optional.empty();
	}

	public Optional<Path> createHardLink(String linkPath, Path target) {
		try {
			Path link = Paths.get(linkPath);
			if (Files.exists(link))
				Files.delete(link);
			Files.createLink(link, target);
			return Optional.of(link);
		} catch (IOException e) {
			// process IO error
		}
		return Optional.empty();
	}

	public Path createTextFile() throws IOException {
		byte[] content = IntStream.range(0, 10000).mapToObj(i -> i + System.lineSeparator()).reduce("", String::concat)
				.getBytes(StandardCharsets.UTF_8);
		Path filePath = Paths.get("", "target_link.txt");
		Files.write(filePath, content, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
		return filePath;
	}

	public void printLinkFiles(Path path) throws IOException {
		try (DirectoryStream<Path> stream = Files.newDirectoryStream(path)) {
			for (Path file : stream) {
				if (Files.isDirectory(file))
					printLinkFiles(file);
				else if (Files.isSymbolicLink(file))
					System.out.format("File link '%s' with target '%s'%n", file, Files.readSymbolicLink(file));
			}
		}
	}
	
	public static void main(String[] args) throws IOException {
		new SymLinkExample().printLinkFiles(Paths.get(""));
	}

}
