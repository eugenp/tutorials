package com.baeldung.h2;

import org.h2.tools.RunScript;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.io.ClassPathResource;

import javax.annotation.PostConstruct;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;


@SpringBootApplication
public class H2Application {

    @Value("${spring.datasource.url}")
    private String url;
    @Value("${spring.datasource.username}")
    private String user;
    @Value("${spring.datasource.password}")
    private String password;

    public static void main(String[] args) {
        System.setProperty("spring.config.name", "h2Application");
        SpringApplication.run(H2Application.class, args);
    }


    @PostConstruct
    public void init() throws SQLException, IOException {
        Connection connection = DriverManager.getConnection(url, user, password);
        ResultSet rs = RunScript.execute(connection, new FileReader(new ClassPathResource("db/script.sql").getFile()));
        System.out.println("Data from the employee table:");
        while (rs.next()) {
            System.out.println(rs.getString("name"));
        }
    }
}