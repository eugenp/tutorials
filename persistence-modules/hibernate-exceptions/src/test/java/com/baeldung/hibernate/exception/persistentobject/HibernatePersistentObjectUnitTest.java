package com.baeldung.hibernate.exception.persistentobject;

import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.hibernate.PropertyValueException;
import org.hibernate.Session;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.baeldung.hibernate.exception.persistentobject.entity.Article;
import com.baeldung.hibernate.exception.persistentobject.entity.Author;
import com.baeldung.hibernate.exception.persistentobject.entity.Book;

public class HibernatePersistentObjectUnitTest {

    private static Session session;

    @Before
    public void beforeAll() {
        session = HibernateUtil.getSessionFactory()
            .openSession();
        session.beginTransaction();
    }

    @After
    public void afterAll() {
        session.close();
    }

    @Test
    public void whenSavingEntityWithNullMandatoryField_thenThrowPropertyValueException() {
        Book book = new Book();

        assertThatThrownBy(() -> session.save(book)).isInstanceOf(PropertyValueException.class)
            .hasMessageContaining("not-null property references a null or transient value");
    }

    @Test
    public void whenSavingEntityWithAllMandatoryField_thenDoNotThrowException() {
        Book book = new Book();
        book.setTitle("Clean Code");

        session.save(book);
    }

    @Test
    public void whenSavingBidirectionalEntitiesWithoutSettingParent_thenThrowPropertyValueException() {
        Author author = new Author("John Doe");
        author.setArticles(asList(new Article("Java Tutorial"), new Article("What's New in JUnit5")));

        assertThatThrownBy(() -> session.save(author)).isInstanceOf(PropertyValueException.class)
            .hasMessageContaining("not-null property references a null or transient value");
    }

    @Test
    public void whenSavingBidirectionalEntitiesWithCorrectParent_thenDoNotThrowException() {
        Author author = new Author("John Doe");
        author.addArticles(asList(new Article("Java tutorial"), new Article("What's new in JUnit5")));

        session.save(author);
    }

}