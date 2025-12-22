package com.baeldung.hollow;

import java.nio.file.Path;
import java.util.Collection;
import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.io.TempDir;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.baeldung.hollow.consumer.api.MonitoringEvent;
import com.baeldung.hollow.consumer.api.MonitoringEventAPI;

import com.baeldung.hollow.service.MonitoringDataService;
import com.netflix.hollow.api.consumer.HollowConsumer;
import com.netflix.hollow.api.consumer.HollowConsumer.AnnouncementWatcher;
import com.netflix.hollow.api.consumer.fs.HollowFilesystemAnnouncementWatcher;
import com.netflix.hollow.api.consumer.fs.HollowFilesystemBlobRetriever;
import com.netflix.hollow.api.producer.HollowProducer;
import com.netflix.hollow.api.producer.HollowProducer.Announcer;
import com.netflix.hollow.api.producer.HollowProducer.Publisher;
import com.netflix.hollow.api.producer.fs.HollowFilesystemAnnouncer;
import com.netflix.hollow.api.producer.fs.HollowFilesystemPublisher;
import com.netflix.hollow.core.write.objectmapper.HollowObjectMapper;

@TestInstance(Lifecycle.PER_CLASS)
public class HollowPublisherConsumerUnitTest {

    final Logger logger = LoggerFactory.getLogger(HollowPublisherConsumerUnitTest.class);

    @TempDir
    static Path snapshotDataDir;

    List<com.baeldung.hollow.model.MonitoringEvent> monitoringEventsForPublishing;

    static long datasetVersion;

    @BeforeAll
    void setup() {
        monitoringEventsForPublishing = new MonitoringDataService().retrieveEvents();
    }

    @Test()
    @Order(1)
    void whenPublisherPublishesEvents_thenSucess() {
        logger.info("publisher: snapshot data file location: {}", snapshotDataDir.toString());

        assertDoesNotThrow(() -> {
            datasetVersion = publishMonitoringEvent(monitoringEventsForPublishing);
            assertThat(datasetVersion).isGreaterThan(0);
        });
    }

    @Test
    @Order(2)
    void whenConsumerConsumesEvents_thenSuccess() {

        Collection<MonitoringEvent> fetchMonitoringEventsFromSnapshot = consumeMonitoringEvents();
        assertThat(fetchMonitoringEventsFromSnapshot.size()).isEqualTo(monitoringEventsForPublishing.size());
    }

    private Collection<MonitoringEvent> consumeMonitoringEvents() {
        AnnouncementWatcher announcementWatcher = new HollowFilesystemAnnouncementWatcher(snapshotDataDir);
        HollowFilesystemBlobRetriever blobRetriever = new HollowFilesystemBlobRetriever(snapshotDataDir);
        logger.info("consumer: snapshot data file location: {}", snapshotDataDir.toString());
        HollowConsumer consumer = new HollowConsumer.Builder<>()
            .withAnnouncementWatcher(announcementWatcher)
            .withBlobRetriever(blobRetriever)
            .withGeneratedAPIClass(MonitoringEventAPI.class)
            .build();
        consumer.triggerRefresh();

        MonitoringEventAPI monitoringEventAPI = consumer.getAPI(MonitoringEventAPI.class);

        assertThat(consumer.getCurrentVersionId()).isEqualTo(datasetVersion);

        return monitoringEventAPI.getAllMonitoringEvent();
    }

    long publishMonitoringEvent(List<com.baeldung.hollow.model.MonitoringEvent> events) {
        Announcer announcer = new HollowFilesystemAnnouncer(snapshotDataDir);
        Publisher publisher = new HollowFilesystemPublisher(snapshotDataDir);

        HollowProducer producer = HollowProducer.withPublisher(publisher)
            .withAnnouncer(announcer)
            .build();
        HollowObjectMapper mapper = new HollowObjectMapper(producer.getWriteEngine());

        events.forEach(mapper::add);

        long currDataSetVersion = producer.runCycle(task -> {
            events.forEach(task::add);
        });
        producer.getWriteEngine().prepareForNextCycle();
        return currDataSetVersion;
    }
}
