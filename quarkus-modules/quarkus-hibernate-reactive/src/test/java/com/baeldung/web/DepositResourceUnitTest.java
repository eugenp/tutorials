package com.baeldung.web;

import com.baeldung.entity.Deposit;
import com.baeldung.repository.DepositRepository;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;

import java.util.List;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;

@QuarkusTest
public class DepositResourceUnitTest {
    @Inject
    DepositRepository repository;


    @Test
    public void testGetAllDeposit() {
        given()
                .when().get("/deposits")
                .then()
                .statusCode(200);
    }

    @Test
    public void testStreamDeposits() {
        Deposit deposit1 = new Deposit("67890", "USD", "200.0");
        Deposit deposit2 = new Deposit("67890", "USD", "300.0");
        repository.save(deposit1).await().indefinitely();
        repository.save(deposit2).await().indefinitely();

        List<Deposit> deposits = given()
                .when().get("/deposits/stream")
                .then()
                .statusCode(200)
                .extract().body().jsonPath().getList(".", Deposit.class);

        assertEquals(3, deposits.size());
    }
    @Test
    public void testDepositGivenRestThenCheckResponse() {
        given()
                .body("{\"depositCode\":\"DEP20230201\", \"amount\":\"10.0\", \"Currency\":\"USD\"}")
                .header("Content-Type", "application/json")
                .when().post("/deposits")
                .then()
                .statusCode(201);
    }


}