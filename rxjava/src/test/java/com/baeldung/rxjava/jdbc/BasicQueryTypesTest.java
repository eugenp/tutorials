package com.baeldung.rxjava.jdbc;

import com.github.davidmoten.rx.jdbc.ConnectionProvider;
import com.github.davidmoten.rx.jdbc.ConnectionProviderFromUrl;
import com.github.davidmoten.rx.jdbc.Database;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import rx.Observable;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class BasicQueryTypesTest {

    private ConnectionProvider cp = null;
    private Database db = null;

    @Before
    public void setup() {
        cp = new ConnectionProviderFromUrl(Connector.DB_CONNECTION, Connector.DB_USER, Connector.DB_PASSWORD);
        db = Database.from(cp);
    }

    @Test
    public void whenCreateTableAndInsertRecords_thenCorrect() {
        Observable<Integer> create = db
          .update("CREATE TABLE IF NOT EXISTS EMPLOYEE(id int primary key, name varchar(255))")
          .count();
        Observable<Integer> insert1 = db.update("INSERT INTO EMPLOYEE(id, name) VALUES(1, 'John')")
          .dependsOn(create)
          .count();
        Observable<Integer> update = db.update("UPDATE EMPLOYEE SET name = 'Alan' WHERE id = 1")
          .dependsOn(create)
          .count();
        Observable<Integer> insert2 = db.update("INSERT INTO EMPLOYEE(id, name) VALUES(2, 'Sarah')")
          .dependsOn(create)
          .count();
        Observable<Integer> insert3 = db.update("INSERT INTO EMPLOYEE(id, name) VALUES(3, 'Mike')")
          .dependsOn(create)
          .count();
        Observable<Integer> insert4 = db.update("INSERT INTO EMPLOYEE(id, name) VALUES(4, 'Jennifer')")
          .dependsOn(create)
          .count();
        Observable<Integer> insert5 = db.update("INSERT INTO EMPLOYEE(id, name) VALUES(5, 'George')")
          .dependsOn(create)
          .count();
        Observable<Integer> delete = db.update("DELETE FROM EMPLOYEE WHERE id = 5")
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
        db.update("DROP TABLE EMPLOYEE");
        cp.close();
    }
}
