package com.baeldung.healthapp.service;

import java.net.MalformedURLException;
import java.util.concurrent.ExecutionException;

import com.baeldung.healthapp.domain.HealthData;

public interface HealthService {
    void process(HealthData healthData) throws MalformedURLException, InterruptedException, ExecutionException;
}
