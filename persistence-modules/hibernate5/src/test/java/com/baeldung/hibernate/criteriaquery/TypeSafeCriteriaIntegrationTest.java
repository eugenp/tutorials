package com.baeldung.hibernate.criteriaquery;

import com.baeldung.hibernate.criteriaquery.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.io.IOException;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;


public class TypeSafeCriteriaIntegrationTest {

    private static SessionFactory sessionFactory;

    private Session session;

    @BeforeClass
    public static void beforeTests() throws IOException {
        sessionFactory = HibernateUtil.getSessionFactory();
    }

    @Before
    public void setUp() {
        session = sessionFactory.openSession();
        session.beginTransaction();
    }

    @Test
    public void givenStudentData_whenUsingTypeSafeCriteriaQuery_thenSearchAllStudentsOfAGradYear() {

        prepareData();
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<Student> criteriaQuery = cb.createQuery(Student.class);

        Root<Student> root = criteriaQuery.from(Student.class);
        criteriaQuery.select(root).where(cb.equal(root.get("gradYear"), 1965));

        Query<Student> query = session.createQuery(criteriaQuery);
        List<Student> results = query.getResultList();

        assertNotNull(results);
        assertEquals(1, results.size());

        Student student = results.get(0);

        assertEquals("Ken", student.getFirstName());
        assertEquals("Thompson", student.getLastName());
        assertEquals(1965, student.getGradYear());
    }

    private void prepareData() {
        Student student1 = new Student();
        student1.setFirstName("Ken");
        student1.setLastName("Thompson");
        student1.setGradYear(1965);

        session.save(student1);

        Student student2 = new Student();
        student2.setFirstName("Dennis");
        student2.setLastName("Ritchie");
        student2.setGradYear(1963);

        session.save(student2);
        session.getTransaction().commit();
    }

    @After
    public void tearDown() {
        session.close();
    }

    @AfterClass
    public static void afterTests() {
        sessionFactory.close();
    }
}
