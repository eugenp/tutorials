package com.baeldung.rethinkdb;

import com.rethinkdb.net.Connection;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

import java.util.UUID;

import static com.rethinkdb.RethinkDB.r;

/**
 * Base class for RethinkDB tests.
 */
public class TestBase {
    /** The database name to work with */
    protected static final String DB_NAME = "test";

    /** A randomly generated table name so they never collide */
    protected final String tableName = UUID.randomUUID().toString().replaceAll("-","");

    /** A database connection */
    protected Connection conn;

    /**
     * Connect to the database for each test
     */
    @BeforeEach
    public void connect() {
        conn = r.connection()
            .hostname("localhost")
            .port(28015)
            .connect();
    }

    /**
     * Disconnect from the database after each test
     */
    @AfterEach
    public void disconnect() {
        if (this.conn != null) {
            conn.close();
        }
    }
}
