package com.baeldung.jcommander.usagebilling.service;

import com.baeldung.jcommander.usagebilling.model.CurrentChargesRequest;
import com.baeldung.jcommander.usagebilling.model.CurrentChargesResponse;

public interface FetchCurrentChargesService {

    static FetchCurrentChargesService getDefault() {
        return new DefaultFetchCurrentChargesService();
    }

    CurrentChargesResponse fetch(CurrentChargesRequest request);
}
