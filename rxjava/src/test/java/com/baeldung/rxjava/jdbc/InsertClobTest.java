package com.baeldung.rxjava.jdbc;

import com.github.davidmoten.rx.jdbc.ConnectionProvider;
import com.github.davidmoten.rx.jdbc.ConnectionProviderFromUrl;
import com.github.davidmoten.rx.jdbc.Database;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import rx.Observable;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import static org.junit.Assert.assertEquals;

public class InsertClobTest {

    private ConnectionProvider cp = new ConnectionProviderFromUrl(Connector.DB_CONNECTION, Connector.DB_USER, Connector.DB_PASSWORD);
    private Database db = Database.from(cp);

    private String expectedDocument = null;
    private String actualDocument = null;

    private Observable<Integer> create, insert = null;

    @Before
    public void setup() throws IOException {
        create = db.update("CREATE TABLE IF NOT EXISTS SERVERLOG (id int primary key, document CLOB)")
          .count();

        InputStream actualInputStream = new FileInputStream("src/test/resources/actual_clob");
        this.actualDocument = Utils.getStringFromInputStream(actualInputStream);

        InputStream expectedInputStream = new FileInputStream("src/test/resources/expected_clob");
        this.expectedDocument = Utils.getStringFromInputStream(expectedInputStream);
        this.insert = db.update("insert into SERVERLOG(id,document) values(?,?)")
          .parameter(1)
          .parameter(Database.toSentinelIfNull(actualDocument))
          .dependsOn(create)
          .count();
    }

    @Test
    public void whenSelectCLOB_thenCorrect() throws IOException {
        db.select("select document from SERVERLOG where id = 1")
          .dependsOn(create)
          .dependsOn(insert)
          .getAs(String.class)
          .toList()
          .toBlocking()
          .single();
        assertEquals(expectedDocument, actualDocument);
    }

    @After
    public void close() {
        db.update("DROP TABLE SERVERLOG")
          .dependsOn(create);
        cp.close();
    }
}