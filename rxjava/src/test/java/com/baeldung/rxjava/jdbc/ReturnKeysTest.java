package com.baeldung.rxjava.jdbc;

import com.github.davidmoten.rx.jdbc.ConnectionProvider;
import com.github.davidmoten.rx.jdbc.ConnectionProviderFromUrl;
import com.github.davidmoten.rx.jdbc.Database;
import org.junit.Before;
import org.junit.Test;
import rx.Observable;

import static org.assertj.core.api.Assertions.assertThat;

public class ReturnKeysTest {

    private String DB_CONNECTION = Connector.DB_CONNECTION;
    private String DB_USER = Connector.DB_USER;
    private String DB_PASSWORD = Connector.DB_PASSWORD;

    private Observable<Integer> createStatement = null;

    private ConnectionProvider cp = new ConnectionProviderFromUrl(DB_CONNECTION, DB_USER, DB_PASSWORD);
    private Database db = Database.from(cp);

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
}
