package com.baeldung.p6spy.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("jdbc")
public class JDBCController {

    @Autowired
    private DataSource dataSource;

    @RequestMapping("/commit")
    public List<Map<String, String>> select() {
        List<Map<String, String>> results = new ArrayList<>();
        try {
            Connection connection = dataSource.getConnection();
            Statement statement = connection.createStatement();
            statement.executeQuery("SELECT * FROM student");
            connection.commit();
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
        return results;
    }

    @RequestMapping("/rollback")
    public List<Map<String, String>> rollback() {
        List<Map<String, String>> results = new ArrayList<>();
        try (Connection connection = dataSource.getConnection()) {
            connection.rollback();
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
        return results;
    }

    @RequestMapping("/query-error")
    public void error() {
        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement()) {
            statement.execute("SELECT UNDEFINED()");
        } catch (Exception ignored) {
        }
    }
}
