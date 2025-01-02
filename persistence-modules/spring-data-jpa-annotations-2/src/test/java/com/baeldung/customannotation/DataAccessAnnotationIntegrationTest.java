package com.baeldung.customannotation;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.sameInstance;
import static org.junit.Assert.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { CustomAnnotationConfiguration.class })
public class DataAccessAnnotationIntegrationTest {

    @DataAccess(entity = Person.class)
    private GenericDAO<Person> personGenericDAO;
    @DataAccess(entity = Account.class)
    private GenericDAO<Account> accountGenericDAO;
    @DataAccess(entity = Person.class)
    private GenericDAO<Person> anotherPersonGenericDAO;

    @Test
    public void whenGenericDAOInitialized_thenNotNull() {
        assertThat(personGenericDAO, is(notNullValue()));
        assertThat(accountGenericDAO, is(notNullValue()));
    }

    @Test
    public void whenGenericDAOInjected_thenItIsSingleton() {
        assertThat(personGenericDAO, not(sameInstance(accountGenericDAO)));
        assertThat(personGenericDAO, not(equalTo(accountGenericDAO)));

        assertThat(personGenericDAO, sameInstance(anotherPersonGenericDAO));
    }

    @Test
    public void whenFindAll_thenMessagesIsCorrect() {
        personGenericDAO.findAll();
        assertThat(personGenericDAO.getMessage(), is("Would create findAll query from Person"));

        accountGenericDAO.findAll();
        assertThat(accountGenericDAO.getMessage(), is("Would create findAll query from Account"));
    }

    @Test
    public void whenPersist_thenMakeSureThatMessagesIsCorrect() {
        personGenericDAO.persist(new Person());
        assertThat(personGenericDAO.getMessage(), is("Would create persist query from Person"));

        accountGenericDAO.persist(new Account());
        assertThat(accountGenericDAO.getMessage(), is("Would create persist query from Account"));
    }
}
