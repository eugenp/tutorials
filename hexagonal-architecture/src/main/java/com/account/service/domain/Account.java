package com.account.service.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigInteger;
import java.time.Instant;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Account {
  private Long id;
  private String accountId;
  private String name;
  private String owner;
  private BigInteger balance;
  private Instant createdAt;

  public void withdraw(final BigInteger money) {
    this.setBalance(this.getBalance().subtract(money));
  }

  public void deposit(final BigInteger money) {
    this.setBalance(this.getBalance().add(money));
  }
}
