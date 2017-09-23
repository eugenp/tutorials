package com.baeldung.rxjava.jdbc;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.List;

import org.junit.After;
import org.junit.Test;

import com.github.davidmoten.rx.jdbc.ConnectionProvider;
import com.github.davidmoten.rx.jdbc.Database;

import rx.Observable;

public class BasicQueryTypesTest {

    ConnectionProvider connectionProvider = Connector.connectionProvider;
    Database db = Database.from(connectionProvider);

    Observable<Integer> create, insert1, insert2, insert3, update, delete = null;

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
        delete = db.update("DELETE FROM EMPLOYEE WHERE id = 2")
            .dependsOn(create)
            .count();
        List<String> names = db.select("select name from EMPLOYEE where id < ?")
            .parameter(3)
            .dependsOn(create)
            .dependsOn(insert1)
            .dependsOn(insert2)
            .dependsOn(insert3)
            .dependsOn(update)
            .dependsOn(delete)
            .getAs(String.class)
            .toList()
            .toBlocking()
            .single();

        assertEquals(Arrays.asList("Alan"), names);
    }

    @After
    public void close() {
        db.update("DROP TABLE EMPLOYEE")
            .dependsOn(create);
        connectionProvider.close();
    }
}
