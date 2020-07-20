package com.baeldung.lob;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(propagation = Propagation.NOT_SUPPORTED)
class FileStorageService {

    @Autowired
    FileStorageRepository fileStorage;

    String save(File file, Path directory) throws IOException {
        return fileStorage.saveToFileSystem(file, directory);
    }

    void checkForValidFile(String path) {
        if (fileStorage.exists(path)) {
            return;
        }
        throw new RuntimeException("File not found in the File System.");
    }

    void remove(String path) {
        fileStorage.remove(path);
    }
}
