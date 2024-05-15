package com.baeldung.rethinkdb;

import com.rethinkdb.net.Result;
import org.junit.jupiter.api.Test;

import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static com.rethinkdb.RethinkDB.r;

/**
 * Some tests demonstrating streaming live changes to data.
 */
public class StreamingIntegrationLiveTest extends TestBase {
    @Test
    public void getLiveInserts() throws InterruptedException {
        ExecutorService executorService = Executors.newCachedThreadPool();

        r.db(DB_NAME).tableCreate(tableName).run(conn);

        r.db(DB_NAME).table(tableName).insert(r.hashMap().with("index", 0)).run(conn);

        executorService.submit(() -> {
            Result<Map> cursor = r.db(DB_NAME).table(tableName).changes().run(conn, Map.class);

            cursor.stream().forEach(record -> System.out.println("Record: " + record));
        });

        for (int i = 0; i < 10; ++i) {
            r.db(DB_NAME).table(tableName).insert(r.hashMap().with("index", i)).run(conn);
            TimeUnit.MILLISECONDS.sleep(100);
        }

        executorService.shutdownNow();
    }

    @Test
    public void getSomeLiveInserts() throws InterruptedException {
        ExecutorService executorService = Executors.newCachedThreadPool();

        r.db(DB_NAME).tableCreate(tableName).run(conn);

        r.db(DB_NAME).table(tableName).insert(r.hashMap().with("index", 0)).run(conn);

        executorService.submit(() -> {
            Result<Map> cursor = r.db(DB_NAME).table(tableName)
                .filter(r -> r.g("index").eq(5))
                .changes()
                .run(conn, Map.class);

            cursor.stream().forEach(record -> System.out.println("Record: " + record));
        });

        for (int i = 0; i < 10; ++i) {
            r.db(DB_NAME).table(tableName).insert(r.hashMap().with("index", i)).run(conn);
            TimeUnit.MILLISECONDS.sleep(100);
        }

        executorService.shutdownNow();
    }

    @Test
    public void getLiveUpdates() throws InterruptedException {
        ExecutorService executorService = Executors.newCachedThreadPool();

        r.db(DB_NAME).tableCreate(tableName).run(conn);

        r.db(DB_NAME).table(tableName).insert(r.hashMap().with("index", 0)).run(conn);

        executorService.submit(() -> {
            Result<Map> cursor = r.db(DB_NAME).table(tableName).changes().run(conn, Map.class);

            cursor.stream().forEach(record -> System.out.println("Record: " + record));
        });

        for (int i = 0; i < 10; ++i) {
            r.db(DB_NAME).table(tableName).update(r.hashMap().with("index", i)).run(conn);
            TimeUnit.MILLISECONDS.sleep(100);
        }

        executorService.shutdownNow();
    }

    @Test
    public void getLiveDeletes() throws InterruptedException {
        ExecutorService executorService = Executors.newCachedThreadPool();

        r.db(DB_NAME).tableCreate(tableName).run(conn);

        for (int i = 0; i < 10; ++i) {
            r.db(DB_NAME).table(tableName).insert(r.hashMap().with("index", i)).run(conn);
        }

        executorService.submit(() -> {
            Result<Map> cursor = r.db(DB_NAME).table(tableName).changes().run(conn, Map.class);

            cursor.stream().forEach(record -> System.out.println("Record: " + record));
        });

        r.db(DB_NAME).table(tableName)
            .filter(r -> r.g("index").eq(1))
            .delete()
            .run(conn);
        r.db(DB_NAME).table(tableName)
            .filter(r -> r.g("index").eq(3))
            .delete()
            .run(conn);
        r.db(DB_NAME).table(tableName)
            .filter(r -> r.g("index").eq(5))
            .delete()
            .run(conn);

        executorService.shutdownNow();
    }
}
