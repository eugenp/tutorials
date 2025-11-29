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
    static MonitoringEventAPI monitoringEventAPI;

    final static long POLL_INTERVAL_MILLISECONDS = 30000;

    public static void main(String[] args) {
        initialize(getSnapshotFilePath());
        while (true) {
            Collection<MonitoringEvent> events = monitoringEventAPI.getAllMonitoringEvent();
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
        announcementWatcher = new HollowFilesystemAnnouncementWatcher(snapshotPath);
        blobRetriever = new HollowFilesystemBlobRetriever(snapshotPath);
        logger.info("snapshot data file location: {}", snapshotPath.toString());
        consumer = new HollowConsumer.Builder<>()
                .withAnnouncementWatcher(announcementWatcher)
                .withBlobRetriever(blobRetriever)
                .withGeneratedAPIClass(MonitoringEventAPI.class)
                .build();
        monitoringEventAPI = consumer.getAPI(MonitoringEventAPI.class);
        consumer.triggerRefresh();
    }

    private static void sleep(long milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    private static Path getSnapshotFilePath() {
        String moduleDir = System.getProperty("user.dir");
        String snapshotPath = moduleDir + "/.hollow/snapshots";
        logger.info("snapshot data directory: {}", snapshotPath);

        Path path = Paths.get(snapshotPath);
        return path;
    }
}