package com.baeldung.configurationproperties;

import org.springframework.core.env.Environment;
import org.springframework.beans.factory.annotation.*;
import org.springframework.context.annotation.*;

@Configuration
public class DatabaseConfig {

    @Autowired private Environment env;

    @Bean(name="dataSource")
    public Database dataSource() {

        Database dataSource = new Database();
        dataSource.setUrl(env.getProperty("jdbc.url"));
        dataSource.setUsername(env.getProperty("database.username"));
        dataSource.setPassword(env.getProperty("database.password"));

        return dataSource;
    }

}