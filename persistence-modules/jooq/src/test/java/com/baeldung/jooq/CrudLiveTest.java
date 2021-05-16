package com.baeldung.jooq;

import com.baeldung.jooq.model.tables.Article;
import com.baeldung.jooq.model.tables.Author;
import com.baeldung.jooq.model.tables.records.ArticleRecord;
import org.jooq.DSLContext;
import org.jooq.Field;
import org.jooq.Record;
import org.jooq.Result;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;

import static com.baeldung.jooq.Crud.delete;
import static com.baeldung.jooq.Crud.getAll;
import static com.baeldung.jooq.Crud.getFields;
import static com.baeldung.jooq.Crud.getOne;
import static com.baeldung.jooq.Crud.save;
import static com.baeldung.jooq.Crud.update;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

public class CrudLiveTest {

    static DSLContext context;

    @BeforeClass
    public static void setup() throws SQLException {
        Connection conn = DriverManager.getConnection("jdbc:h2:mem:tes;INIT=CREATE SCHEMA IF NOT EXISTS \"public\"");
        context = DSL.using(conn, SQLDialect.H2);

        context.createTable(Author.AUTHOR)
                .columns(
                        Author.AUTHOR.ID,
                        Author.AUTHOR.FIRST_NAME,
                        Author.AUTHOR.LAST_NAME,
                        Author.AUTHOR.AGE
                )
                .execute();
        context.createTable(Article.ARTICLE)
                .columns(
                        Article.ARTICLE.ID,
                        Article.ARTICLE.TITLE,
                        Article.ARTICLE.DESCRIPTION,
                        Article.ARTICLE.AUTHOR_ID
                )
                .execute();
    }

    @Before
    public void cleanup() {
        context.truncateTable(Article.ARTICLE)
                .execute();
        context.truncateTable(Author.AUTHOR)
                .execute();
    }

    @Test
    public void givenArticleRecord_whenSave_thenNewRecordInDb() {
        // given
        ArticleRecord article = article();

        // when
        save(article);

        // then
        ArticleRecord savedArticle = getOne(
                context,
                Article.ARTICLE,
                Article.ARTICLE.ID.eq(1)
        );
        assertEquals(savedArticle.getId().intValue(), 1);
        assertEquals(savedArticle.getTitle(), "jOOQ examples");
        assertEquals(savedArticle.getDescription(), "A few examples of jOOQ CRUD operations");
        assertEquals(savedArticle.getAuthorId().intValue(), 1);
    }

    @Test
    public void givenArticleRecord_whenGetAll_thenValidOneRecord() {
        // given
        ArticleRecord article = article();
        save(article);

        // when
        Result<Record> articles = getAll(
                context,
                Article.ARTICLE
        );

        // then
        assertEquals(articles.size(), 1);

        Record record = articles.get(0);
        ArticleRecord savedArticle = record.into(Article.ARTICLE);

        assertEquals(savedArticle.getId().intValue(), 1);
        assertEquals(savedArticle.getTitle(), "jOOQ examples");
        assertEquals(savedArticle.getDescription(), "A few examples of jOOQ CRUD operations");
        assertEquals(savedArticle.getAuthorId().intValue(), 1);
    }

    @Test
    public void givenArticleRecord_whenGetOnlyTitles_thenValidOneValue() {
        // given
        ArticleRecord article = article();
        save(article);

        // when
        Result<Record> articles = getFields(
                context,
                Article.ARTICLE,
                Article.ARTICLE.TITLE
        );

        // then
        assertEquals(articles.size(), 1);

        Record record = articles.get(0);
        ArticleRecord savedArticle = record.into(Article.ARTICLE);

        assertNull(savedArticle.getId());
        assertEquals(savedArticle.getTitle(), "jOOQ examples");
        assertNull(savedArticle.getDescription());
        assertNull(savedArticle.getAuthorId());
    }

    @Test
    public void givenArticleRecord_whenGetOne_thenValidRecord() {
        // given
        ArticleRecord article = article();
        save(article);

        // when
        ArticleRecord savedArticle = getOne(
                context,
                Article.ARTICLE,
                Article.ARTICLE.ID.eq(1)
        );

        // then
        assertEquals(savedArticle.getId().intValue(), 1);
        assertEquals(savedArticle.getTitle(), "jOOQ examples");
        assertEquals(savedArticle.getDescription(), "A few examples of jOOQ CRUD operations");
        assertEquals(savedArticle.getAuthorId().intValue(), 1);
    }

    @Test
    public void givenArticleRecord_whenUpdateById_thenChangedValuesDbTable() {
        // given
        ArticleRecord article = article();
        save(article);

        HashMap<Field<String>, String> updateFields = new HashMap<>();
        updateFields.put(Article.ARTICLE.TITLE, "new title");
        updateFields.put(Article.ARTICLE.DESCRIPTION, "new description");

        // when
        update(
                context,
                Article.ARTICLE,
                updateFields,
                Article.ARTICLE.ID.eq(1)
        );

        // then
        ArticleRecord updatedArticle = getOne(
                context,
                Article.ARTICLE,
                Article.ARTICLE.ID.eq(1)
        );
        assertEquals(updatedArticle.getId().intValue(), 1);
        assertEquals(updatedArticle.getTitle(), "new title");
        assertEquals(updatedArticle.getDescription(), "new description");
        assertEquals(updatedArticle.getAuthorId().intValue(), 1);
    }

    @Test
    public void givenArticleRecord_whenUpdate_thenChangedValuesDbTable() {
        // given
        ArticleRecord article = article();
        save(article);

        article.setTitle("new title");
        article.setDescription("new description");

        // when
        update(article);

        // then
        ArticleRecord updatedArticle = getOne(
                context,
                Article.ARTICLE,
                Article.ARTICLE.ID.eq(1)
        );
        assertEquals(updatedArticle.getId().intValue(), 1);
        assertEquals(updatedArticle.getTitle(), "new title");
        assertEquals(updatedArticle.getDescription(), "new description");
        assertEquals(updatedArticle.getAuthorId().intValue(), 1);
    }

    @Test
    public void givenArticleRecord_whenDelete_thenEmptyDbTable() {
        // given
        ArticleRecord article = article();
        save(article);

        // when
        delete(article);

        // then
        Result<Record> articles = getAll(
                context,
                Article.ARTICLE
        );
        assertTrue(articles.isEmpty());
    }

    @Test
    public void givenArticleRecord_whenDeleteById_thenEmptyDbTable() {
        // given
        ArticleRecord article = article();
        save(article);

        // when
        delete(
                context,
                Article.ARTICLE,
                Article.ARTICLE.ID.eq(1)
        );

        // then
        Result<Record> articles = getAll(
                context,
                Article.ARTICLE
        );
        assertTrue(articles.isEmpty());
    }

    private ArticleRecord article() {
        ArticleRecord article = context.newRecord(Article.ARTICLE);
        article.setId(1);
        article.setTitle("jOOQ examples");
        article.setDescription("A few examples of jOOQ CRUD operations");
        article.setAuthorId(1);

        return article;
    }

}
