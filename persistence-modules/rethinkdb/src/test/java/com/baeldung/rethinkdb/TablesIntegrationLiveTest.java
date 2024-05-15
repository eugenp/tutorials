package com.baeldung.rethinkdb;

import com.rethinkdb.gen.exc.ReqlOpFailedError;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

import static com.rethinkdb.RethinkDB.r;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Some tests demonstrating working with tables.
 */
public class TablesIntegrationLiveTest extends TestBase {

    @Test
    public void createTable() {
        r.db(DB_NAME).tableCreate(tableName).run(conn);
    }

    @Test
    public void createTableTwice() {
        r.db(DB_NAME).tableCreate(tableName).run(conn);

        Assertions.assertThrows(ReqlOpFailedError.class, () -> {
            r.db(DB_NAME).tableCreate(tableName).run(conn);
        });
    }

    @Test
    public void listTables() {
        r.db(DB_NAME).tableCreate(tableName).run(conn);

        List<String> tables = r.db(DB_NAME).tableList().run(conn, List.class).first();

        assertTrue(tables.contains(tableName));
    }
}
