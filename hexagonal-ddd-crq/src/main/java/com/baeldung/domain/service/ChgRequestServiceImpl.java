package com.baeldung.domain.service;

import java.util.List;
import java.util.UUID;

import com.baeldung.domain.AppMetadata;
import com.baeldung.domain.ChgRequest;
import com.baeldung.domain.repository.ChgRequestRepository;

public class ChgRequestServiceImpl implements ChgRequestService {

    private final ChgRequestRepository chgRequestRepository;

    public ChgRequestServiceImpl(ChgRequestRepository chgRequestRepository) {
        this.chgRequestRepository = chgRequestRepository;
    }

    @Override
    public List<ChgRequest> findAll() {
        return chgRequestRepository.findAll();
    }

    @Override
    public ChgRequest findById(UUID id) {
        return getChgRequest(id);
    }

    @Override
    public UUID createChgRequest(AppMetadata appMetadata) {
        final ChgRequest chgRequest = new ChgRequest(UUID.randomUUID(), appMetadata);
        chgRequestRepository.save(chgRequest);

        return chgRequest.getId();
    }

    @Override
    public void beginChgRequest(UUID id) {
        var chgReq = getChgRequest(id);

        chgReq.beginChgRequest();

        chgRequestRepository.save(chgReq);
    }

    @Override
    public void doneChgRequest(UUID id) {
        var chgReq = getChgRequest(id);

        chgReq.markChgRequestAsDone();

        chgRequestRepository.save(chgReq);
    }

    @Override
    public void rollbackChgRequest(UUID id) {
        var chgReq = getChgRequest(id);

        chgReq.rollingBackChgReq();

        chgRequestRepository.save(chgReq);
    }


    private ChgRequest getChgRequest(UUID id) {
        return chgRequestRepository
                .findById(id)
                .orElseThrow(() -> new RuntimeException("ChgRequest with given id doesn't exist"));
    }
}
