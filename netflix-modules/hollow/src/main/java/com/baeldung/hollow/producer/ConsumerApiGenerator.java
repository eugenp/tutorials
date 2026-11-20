package com.baeldung.hollow.producer;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.baeldung.hollow.model.MonitoringEvent;
import com.netflix.hollow.api.codegen.HollowAPIGenerator;
import com.netflix.hollow.core.write.HollowWriteStateEngine;
import com.netflix.hollow.core.write.objectmapper.HollowObjectMapper;

public class ConsumerApiGenerator {
    private static final Logger logger = LoggerFactory.getLogger(ConsumerApiGenerator.class);

    public static void main(String[] args) {
        String sourceDir = args[0];
        Path outputPath = getGeneratedSourceDirectory(sourceDir);

        HollowWriteStateEngine writeEngine = new HollowWriteStateEngine();
        HollowObjectMapper mapper = new HollowObjectMapper(writeEngine);

        mapper.initializeTypeState(MonitoringEvent.class);

        logger.info("Starting HollowAPIGenerator with destination: {}", outputPath);

        HollowAPIGenerator generator = new HollowAPIGenerator.Builder()
                .withDestination(outputPath)
                .withAPIClassname("MonitoringEventAPI")
                .withPackageName("com.baeldung.hollow.consumer.api")
                .withDataModel(writeEngine)
                .build();
        try {
            generator.generateSourceFiles();
        } catch (IOException e) {
            logger.error("Error generating consumer API source files", e);
            throw new RuntimeException(e);
        }
    }

    private static Path getGeneratedSourceDirectory(String sourceDir) {
        Path generatedSourceDir = Paths.get(sourceDir);
        try {
            Files.createDirectories(generatedSourceDir);
        } catch (IOException e) {
            logger.error("Unable to create output directory {}", generatedSourceDir, e);
            throw new RuntimeException(e);
        }

        return generatedSourceDir;
    }

}
