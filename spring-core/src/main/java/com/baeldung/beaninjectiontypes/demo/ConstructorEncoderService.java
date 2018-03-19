package com.baeldung.beaninjectiontypes.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ConstructorEncoderService implements EncoderService{

    Encoder encoder;

    @Autowired
    public ConstructorEncoderService(Encoder encoder) {
        this.encoder = encoder;
    }

    @Override
    public String encodeMessage(String message) {
        return this.encoder.encode(message);
    }
}
