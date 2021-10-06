package com.account.service.api;

import com.account.service.api.dto.AccountResponse;
import com.account.service.api.dto.AccountsResponse;
import com.account.service.api.dto.ErrorResponse;
import com.account.service.domain.ErrorCode;
import com.jayway.restassured.response.ExtractableResponse;
import com.jayway.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigInteger;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.NO_CONTENT;
import static org.springframework.http.HttpStatus.OK;

public class AccountFunctionalTest extends CommonFunctionalTest {

  @Test
  @DisplayName("Create account works successfully")
  public void shouldCreateAccountsWorksSuccessfully() {
    final var result = postCreateAccountSuccessfully("12345678");

    assertNotNull(result.getId());
    assertEquals("12345678", result.getAccountId());
    assertEquals("Saving Account", result.getName());
    assertEquals("John Doe", result.getOwner());
    assertEquals(BigInteger.TEN, result.getBalance());
  }

  @Test
  @DisplayName("Retrieve account list works successfully")
  public void shouldRetrieveAccountsWorksSuccessfully() {
    final var result = getAccountsSuccessfully();

    assertNotNull(result.getAccounts());
    assertEquals(1L, result.getAccounts().get(0).getId());
    assertEquals("123456789", result.getAccounts().get(0).getAccountId());
    assertEquals("Main Account", result.getAccounts().get(0).getName());
    assertEquals("John Doe", result.getAccounts().get(0).getOwner());
    assertEquals(BigInteger.valueOf(1000), result.getAccounts().get(0).getBalance());
    assertEquals(2L, result.getAccounts().get(1).getId());
    assertEquals("123456788", result.getAccounts().get(1).getAccountId());
    assertEquals("Saving Account", result.getAccounts().get(1).getName());
    assertEquals("Jane Doe", result.getAccounts().get(1).getOwner());
    assertEquals(BigInteger.valueOf(1000), result.getAccounts().get(1).getBalance());
  }

  @Test
  @DisplayName("Retrieve account by id works successfully")
  public void shouldRetrieveAccountByIdWorksSuccessfully() {
    final var result = getAccountByIdSuccessfully(1L);

    assertEquals(1L, result.getId());
    assertEquals("123456789", result.getAccountId());
    assertEquals("Main Account", result.getName());
    assertEquals("John Doe", result.getOwner());
    assertEquals(BigInteger.valueOf(1000), result.getBalance());
  }

  @Test
  @DisplayName("Retrieve by id returns not found error for non exist account id")
  public void shouldRetrieveByIdReturnNonFound() {
    final var result = getAccountByIdNotFound(99999L);

    assertEquals(NOT_FOUND.value(), result.getStatus());
    assertEquals(ErrorCode.ACCOUNT_NOT_FOUND.name(), result.getCode());
    assertEquals(ErrorCode.ACCOUNT_NOT_FOUND.getStatus().name(), result.getType());
  }


  @Test
  @DisplayName("Delete account works successfully")
  public void shouldDeleteUserWorksSuccessfully() {
    final var created = postCreateAccountSuccessfully("ABCDEFG");
    deleteAccountSuccessfully(created.getId());
    final var result = getAccountByIdNotFound(created.getId());

    assertEquals(NOT_FOUND.value(), result.getStatus());
    assertEquals(ErrorCode.ACCOUNT_NOT_FOUND.name(), result.getCode());
    assertEquals(ErrorCode.ACCOUNT_NOT_FOUND.getStatus().name(), result.getType());
  }

  private AccountResponse postCreateAccountSuccessfully(final String accountId) {
    return requestSpecification()
      .body("{\n"
        + "  \"accountId\": \"" + accountId + "\",\n"
        + "  \"name\": \"Saving Account\",\n"
        + "  \"owner\": \"John Doe\",\n"
        + "  \"balance\": \"10\"\n"
        + "}")
      .post("/accounts")
      .then()
      .log()
      .all()
      .statusCode(CREATED.value())
      .extract()
      .as(AccountResponse.class);
  }

  private AccountsResponse getAccountsSuccessfully() {
    return requestSpecification()
      .get("/accounts")
      .then()
      .log()
      .all()
      .statusCode(OK.value())
      .extract()
      .as(AccountsResponse.class);
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

  private ErrorResponse getAccountByIdNotFound(final Long id) {
    return requestSpecification()
      .get("/accounts/" + id)
      .then()
      .log()
      .all()
      .statusCode(NOT_FOUND.value())
      .extract()
      .as(ErrorResponse.class);
  }

  private ExtractableResponse<Response> deleteAccountSuccessfully(final Long id) {
    return requestSpecification()
      .delete("/accounts/" + id)
      .then()
      .log()
      .all()
      .statusCode(NO_CONTENT.value())
      .extract();
  }


}
