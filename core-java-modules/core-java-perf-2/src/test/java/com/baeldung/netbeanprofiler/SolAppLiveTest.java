package com.baeldung.netbeanprofiler;

import org.junit.jupiter.api.Test;

import java.io.IOException;


class SolAppLiveTest {

    SolApp solApp = new SolApp("solarSystem.hprof");

    SolAppLiveTest() throws Exception {
    }

    @Test
    void solarSystem() throws IOException {
        SolApp.solarSystem();
    }

    @Test
    void givenSolarSystemClass_whenExecuted_thenTakeDumpFileDuringExecution() {
        SolApp.heapDumpSummary();
    }

    @Test
    void givenDumpFile_whenProfilingTheSolarSystemClass_thenReturnInstancesAndByteUsed() {
        SolApp.solarSystemSummary();
    }

    @Test
    void givenDumpFile_whenProfilingTheSolarSystemClass_thenReturnFieldDetails() {
        SolApp.logFieldDetails();
    }

    @Test
    void givenDumpFile_whenProfilingGCroots_returnTopTenGCroots() {
        SolApp.analyzeGCRoots();
    }

    @Test
    void givenADumpFile_whenProfilingTopTenInstancesAndBytesUsed_thenReturnTopInstances() {
        SolApp.analyzeClassHistogram();
    }

}