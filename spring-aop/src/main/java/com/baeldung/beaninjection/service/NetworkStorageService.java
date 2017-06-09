package com.baeldung.beaninjection.service;

import com.baeldung.beaninjection.config.StorageProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by smatt on 12/05/2017.
 */
@Service
public class NetworkStorageService {

    StorageProperties storageProperties;

    public NetworkStorageService() {}

    @Autowired
    public void setStorageProperties(StorageProperties storageProperties) {
        this.storageProperties = storageProperties;
    }

    public StorageProperties getStorageProperties() {
        return storageProperties;
    }

    @Override
    public String toString() {
        return "NetworkStorageService: Storage Location = " + storageProperties.getLocation();
    }

}
