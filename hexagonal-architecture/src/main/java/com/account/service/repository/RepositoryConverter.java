package com.account.service.repository;

import com.account.service.domain.Account;
import com.account.service.domain.Transfer;
import com.account.service.repository.command.InsertAccountCommand;
import com.account.service.repository.command.InsertTransferCommand;
import com.account.service.repository.command.UpdateAccountCommand;
import com.account.service.repository.jpa.AccountEntity;
import com.account.service.repository.jpa.TransferEntity;
import lombok.experimental.UtilityClass;

import java.time.Instant;

@UtilityClass
public class RepositoryConverter {

  public static TransferEntity toTransferEntity(final InsertTransferCommand command) {
    return TransferEntity.builder()
      .amount(command.getAmount())
      .senderAccountId(command.getSenderAccountId())
      .receiverAccountId(command.getReceiverAccountId())
      .result(command.getResult())
      .detail(command.getDetail())
      .createdAt(Instant.now())
      .build();
  }

  public static Transfer toTransfer(final TransferEntity entity) {
    return Transfer.builder()
      .id(entity.getId())
      .amount(entity.getAmount())
      .senderAccountId(entity.getSenderAccountId())
      .receiverAccountId(entity.getReceiverAccountId())
      .result(entity.getResult())
      .detail(entity.getDetail())
      .createdAt(entity.getCreatedAt())
      .build();
  }

  public static Account toAccount(final AccountEntity entity) {
    var account = new Account();
    account.setId(entity.getId());
    account.setBalance(entity.getBalance());
    account.setName(entity.getName());
    account.setOwner(entity.getOwner());
    account.setAccountId(entity.getAccountId());
    account.setCreatedAt(entity.getCreatedAt());
    return account;
  }

  public static AccountEntity toAccountEntity(final UpdateAccountCommand command) {
    return AccountEntity.builder()
      .id(command.getId())
      .balance(command.getBalance())
      .name(command.getName())
      .owner(command.getOwner())
      .accountId(command.getAccountId())
      .createdAt(Instant.now())
      .build();
  }

  public static AccountEntity toAccountEntity(final InsertAccountCommand command) {
    return AccountEntity.builder()
      .balance(command.getBalance())
      .name(command.getName())
      .owner(command.getOwner())
      .accountId(command.getAccountId())
      .createdAt(Instant.now())
      .build();
  }
}
