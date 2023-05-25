package com.baeldung.rxjava.jdbc;

import static org.junit.Assert.assertEquals;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.github.davidmoten.rx.jdbc.ConnectionProvider;
import com.github.davidmoten.rx.jdbc.Database;

import rx.Observable;

public class InsertClobIntegrationTest {

    private ConnectionProvider connectionProvider = Connector.connectionProvider;
    private Database db = Database.from(connectionProvider);

    private String expectedDocument = null;
    private String actualDocument = null;

    private Observable<Integer> create, insert = null;

    @Before
    public void setup() throws IOException {
        create = db.update("CREATE TABLE IF NOT EXISTS SERVERLOG_TABLE (id int primary key, document CLOB)")
          .count();

        InputStream actualInputStream = new FileInputStream("src/test/resources/actual_clob");
        this.actualDocument = Utils.getStringFromInputStream(actualInputStream);

        InputStream expectedInputStream = new FileInputStream("src/test/resources/expected_clob");
        this.expectedDocument = Utils.getStringFromInputStream(expectedInputStream);
        this.insert = db.update("insert into SERVERLOG_TABLE(id,document) values(?,?)")
          .parameter(1)
          .parameter(Database.toSentinelIfNull(actualDocument))
          .dependsOn(create)
          .count();
    }

    @Test
    public void whenSelectCLOB_thenCorrect() throws IOException {
        db.select("select document from SERVERLOG_TABLE where id = 1")
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
        db.update("DROP TABLE SERVERLOG_TABLE")
          .dependsOn(create);
        connectionProvider.close();
    }
}