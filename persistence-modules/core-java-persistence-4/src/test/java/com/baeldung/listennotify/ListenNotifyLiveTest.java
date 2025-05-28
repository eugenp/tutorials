package com.baeldung.listennotify;

import com.impossibl.postgres.api.jdbc.PGNotificationListener;
import org.junit.jupiter.api.Test;
import org.postgresql.PGNotification;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;

public class ListenNotifyLiveTest {
    private static final Logger LOG = LoggerFactory.getLogger(ListenNotifyLiveTest.class);

    private static final String POSTGRES_URL = "jdbc:postgresql://localhost:5432/postgres";
    private static final String PGJDBC_URL = "jdbc:pgsql://localhost:5432/postgres";
    private static final String USERNAME = "postgres";
    private static final String PASSWORD = "mysecretpassword";

    @Test
    void whenTriggeringNotifications_thenNotificationsAreSent() throws SQLException{
        try (Connection connection = DriverManager.getConnection(POSTGRES_URL, USERNAME, PASSWORD)) {
            try (Statement statement = connection.createStatement()) {
                statement.execute("NOTIFY my_channel, 'Hello, NOTIFY!'");
            }

            try (PreparedStatement statement = connection.prepareStatement("SELECT pg_notify(?, ?)")) {
                statement.setString(1, "my_channel");
                statement.setString(2, "Hello, pg_notify!");
                statement.execute();
            }
        }
    }

    @Test
    void whenUsingPostgresqlDriver_thenNotificationsAreReceived() throws SQLException, InterruptedException {
        try (Connection connection = DriverManager.getConnection(POSTGRES_URL, USERNAME, PASSWORD)) {
            try (Statement statement = connection.createStatement()) {
                statement.execute("LISTEN my_channel");
            }

            var pgConnection = connection.unwrap(org.postgresql.PGConnection.class);
            while (!Thread.currentThread().isInterrupted()) {
                PGNotification[] notifications = pgConnection.getNotifications();
                if (notifications != null) {
                    LOG.info("Received {} notifications", notifications.length);
                    for (PGNotification notification : notifications) {
                        LOG.info("Received notification: Channel='{}', Payload='{}', PID={}",
                                notification.getName(),
                                notification.getParameter(),
                                notification.getPID());
                    }
                }
                
                Thread.sleep(100);
            }
        }
    }

    @Test
    void whenUsingPgsqlDriver_thenNotificationsAreReceivedViaListener() throws SQLException, InterruptedException {
        try (Connection connection = DriverManager.getConnection(PGJDBC_URL, USERNAME, PASSWORD)) {
            try (Statement statement = connection.createStatement()) {
                statement.execute("LISTEN my_channel");
            }

            var pgConnection = connection.unwrap(com.impossibl.postgres.api.jdbc.PGConnection.class);
            pgConnection.addNotificationListener(new Listener());

            Thread.sleep(10000);
        }
    }

    private static class Listener implements PGNotificationListener {
        @Override
        public void notification(int processId, String channelName, String payload) {
            LOG.info("Received notification: Channel='{}', Payload='{}', PID={}",
                    channelName, payload, processId);
        }
    }
}
