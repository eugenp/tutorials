package com.baeldung.lob;

import java.time.LocalTime;

import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
class StorageContext {

    @Autowired
    Storage storage;

    @Transactional
    String save(String fileName, byte[] fileContent) {
        String fileUri = storage.uri() + day() + "/" + time() + fileName;
        return storage.save(fileUri, fileContent);
    }

    private String day() {
        return LocalDate.now()
            .toString("YYYYMMDD");
    }

    private String time() {
        return LocalTime.now()
            .toSecondOfDay() + "-";
    }
}
