package com.baeldung.lob;

import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Service
@Primary
class FileSystemStorage implements Storage {

    @Autowired
    FileSystemRepository storageRepository;

    @Override
    public String save(String fileName, byte[] fileContent) {
        FileLocation locationEntity = new FileLocation();
        locationEntity.setUri(fileName);

        return storageRepository.save(locationEntity, fileContent);
    }

    @Override
    public String uri() {
        return Paths.get("src", "test", "resources")
            .toUri()
            .toString();
    }
}
