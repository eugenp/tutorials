package com.baeldung.apachecayenne;

import com.baeldung.apachecayenne.persistent.Author;
import org.apache.cayenne.ObjectContext;
import org.apache.cayenne.QueryResponse;
import org.apache.cayenne.configuration.server.ServerRuntime;
import org.apache.cayenne.exp.Expression;
import org.apache.cayenne.exp.ExpressionFactory;
import org.apache.cayenne.query.*;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static junit.framework.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class CayenneAdvancedOperationLiveTest {
    private static ObjectContext context = null;

    @BeforeClass
    public static void setupTheCayenneContext() {
        ServerRuntime cayenneRuntime = ServerRuntime.builder()
          .addConfig("cayenne-project.xml")
          .build();
        context = cayenneRuntime.newContext();
    }

    @Before
    public void saveThreeAuthors() {
        Author authorOne = context.newObject(Author.class);
        authorOne.setName("Paul Xavier");
        Author authorTwo = context.newObject(Author.class);
        authorTwo.setName("pAuL Smith");
        Author authorThree = context.newObject(Author.class);
        authorThree.setName("Vicky Sarra");
        context.commitChanges();
    }

    @After
    public void deleteAllAuthors() {
        SQLTemplate deleteAuthors = new SQLTemplate(Author.class, "delete from author");
        context.performGenericQuery(deleteAuthors);
    }

    @Test
    public void givenAuthors_whenFindAllSQLTmplt_thenWeGetThreeAuthors() {
        SQLTemplate select = new SQLTemplate(Author.class, "select * from Author");
        List<Author> authors = context.performQuery(select);

        assertEquals(authors.size(), 3);
    }

    @Test
    public void givenAuthors_whenFindByNameSQLTmplt_thenWeGetOneAuthor() {
        SQLTemplate select = new SQLTemplate(Author.class, "select * from Author where name = 'Vicky Sarra'");
        List<Author> authors = context.performQuery(select);
        Author author = authors.get(0);

        assertEquals(authors.size(), 1);
        assertEquals(author.getName(), "Vicky Sarra");
    }

    @Test
    public void givenAuthors_whenLikeSltQry_thenWeGetOneAuthor() {
        Expression qualifier = ExpressionFactory.likeExp(Author.NAME.getName(), "Paul%");
        SelectQuery query = new SelectQuery(Author.class, qualifier);
        List<Author> authorsTwo = context.performQuery(query);

        assertEquals(authorsTwo.size(), 1);
    }

    @Test
    public void givenAuthors_whenCtnsIgnorCaseSltQry_thenWeGetTwoAuthors() {
        Expression qualifier = ExpressionFactory.containsIgnoreCaseExp(Author.NAME.getName(), "Paul");
        SelectQuery query = new SelectQuery(Author.class, qualifier);
        List<Author> authors = context.performQuery(query);

        assertEquals(authors.size(), 2);
    }

    @Test
    public void givenAuthors_whenCtnsIgnorCaseEndsWSltQry_thenWeGetTwoAuthors() {
        Expression qualifier = ExpressionFactory.containsIgnoreCaseExp(Author.NAME.getName(), "Paul")
                .andExp(ExpressionFactory.endsWithExp(Author.NAME.getName(), "h"));
        SelectQuery query = new SelectQuery(Author.class, qualifier);
        List<Author> authors = context.performQuery(query);

        Author author = authors.get(0);

        assertEquals(authors.size(), 1);
        assertEquals(author.getName(), "pAuL Smith");
    }

    @Test
    public void givenAuthors_whenAscOrderingSltQry_thenWeGetOrderedAuthors() {
        SelectQuery query = new SelectQuery(Author.class);
        query.addOrdering(Author.NAME.asc());

        List<Author> authors = query.select(context);
        Author firstAuthor = authors.get(0);

        assertEquals(authors.size(), 3);
        assertEquals(firstAuthor.getName(), "Paul Xavier");
    }

    @Test
    public void givenAuthors_whenDescOrderingSltQry_thenWeGetOrderedAuthors() {
        SelectQuery query = new SelectQuery(Author.class);
        query.addOrdering(Author.NAME.desc());

        List<Author> authors = query.select(context);
        Author firstAuthor = authors.get(0);

        assertEquals(authors.size(), 3);
        assertEquals(firstAuthor.getName(), "pAuL Smith");
    }

    @Test
    public void givenAuthors_onContainsObjS_thenWeGetOneRecord() {
        List<Author> authors = ObjectSelect.query(Author.class)
          .where(Author.NAME.contains("Paul"))
          .select(context);

        assertEquals(authors.size(), 1);
    }

    @Test
    public void givenAuthors_whenLikeObjS_thenWeGetTwoAuthors() {
        List<Author> authors = ObjectSelect.query(Author.class)
          .where(Author.NAME.likeIgnoreCase("Paul%"))
          .select(context);

        assertEquals(authors.size(), 2);
    }

    @Test
    public void givenTwoAuthor_whenEndsWithObjS_thenWeGetOrderedAuthors() {
        List<Author> authors = ObjectSelect.query(Author.class)
          .where(Author.NAME.endsWith("Sarra"))
          .select(context);
        Author firstAuthor = authors.get(0);

        assertEquals(authors.size(), 1);
        assertEquals(firstAuthor.getName(), "Vicky Sarra");
    }

    @Test
    public void givenTwoAuthor_whenInObjS_thenWeGetAuthors() {
        List<String> names = Arrays.asList("Paul Xavier", "pAuL Smith", "Vicky Sarra");
        List<Author> authors = ObjectSelect.query(Author.class)
                .where(Author.NAME.in(names))
                .select(context);

        assertEquals(authors.size(), 3);
    }

    @Test
    public void givenTwoAuthor_whenNinObjS_thenWeGetAuthors() {
        List<String> names = Arrays.asList("Paul Xavier", "pAuL Smith");
        List<Author> authors = ObjectSelect.query(Author.class)
                .where(Author.NAME.nin(names))
                .select(context);
        Author author = authors.get(0);

        assertEquals(authors.size(), 1);
        assertEquals(author.getName(), "Vicky Sarra");
    }

    @Test
    public void givenTwoAuthor_whenIsNotNullObjS_thenWeGetAuthors() {
        List<Author> authors = ObjectSelect.query(Author.class)
          .where(Author.NAME.isNotNull())
          .select(context);

        assertEquals(authors.size(), 3);
    }

    @Test
    public void givenAuthors_whenFindAllEJBQL_thenWeGetThreeAuthors() {
        EJBQLQuery query = new EJBQLQuery("select a FROM Author a");
        List<Author> authors = context.performQuery(query);

        assertEquals(authors.size(), 3);
    }

    @Test
    public void givenAuthors_whenFindByNameEJBQL_thenWeGetOneAuthor() {
        EJBQLQuery query = new EJBQLQuery("select a FROM Author a WHERE a.name = 'Vicky Sarra'");
        List<Author> authors = context.performQuery(query);
        Author author = authors.get(0);

        assertEquals(authors.size(), 1);
        assertEquals(author.getName(), "Vicky Sarra");
    }

    @Test
    public void givenAuthors_whenUpdadingByNameEJBQL_thenWeGetTheUpdatedAuthor() {
        EJBQLQuery query = new EJBQLQuery("UPDATE Author AS a SET a.name = 'Vicky Edison' WHERE a.name = 'Vicky Sarra'");
        QueryResponse queryResponse = context.performGenericQuery(query);

        EJBQLQuery queryUpdatedAuthor = new EJBQLQuery("select a FROM Author a WHERE a.name = 'Vicky Edison'");
        List<Author> authors = context.performQuery(queryUpdatedAuthor);
        Author author = authors.get(0);

        assertNotNull(author);
    }

    @Test
    public void givenAuthors_whenSeletingNamesEJBQL_thenWeGetListWithSizeThree() {
        String [] args = {"Paul Xavier", "pAuL Smith", "Vicky Sarra"};
        List<String> names = Arrays.asList(args);
        EJBQLQuery query = new EJBQLQuery("select a.name FROM Author a");
        List<String> nameList = context.performQuery(query);

        Collections.sort(names);
        Collections.sort(nameList);

        assertEquals(names.size(), 3);
        assertEquals(nameList.size(), 3);
        assertEquals(names, nameList);
    }

    @Test
    public void givenAuthors_whenDeletingAllWithEJB_thenWeGetNoAuthor() {
        EJBQLQuery deleteQuery = new EJBQLQuery("delete FROM Author");
        EJBQLQuery findAllQuery = new EJBQLQuery("select a FROM Author a");

        context.performQuery(deleteQuery);
        List<Author> objects = context.performQuery(findAllQuery);

        assertEquals(objects.size(), 0);
    }

    @Test
    public void givenAuthors_whenInsertingSQLExec_thenWeGetNewAuthor() {
        int inserted = SQLExec
          .query("INSERT INTO Author (name) VALUES ('Baeldung')")
          .update(context);

        assertEquals(inserted, 1);
    }

    @Test
    public void givenAuthors_whenUpdatingSQLExec_thenItsUpdated() {
        int updated = SQLExec
          .query("UPDATE Author SET name = 'Baeldung' WHERE name = 'Vicky Sarra'")
          .update(context);

        assertEquals(updated, 1);
    }
}
