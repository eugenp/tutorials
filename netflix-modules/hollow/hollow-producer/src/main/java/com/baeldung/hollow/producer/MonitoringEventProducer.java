package com.baeldung.hollow.producer;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.baeldung.hollow.model.MonitoringEvent;
import com.baeldung.hollow.service.MonitoringDataService;

import com.netflix.hollow.api.producer.HollowProducer;
import com.netflix.hollow.api.producer.fs.HollowFilesystemAnnouncer;
import com.netflix.hollow.api.producer.fs.HollowFilesystemPublisher;
import com.netflix.hollow.core.write.objectmapper.HollowObjectMapper;

public class MonitoringEventProducer {
    private static final Logger logger = LoggerFactory.getLogger(MonitoringEventProducer.class);

    static HollowProducer.Publisher publisher;
    static HollowProducer.Announcer announcer;
    static HollowProducer producer;
    static HollowObjectMapper mapper;

    final static long POLL_INTERVAL_MILLISECONDS = 30000;
    final static String SNAPSHOT_DIR = System.getProperty("user.home") + "/.hollow/snapshots";

    static MonitoringDataService dataService;

    public static void main(String[] args) {
        initialize(getSnapshotFilePath());
        pollEvents();
    }

    private static void initialize(final Path snapshotPath) {
        publisher = new HollowFilesystemPublisher(snapshotPath);
        announcer = new HollowFilesystemAnnouncer(snapshotPath);
        producer = HollowProducer.withPublisher(publisher)
                .withAnnouncer(announcer)
                .build();
        dataService = new MonitoringDataService();
        mapper = new HollowObjectMapper(producer.getWriteEngine());
    }

    private static void pollEvents() {
        while (true) {
            List<MonitoringEvent> events = dataService.retrieveEvents();
            events.forEach(mapper::add);
            producer.runCycle(task -> {
                events.forEach(task::add);
            });
            producer.getWriteEngine().prepareForNextCycle();
            sleep(POLL_INTERVAL_MILLISECONDS);
        }
    }

    private static void sleep(long milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    private static Path getSnapshotFilePath() {

        logger.info("snapshot data directory: {}", SNAPSHOT_DIR);
        Path path = Paths.get(SNAPSHOT_DIR);

        // Create directories if they don't exist
        try {
            Files.createDirectories(path);
        } catch (java.io.IOException e) {
            throw new RuntimeException("Failed to create snapshot directory: " + path, e);
        }

        return path;
    }
}
