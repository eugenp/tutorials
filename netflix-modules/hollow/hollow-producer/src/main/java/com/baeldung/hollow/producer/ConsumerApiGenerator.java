package com.baeldung.hollow.producer;

import com.baeldung.hollow.model.MonitoringEvent;
import com.netflix.hollow.api.codegen.HollowAPIGenerator;
import com.netflix.hollow.core.write.HollowWriteStateEngine;
import com.netflix.hollow.core.write.objectmapper.HollowObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Collectors;

public class ConsumerApiGenerator {
    private static final Logger logger = LoggerFactory.getLogger(ConsumerApiGenerator.class);

    public static void main(String[] args) {
        logger.info("========================================");
        logger.info("ConsumerApiGenerator.main() INVOKED");
        logger.info("========================================");
        logger.info("ConsumerApiGenerator invoked (args={})", (Object) args);

        if (args == null || args.length == 0) {
            // fallback to target/generated-sources inside project base dir
            String projectBasedir = System.getProperty("project.basedir", System.getProperty("user.dir"));
            String fallback = projectBasedir + "/target/generated-sources";            
            logger.warn("No output dir provided. Using fallback: {}", fallback);
            args = new String[]{fallback};
        }

        String apiOutputDir = args[0];
        Path outputPath = Paths.get(apiOutputDir);
        try {
            Files.createDirectories(outputPath);
        } catch (IOException e) {
            logger.error("Unable to create output directory {}", apiOutputDir, e);
            System.err.println("Unable to create output directory " + apiOutputDir + ": " + e.getMessage());
            throw new RuntimeException(e);
        }

        // Code to generate consumer API using HollowConsumerApiGenerator
        HollowWriteStateEngine writeEngine = new HollowWriteStateEngine();
        HollowObjectMapper mapper = new HollowObjectMapper(writeEngine);

        mapper.initializeTypeState(MonitoringEvent.class);

        logger.info("Starting HollowAPIGenerator with destination: {}", apiOutputDir);

        HollowAPIGenerator generator = new HollowAPIGenerator.Builder()
                .withDestination(apiOutputDir)
                .withAPIClassname("MonitoringEventAPI")
                .withPackageName("com.baeldung.hollow.consumer.api")
                .withDataModel(writeEngine)
                .build();
        try {
            generator.generateSourceFiles();
            logger.info("Consumer API source files generated at: {}", 
                Files.walk(outputPath)
                    .filter(Files::isRegularFile)
                    .map(Path::toString)
                    .collect(Collectors.joining(", "))
            );
        } catch (IOException e) {
            logger.error("Error generating consumer API source files", e);
            throw new RuntimeException(e);
        }
    }

}
