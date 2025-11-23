package com.baeldung.hollow.consumer;

import com.baeldung.hollow.consumer.api.MonitoringEvent;
import com.baeldung.hollow.consumer.api.MonitoringEventAPI;
import com.netflix.hollow.api.consumer.HollowConsumer;
import com.netflix.hollow.api.consumer.fs.HollowFilesystemAnnouncementWatcher;
import com.netflix.hollow.api.consumer.fs.HollowFilesystemBlobRetriever;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collection;

public class MonitoringEventConsumer {
    private final static Logger logger = LoggerFactory.getLogger(MonitoringEventConsumer.class);

    static HollowConsumer consumer;
    static HollowFilesystemAnnouncementWatcher announcementWatcher;
    static HollowFilesystemBlobRetriever blobRetriever;
    static boolean initialized = false;

    final static long POLL_INTERVAL_MILLISECONDS = 30000;
    final static String SNAPSHOT_DIR = System.getProperty("user.home") + "/.hollow/snapshots";

    public static void main(String[] args) {
        initialize(getSnapshotFilePath());
        while (true) {
            Collection<MonitoringEvent> events = consumer.getAPI(MonitoringEventAPI.class).getAllMonitoringEvent();
            processEvents(events);
            sleep(POLL_INTERVAL_MILLISECONDS);
        }
    }

    private static void processEvents(Collection<MonitoringEvent> events) {
        logger.info("Processing {} events", events.size());
        events.forEach(evt -> {
            logger.info("Event ID: {}, Name: {}, Type: {}, Status: {}, Device ID: {}, Creation Date: {}",
                evt.getEventId(),
                evt.getEventName().getValue(),
                evt.getEventType().getValue(),
                evt.getStatus().getValue(),
                evt.getDeviceId().getValue(),
                evt.getCreationDate().getValue());
        });
    }

    private static void initialize(final Path snapshotPath) {
        if (initialized) {
            return;
        }

        announcementWatcher = new HollowFilesystemAnnouncementWatcher(snapshotPath);
        blobRetriever = new HollowFilesystemBlobRetriever(snapshotPath);

        consumer = new HollowConsumer.Builder<>()
            .withAnnouncementWatcher(announcementWatcher)
            .withBlobRetriever(blobRetriever)
            .withGeneratedAPIClass(MonitoringEventAPI.class)
            .build();
        consumer.triggerRefresh();
        initialized = true;
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
        return path;
    }
}
