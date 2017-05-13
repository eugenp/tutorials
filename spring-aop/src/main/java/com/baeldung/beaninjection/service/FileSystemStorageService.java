package com.baeldung.beaninjection.service;

import com.baeldung.beaninjection.config.StorageProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by smatt on 12/05/2017.
 */
@Service
public class FileSystemStorageService {

    StorageProperties storageProperties;

    @Autowired
    public FileSystemStorageService(StorageProperties storageProperties) {
        this.storageProperties = storageProperties;
    }

    //this is for test purpose
    public StorageProperties getStorageProperties() {
        return storageProperties;
    }

    @Override
    public String toString() {
        return "FileSystemStorageService: Storage Location = " + storageProperties.getLocation();
    }
}
