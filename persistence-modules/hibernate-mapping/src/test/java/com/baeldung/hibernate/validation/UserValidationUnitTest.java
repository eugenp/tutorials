package com.baeldung.hibernate.validation;

import static org.junit.Assert.assertEquals;
import java.util.Set;
import jakarta.persistence.PersistenceException;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import com.baeldung.hibernate.HibernateUtil;
import com.baeldung.hibernate.Strategy;
import com.baeldung.hibernate.persistmaps.mapkey.User;

public class UserValidationUnitTest {

    private static Validator validator;
    private static SessionFactory sessionFactory;
    private Session session;
    private Set<ConstraintViolation<User>> constraintViolations;

    @BeforeClass
    public static void before() {
        ValidatorFactory config = Validation.buildDefaultValidatorFactory();
        validator = config.getValidator();
        sessionFactory = HibernateUtil.getSessionFactory(Strategy.MAP_KEY_BASED);
    }

    @Before
    public void setUp() {
        session = sessionFactory.openSession();
        session.beginTransaction();
    }

    @Test
    public void whenValidationWithSizeAndInvalidMiddleName_thenConstraintViolation() {
        User user = new User("john", "m", "butler", "japan");
        constraintViolations = validator.validateProperty(user, "middleName");
        assertEquals(constraintViolations.size(), 1);
    }

    @Test
    public void whenValidationWithSizeAndValidMiddleName_thenNoConstraintViolation() {
        User user = new User("john", "mathis", "butler", "japan");
        constraintViolations = validator.validateProperty(user, "middleName");
        assertEquals(constraintViolations.size(), 0);
    }

    @Test
    public void whenValidationWithLengthAndInvalidLastName_thenConstraintViolation() {
        User user = new User("john", "m", "b", "japan");
        constraintViolations = validator.validateProperty(user, "lastName");
        assertEquals(constraintViolations.size(), 1);
    }

    @Test
    public void whenValidationWithLengthAndValidLastName_thenNoConstraintViolation() {
        User user = new User("john", "mathis", "butler", "japan");
        constraintViolations = validator.validateProperty(user, "lastName");
        assertEquals(constraintViolations.size(), 0);
    }

    @Test(expected = PersistenceException.class)
    public void whenSavingFirstNameWithInvalidFirstName_thenPersistenceException() {
        User user = new User("john", "mathis", "butler", "japan");
        session.save(user);
        session.getTransaction()
            .commit();
    }

    @Test
    public void whenValidationWithSizeAndColumnWithValidCity_thenNoConstraintViolation() {
        User user = new User("john", "mathis", "butler", "japan");
        constraintViolations = validator.validateProperty(user, "city");
        assertEquals(constraintViolations.size(), 0);
    }
}
