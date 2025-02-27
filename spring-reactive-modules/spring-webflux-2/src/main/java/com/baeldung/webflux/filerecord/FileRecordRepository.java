package com.baeldung.webflux.filerecord;

import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FileRecordRepository extends R2dbcRepository<FileRecord, Integer> {
}
