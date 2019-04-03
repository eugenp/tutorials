package com.baeldung;
/*
 * created by pareshP on 03/04/19
 */

import com.baeldung.adapters.CacheStorageAdapter;
import com.baeldung.adapters.PersistentStorageAdapter;
import com.baeldung.persistence.model.Account;
import com.baeldung.persistence.repo.AccountRepository;
import com.baeldung.web.AccountServiceController;
import com.baeldung.web.exception.AccountNotFoundException;
import com.baeldung.web.helper.StorageAdapterProvider;
import com.fasterxml.jackson.databind.ObjectMapper;
import mockit.Deencapsulation;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.HashMap;
import java.util.Map;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;
import static org.apache.commons.lang3.RandomStringUtils.randomAlphanumeric;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@RunWith(SpringRunner.class)
public class AccountServiceIntegrationTest {

    private static final String ACCOUNT_API = "http://localhost:8080/api/account";

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    @Autowired
    private AccountServiceController accountServiceController;

    @Autowired
    private AccountRepository accountRepository;

    private CacheStorageAdapter cacheAdapter;

    @MockBean
    private StorageAdapterProvider storageAdapterProvider;

    private MockMvc mockMvc;

    @Before
    public void setUp() {
        this.cacheAdapter = new CacheStorageAdapter();

        PersistentStorageAdapter persistentAdapter = new PersistentStorageAdapter(accountRepository);

        this.storageAdapterProvider
          = new StorageAdapterProvider(cacheAdapter, persistentAdapter, false);

        this.accountServiceController = new AccountServiceController(storageAdapterProvider);

        mockMvc = MockMvcBuilders.standaloneSetup(accountServiceController).build();
    }

    @Test
    public void whenCreateAccount_thenOk() throws Exception {
        final Account account = createMockAccount();

        final MvcResult result = mockMvc.perform(
          put(ACCOUNT_API + "/create")
          .content(OBJECT_MAPPER.writeValueAsString(account))
          .contentType(MediaType.APPLICATION_JSON))
          .andReturn();

        assertEquals(HttpStatus.CREATED.value(), result.getResponse().getStatus());
    }

    @Test
    public void givenAccountExists_whenReturnAccount_thenOk() throws Exception {
        final Account account = createMockAccount();

        setPrerequisites(account, false);

        final MvcResult result = mockMvc.perform(
          get(ACCOUNT_API + "/get" + "/" + account.getAccountId()))
            .andReturn();

        assertEquals(HttpStatus.OK.value(), result.getResponse().getStatus());
        assertNotNull(result.getResponse().getContentAsString());
    }

    @Test
    public void givenAccountExists_whenUpdateAccount_thenOk() throws Exception {
        final Account account = createMockAccount();

        setPrerequisites(account, false);

        final MvcResult result = mockMvc.perform(
          post(ACCOUNT_API + "/update")
          .contentType(MediaType.APPLICATION_JSON)
          .content(OBJECT_MAPPER.writeValueAsString(account)))
          .andReturn();

        assertEquals(HttpStatus.OK.value(), result.getResponse().getStatus());
    }

    @Test
    public void givenAccountExists_whenDeleteAccount_thenOk() throws Exception {
        final Account account = createMockAccount();

        setPrerequisites(account, false);

        final MvcResult result = mockMvc.perform(
          delete(ACCOUNT_API + "/delete" + "/" + account.getAccountId()))
          .andReturn();

        assertEquals(HttpStatus.OK.value(), result.getResponse().getStatus());
        assertNotNull(result.getResponse().getContentAsString());
    }

    @Test
    public void givenAccountDoesNotExist_whenReturnAccount_thenThrowException() {
        try {
            mockMvc.perform(
              get(ACCOUNT_API + "/get" + "/" + "non-existent-account-id"));
        } catch (Exception e) {
            assertTrue(e.getCause() instanceof AccountNotFoundException);
            assertNotNull(e.getMessage());
        }
    }

    @Test
    public void givenAccountDoesNotExist_whenUpdateAccount_thenThrowException() {
        try {
            mockMvc.perform(
              post(ACCOUNT_API + "/update")
                .contentType(MediaType.APPLICATION_JSON)
                .content(OBJECT_MAPPER.writeValueAsString(createMockAccount())));
        } catch (Exception e) {
            assertTrue(e.getCause() instanceof AccountNotFoundException);
            assertNotNull(e.getMessage());
        }
    }

    @Test
    public void givenPersistenceTrue_whenCreateAccount_thenCreated() throws Exception {
        enablePersistentAdapter();
        whenCreateAccount_thenOk();
    }

    @Test
    public void givenPersistentAccountExists_whenReturnAccount_thenOk() throws Exception {

        enablePersistentAdapter();

        Account account = createMockAccount();

        account = setPrerequisites(account, true);

        final MvcResult result = mockMvc.perform(
          get(ACCOUNT_API + "/get" + "/" + account.getAccountId()))
          .andReturn();

        assertEquals(HttpStatus.OK.value(), result.getResponse().getStatus());
        assertNotNull(result.getResponse().getContentAsString());
    }

    @Test
    public void givenPersistentAccountExists_whenUpdateAccount_thenOk() throws Exception {
        enablePersistentAdapter();

        Account account = createMockAccount();

        account = setPrerequisites(account, true);

        account.setName("other-name");

        MvcResult result = mockMvc.perform(
          post(ACCOUNT_API + "/update")
          .contentType(MediaType.APPLICATION_JSON)
          .content(OBJECT_MAPPER.writeValueAsString(account)))
          .andReturn();

        assertEquals(HttpStatus.OK.value(), result.getResponse().getStatus());
        assertNotNull(result.getResponse().getContentAsString());
        assertTrue(result.getResponse().getContentAsString().contains("other-name"));
    }

    @Test
    public void givenPersistentAccountExists_whenDeleteAccount_thenOk() throws Exception {
        enablePersistentAdapter();

        Account account = createMockAccount();

        account = setPrerequisites(account, true);

        final MvcResult result = mockMvc.perform(
          delete(ACCOUNT_API + "/delete" + "/" + account.getAccountId()))
          .andReturn();

        assertEquals(HttpStatus.OK.value(), result.getResponse().getStatus());
        assertNotNull(result.getResponse().getContentAsString());
        assertEquals(Boolean.TRUE.toString(), result.getResponse().getContentAsString());
    }

    private void enablePersistentAdapter() {
        Deencapsulation.setField(
          storageAdapterProvider, "persistenceEnabled", true);
        Deencapsulation.setField(
          accountServiceController, "adapterProvider", storageAdapterProvider);
    }

    private Account createMockAccount() {
        Account account = new Account();

        account.setAccountId(randomAlphanumeric(10));
        account.setEmail("a.b@gmail.com");
        account.setName(randomAlphabetic(10));

        return account;
    }

    private Account setPrerequisites(Account account, boolean persistenceEnabled) {
        if (persistenceEnabled) {
            return accountRepository.save(account);
        } else {
            Map<String, Account> accountCache = new HashMap<>();
            accountCache.put(account.getAccountId(), account);

            Deencapsulation.setField(cacheAdapter, "accountCache", accountCache);

            return account;
        }
    }
}
