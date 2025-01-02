package com.baeldung.web;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.assertEquals;

import jakarta.inject.Inject;

import org.junit.jupiter.api.Test;

import com.baeldung.entity.Deposit;
import com.baeldung.repository.DepositRepository;

import io.quarkus.test.hibernate.reactive.panache.TransactionalUniAsserter;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.vertx.RunOnVertxContext;
import io.restassured.RestAssured;
import io.restassured.response.Response;

@QuarkusTest
public class DepositResourceIntegrationTest {

    @Inject
    DepositRepository repository;

    @Test
    public void givenAccountWithDeposits_whenGetDeposits_thenReturnAllDeposits() {
        given().when()
            .get("/deposits")
            .then()
            .statusCode(200);
    }

    @Test
    @RunOnVertxContext
    public void givenDeposit_whenSaveDepositCalled_thenCheckCount(TransactionalUniAsserter asserter) {
        asserter.execute(() -> repository.save(new Deposit("DEP20230201", "10", "USD")));
        asserter.assertEquals(() -> Deposit.count(), 2l);
    }

    @Test
    public void givenDepositsInDatabase_whenStreamAllDeposits_thenDepositsAreStreamedWithDelay() {
        Deposit deposit1 = new Deposit("67890", "USD", "200.0");
        Deposit deposit2 = new Deposit("67891", "USD", "300.0");
        repository.save(deposit1)
            .await()
            .indefinitely();
        repository.save(deposit2)
            .await()
            .indefinitely();
        Response response = RestAssured.get("/deposits/stream")
            .then()
            .extract()
            .response();

        // Then: the response contains the streamed books with a delay
        response.then()
            .statusCode(200);
        response.then()
            .body("$", hasSize(2));
        response.then()
            .body("[0].depositCode", equalTo("67890"));
        response.then()
            .body("[1].depositCode", equalTo("67891"));
    }
}
