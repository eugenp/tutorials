package com.baeldung.h2db.manytomany.removal;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@RunWith(SpringRunner.class)
@DataJpaTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@Transactional
class BidirectionalRemovalUnitTest {

    @PersistenceContext
    private EntityManager entityManager;

    @BeforeEach
    void init() {
        Author author1 = new Author("Erich Gamma");
        Author author2 = new Author("John Vlissides");
        Author author3 = new Author("Ralph Johnson");
        Author author4 = new Author("Richard Helm");

        Book book = new Book();
        book.setTitle("GOF Java");
        book.addAuthor(author1);
        book.addAuthor(author2);
        book.addAuthor(author3);
        book.addAuthor(author4);
        entityManager.persist(book);

        Book book2 = new Book();
        book2.setTitle("A Lost Age");
        book2.addAuthor(author3);
        entityManager.persist(book2);
    }

    @Test
    void givenEntities_whenRemoveFromOwner_thenRemoveAssociation() {
        Author author = (Author) entityManager
                .createQuery("SELECT author from Author author where author.name = ?1")
                .setParameter(1, "Ralph Johnson")
                .getSingleResult();

        Book book1 = entityManager.find(Book.class, 1L);
        Book book2 = entityManager.find(Book.class, 2L);

        book1.removeAuthor(author);
        entityManager.persist(book1);

        Assertions.assertEquals(3, book1.getAuthors().size());
        Assertions.assertEquals(1, book2.getAuthors().size());
    }

    @Test
    void givenEntities_whenRemoveFromNotOwner_thenRemoveAssociation() {
        Author author = (Author) entityManager
                .createQuery("SELECT author from Author author where author.name = ?1")
                .setParameter(1, "Ralph Johnson")
                .getSingleResult();
        Book book1 = entityManager.find(Book.class, 1L);
        Book book2 = entityManager.find(Book.class, 2L);

        entityManager.remove(author);

        Assertions.assertEquals(3, book1.getAuthors().size());
        Assertions.assertEquals(0, book2.getAuthors().size());
    }

}

