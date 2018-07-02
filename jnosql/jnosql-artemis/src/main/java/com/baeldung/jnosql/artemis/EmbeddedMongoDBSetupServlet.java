package com.baeldung.jnosql.artemis;

import de.flapdoodle.embed.mongo.MongodExecutable;
import de.flapdoodle.embed.mongo.MongodProcess;
import de.flapdoodle.embed.mongo.MongodStarter;
import de.flapdoodle.embed.mongo.config.MongodConfigBuilder;
import de.flapdoodle.embed.mongo.config.Net;
import de.flapdoodle.embed.mongo.distribution.Version;
import de.flapdoodle.embed.process.runtime.Network;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import java.io.IOException;

@WebServlet(value = "/init", loadOnStartup = 1)
public class EmbeddedMongoDBSetupServlet extends HttpServlet {

    private static final String MONGODB_HOST = "localhost";
    private static final int MONGODB_PORT = 27019;

    private static final MongodStarter starter = MongodStarter.getDefaultInstance();
    private static MongodExecutable _mongodExe;
    private static MongodProcess _mongod;

    @Override
    public void init() throws ServletException {
        super.init();
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

    @Override
    public void destroy() {
        super.destroy();
        System.out.println("Stopping Embedded MongoDB");
        _mongod.stop();
        _mongodExe.stop();
        System.out.println("Embedded MongoDB stopped !");
    }
}
