package com.baeldung.jcommander.usagebilling.service;

import com.baeldung.jcommander.usagebilling.model.UsageRequest;

public interface SubmitUsageService {

    static SubmitUsageService getDefault() {
        return new DefaultSubmitUsageService();
    }

    String submit(UsageRequest request);
}
