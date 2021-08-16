package com.baeldung.examples.hexagonal;

import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class AccountTest {

  @Test
  public void whenDepositNegativeAmount_thenExpectRunTimeException() {
    Account account = new Account(UUID.randomUUID(), 100D);

    RuntimeException runtimeException =
            assertThrows(RuntimeException.class, () -> account.deposit(-12D));
    assertEquals("Invalid amount", runtimeException.getMessage());
  }

  @Test
  public void whenDepositValidAmount_thenExpectBalanceIncrease() {
    Account account = new Account(UUID.randomUUID(), 100D);
    account.deposit(10D);
    assertEquals(110D, account.getBalance());
  }

  @Test
  public void whenWithdrawNegativeAmount_thenExpectException() {
    Account account = new Account(UUID.randomUUID(), 100D);
    RuntimeException runtimeException =
            assertThrows(RuntimeException.class, () -> account.withdrawal(-20D));
    assertEquals("Invalid amount", runtimeException.getMessage());
  }

  @Test
  public void whenWithdrawMoreThenBalanceAmount_thenExpectException() {
    Account account = new Account(UUID.randomUUID(), 100D);
    RuntimeException runtimeException =
            assertThrows(RuntimeException.class, () -> account.withdrawal(200D));
    assertEquals("Insufficient balance", runtimeException.getMessage());
  }

  @Test
  public void whenWithdrawAmount_thenExpectBalanceDecrease() {
    Account account = new Account(UUID.randomUUID(), 100D);
    account.withdrawal(10D);
    assertEquals(90D, account.getBalance());
  }
}
