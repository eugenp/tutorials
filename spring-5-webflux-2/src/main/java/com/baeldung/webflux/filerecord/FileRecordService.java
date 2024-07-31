package com.baeldung.webflux.filerecord;

import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class FileRecordService {

    private FileRecordRepository fileRecordRepository;

    public FileRecordService(FileRecordRepository fileRecordRepository) {
        this.fileRecordRepository = fileRecordRepository;
    }

    public Mono<FileRecord> save(FileRecord fileRecord) {
        return fileRecordRepository.save(fileRecord);
    }

    public Mono<FileRecord> findById(int id) {
        return fileRecordRepository.findById(id);
    }

}
