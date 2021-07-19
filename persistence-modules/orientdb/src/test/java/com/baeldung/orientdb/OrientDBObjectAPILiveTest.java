package com.baeldung.orientdb;

import com.orientechnologies.orient.core.sql.query.OSQLSynchQuery;
import com.orientechnologies.orient.object.db.OObjectDatabaseTx;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.List;

import static junit.framework.Assert.assertEquals;

public class OrientDBObjectAPILiveTest {
    private static OObjectDatabaseTx db = null;

    // @BeforeClass
    public static void setup() {
        String orientDBFolder = System.getenv("ORIENTDB_HOME");
        db = new OObjectDatabaseTx("plocal:" + orientDBFolder + "/databases/BaeldungDBThree").open("admin", "admin");
        db.setSaveOnlyDirty(true);
        db.getEntityManager().registerEntityClass(Author.class);
    }

    // @Test
    public void givenDB_whenSavingObject_thenHisIdExists() {
        Author author = db.newInstance(Author.class);
        author.setFirstName("Luke");
        author.setLastName("Sky");
        author.setLevel(9);

        long authors = db.countClass(Author.class);

        db.save(author);
    }

    // @Test
    public void givenDB_whenSavingAuthors_thenWeGetOnesWithLevelSeven() {
        for (Author author : db.browseClass(Author.class)) db.delete(author);

        Author authorOne = db.newInstance(Author.class, "Leo", "Marta", 7);
        db.save(authorOne);

        Author authorTwo = db.newInstance(Author.class, "Lucien", "Aurelien", 9);
        db.save(authorTwo);

        List<Author> result = db.query(
            new OSQLSynchQuery<Author>("select * from Author where level = 7"));

        assertEquals(1, result.size());
    }

    // @AfterClass
    public static void closeDB() {
        db.close();
    }
}
