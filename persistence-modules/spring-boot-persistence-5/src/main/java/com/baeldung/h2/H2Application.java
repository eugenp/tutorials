package com.baeldung.h2;

import jakarta.annotation.PostConstruct;
import org.h2.tools.RunScript;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.io.ClassPathResource;

import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

@SpringBootApplication
public class H2Application {
    public static final Logger log = LoggerFactory.getLogger(H2Application.class);

    @Value("${spring.datasource.url}")
    private String url;
    @Value("${spring.datasource.username}")
    private String user;
    @Value("${spring.datasource.password}")
    private String password;

    public static void main(String[] args) {
        SpringApplication.run(H2Application.class, args);
    }


    @PostConstruct
    public void init() throws SQLException, IOException {
        Connection connection = DriverManager.getConnection(url, user, password);
        ResultSet rs = RunScript.execute(connection, new FileReader(new ClassPathResource("db/script.sql").getFile()));
        log.info("Reading Data from the employee table");
        while (rs.next()) {
            log.info("ID: {}, Name: {}", rs.getInt("id"), rs.getString("name"));
        }
    }
}