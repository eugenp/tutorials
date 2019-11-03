package com.baeldung.jnosql.artemis;

import de.flapdoodle.embed.mongo.MongodExecutable;
import de.flapdoodle.embed.mongo.MongodProcess;
import de.flapdoodle.embed.mongo.MongodStarter;
import de.flapdoodle.embed.mongo.config.MongodConfigBuilder;
import de.flapdoodle.embed.mongo.config.Net;
import de.flapdoodle.embed.mongo.distribution.Version;
import de.flapdoodle.embed.process.runtime.Network;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.Destroyed;
import javax.enterprise.context.Initialized;
import javax.enterprise.event.Observes;
import java.io.IOException;

@ApplicationScoped
public class EmbeddedMongoDBSetup {

    private static final String MONGODB_HOST = "localhost";
    private static final int MONGODB_PORT = 27019;

    private static final MongodStarter starter = MongodStarter.getDefaultInstance();
    private static MongodExecutable _mongodExe;
    private static MongodProcess _mongod;

    public void init(@Observes @Initialized(ApplicationScoped.class) Object init) {
        try {
            System.out.println("Starting Embedded MongoDB");
            initdb();
            System.out.println("Embedded MongoDB started");
        } catch (IOException e) {
            System.out.println("Embedded MongoDB starting error !!");
            e.printStackTrace();
        }
    }

    private void initdb() throws IOException {
        _mongodExe = starter.prepare(new MongodConfigBuilder()
                .version(Version.Main.DEVELOPMENT)
                .net(new Net(MONGODB_HOST, MONGODB_PORT, Network.localhostIsIPv6()))
                .build());
        _mongod = _mongodExe.start();
    }

    public void destroy(@Observes @Destroyed(ApplicationScoped.class) Object init) {
        System.out.println("Stopping Embedded MongoDB");
        _mongod.stop();
        _mongodExe.stop();
        System.out.println("Embedded MongoDB stopped !");
    }
}
