package com.baeldung.rxjava.jdbc;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.github.davidmoten.rx.jdbc.ConnectionProvider;
import com.github.davidmoten.rx.jdbc.ConnectionProviderFromUrl;
import com.github.davidmoten.rx.jdbc.Database;

import rx.Observable;

public class TransactionTest {

    private String DB_CONNECTION = Connector.DB_CONNECTION;
    private String DB_USER = Connector.DB_USER;
    private String DB_PASSWORD = Connector.DB_PASSWORD;

    Observable<Boolean> begin, commit = null;
    Observable<Integer> createStatement, insertStatement, updateStatement = null;

    ConnectionProvider cp = new ConnectionProviderFromUrl(DB_CONNECTION, DB_USER, DB_PASSWORD);
    Database db = Database.from(cp);

    @Test
    public void whenCommitTransaction_thenRecordUpdated() {
        begin = db.beginTransaction();
        createStatement = db.update("CREATE TABLE IF NOT EXISTS EMPLOYEE(id int primary key, name varchar(255))")
            .dependsOn(begin)
            .count();
        insertStatement = db.update("INSERT INTO EMPLOYEE(id, name) VALUES(1, 'John')")
            .dependsOn(createStatement)
            .count();
        updateStatement = db.update("UPDATE EMPLOYEE SET name = 'Tom' WHERE id = 1")
            .dependsOn(insertStatement)
            .count();
        commit = db.commit(updateStatement);
        String name = db.select("select name from EMPLOYEE WHERE id = 1")
            .dependsOn(commit)
            .getAs(String.class)
            .toBlocking()
            .single();

        assertEquals("Tom", name);
    }
}
