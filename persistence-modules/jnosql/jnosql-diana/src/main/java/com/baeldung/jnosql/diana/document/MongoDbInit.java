package com.baeldung.jnosql.diana.document;

import de.flapdoodle.embed.mongo.MongodExecutable;
import de.flapdoodle.embed.mongo.MongodProcess;
import de.flapdoodle.embed.mongo.MongodStarter;
import de.flapdoodle.embed.mongo.config.MongodConfigBuilder;
import de.flapdoodle.embed.mongo.config.Net;
import de.flapdoodle.embed.mongo.distribution.Version;
import de.flapdoodle.embed.process.runtime.Network;

import java.io.IOException;

public abstract class MongoDbInit {

    private static final MongodStarter starter = MongodStarter.getDefaultInstance();
    private static MongodExecutable _mongodExe;
    private static MongodProcess _mongod;

    public static void startMongoDb() throws IOException {
        _mongodExe = starter.prepare(new MongodConfigBuilder()
                .version(Version.Main.DEVELOPMENT)
                .net(new Net("localhost", 27017, Network.localhostIsIPv6()))
                .build());
        _mongod = _mongodExe.start();
    }

    public static void stopMongoDb(){
        _mongod.stop();
        _mongodExe.stop();
    }

}