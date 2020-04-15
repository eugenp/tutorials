package com.baeldung.jpa.stringcast;

import org.junit.BeforeClass;
import org.junit.Test;

import javax.persistence.*;
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
        Message message = new Message();
        message.setText("text");

        EntityTransaction tr = em.getTransaction();
        tr.begin();
        em.persist(message);
        tr.commit();
    }

    @Test(expected = ClassCastException.class)
    public void givenExecutorNoCastCheck_whenQueryReturnsOneColumn_thenClassCastThrown() {
        List<String[]> results = QueryExecutor.executeNativeQueryNoCastCheck("select text from message", em);

        // fails
        for (String[] row : results) {
            // do nothing
        }
    }

    @Test
    public void givenExecutorWithCastCheck_whenQueryReturnsOneColumn_thenNoClassCastThrown() {
        List<String[]> results = QueryExecutor.executeNativeQueryWithCastCheck("select text from message", em);
        assertEquals("text", results.get(0)[0]);
    }

    @Test
    public void givenExecutorGeneric_whenQueryReturnsOneColumn_thenNoClassCastThrown() {
        List<Message> results = QueryExecutor.executeNativeQueryGeneric("select text from message", "textQueryMapping", em);
        assertEquals("text", results.get(0).getText());
    }

}
