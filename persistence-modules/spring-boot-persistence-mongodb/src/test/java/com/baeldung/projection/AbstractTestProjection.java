package com.baeldung.projection;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.springframework.data.mongodb.core.MongoTemplate;

import com.baeldung.projection.model.InStock;
import com.baeldung.projection.model.Inventory;
import com.baeldung.projection.model.Size;
import com.mongodb.client.MongoClients;

import de.flapdoodle.embed.mongo.MongodExecutable;
import de.flapdoodle.embed.mongo.MongodStarter;
import de.flapdoodle.embed.mongo.config.ImmutableMongodConfig;
import de.flapdoodle.embed.mongo.config.MongodConfig;
import de.flapdoodle.embed.mongo.config.Net;
import de.flapdoodle.embed.mongo.distribution.Version;
import de.flapdoodle.embed.process.runtime.Network;

public class AbstractTestProjection {

    private static final String CONNECTION_STRING = "mongodb://%s:%d";

    private MongodExecutable mongodExecutable;
    protected MongoTemplate mongoTemplate;

    @AfterEach
    void clean() {
        mongodExecutable.stop();
    }

    void setUp() throws IOException {
        String ip = "localhost";
        int port = 27017;

        ImmutableMongodConfig mongodbConfig = MongodConfig.builder()
          .version(Version.Main.PRODUCTION)
          .net(new Net(ip, port, Network.localhostIsIPv6()))
          .build();

        MongodStarter starter = MongodStarter.getDefaultInstance();
        mongodExecutable = starter.prepare(mongodbConfig);
        mongodExecutable.start();
        mongoTemplate = new MongoTemplate(MongoClients.create(String.format(CONNECTION_STRING, ip, port)), "test");
    }

    public List<Inventory> getInventories() {

        Inventory journal = new Inventory();
        journal.setItem("journal");
        journal.setStatus("A");
        journal.setSize(new Size(14.0, 21.0, "cm"));
        journal.setInStock(Collections.singletonList(new InStock("A", 5)));

        Inventory notebook = new Inventory();
        notebook.setItem("notebook");
        notebook.setStatus("A");
        notebook.setSize(new Size(8.5, 11.0, "in"));
        notebook.setInStock(Collections.singletonList(new InStock("C", 5)));

        Inventory paper = new Inventory();
        paper.setItem("paper");
        paper.setStatus("D");
        paper.setSize(new Size(8.5, 11.0, "in"));
        paper.setInStock(Collections.singletonList(new InStock("A", 60)));

        return Arrays.asList(journal, notebook, paper);
    }
}
