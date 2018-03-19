package com.baeldung.beaninjectiontypes.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class QualifiedSetterEncoderService implements EncoderService{

    Encoder encoder;

    @Autowired
    public void setEncoder(@Qualifier("basicEncoder") Encoder encoder) {
        this.encoder = encoder;
    }

    @Override
    public String encodeMessage(String message) {
        return this.encoder.encode(message);
    }
}
