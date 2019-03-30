package com.baeldung.rxjava.jdbc;

import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.Test;

import com.github.davidmoten.rx.jdbc.ConnectionProvider;
import com.github.davidmoten.rx.jdbc.Database;

import rx.Observable;

public class TransactionIntegrationTest {

    private Observable<Integer> createStatement = null;

    private ConnectionProvider connectionProvider = Connector.connectionProvider;
    private Database db = Database.from(connectionProvider);

    @Test
    public void whenCommitTransaction_thenRecordUpdated() {
        Observable<Boolean> begin = db.beginTransaction();
        Observable<Integer> createStatement = db
          .update("CREATE TABLE IF NOT EXISTS EMPLOYEE(id int primary key, name varchar(255))")
          .dependsOn(begin)
          .count();
        Observable<Integer> truncateStatement = db.update("TRUNCATE TABLE EMPLOYEE")
                .dependsOn(createStatement)
                .count();
        Observable<Integer> insertStatement = db.update("INSERT INTO EMPLOYEE(id, name) VALUES(1, 'John')")
          .dependsOn(truncateStatement)
          .count();
        Observable<Integer> updateStatement = db.update("UPDATE EMPLOYEE SET name = 'Tom' WHERE id = 1")
          .dependsOn(insertStatement)
          .count();
        Observable<Boolean> commit = db.commit(updateStatement);
        String name = db.select("select name from EMPLOYEE WHERE id = 1")
          .dependsOn(commit)
          .getAs(String.class)
          .toBlocking()
          .single();

        assertEquals("Tom", name);
    }

    @After
    public void close() {
        db.update("DROP TABLE EMPLOYEE")
          .dependsOn(createStatement);
        connectionProvider.close();
    }
}
