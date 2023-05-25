package com.baeldung.readonlytransactions.mysql.dao;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.SplittableRandom;
import java.util.concurrent.atomic.AtomicLong;

public class MyRepoJdbc extends BaseRepo {

    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private HikariDataSource ds;
    private SplittableRandom random = new SplittableRandom();

    public MyRepoJdbc(boolean readOnly, boolean autocommit) {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl("jdbc:mysql://localhost/baeldung?useUnicode=true&characterEncoding=UTF-8");
        config.setUsername("baeldung");
        config.setPassword("baeldung");
        config.setReadOnly(readOnly);
        config.setAutoCommit(autocommit);
        ds = new HikariDataSource(config);
    }

    private Connection getConnection() throws SQLException {
        return ds.getConnection();
    }

    public long runQuery(Boolean autoCommit, Boolean readOnly) {
        try {
            return execQuery(count -> runSql(count, autoCommit, readOnly));
        } finally {
            ds.close();
        }
    }

    private void runSql(AtomicLong count, Boolean autoCommit, Boolean readOnly) {
        if (Thread.interrupted()) {
            return;
        }

        try (Connection connect = getConnection(); PreparedStatement statement = connect.prepareStatement("select * from transactions where id = ?")) {
            if (autoCommit != null)
                connect.setAutoCommit(autoCommit);

            if (readOnly != null)
                connect.setReadOnly(readOnly);

            statement.setLong(1, 1L + random.nextLong(0, 100000));
            ResultSet resultSet = statement.executeQuery();

            if (autoCommit != null && !autoCommit)
                connect.commit();

            count.incrementAndGet();
            resultSet.close();
        } catch (Exception ignored) {
        }
    }
}