package com.baeldung.java.core.general.copying;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertSame;

import org.junit.jupiter.api.Test;

import com.baeldung.java.core.general.copying.cloneable.Article;
import com.baeldung.java.core.general.copying.cloneable.ArticleShallow;
import com.baeldung.java.core.general.copying.cloneable.Author;

public class CloneableUnitTest {

    @Test
    void whenPerformingObjectClone_thenDeepCopyIsReturned() throws CloneNotSupportedException {
        Author author = new Author(111, "John", "Doe");
        Article article = new Article(25, "Some title", author);

        Article articleClone = article.clone();

        //checking that objects are NOT equal by reference
        assertNotSame(article.getAuthor(), articleClone.getAuthor());
        assertEquals(article.getAuthor(), articleClone.getAuthor());

        assertEquals(article, articleClone);
    }

    @Test
    void whenPerformingObjectClone_thenShallowCopyIsReturned() throws CloneNotSupportedException {
        Author author = new Author(111, "John", "Doe");
        ArticleShallow article = new ArticleShallow(25, "Some title", author);

        ArticleShallow articleClone = article.clone();

        //checking that objects are equal by reference -> means shallow copy is performed
        assertSame(article.getAuthor(), articleClone.getAuthor());

        assertEquals(article, articleClone);
    }

    @Test
    void whenUsingCopyConstructor_thenDeepCopyIsReturned() {
        Author author = new Author(111, "John", "Doe");
        Article article = new Article(25, "Some title", author);

        Article articleClone = new Article(article);

        //checking that objects are NOT equal by reference
        assertNotSame(article.getAuthor(), articleClone.getAuthor());
        assertEquals(article.getAuthor(), articleClone.getAuthor());

        assertEquals(article, articleClone);
    }
}
