package com.baeldung.jcommander.usagebilling.service;

import com.baeldung.jcommander.usagebilling.model.UsageRequest;

import java.util.UUID;

class DefaultSubmitUsageService implements SubmitUsageService {

    @Override
    public String submit(UsageRequest request) {
        System.out.println("Submitting usage..." + request);

        System.out.println("Submitted usage successfully...");
        return UUID.randomUUID().toString();
    }
}
