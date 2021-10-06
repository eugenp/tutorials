package com.account.service.api;

import com.account.service.api.dto.AccountRequest;
import com.account.service.api.dto.AccountResponse;
import com.account.service.api.dto.MoneyTransferRequest;
import com.account.service.api.dto.MoneyTransferResponse;
import com.account.service.domain.Account;
import com.account.service.domain.Transfer;
import com.account.service.service.command.CreateAccountCommand;
import com.account.service.service.command.SendMoneyCommand;
import lombok.experimental.UtilityClass;

@UtilityClass
public class AccountApiConverter {

  public static MoneyTransferResponse toMoneyTransferResponse(final Transfer transfer) {
    return MoneyTransferResponse.builder()
      .id(transfer.getId())
      .senderAccountId(transfer.getSenderAccountId())
      .receiverAccountId(transfer.getReceiverAccountId())
      .amount(transfer.getAmount())
      .timeStamp(transfer.getCreatedAt())
      .result(transfer.getResult())
      .build();
  }

  public static AccountResponse toAccountResponse(final Account account) {
    return AccountResponse.builder()
      .id(account.getId())
      .accountId(account.getAccountId())
      .name(account.getName())
      .owner(account.getOwner())
      .balance(account.getBalance())
      .createdAt(account.getCreatedAt())
      .build();
  }

  public static SendMoneyCommand toCommand(final MoneyTransferRequest request) {
    return SendMoneyCommand.builder()
      .senderAccountId(request.getSenderAccountId())
      .receiverAccountId(request.getReceiverAccountId())
      .amount(request.getAmount())
      .build();
  }

  public static CreateAccountCommand toCreateAccountCommand(final AccountRequest request) {
    return CreateAccountCommand.builder()
      .accountId(request.getAccountId())
      .name(request.getName())
      .owner(request.getOwner())
      .balance(request.getBalance())
      .build();
  }
}
