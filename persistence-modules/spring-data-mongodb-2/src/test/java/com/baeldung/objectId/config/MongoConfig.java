package com.baeldung.objectId.config;

import org.bson.UuidRepresentation;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.util.SocketUtils;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;

import de.flapdoodle.embed.mongo.MongodExecutable;
import de.flapdoodle.embed.mongo.MongodStarter;
import de.flapdoodle.embed.mongo.config.ImmutableMongodConfig;
import de.flapdoodle.embed.mongo.config.MongodConfig;
import de.flapdoodle.embed.mongo.config.Net;
import de.flapdoodle.embed.mongo.distribution.Version;
import de.flapdoodle.embed.process.runtime.Network;

@Configuration
public class MongoConfig {
    private static final String CONNECTION_STRING = "mongodb://%s:%d";
    private static final String HOST = "localhost";

    @Bean
    public MongoTemplate mongoTemplate() throws Exception {
        int randomPort = SocketUtils.findAvailableTcpPort();

        ImmutableMongodConfig mongoDbConfig = MongodConfig.builder()
          .version(Version.Main.PRODUCTION)
          .net(new Net(HOST, randomPort, Network.localhostIsIPv6()))
          .build();

        MongodStarter starter = MongodStarter.getDefaultInstance();
        MongodExecutable mongodExecutable = starter.prepare(mongoDbConfig);
        mongodExecutable.start();

        return new MongoTemplate(MongoClients.create(String.format(CONNECTION_STRING, HOST, randomPort)), "mongo_auth");
    }
}
