package com.baeldung.read_only_transactions.mysql.dao;

import com.baeldung.read_only_transactions.utils.ExecutorUtils;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.SplittableRandom;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

public class MyRepoJdbc {

    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) { throw new RuntimeException(e); }
    }

    private final HikariDataSource ds;
    private final SplittableRandom random = new SplittableRandom();

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
            AtomicLong count = new AtomicLong(0);
            execQuery(() -> runSql(count, autoCommit, readOnly));
            return count.get();
        } finally {
            ds.close();
        }
    }

    private void execQuery(Runnable runnable) {

        ExecutorService executor = ExecutorUtils.createExecutor(10, 10);
        ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();

        scheduler.schedule(executor::shutdownNow, 5L, TimeUnit.SECONDS);
        scheduler.shutdown();

        while (true) {
            if (executor.isShutdown()) {
                break;
            }

            executor.execute(runnable);
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

            statement.setLong(1, 1L+random.nextLong(0,  100000));
            ResultSet resultSet = statement.executeQuery();

            if (autoCommit != null && !autoCommit)
                connect.commit();

            count.incrementAndGet();
            resultSet.close();
        } catch (Exception ignored) {}
    }
}