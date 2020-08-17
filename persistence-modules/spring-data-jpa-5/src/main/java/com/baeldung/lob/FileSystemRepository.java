package com.baeldung.lob;

import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
interface FileSystemRepository extends JpaRepository<FileLocation, String> {

    default String save(FileLocation fileLocation, byte[] fileContent) {
        save(fileLocation);

        return saveToFileSystem(fileLocation, fileContent);
    }

    default String saveToFileSystem(FileLocation fileLocation, byte[] fileContent) {
        try {
            Path newFile = Paths.get(new URI(fileLocation.getUri()));
            Files.createDirectories(newFile.getParent());

            return Files.write(newFile, fileContent)
                .toUri()
                .toString();
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
