package com.baeldung.hibernate.validation;

import com.baeldung.hibernate.HibernateUtil;
import com.baeldung.hibernate.Strategy;
import com.baeldung.hibernate.persistmaps.mapkey.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import javax.persistence.PersistenceException;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class UserAdditionalValidationUnitTest {

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
    public void whenValidationWithCCNAndNullCCN_thenNoConstraintViolation() {
        User user = new User("John", "Paul", "Butler", "York");
        constraintViolations = validator.validateProperty(user, "creditCardNumber");
        assertTrue(constraintViolations.isEmpty());
    }

    @Test
    public void whenValidationWithCCNAndValidCCN_thenNoConstraintViolation() {
        User user = new User("John", "Paul", "Butler", "York");
        user.setCreditCardNumber("79927398713");
        constraintViolations = validator.validateProperty(user, "creditCardNumber");
        assertTrue(constraintViolations.isEmpty());
    }

    @Test
    public void whenValidationWithCCNAndInvalidCCN_thenConstraintViolation() {
        User user = new User("John", "Paul", "Butler", "York");
        user.setCreditCardNumber("79927398714");
        constraintViolations = validator.validateProperty(user, "creditCardNumber");
        assertEquals(constraintViolations.size(), 1);
    }

    @Test
    public void whenValidationWithLenientCCNAndValidCCNWithDashes_thenNoConstraintViolation() {
        User user = new User("John", "Paul", "Butler", "York");
        user.setLenientCreditCardNumber("7992-7398-713");
        constraintViolations = validator.validateProperty(user, "lenientCreditCardNumber");
        assertTrue(constraintViolations.isEmpty());
    }
}
