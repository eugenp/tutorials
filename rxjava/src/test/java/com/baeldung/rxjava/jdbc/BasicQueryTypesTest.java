package com.baeldung.rxjava.jdbc;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.github.davidmoten.rx.jdbc.ConnectionProvider;
import com.github.davidmoten.rx.jdbc.ConnectionProviderFromUrl;
import com.github.davidmoten.rx.jdbc.Database;

import rx.Observable;

public class BasicQueryTypesTest {

    private String DB_CONNECTION = Connector.DB_CONNECTION;
    private String DB_USER = Connector.DB_USER;
    private String DB_PASSWORD = Connector.DB_PASSWORD;

    ConnectionProvider cp = null;
    Database db = null;

    Observable<Integer> create, insert1, insert2, insert3, insert4, insert5, update, delete = null;

    @Before
    public void setup() {
        cp = new ConnectionProviderFromUrl(DB_CONNECTION, DB_USER, DB_PASSWORD);
        db = Database.from(cp);
    }

    @Test
    public void whenCreateTableAndInsertRecords_thenCorrect() {
        create = db.update("CREATE TABLE IF NOT EXISTS EMPLOYEE(id int primary key, name varchar(255))")
            .count();
        insert1 = db.update("INSERT INTO EMPLOYEE(id, name) VALUES(1, 'John')")
            .dependsOn(create)
            .count();
        update = db.update("UPDATE EMPLOYEE SET name = 'Alan' WHERE id = 1")
            .dependsOn(create)
            .count();
        insert2 = db.update("INSERT INTO EMPLOYEE(id, name) VALUES(2, 'Sarah')")
            .dependsOn(create)
            .count();
        insert3 = db.update("INSERT INTO EMPLOYEE(id, name) VALUES(3, 'Mike')")
            .dependsOn(create)
            .count();
        insert4 = db.update("INSERT INTO EMPLOYEE(id, name) VALUES(4, 'Jennifer')")
            .dependsOn(create)
            .count();
        insert5 = db.update("INSERT INTO EMPLOYEE(id, name) VALUES(5, 'George')")
            .dependsOn(create)
            .count();
        delete = db.update("DELETE FROM EMPLOYEE WHERE id = 5")
            .dependsOn(create)
            .count();
        List<String> names = db.select("select name from EMPLOYEE where id > ?")
            .parameter(2)
            .dependsOn(create)
            .dependsOn(insert1)
            .dependsOn(insert2)
            .dependsOn(insert3)
            .dependsOn(insert4)
            .dependsOn(insert5)
            .dependsOn(update)
            .dependsOn(delete)
            .getAs(String.class)
            .toList()
            .toBlocking()
            .single();
        assertEquals(Arrays.asList("Mike", "Jennifer"), names);
    }

    @After
    public void close() {
        db.update("DROP TABLE EMPLOYEE")
            .dependsOn(create);
        cp.close();
    }
}
