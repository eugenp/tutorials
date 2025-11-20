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

public class MonitoringEventConsumer {
    private final static Logger logger = LoggerFactory.getLogger(MonitoringEventConsumer.class);

    public static void main(String[] args) {
        HollowFilesystemAnnouncementWatcher announcementWatcher = new HollowFilesystemAnnouncementWatcher(getSnapshotFilePath());
        HollowFilesystemBlobRetriever blobRetriever = new HollowFilesystemBlobRetriever(getSnapshotFilePath());

        HollowConsumer consumer = new HollowConsumer.Builder()
            .withAnnouncementWatcher(announcementWatcher)
            .withBlobRetriever(blobRetriever)
            .withGeneratedAPIClass(MonitoringEventAPI.class)
            .build();

        while (true) {
            consumer.triggerRefresh();
/*
            HollowReadStateEngine readEngine = consumer.getStateEngine();
            MonitoringEventAPI monitoringEventAPI = new MonitoringEventAPI(readEngine);
*/
            for(MonitoringEvent event : consumer.getAPI(MonitoringEventAPI.class).getAllMonitoringEvent()) {
                logger.info("Message received from the monitoring tool - \n Event ID: {}, Name: {}, Type: {}, Status: {}, Device ID: {}, Created At: {}",
                    event.getEventId(),
                    event.getEventName(),
                    event.getEventType(),
                    event.getStatus(),
                    event.getDeviceId(),
                    event.getCreationDate()
                );
            }
            logger.info("========================================");
            logger.info("MonitoringEventConsumer.main() completed a refresh cycle");
            logger.info("========================================");
            try {
                Thread.sleep(60000); // Sleep for 1 minute before the next refresh
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }
    }

    private static Path getSnapshotFilePath() {
        String path = MonitoringEventConsumer.class.getClassLoader().getResource("snapshot.bin").getPath();
        return Paths.get(path);
    }
}
