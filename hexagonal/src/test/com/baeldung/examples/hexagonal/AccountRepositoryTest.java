package com.baeldung.examples.hexagonal;

import com.baeldung.examples.hexagonal.Account;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Optional;
import java.util.UUID;

class AccountRepositoryTest {

  private static AccountRepository accountRepository;

  @BeforeAll
  public static void initiateInmemoryDataBase() {
    accountRepository = new InMemoryAccountRepository();
  }

  @Test
  public void whenSaveAccount_ThenExpectItSave() {
    UUID uuid = UUID.randomUUID();
    Account account = new Account(uuid, 100D);
    accountRepository.save(account);
    Optional<Account> byId = accountRepository.findById(uuid);
    Assertions.assertTrue(byId.isPresent());
    Assertions.assertEquals(byId.get(), account);
  }

  @Test
  public void givenInvalidId_thenReturnEmptyOptional() {
    Optional<Account> byId = accountRepository.findById(UUID.randomUUID());
    Assertions.assertFalse(byId.isPresent());
  }
}
