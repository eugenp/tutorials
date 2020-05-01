package com.baeldung.rxjava.jdbc;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.List;

import org.junit.After;
import org.junit.Test;

import com.github.davidmoten.rx.jdbc.ConnectionProvider;
import com.github.davidmoten.rx.jdbc.Database;

import rx.Observable;

public class BasicQueryTypesIntegrationTest {

    private ConnectionProvider connectionProvider = Connector.connectionProvider;
    private Database db = Database.from(connectionProvider);

    private Observable<Integer> create;

    @Test
    public void whenCreateTableAndInsertRecords_thenCorrect() {
        create = db.update("CREATE TABLE IF NOT EXISTS EMPLOYEE_TABLE(id int primary key, name varchar(255))")
          .count();
        Observable<Integer> insert1 = db.update("INSERT INTO EMPLOYEE_TABLE(id, name) VALUES(1, 'John')")
          .dependsOn(create)
          .count();
        Observable<Integer> update = db.update("UPDATE EMPLOYEE_TABLE SET name = 'Alan' WHERE id = 1")
          .dependsOn(create)
          .count();
        Observable<Integer> insert2 = db.update("INSERT INTO EMPLOYEE_TABLE(id, name) VALUES(2, 'Sarah')")
          .dependsOn(create)
          .count();
        Observable<Integer> insert3 = db.update("INSERT INTO EMPLOYEE_TABLE(id, name) VALUES(3, 'Mike')")
          .dependsOn(create)
          .count();
        Observable<Integer> delete = db.update("DELETE FROM EMPLOYEE_TABLE WHERE id = 2")
          .dependsOn(create)
          .count();
        List<String> names = db.select("select name from EMPLOYEE_TABLE where id < ?")
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
        db.update("DROP TABLE EMPLOYEE_TABLE")
          .dependsOn(create);
        connectionProvider.close();
    }
}
