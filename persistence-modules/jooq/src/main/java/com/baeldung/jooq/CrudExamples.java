package com.baeldung.jooq;

import com.baeldung.jooq.model.tables.Article;
import com.baeldung.jooq.model.tables.Author;
import com.baeldung.jooq.model.tables.records.ArticleRecord;
import com.baeldung.jooq.model.tables.records.AuthorRecord;
import org.jooq.DSLContext;
import org.jooq.Field;
import org.jooq.Record;
import org.jooq.Result;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;

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

public class CrudExamples {

    public void crudExamples() throws SQLException {
        String userName = "username";
        String password = "password";
        String url = "jdbc:postgresql://db_url:5432/baeldung_database";

        Connection conn = DriverManager.getConnection(url, userName, password);
        DSLContext context = DSL.using(conn, SQLDialect.POSTGRES);

        createValues(context);
        readValues(context);
        updateValues(context);
        deleteValues(context);
    }

    private void createValues(DSLContext context) {
        ArticleRecord article = context.newRecord(Article.ARTICLE);

        article.setId(2);
        article.setTitle("jOOQ examples");
        article.setDescription("A few examples of jOOQ CRUD operations");
        article.setAuthorId(1);

        save(article);

        AuthorRecord author = context.newRecord(Author.AUTHOR);
        author.setId(1);
        author.setFirstName("John");
        author.setLastName("Smith");
        author.setAge(40);

        save(author);
    }

    private void readValues(DSLContext context) {
        Result<Record> authors = getAll(
          context,
          Author.AUTHOR
        );

        authors.forEach(author -> {
            Integer id = author.getValue(Author.AUTHOR.ID);
            String firstName = author.getValue(Author.AUTHOR.FIRST_NAME);
            String lastName = author.getValue(Author.AUTHOR.LAST_NAME);
            Integer age = author.getValue(Author.AUTHOR.AGE);
            System.out.printf("Author %s %s has id: %d and age: %d%n", firstName, lastName, id, age);
        });

        Result<Record> articles = getFields(
          context,
          Author.AUTHOR,
          Article.ARTICLE.ID, Article.ARTICLE.TITLE
        );

        AuthorRecord author = getOne(
          context,
          Author.AUTHOR,
          Author.AUTHOR.ID.eq(1)
        );
    }

    private void updateValues(DSLContext context) {
        HashMap<Field<String>, String> fieldsToUpdate = new HashMap<>();
        fieldsToUpdate.put(Author.AUTHOR.FIRST_NAME, "David");
        fieldsToUpdate.put(Author.AUTHOR.LAST_NAME, "Brown");
        update(
          context,
          Author.AUTHOR,
          fieldsToUpdate,
          Author.AUTHOR.ID.eq(1)
        );

        ArticleRecord article = context.fetchOne(Article.ARTICLE, Article.ARTICLE.ID.eq(1));
        article.setTitle("A New Article Title");
        update(article);
    }

    private void deleteValues(DSLContext context) {
        delete(
          context,
          Article.ARTICLE,
          Article.ARTICLE.ID.eq(1)
        );

        AuthorRecord author = context.fetchOne(Author.AUTHOR, Author.AUTHOR.ID.eq(1));
        delete(author);
    }

}
