package com.baeldung.spf4j.core;

import java.io.File;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.spf4j.perf.MeasurementRecorder;
import org.spf4j.perf.impl.RecorderFactory;

public class Spf4jConfig {
    private static final Logger LOGGER = LoggerFactory.getLogger(App.class);

    public static void initialize() {
        String tsDbFile = System.getProperty("user.dir") + File.separator + "spf4j-performance-monitoring.tsdb2";
        String tsTextFile = System.getProperty("user.dir") + File.separator + "spf4j-performance-monitoring.txt";

        LOGGER.info("\nTime Series DB (TSDB) : {}\nTime Series text file : {}", tsDbFile, tsTextFile);
        System.setProperty("spf4j.perf.ms.config", "TSDB@" + tsDbFile + "," + "TSDB_TXT@" + tsTextFile);
    }

    public static MeasurementRecorder getMeasurementRecorder(Object forWhat) {
        String unitOfMeasurement = "ms";
        int sampleTimeMillis = 1_000;
        int factor = 10;
        int lowerMagnitude = 0;
        int higherMagnitude = 4;
        int quantasPerMagnitude = 10;

        return RecorderFactory.createScalableQuantizedRecorder(forWhat, unitOfMeasurement, sampleTimeMillis, factor, lowerMagnitude, higherMagnitude, quantasPerMagnitude);
    }
}
