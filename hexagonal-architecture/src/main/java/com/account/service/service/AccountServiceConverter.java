package com.account.service.service;

import com.account.service.repository.command.InsertAccountCommand;
import com.account.service.service.command.CreateAccountCommand;
import lombok.experimental.UtilityClass;

import java.math.BigInteger;
import java.util.Optional;

@UtilityClass
public class AccountServiceConverter {
  public static InsertAccountCommand toInsertCommand(final CreateAccountCommand command) {
    return InsertAccountCommand.builder()
      .accountId(command.getAccountId())
      .name(command.getName())
      .owner(command.getOwner())
      .balance(Optional.ofNullable(command.getBalance())
        .orElse(BigInteger.ZERO))
      .build();
  }
}
