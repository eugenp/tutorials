package com.baeldung.repository;

import com.baeldung.domain.FileWriterResponse;

public interface FileWriterRepository {

    FileWriterResponse createInvitation(FileWriterResponse fileWriterResponse);
    FileWriterResponse getInvitation(String eventId);
}
