package com.baeldung.lob;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
class DatabaseStorage implements Storage {

    @Autowired
    DatabaseRepository lobSampleRepository;

    @Override
    public String save(String fileName, byte[] fileContent) {
        LobSample sample = new LobSample();
        sample.setKey(uri() + fileName);
        sample.setContent(fileContent);

        return lobSampleRepository.save(sample)
            .getKey();
    }

    @Override
    public String uri() {
        return "jdbc:h2://mem/";
    }
}
