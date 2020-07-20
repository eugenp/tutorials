package com.baeldung.lob;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
class FileDatabaseService {

    @Autowired
    FileLocationRepository fileLocation;

    @Autowired
    FileStorageService fileSystem;

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    void save(String path) throws IOException {

        FileLocationSample entity = new FileLocationSample();
        entity.setFilePath(path);

        fileSystem.checkForValidFile(path);

        saveLocation(path, entity);
    }

    private void saveLocation(String path, FileLocationSample entity) {
        try {
            fileLocation.save(entity);
        } catch (Exception e) {
            fileSystem.remove(path);
        }
    }
}
