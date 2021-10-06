package com.account.service.api;

import com.account.service.api.dto.AccountResponse;
import com.account.service.api.dto.MoneyTransferResponse;
import com.account.service.domain.TransferResult;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigInteger;
import java.time.Instant;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

public class MoneyTransferFunctionalTest extends CommonFunctionalTest {

  @Test
  @DisplayName("Create account works successfully")
  public void shouldCreateAccountsWorksSuccessfully() {
    final var sender = "123456789";
    final var receiver = "123456788";
    final var result = postMoneyTransferAccountSuccessfully(sender, receiver);
    final var senderAccount = getAccountByIdSuccessfully(1L);
    final var receiverAccount = getAccountByIdSuccessfully(2L);

    assertNotNull(result.getId());
    assertEquals(sender, result.getSenderAccountId());
    assertEquals(receiver, result.getReceiverAccountId());
    assertEquals(BigInteger.TEN, result.getAmount());
    assertEquals(TransferResult.SUCCESSFUL, result.getResult());
    assertTrue(result.getTimeStamp().isBefore(Instant.now()));
    assertEquals(BigInteger.valueOf(990), senderAccount.getBalance());
    assertEquals(BigInteger.valueOf(1010), receiverAccount.getBalance());
  }

  private MoneyTransferResponse postMoneyTransferAccountSuccessfully(final String sender,
                                                                     final String receiver) {
    return requestSpecification()
      .body("{\n" +
        "  \"senderAccountId\": \"" + sender + "\",\n" +
        "  \"receiverAccountId\": \"" + receiver + "\",\n" +
        "  \"amount\": 10\n" +
        "}")
      .post("/accounts/transfer")
      .then()
      .log()
      .all()
      .statusCode(CREATED.value())
      .extract()
      .as(MoneyTransferResponse.class);
  }

  private AccountResponse getAccountByIdSuccessfully(final Long id) {
    return requestSpecification()
      .get("/accounts/" + id)
      .then()
      .log()
      .all()
      .statusCode(OK.value())
      .extract()
      .as(AccountResponse.class);
  }

}
