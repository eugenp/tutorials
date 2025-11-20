package com.baeldung.hollow.producer;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import com.baeldung.hollow.model.MonitoringEvent;
import com.baeldung.hollow.service.MonitoringDataService;
import com.netflix.hollow.api.producer.HollowProducer;
import com.netflix.hollow.api.producer.fs.HollowFilesystemAnnouncer;
import com.netflix.hollow.api.producer.fs.HollowFilesystemPublisher;
import com.netflix.hollow.core.write.objectmapper.HollowObjectMapper;

public class MonitoringEventProducer {

    public static void main(String[] args) {
        Path snapshotPath = getSnapshotFilePath();
        HollowProducer.Publisher publisher = new HollowFilesystemPublisher(snapshotPath);
        HollowProducer.Announcer announcer = new HollowFilesystemAnnouncer(snapshotPath);
        HollowProducer producer = HollowProducer.withPublisher(publisher)
            .withAnnouncer(announcer)
            .build();
        MonitoringDataService dataService = new MonitoringDataService();
        HollowObjectMapper mapper = new HollowObjectMapper(producer.getWriteEngine());

        while(true) {
            List<MonitoringEvent> events = dataService.retrieveEvents();
            events.forEach(mapper::add);
            producer.runCycle(task -> {
                events.forEach(task::add);
            });
            producer.getWriteEngine().prepareForNextCycle();
            try {
                Thread.sleep(60000); // Sleep for 1 minute before producing the next set of events
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }
    }

    private static Path getSnapshotFilePath() {
        String path = MonitoringEventProducer.class.getClassLoader().getResource("snapshot.bin").getPath();
        return Paths.get(path);
    }

}
