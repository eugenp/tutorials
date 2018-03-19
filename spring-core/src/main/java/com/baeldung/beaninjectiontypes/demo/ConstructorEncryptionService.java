package com.baeldung.beaninjectiontypes.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ConstructorEncryptionService {

    Encrypter encrypter;

    @Autowired
    public ConstructorEncryptionService(Encrypter encrypter) {
        this.encrypter = encrypter;
    }

    public String encryptMessage(String message) {
        return this.encrypter.encrypt(message);
    }
}
