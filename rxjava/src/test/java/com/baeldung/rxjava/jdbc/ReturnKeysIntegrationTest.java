package com.baeldung.rxjava.jdbc;

import com.github.davidmoten.rx.jdbc.Database;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import rx.Observable;

import static org.assertj.core.api.Assertions.assertThat;

public class ReturnKeysIntegrationTest {

    private Observable<Integer> createStatement;

    private Database db = Database.from(Connector.connectionProvider);

    @Before
    public void setup() {
        Observable<Boolean> begin = db.beginTransaction();
        createStatement = db
          .update("CREATE TABLE IF NOT EXISTS EMPLOYEE(id int auto_increment primary key, name varchar(255))")
          .dependsOn(begin)
          .count();
    }

    @Test
    public void whenInsertAndReturnGeneratedKey_thenCorrect() {
        Integer key = db.update("INSERT INTO EMPLOYEE(name) VALUES('John')")
          .dependsOn(createStatement)
          .returnGeneratedKeys()
          .getAs(Integer.class)
          .count()
          .toBlocking()
          .single();
        assertThat(key).isEqualTo(1);
    }

    @After
    public void close() {
        db.update("DROP TABLE EMPLOYEE");
        Connector.connectionProvider.close();
    }
}
