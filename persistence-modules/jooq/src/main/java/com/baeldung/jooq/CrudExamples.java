package com.baeldung.jooq;

import com.baeldung.jooq.model.tables.Article;
import com.baeldung.jooq.model.tables.Author;
import com.baeldung.jooq.model.tables.records.ArticleRecord;
import com.baeldung.jooq.model.tables.records.AuthorRecord;
import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.Record2;
import org.jooq.Result;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class CrudExamples {

    public void crud() throws SQLException {
        String userName = "username";
        String password = "password";
        String url = "jdbc:postgresql://db_url:5432/baeldung_database";

        Connection conn = DriverManager.getConnection(url, userName, password);
        DSLContext context = DSL.using(conn, SQLDialect.POSTGRES);

        readValues(context);
        createValues(context);
        updateValues(context);
        deleteValues(context);
    }

    private void readValues(DSLContext context) {
        Result<Record> authors = context.select()
                .from(Author.AUTHOR)
                .fetch();

        authors.forEach(author -> {
            Integer id = author.getValue(Author.AUTHOR.ID);
            String firstName = author.getValue(Author.AUTHOR.FIRST_NAME);
            String lastName = author.getValue(Author.AUTHOR.LAST_NAME);
            Integer age = author.getValue(Author.AUTHOR.AGE);
            System.out.printf("Author %s %s has id: %d and age: %d%n", firstName, lastName, id, age);
        });

        Result<Record2<Integer, String>> articles = context.select(Article.ARTICLE.ID, Article.ARTICLE.TITLE)
                .from(Author.AUTHOR)
                .fetch();

        AuthorRecord author = context.fetchOne(Author.AUTHOR, Author.AUTHOR.ID.eq(1));
    }

    private void createValues(DSLContext context) {
        ArticleRecord article = context.newRecord(Article.ARTICLE);

        article.setId(2);
        article.setTitle("jOOQ examples");
        article.setDescription("A few examples of jOOQ CRUD operations");
        article.setAuthorId(1);

        article.store();
    }

    private void updateValues(DSLContext context) {
        context.update(Author.AUTHOR)
                .set(Author.AUTHOR.FIRST_NAME, "David")
                .set(Author.AUTHOR.LAST_NAME, "Brown")
                .where(Author.AUTHOR.ID.eq(1))
                .execute();

        ArticleRecord article = context.fetchOne(Article.ARTICLE, Article.ARTICLE.ID.eq(1));
        article.setTitle("A New Article Title");
        article.store();
    }

    private void deleteValues(DSLContext context) {
        context.delete(Article.ARTICLE)
                .where(Article.ARTICLE.ID.eq(1))
                .execute();

        ArticleRecord articleRecord = context.fetchOne(Article.ARTICLE, Article.ARTICLE.ID.eq(1));
        articleRecord.delete();
    }

}
