package com.baeldung.examples.r2dbc;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;

import io.r2dbc.spi.ConnectionFactory;
import reactor.core.publisher.Flux;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class R2dbcExampleApplicationIntegrationTest {
    
    
    @Autowired
    private WebTestClient webTestClient;
    
    @Autowired
    ConnectionFactory cf;
    
    @Before
    public void initDatabase() {
        
        Flux.from(cf.create())
          .flatMap(c -> 
            c.createBatch()
              .add("drop table if exists Account")
              .add("create table Account(id IDENTITY(1,1), iban varchar(80) not null, balance DECIMAL(18,2) not null)")
              .add("insert into Account(iban,balance) values ( 'BR430120980198201982', 100.00 ) ")
              .add("insert into Account(iban,balance) values ( 'BR430120998729871000', 250.00 ) ")
              .execute()
          )
          .log()
          .blockLast();
    }

    @Test
    public void givenExistingAccountId_whenGetAccount_thenReturnExistingAccountInfo() {
        
        webTestClient
          .get()
          .uri("/accounts/1")
          .accept(MediaType.APPLICATION_JSON)
          .exchange()
          .expectStatus()
            .isOk()
          .expectBody(Account.class)
            .value((acc) -> {
                assertThat(acc.getId(),is(1l));
            });
    }
    
    @Test
    public void givenDatabaseHasSomeAccounts_whenGetAccount_thenReturnExistingAccounts() {
        
        webTestClient
          .get()
          .uri("/accounts")
          .accept(MediaType.APPLICATION_JSON)
          .exchange()
          .expectStatus()
            .isOk()
          .expectBody(List.class)
            .value((accounts) -> {
                assertThat(accounts.size(),not(is(0)));
            });
    }
    
    
    @Test
    public void givenNewAccountData_whenPostAccount_thenReturnNewAccountInfo() {

        webTestClient
        .post()
        .uri("/accounts")
        .syncBody(new Account(null,"BR4303010298012098", 151.00 ))
        .accept(MediaType.APPLICATION_JSON)
        .exchange()
        .expectStatus()
          .is2xxSuccessful()
        .expectBody(Account.class)
          .value((acc) -> {
              assertThat(acc.getId(),is(notNullValue()));
          });
        
    }

}
