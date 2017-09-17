package com.baeldung.di.domain;

import org.springframework.stereotype.Component;

@Component
public class TapeStorageMessageArchiver implements MessageArchiver {

    @Override
    public void archive(Message message) {
        // Business logic
    }

    @Override
    public String toString() {
        return "TapeStorageMessageArchiver";
    }
}
