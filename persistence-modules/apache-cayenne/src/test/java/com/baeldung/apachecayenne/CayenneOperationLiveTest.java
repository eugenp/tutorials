package com.baeldung.apachecayenne;

import com.baeldung.apachecayenne.persistent.Article;
import com.baeldung.apachecayenne.persistent.Author;
import org.apache.cayenne.ObjectContext;
import org.apache.cayenne.configuration.server.ServerRuntime;
import org.apache.cayenne.query.ObjectSelect;
import org.apache.cayenne.query.SQLTemplate;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.List;

import static junit.framework.Assert.assertEquals;
import static org.junit.Assert.assertNull;


public class CayenneOperationLiveTest {
    private static ObjectContext context = null;

    @BeforeClass
    public static void setupTheCayenneContext() {
        ServerRuntime cayenneRuntime = ServerRuntime.builder()
                .addConfig("cayenne-project.xml")
                .build();
        context = cayenneRuntime.newContext();
    }

    @After
    public void deleteAllRecords() {
        SQLTemplate deleteArticles = new SQLTemplate(Article.class, "delete from article");
        SQLTemplate deleteAuthors = new SQLTemplate(Author.class, "delete from author");

        context.performGenericQuery(deleteArticles);
        context.performGenericQuery(deleteAuthors);
    }

    @Test
    public void givenAuthor_whenInsert_thenWeGetOneRecordInTheDatabase() {
        Author author = context.newObject(Author.class);
        author.setName("Paul");

        context.commitChanges();

        long records = ObjectSelect.dataRowQuery(Author.class).selectCount(context);
        assertEquals(1, records);
    }

    @Test
    public void givenAuthor_whenInsert_andQueryByFirstName_thenWeGetTheAuthor() {
        Author author = context.newObject(Author.class);
        author.setName("Paul");

        context.commitChanges();

        Author expectedAuthor = ObjectSelect.query(Author.class)
                .where(Author.NAME.eq("Paul"))
                .selectOne(context);

        assertEquals("Paul", expectedAuthor.getName());
    }

    @Test
    public void givenTwoAuthor_whenInsert_andQueryAll_thenWeGetTwoAuthors() {
        Author firstAuthor = context.newObject(Author.class);
        firstAuthor.setName("Paul");

        Author secondAuthor = context.newObject(Author.class);
        secondAuthor.setName("Ludovic");

        context.commitChanges();

        List<Author> authors = ObjectSelect.query(Author.class).select(context);
        assertEquals(2, authors.size());
    }

    @Test
    public void givenAuthor_whenUpdating_thenWeGetAnUpatedeAuthor() {
        Author author = context.newObject(Author.class);
        author.setName("Paul");
        context.commitChanges();

        Author expectedAuthor = ObjectSelect.query(Author.class)
                .where(Author.NAME.eq("Paul"))
                .selectOne(context);
        expectedAuthor.setName("Garcia");
        context.commitChanges();

        assertEquals(author.getName(), expectedAuthor.getName());
    }

    @Test
    public void givenAuthor_whenDeleting_thenWeLostHisDetails() {
        Author author = context.newObject(Author.class);
        author.setName("Paul");
        context.commitChanges();

        Author savedAuthor = ObjectSelect.query(Author.class)
                .where(Author.NAME.eq("Paul")).selectOne(context);
        if(savedAuthor != null) {
            context.deleteObjects(author);
            context.commitChanges();
        }

        Author expectedAuthor = ObjectSelect.query(Author.class)
                .where(Author.NAME.eq("Paul")).selectOne(context);
        assertNull(expectedAuthor);
    }

    @Test
    public void givenAuthor_whenAttachingToArticle_thenTheRelationIsMade() {
        Author author = context.newObject(Author.class);
        author.setName("Paul");

        Article article = context.newObject(Article.class);
        article.setTitle("My post title");
        article.setContent("The content");
        article.setAuthor(author);

        context.commitChanges();

        Author expectedAuthor = ObjectSelect.query(Author.class)
                .where(Author.NAME.eq("Paul"))
                .selectOne(context);

        Article expectedArticle = (expectedAuthor.getArticles()).get(0);
        assertEquals(article.getTitle(), expectedArticle.getTitle());
    }

}
