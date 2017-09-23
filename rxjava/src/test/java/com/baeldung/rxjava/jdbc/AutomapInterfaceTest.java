package com.baeldung.rxjava.jdbc;

import com.github.davidmoten.rx.jdbc.ConnectionProvider;
import com.github.davidmoten.rx.jdbc.ConnectionProviderFromUrl;
import com.github.davidmoten.rx.jdbc.Database;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import rx.Observable;

import java.util.List;

import static com.baeldung.rxjava.jdbc.Connector.DB_CONNECTION;
import static com.baeldung.rxjava.jdbc.Connector.DB_PASSWORD;
import static com.baeldung.rxjava.jdbc.Connector.DB_USER;
import static org.assertj.core.api.Assertions.assertThat;

public class AutomapInterfaceTest {

    private ConnectionProvider cp = null;
    private Database db = null;

    private Observable<Integer> create = null;
    private Observable<Integer> insert1, insert2 = null;

    @Before
    public void setup() {
        cp = new ConnectionProviderFromUrl(DB_CONNECTION, DB_USER, DB_PASSWORD);
        db = Database.from(cp);

        create = db.update("CREATE TABLE IF NOT EXISTS EMPLOYEE(id int primary key, name varchar(255))")
          .count();
        insert1 = db.update("INSERT INTO EMPLOYEE(id, name) VALUES(1, 'Alan')")
          .dependsOn(create)
          .count();
        insert2 = db.update("INSERT INTO EMPLOYEE(id, name) VALUES(2, 'Sarah')")
          .dependsOn(create)
          .count();
    }

    @Test
    public void whenSelectFromTableAndAutomap_thenCorrect() {
        List<Employee> employees = db.select("select id, name from EMPLOYEE")
          .dependsOn(create)
          .dependsOn(insert1)
          .dependsOn(insert2)
          .autoMap(Employee.class)
          .toList()
          .toBlocking()
          .single();

        assertThat(employees.get(0)
          .id()).isEqualTo(1);
        assertThat(employees.get(0)
          .name()).isEqualTo("Alan");
        assertThat(employees.get(1)
          .id()).isEqualTo(2);
        assertThat(employees.get(1)
          .name()).isEqualTo("Sarah");
    }

    @After
    public void close() {
        db.update("DROP TABLE EMPLOYEE")
          .dependsOn(create);
        cp.close();
    }
}
