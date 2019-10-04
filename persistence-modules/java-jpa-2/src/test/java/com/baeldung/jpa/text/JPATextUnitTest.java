package com.baeldung.jpa.text;

import static org.junit.Assert.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.junit.*;

public class JPATextUnitTest {


    private static EntityManager entityManager;

    @BeforeClass
    public static void setup() {
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("jpa-h2-text");
        entityManager = factory.createEntityManager();
    }
    
    @Test
    public void shouldCreateExam(){
    	Exam exam = new Exam();
    	exam.setDescription("This is a very long text");
    	exam.setText("This is a long text");
    	
    	exam = entityManager.merge(exam);
    	
    	assertEquals(entityManager.find(Exam.class, exam.getId()),exam);
    }



}
