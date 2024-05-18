package com.baeldung.libraries.ebean.app;

import java.util.Properties;

import io.ebean.Database;
import io.ebean.DatabaseFactory;
import io.ebean.config.DatabaseConfig;

public class App2 {

    public static void main(String[] args) {
        DatabaseConfig cfg = new DatabaseConfig();
        cfg.setDefaultServer(true);
        Properties properties = new Properties();
        properties.put("ebean.db.ddl.generate", "true");
        properties.put("ebean.db.ddl.run", "true");
        properties.put("datasource.db.username", "sa");
        properties.put("datasource.db.password", "");
        properties.put("datasource.db.databaseUrl", "jdbc:h2:mem:app2");
        properties.put("datasource.db.databaseDriver", "org.h2.Driver");
        cfg.loadFromProperties(properties);
        Database server = DatabaseFactory.create(cfg);
    }
}
