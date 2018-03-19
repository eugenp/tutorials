package com.baeldung.beaninjectiontypes.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SetterEncryptionService {

    Encrypter encrypter;

    @Autowired
    public SetterEncryptionService setEncrypter(Encrypter encrypter) {
        this.encrypter = encrypter;
        return this;
    }

    public String encryptMessage(String message) {
        return this.encrypter.encrypt(message);
    }
}
