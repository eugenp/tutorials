package com.baeldung.reactive.controller;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

import com.baeldung.reactive.model.CpuUsage;

import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

public class CpuUsageControllerUnitTest {

    @Test
    public void whenGetCpuUsage_thenCpuUsageFluxIsReturned() {
        CpuUsageController cpuUsageController = new CpuUsageController();
        
        Flux<CpuUsage> result = cpuUsageController.getCpuUsage();

        StepVerifier.create(result)
            .consumeNextWith(cpuUsage -> assertThat(cpuUsage.getUsage()).isGreaterThanOrEqualTo(0))
            .thenCancel()
            .verify();
    }

}
