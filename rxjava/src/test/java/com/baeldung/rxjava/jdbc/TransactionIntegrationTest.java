package com.baeldung.rxjava.jdbc;

import com.github.davidmoten.rx.jdbc.Database;
import org.junit.After;
import org.junit.Test;
import rx.Observable;

import static org.junit.Assert.assertEquals;

public class TransactionIntegrationTest {

    private Database db = Database.from(Connector.connectionProvider);

    @Test
    public void whenCommitTransaction_thenRecordUpdated() {
        Observable<Boolean> begin = db.beginTransaction();
        Observable<Integer> createStatement = db
          .update("CREATE TABLE IF NOT EXISTS EMPLOYEE(id int primary key, name varchar(255))")
          .dependsOn(begin)
          .count();
        Observable<Integer> insertStatement = db.update("INSERT INTO EMPLOYEE(id, name) VALUES(1, 'John')")
          .dependsOn(createStatement)
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
        db.update("DROP TABLE EMPLOYEE");
        Connector.connectionProvider.close();
    }
}
