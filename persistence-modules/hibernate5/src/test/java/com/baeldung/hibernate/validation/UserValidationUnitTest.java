package com.baeldung.hibernate.validation;

import static org.junit.Assert.assertEquals;
import java.util.Locale;
import java.util.Set;
import javax.persistence.PersistenceException;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import org.apache.log4j.BasicConfigurator;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.baeldung.hibernate.criteriaquery.HibernateUtil;
import com.baeldung.hibernate.entities.User;

public class UserValidationUnitTest {

    private static Validator validator;
    private static SessionFactory sessionFactory;
    private Session session;
    private Set<ConstraintViolation<User>> constraintViolations;

    @BeforeClass
    public static void before() {
        BasicConfigurator.configure();
        Locale.setDefault(Locale.ENGLISH);
        ValidatorFactory config = Validation.buildDefaultValidatorFactory();
        validator = config.getValidator();
        sessionFactory = HibernateUtil.getSessionFactory();
    }

    @Before
    public void setUp() {
        session = sessionFactory.openSession();
        session.beginTransaction();
    }

    @Test
    public void whenValidationWithSizeAndInvalidMiddleName_thenConstraintViolation() {
        User user = new User("john", "m", "butler","japan");
        constraintViolations = validator.validateProperty(user, "middleName");
        assertEquals(constraintViolations.size(), 1);
    }

    @Test
    public void whenValidationWithSizeAndValidMiddleName_thenNoConstraintViolation() {
        User user = new User("john", "mathis", "butler","japan");
        constraintViolations = validator.validateProperty(user, "middleName");
        assertEquals(constraintViolations.size(), 0);
    }

    @Test
    public void whenValidationWithLengthAndInvalidLastName_thenConstraintViolation() {
        User user = new User("john", "m", "b","japan");
        constraintViolations = validator.validateProperty(user, "lastName");
        assertEquals(constraintViolations.size(), 1);
    }

    @Test
    public void whenValidationWithLengthAndValidLastName_thenNoConstraintViolation() {
        User user = new User("john", "mathis", "butler","japan");
        constraintViolations = validator.validateProperty(user, "lastName");
        assertEquals(constraintViolations.size(), 0);
    }

    @Test(expected = PersistenceException.class)
    public void whenSavingFirstNameWithInvalidFirstName_thenPersistenceException() {
        User user = new User("john", "mathis", "butler","japan");
        session.save(user);
        session.getTransaction()
            .commit();
    }

    @Test
    public void whenValidationWithSizeAndColumnWithValidCity_thenNoConstraintViolation() {
        User user = new User("john", "mathis", "butler","japan");
        constraintViolations = validator.validateProperty(user, "city");
        assertEquals(constraintViolations.size(), 0);
    }
}
