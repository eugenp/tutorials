package com.account.service.api;

import com.account.service.api.dto.MoneyTransferRequest;
import com.account.service.api.dto.MoneyTransferResponse;
import com.account.service.service.AccountUseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.HttpStatus.CREATED;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping(value = "accounts/transfer")
public class MoneyTransferController {

  private final AccountUseCase service;

  @PostMapping()
  @ResponseStatus(CREATED)
  public MoneyTransferResponse transferMoney(@RequestBody final MoneyTransferRequest request) {
    log.info("Transfer money with request : {}", request);
    return AccountApiConverter.toMoneyTransferResponse(service.transferMoney(AccountApiConverter.toCommand(request)));
  }

}
