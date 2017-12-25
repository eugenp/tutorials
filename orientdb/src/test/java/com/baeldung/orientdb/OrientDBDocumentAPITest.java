package com.baeldung.orientdb;

import com.orientechnologies.orient.core.db.document.ODatabaseDocumentTx;
import com.orientechnologies.orient.core.exception.OSchemaException;
import com.orientechnologies.orient.core.metadata.schema.OType;
import com.orientechnologies.orient.core.record.impl.ODocument;
import com.orientechnologies.orient.core.sql.query.OSQLSynchQuery;
import com.tinkerpop.blueprints.Vertex;
import com.tinkerpop.blueprints.impls.orient.OrientGraphNoTx;
import com.tinkerpop.blueprints.impls.orient.OrientVertexType;
import org.junit.*;

import java.util.List;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
import static org.junit.Assert.assertNotEquals;

public class OrientDBDocumentAPITest {
    private static ODatabaseDocumentTx db = null;

    @BeforeClass
    public static void setup() {
        String orientDBFolder = System.getenv("ORIENTDB_HOME");
        db = new ODatabaseDocumentTx("plocal:" + orientDBFolder + "/databases/BaeldungDBTwo").open("admin", "admin");
    }

    @Test
    public void givenDB_whenSavingDocument_thenClassIsAutoCreated() {
        ODocument author = new ODocument("Author");
        author.field( "firstName", "Paul" );
        author.field( "lastName", "Smith" );
        author.field( "country", "USA" );
        author.field( "publicProfile", false );
        author.field( "level", 7 );
        author.save();

        assertEquals("Author", author.getSchemaClass().getName());
    }

    @Test
    public void givenAnEmptyDB_afterSavingTwoAuthors_thenWeGetAuthorsWithLevelSeven() {
        for (ODocument author : db.browseClass("Author")) author.delete();

        ODocument authorOne = new ODocument("Author");
        authorOne.field( "firstName", "Leo" );
        authorOne.field( "level", 7 );
        authorOne.save();

        ODocument authorTwo = new ODocument("Author");
        authorTwo.field( "firstName", "Lucien" );
        authorTwo.field( "level", 9 );
        authorTwo.save();

        List<ODocument> result = db.query(
                new OSQLSynchQuery<ODocument>("select * from Author where level = 7"));

        assertEquals(1, result.size());
    }

    @AfterClass
    public static void closeDB() {
        db.close();
    }
}
