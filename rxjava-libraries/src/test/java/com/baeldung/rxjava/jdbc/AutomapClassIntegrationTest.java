package com.baeldung.rxjava.jdbc;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.github.davidmoten.rx.jdbc.ConnectionProvider;
import com.github.davidmoten.rx.jdbc.Database;

import rx.Observable;

public class AutomapClassIntegrationTest {

    private ConnectionProvider connectionProvider = Connector.connectionProvider;
    private Database db = Database.from(connectionProvider);

    private Observable<Integer> create = null;
    private Observable<Integer> insert1, insert2 = null;

    @Before
    public void setup() {
        create = db.update("CREATE TABLE IF NOT EXISTS MANAGER(id int primary key, name varchar(255))")
          .count();
        insert1 = db.update("INSERT INTO MANAGER(id, name) VALUES(1, 'Alan')")
          .dependsOn(create)
          .count();
        insert2 = db.update("INSERT INTO MANAGER(id, name) VALUES(2, 'Sarah')")
          .dependsOn(create)
          .count();
    }

    @Test
    public void whenSelectManagersAndAutomap_thenCorrect() {
        List<Manager> managers = db.select("select id, name from MANAGER")
          .dependsOn(create)
          .dependsOn(insert1)
          .dependsOn(insert2)
          .autoMap(Manager.class)
          .toList()
          .toBlocking()
          .single();

        assertThat(managers.get(0)
          .getId()).isEqualTo(1);
        assertThat(managers.get(0)
          .getName()).isEqualTo("Alan");
        assertThat(managers.get(1)
           .getId()).isEqualTo(2);
        assertThat(managers.get(1)
          .getName()).isEqualTo("Sarah");
    }

    @After
    public void close() {
        db.update("DROP TABLE MANAGER")
          .dependsOn(create);
        connectionProvider.close();
    }
}
