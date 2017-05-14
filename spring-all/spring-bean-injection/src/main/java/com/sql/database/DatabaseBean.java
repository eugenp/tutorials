package com.sql.database;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import com.sql.security.MySecureBean;

@Component
@PropertySource(value = "classpath:myProps.properties")
public class DatabaseBean {

    private String databasePath;
    private String port;
    private String login;
    private MySecureBean password;

    public DatabaseBean(@Value("${database.address}") String databasePath, 
        @Value("${database.port}") String port, 
        @Value("${database.login}") String login, MySecureBean password) {

        this.databasePath = databasePath;
        this.port = port;
        this.login = login;
        this.password = password;
    }

    public String getDatabasePath() {
        return databasePath;
    }

    public String getPort() {
        return port;
    }

    public String getLogin() {
        return login;
    }

    public MySecureBean getPassword() {
        return password;
    }
}
