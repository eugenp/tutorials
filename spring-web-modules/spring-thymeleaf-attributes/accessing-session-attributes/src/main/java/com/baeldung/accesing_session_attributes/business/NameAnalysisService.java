package com.baeldung.accesing_session_attributes.business;

import java.util.concurrent.CompletableFuture;

import org.springframework.stereotype.Service;

import com.baeldung.accesing_session_attributes.business.beans.NameRequest;
import com.baeldung.accesing_session_attributes.business.entities.NameAnalysisEntity;

@Service
public interface NameAnalysisService {
    public NameRequest getLastNameRequest();

    public CompletableFuture<NameAnalysisEntity> searchForName(NameRequest nameRequest);
}
