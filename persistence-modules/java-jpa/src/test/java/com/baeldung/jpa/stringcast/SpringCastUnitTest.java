package com.baeldung.jpa.stringcast;

import org.junit.BeforeClass;
import org.junit.Test;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class SpringCastUnitTest {

    private static EntityManager em;
    private static EntityManagerFactory emFactory;

    @BeforeClass
    public static void setup() {
        emFactory = Persistence.createEntityManagerFactory("jpa-h2");
        em = emFactory.createEntityManager();

        // insert an object into the db
        DummyEntity dummyEntity = new DummyEntity();
        dummyEntity.setText("text");

        EntityTransaction tr = em.getTransaction();
        tr.begin();
        em.persist(dummyEntity);
        tr.commit();
    }

    @Test(expected = ClassCastException.class)
    public void givenExecutorNoCastCheck_whenQueryReturnsOneColumn_thenClassCastThrown() {
        List<String[]> results = QueryExecutor.executeNativeQueryNoCastCheck("select text from dummy", em);

        // fails
        for (String[] row : results) {
            // do nothing
        }
    }

    @Test
    public void givenExecutorWithCastCheck_whenQueryReturnsOneColumn_thenNoClassCastThrown() {
        List<String[]> results = QueryExecutor.executeNativeQueryWithCastCheck("select text from dummy", em);
        assertEquals("text", results.get(0)[0]);
    }

    @Test
    public void givenExecutorGeneric_whenQueryReturnsOneColumn_thenNoClassCastThrown() {
        List<DummyEntity> results = QueryExecutor.executeNativeQueryGeneric("select text from dummy", "textQueryMapping", em);
        assertEquals("text", results.get(0).getText());
    }

}
