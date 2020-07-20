package com.baeldung.lob;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
class FileService {

    @Autowired
    FileStorageService fileSystem;
    @Autowired
    FileDatabaseService fileDatabase;

    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    void save(File file, Path directory) throws IOException {
        String absolutePath = fileSystem.save(file, directory);

        fileDatabase.save(absolutePath);
    }
}
