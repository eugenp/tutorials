package com.baeldung.hibernate.exception.transientobject;

import com.baeldung.hibernate.exception.transientobject.entity.Address;
import com.baeldung.hibernate.exception.transientobject.entity.Department;
import com.baeldung.hibernate.exception.transientobject.entity.Author;
import com.baeldung.hibernate.exception.transientobject.entity.Book;
import com.baeldung.hibernate.exception.transientobject.entity.Employee;
import com.baeldung.hibernate.exception.transientobject.entity.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class HibernateTransientObjectUnitTest {

    private static SessionFactory sessionFactory;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @BeforeClass
    public static void init() {
        sessionFactory = HibernateUtil.getSessionFactory();
    }

    @Test
    public void whenSaveEntitiesWithOneToOneAssociation_thenSuccess() {
        User user = new User("Bob", "Smith");
        Address address = new Address("London", "221b Baker Street");
        user.setAddress(address);
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.save(user);
        session.getTransaction().commit();
        session.close();
    }

    @Test
    public void whenSaveEntitiesWithOneToManyAssociation_thenSuccess() {
        Department department = new Department();
        department.setName("IT Support");
        Employee employee = new Employee("John Doe");
        employee.setDepartment(department);
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.save(employee);
        session.getTransaction().commit();
        session.close();
    }

    @Test
    public void whenSaveEntitiesWithManyToManyAssociation_thenSuccess_1() {
        Book book = new Book("Design Patterns: Elements of Reusable Object-Oriented Software");
        book.addAuthor(new Author("Erich Gamma"));
        book.addAuthor(new Author("John Vlissides"));
        book.addAuthor(new Author("Richard Helm"));
        book.addAuthor(new Author("Ralph Johnson"));
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.save(book);
        session.getTransaction().commit();
        session.close();
    }

    @Test
    public void whenSaveEntitiesWithManyToManyAssociation_thenSuccess_2() {
        Author author = new Author("Erich Gamma");
        author.addBook(new Book("Design Patterns: Elements of Reusable Object-Oriented Software"));
        author.addBook(new Book("Introduction to Object Orient Design in C"));
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.save(author);
        session.getTransaction().commit();
        session.close();
    }

    @AfterClass
    public static void cleanUp() {
        sessionFactory.close();
    }
}
