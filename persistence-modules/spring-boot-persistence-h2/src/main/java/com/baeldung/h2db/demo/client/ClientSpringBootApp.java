package com.baeldung.h2db.demo.client;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import jakarta.annotation.PostConstruct;

@SpringBootApplication
@ComponentScan("com.baeldung.h2db.demo.client")
public class ClientSpringBootApp {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public static void main(String[] args) {
        System.setProperty("spring.datasource.url","jdbc:h2:tcp://localhost:9091/mem:mydb");
        SpringApplication.run(ClientSpringBootApp.class, args);
    }

    @PostConstruct
    private void initDb() {
        System.out.println("****** Inserting more sample data in the table: Employees ******");
        String sqlStatements[] = {
            "insert into employees(first_name, last_name) values('Donald','Trump')",
            "insert into employees(first_name, last_name) values('Barack','Obama')"
        };

        Arrays.asList(sqlStatements).stream().forEach(sql -> {
            System.out.println(sql);
            jdbcTemplate.execute(sql);
        });

        System.out.println(String.format("****** Fetching from table: %s ******", "Employees"));
        jdbcTemplate.query("select id,first_name,last_name from employees",
            new RowMapper<Object>() {
                @Override
                public Object mapRow(ResultSet rs, int i) throws SQLException {
                    System.out.println(String.format("id:%s,first_name:%s,last_name:%s",
                        rs.getString("id"),
                        rs.getString("first_name"),
                        rs.getString("last_name")));
                    return null;
                }
            });
    }
}
