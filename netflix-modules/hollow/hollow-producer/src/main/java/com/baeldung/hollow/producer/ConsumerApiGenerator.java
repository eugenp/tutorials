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
        System.out.println("========================================");
        System.out.println("ConsumerApiGenerator.main() INVOKED");
        System.out.println("========================================");
        logger.info("ConsumerApiGenerator invoked (args={})", (Object) args);

        if (args == null || args.length == 0) {
            // fallback to target/generated-sources inside project base dir
            String projectBasedir = System.getProperty("project.basedir", System.getProperty("user.dir"));
            String fallback = projectBasedir + "/target/generated-sources";
            System.out.println("No output dir provided. Using fallback: " + fallback);
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
        System.out.println("Starting HollowAPIGenerator with destination: " + apiOutputDir);

        HollowAPIGenerator generator = new HollowAPIGenerator.Builder()
                .withDestination(apiOutputDir)
                .withAPIClassname("MonitoringEventAPI")
                .withPackageName("com.baeldung.hollow.consumer.api")
                .withDataModel(MonitoringEvent.class)
                .build();
        try {
            generator.generateSourceFiles();
            logger.info("Consumer API source files generated successfully to {}", apiOutputDir);
            System.out.println("Consumer API source files generated successfully to " + apiOutputDir);

            // list generated files for visibility
            try {
                String files = Files.walk(outputPath)
                        .filter(Files::isRegularFile)
                        .map(Path::toString)
                        .collect(Collectors.joining(System.lineSeparator()));
                if (files.isEmpty()) {
                    System.out.println("No files generated under " + apiOutputDir);
                    logger.warn("No files generated under {}", apiOutputDir);
                } else {
                    System.out.println("Generated files:\n" + files);
                    logger.info("Generated files:\n{}", files);
                }
            } catch (IOException io) {
                logger.warn("Unable to list generated files in {}", apiOutputDir, io);
            }

        } catch (IOException e) {
            logger.error("Error generating consumer API source files", e);
            System.err.println("Error generating consumer API source files: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

}
