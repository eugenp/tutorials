package com.baeldung.solon.persistence;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import javax.sql.DataSource;

import org.noear.solon.annotation.Component;
import org.noear.solon.annotation.Init;
import org.noear.solon.annotation.Inject;
import org.noear.solon.core.util.ResourceUtil;

@Component
public class DatabaseInitializer {

    @Inject("tasks")
    private DataSource dataSource;

    @Init
    public void initialize() throws SQLException, IOException {
        String schema = ResourceUtil.getResourceAsString("schema.sql");
        try (Connection connection = dataSource.getConnection(); Statement statement = connection.createStatement()) {
            statement.execute(schema);
        }
    }
}
