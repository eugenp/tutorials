package com.baeldung.data.mariadb4j;

import ch.vorburger.mariadb4j.DB;
import ch.vorburger.mariadb4j.DBConfigurationBuilder;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.DriverManager;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class JavaLiveTest {
    @Test
    void whenStartingADatabase_thenTheDatabaseIsUsable() throws Exception {
        DB db = DB.newEmbeddedDB(0);
        try {
            db.start();

            assertEquals("jdbc:mariadb://localhost:" + db.getConfiguration().getPort() + "/testing",
                db.getConfiguration().getURL("testing"));
        } finally {
            db.stop();
        }
    }

    @Test
    void whenStartingADatabaseWithConfiguration_thenTheDatabaseIsUsable(@TempDir Path tempDir) throws Exception {
        DBConfigurationBuilder config = DBConfigurationBuilder.newBuilder();
        config.setPort(13306);
        config.setDataDir(Path.of(tempDir.toString(), "data").toString());
        config.setBaseDir(Path.of(tempDir.toString(), "base").toString());

        DB db = DB.newEmbeddedDB(config.build());
        try {
            db.start();

            db.createDB("testing", "testuser", "testpass");
            assertEquals("jdbc:mariadb://localhost:13306/testing", db.getConfiguration().getURL("testing"));
        } finally {
            db.stop();
        }
    }

    @Test
    void whenRunningCommandsAgainstADatabase_thenTheCommandSucceeds() throws Exception {
        DB db = DB.newEmbeddedDB(0);
        try {
            db.start();
            db.createDB("testing", "testuser", "testpass");
            db.run("show databases");
        } finally {
            db.stop();
        }
    }

    @Test
    void whenRunningAScriptAgainstADatabase_thenTheCommandSucceeds() throws Exception {
        DB db = DB.newEmbeddedDB(0);
        try {
            db.start();
            db.createDB("testing", "testuser", "testpass");
            db.source("com/baeldung/data/mariadb4j/source.sql", "testuser", "testpass", "testing");
        } finally {
            db.stop();
        }
    }

    @Test
    void whenRunningADatabase_thenICanConnectWithJDBC() throws Exception {
        DB db = DB.newEmbeddedDB(0);
        try {
            db.start();
            db.createDB("testing", "testuser", "testpass");

            try (Connection conn = DriverManager.getConnection(db.getConfiguration().getURL("testing"), "testuser", "testpass")) {
                assertFalse(conn.isClosed());
            }
        } finally {
            db.stop();
        }
    }
}
