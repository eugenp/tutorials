package com.baeldung.listennotify;

import com.impossibl.postgres.api.jdbc.PGNotificationListener;
import org.junit.jupiter.api.Test;
import org.postgresql.PGNotification;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.time.Duration;
import java.util.HashSet;
import java.util.Set;

import static org.awaitility.Awaitility.await;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ListenNotifyLiveTest {
    private static final Logger LOG = LoggerFactory.getLogger(ListenNotifyLiveTest.class);

    private static final String POSTGRES_URL = "jdbc:postgresql://localhost:5432/postgres";
    private static final String PGJDBC_URL = "jdbc:pgsql://localhost:5432/postgres";
    private static final String USERNAME = "postgres";
    private static final String PASSWORD = "mysecretpassword";

    private void sendNotifications() throws SQLException{
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

            sendNotifications();

            var pgConnection = connection.unwrap(org.postgresql.PGConnection.class);
            Set<String> receivedNotifications = new HashSet<>();

            while (receivedNotifications.size() < 2) {
                PGNotification[] notifications = pgConnection.getNotifications(0);
                if (notifications != null) {
                    LOG.info("Received {} notifications", notifications.length);
                    for (PGNotification notification : notifications) {
                        LOG.info("Received notification: Channel='{}', Payload='{}', PID={}",
                                notification.getName(),
                                notification.getParameter(),
                                notification.getPID());
                        receivedNotifications.add(notification.getParameter());
                    }
                }
            }

            assertEquals(Set.of("Hello, NOTIFY!", "Hello, pg_notify!"), receivedNotifications);
        }
    }

    @Test
    void whenUsingPgsqlDriver_thenNotificationsAreReceivedViaListener() throws SQLException, InterruptedException {
        try (Connection connection = DriverManager.getConnection(PGJDBC_URL, USERNAME, PASSWORD)) {
            try (Statement statement = connection.createStatement()) {
                statement.execute("LISTEN my_channel");
            }

            var pgConnection = connection.unwrap(com.impossibl.postgres.api.jdbc.PGConnection.class);

            Listener pgNotificationListener = new Listener();
            pgConnection.addNotificationListener(pgNotificationListener);

            sendNotifications();

            await()
                .atMost(Duration.ofSeconds(5))
                .until(() -> pgNotificationListener.receivedNotifications.size() == 2);

            assertEquals(Set.of("Hello, NOTIFY!", "Hello, pg_notify!"), pgNotificationListener.receivedNotifications);

        }
    }

    @Test
    void whenUsingTriggers_thenNotificationsAreSent() throws SQLException {
        try (Connection connection = DriverManager.getConnection(POSTGRES_URL, USERNAME, PASSWORD)) {
            // First set up the database state
            try (Statement statement = connection.createStatement()) {
                statement.execute("CREATE TABLE IF NOT EXISTS listen_notify_trigger(id INT PRIMARY KEY)");
                statement.execute("TRUNCATE listen_notify_trigger");

                statement.execute("""
                        CREATE OR REPLACE FUNCTION notify_table_change() RETURNS TRIGGER AS $$ 
                            BEGIN 
                                PERFORM pg_notify('table_change', TG_TABLE_NAME); 
                                RETURN NEW; 
                            END; 
                        $$ LANGUAGE plpgsql;
                        """);

                statement.execute("""
                        CREATE OR REPLACE TRIGGER table_change 
                            AFTER INSERT OR UPDATE OR DELETE ON listen_notify_trigger
                            FOR EACH ROW EXECUTE PROCEDURE notify_table_change();
                        """);
            }

            try (Statement statement = connection.createStatement()) {
                statement.execute("LISTEN table_change");
            }

            try (Statement statement = connection.createStatement()) {
                statement.execute("INSERT INTO listen_notify_trigger(id) VALUES (1)");
            }

            var pgConnection = connection.unwrap(org.postgresql.PGConnection.class);
            Set<String> receivedNotifications = new HashSet<>();

            while (receivedNotifications.isEmpty()) {
                PGNotification[] notifications = pgConnection.getNotifications(0);
                if (notifications != null) {
                    LOG.info("Received {} notifications", notifications.length);
                    for (PGNotification notification : notifications) {
                        LOG.info("Received notification: Channel='{}', Payload='{}', PID={}",
                                notification.getName(),
                                notification.getParameter(),
                                notification.getPID());
                        receivedNotifications.add(notification.getName() + " - " + notification.getParameter());
                    }
                }
            }

            assertEquals(Set.of("table_change - listen_notify_trigger"), receivedNotifications);
        }
    }

    private static class Listener implements PGNotificationListener {
        Set<String> receivedNotifications = new HashSet<>();

        @Override
        public void notification(int processId, String channelName, String payload) {
            LOG.info("Received notification: Channel='{}', Payload='{}', PID={}",
                    channelName, payload, processId);
            receivedNotifications.add(payload);
        }
    }
}
