package com.baeldung.domain.service;

import java.util.List;
import java.util.UUID;

import com.baeldung.domain.ChgRequest;
import com.baeldung.domain.AppMetadata;

public interface ChgRequestService {
    List<ChgRequest> findAll();

    ChgRequest findById(UUID id);

    UUID createChgRequest(AppMetadata appMetadata);

    void beginChgRequest(UUID id);

    void doneChgRequest(UUID id);

    void rollbackChgRequest(UUID id);
}
