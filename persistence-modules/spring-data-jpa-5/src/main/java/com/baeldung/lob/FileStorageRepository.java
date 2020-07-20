package com.baeldung.lob;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.stereotype.Repository;

@Repository
class FileStorageRepository {

    String saveToFileSystem(File file, Path directory) throws IOException {
        Path newFile = Paths.get(directory + file.getName());

        Files.deleteIfExists(newFile);
        Path savedFile = Files.createFile(newFile);

        return savedFile.toAbsolutePath()
            .toString();
    }

    boolean exists(String path) {
        return Files.exists(Paths.get(path));
    }

    void remove(String path) {
        try {
            Files.deleteIfExists(Paths.get(path));
        } catch (IOException e) {
            throw new RuntimeException("Error deleting file.");
        }
    }
}
