package com.baeldung.datastax.cassandra;

import com.baeldung.cassandra.batch.repository.ProductRepository;
import com.baeldung.datastax.cassandra.domain.Video;
import com.baeldung.datastax.cassandra.repository.KeyspaceRepository;
import com.baeldung.datastax.cassandra.repository.VideoRepository;
import com.datastax.oss.driver.api.core.CqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

public class Application {

    private static final Logger LOG = LoggerFactory.getLogger(Application.class);

    public static void main(String[] args) {
        new Application().run();
    }

    public void run() {
        CassandraConnector connector = new CassandraConnector();
        connector.connect("127.0.0.1", 9042, "datacenter1");
        CqlSession session = connector.getSession();

        KeyspaceRepository keyspaceRepository = new KeyspaceRepository(session);

        keyspaceRepository.createKeyspace("testKeyspace", 1);
        keyspaceRepository.useKeyspace("testKeyspace");

        VideoRepository videoRepository = new VideoRepository(session);
        ProductRepository productRepository = new ProductRepository(session);

        videoRepository.createTable();

        productRepository.createProductTableByName("testKeyspace");
        videoRepository.insertVideo(new Video("Video Title 1", Instant.now()));
        videoRepository.insertVideo(new Video("Video Title 2",
            Instant.now().minus(1, ChronoUnit.DAYS)));

        List<Video> videos = videoRepository.selectAll();

        videos.forEach(x -> LOG.info(x.toString()));

        connector.close();
    }
}
