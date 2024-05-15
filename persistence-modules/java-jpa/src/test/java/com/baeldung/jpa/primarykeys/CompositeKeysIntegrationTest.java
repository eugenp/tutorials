package com.baeldung.jpa.primarykeys;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import com.baeldung.jpa.primarykeys.Account;
import com.baeldung.jpa.primarykeys.AccountId;
import com.baeldung.jpa.primarykeys.Book;
import com.baeldung.jpa.primarykeys.BookId;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

public class CompositeKeysIntegrationTest {

    private static final String SAVINGS_ACCOUNT = "Savings";
    private static final String ACCOUNT_NUMBER = "JXSDF324234";
    private static final String ENGLISH = "English";
    private static final String WAR_AND_PEACE = "War and Peace";

    private static EntityManagerFactory emf;
    private static EntityManager em;

    @BeforeClass
    public static void setup() {
        emf = Persistence.createEntityManagerFactory("jpa-entity-definition");
        em = emf.createEntityManager();
    }

    @Test
    public void persistBookWithCompositeKeyThenRetrieveDetails() {
        Book warAndPeace = createBook();
        persist(warAndPeace);
        clearThePersistenceContext();
        Book book = findBookByBookId();
        verifyAssertionsWith(book);
    }

    @Test
    public void persistAccountWithCompositeKeyThenRetrieveDetails() {
        Account savingsAccount = createAccount();
        persist(savingsAccount);
        clearThePersistenceContext();
        Account account = findAccountByAccountId();
        verifyAssertionsWith(account);
    }

    @AfterClass
    public static void destroy() {
        if (em != null) {
            em.close();
        }
        if (emf != null) {
            emf.close();
        }
    }

    private Account createAccount() {
        Account savingsAccount = new Account();
        savingsAccount.setAccountNumber(ACCOUNT_NUMBER);
        savingsAccount.setAccountType(SAVINGS_ACCOUNT);
        savingsAccount.setDescription("Savings account");
        return savingsAccount;
    }

    private void verifyAssertionsWith(Account account) {
        assertEquals(ACCOUNT_NUMBER, account.getAccountNumber());
        assertEquals(SAVINGS_ACCOUNT, account.getAccountType());
    }

    private Account findAccountByAccountId() {
        return em.find(Account.class, new AccountId(ACCOUNT_NUMBER, SAVINGS_ACCOUNT));
    }

    private void persist(Account account) {
        em.getTransaction()
            .begin();
        em.persist(account);
        em.getTransaction()
            .commit();
    }

    private Book findBookByBookId() {
        return em.find(Book.class, new BookId(WAR_AND_PEACE, ENGLISH));
    }

    private Book createBook() {
        BookId bookId = new BookId(WAR_AND_PEACE, ENGLISH);
        Book warAndPeace = new Book(bookId);
        warAndPeace.setDescription("Novel and Historical Fiction");
        return warAndPeace;
    }

    private void verifyAssertionsWith(Book book) {
        assertNotNull(book);
        assertNotNull(book.getBookId());
        assertEquals(WAR_AND_PEACE, book.getBookId()
            .getTitle());
        assertEquals(ENGLISH, book.getBookId()
            .getLanguage());
    }

    private void persist(Book book) {
        em.getTransaction()
            .begin();
        em.persist(book);
        em.getTransaction()
            .commit();
    }

    private void clearThePersistenceContext() {
        em.clear();
    }
}
