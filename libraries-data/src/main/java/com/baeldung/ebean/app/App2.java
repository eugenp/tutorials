package com.baeldung.ebean.app;

import java.util.Properties;

import io.ebean.EbeanServer;
import io.ebean.EbeanServerFactory;
import io.ebean.config.ServerConfig;

public class App2 {

    public static void main(String[] args) {
        ServerConfig cfg = new ServerConfig();
        cfg.setDefaultServer(true);
        Properties properties = new Properties();
        properties.put("ebean.db.ddl.generate", "true");
        properties.put("ebean.db.ddl.run", "true");
        properties.put("datasource.db.username", "sa");
        properties.put("datasource.db.password", "");
        properties.put("datasource.db.databaseUrl", "jdbc:h2:mem:app2");
        properties.put("datasource.db.databaseDriver", "org.h2.Driver");
        cfg.loadFromProperties(properties);
        EbeanServer server = EbeanServerFactory.create(cfg);

    }

}
