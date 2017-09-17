package com.baeldung.di.domain;

import org.springframework.stereotype.Component;

@Component
public class CloudStorageMessageArchiver implements MessageArchiver {

    @Override
    public void archive(Message message) {
        // Business logic
    }

    @Override
    public String toString() {
        return "CloudStorageMessageArchiver";
    }

}
