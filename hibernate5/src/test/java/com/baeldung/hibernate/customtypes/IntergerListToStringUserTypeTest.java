package com.baeldung.hibernate.customtypes;

import com.baeldung.hibernate.HibernateUtil;
import com.baeldung.hibernate.customtypes.entity.Student;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class IntergerListToStringUserTypeTest {

    private Session session;
    private Transaction transaction;

    @Before
    public void setUp() throws IOException {
        session = HibernateUtil.getSessionFactory()
                .openSession();
        transaction = session.beginTransaction();

        session.createNativeQuery("delete from student_ct")
                .executeUpdate();

        transaction.commit();
        transaction = session.beginTransaction();
    }

    @After
    public void tearDown() {
        transaction.rollback();
        session.close();
    }

    @Test
    public void givenEntity_whenUsingCustomTypeForPhoneNumbers_thenSave() {
        List<Integer> phoneNumbers = new ArrayList<>();
        phoneNumbers.add(912356);
        phoneNumbers.add(716131);


        Student student = new Student();
        student.setFirstName("Baeldung");
        student.setLastName(null);
        student.setPhoneNumbers(phoneNumbers);

        Integer id = (Integer) session.save(student);
        session.flush();
        session.clear();

        String savedPhoneNumbers = (String) session.createNativeQuery("select phone_numbers from student_ct where id =:id")
                .setParameter("id", id)
                .getSingleResult();

        assertThat(savedPhoneNumbers).isEqualTo("{912356,716131}");


        Student savedStudent = session.get(Student.class, id);
        assertThat(savedStudent.getId()).isEqualTo(id);
        assertThat(savedStudent.getFirstName()).isEqualTo("Baeldung");
        assertThat(savedStudent.getLastName()).isNull();
        assertThat(savedStudent.getPhoneNumbers()).isEqualTo(phoneNumbers);
    }

}
