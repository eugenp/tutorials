/**
 * 
 */
package org.baeldung.examples.r2dbc;

import java.math.BigDecimal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import io.r2dbc.spi.Connection;
import io.r2dbc.spi.ConnectionFactory;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * @author Philippe
 *
 */
@RestController
public class AccountResource {

    private static final Logger log = LoggerFactory.getLogger(AccountResource.class);

    private final ConnectionFactory connectionFactory;

    public AccountResource(ConnectionFactory cf) {
        this.connectionFactory = cf;
    }

    @GetMapping("/accounts/{id}")
    public Mono<ResponseEntity<Account>> getAccount(@PathVariable("id") Long id) {

        return Mono.from(connectionFactory.create())
            .flatMap(c -> Mono.from(c.createStatement("select id,iban,balance from Account where id = $1")
                .bind("$1", id)
                .execute())
                .doFinally((st) -> close(c)))
            .flatMap(result -> Mono.from(result.map((row, meta) -> {
                Account acc = new Account();
                acc.setId(row.get("id", Long.class));
                acc.setIban(row.get("iban", String.class));
                acc.setBalance(row.get("balance", BigDecimal.class));
                return new ResponseEntity<>(acc, HttpStatus.OK);
            })))
            .switchIfEmpty(Mono.just(new ResponseEntity<>(HttpStatus.NOT_FOUND)));
        // .log();
    }

    @GetMapping("/accounts")
    public Flux<Account> getAllAccounts() {

        return Mono.from(connectionFactory.create())
            .flatMap((c) -> Mono.from(c.createStatement("select id,iban,balance from Account")
                .execute())
                .doFinally((st) -> close(c)))
            .flatMapMany(result -> Flux.from(result.map((row, meta) -> {
                Account acc = new Account();
                acc.setId(row.get("id", Long.class));
                acc.setIban(row.get("iban", String.class));
                acc.setBalance(row.get("balance", BigDecimal.class));
                return acc;
            })))
            .switchIfEmpty(Flux.empty());
        // .log();

    }

    @PostMapping("/accounts")
    public Mono<ResponseEntity<Account>> postAccount(@RequestBody Account account) {

        return Mono.from(connectionFactory.create())
            .flatMap(c -> Mono.from(c.beginTransaction())
                .then(Mono.from(c.createStatement("insert into Account(iban,balance) values($1,$2)")
                    .bind("$1", account.getIban())
                    .bind("$2", account.getBalance())
                    .returnGeneratedValues("id")
                    .execute()))
                .flatMap(result -> Mono.from(result.map((row, meta) -> {
                    Account acc = new Account(row.get("id", Long.class), account.getIban(), account.getBalance());
                    return new ResponseEntity<>(acc, HttpStatus.CREATED);
                })))
                .flatMap(r -> Mono.from(c.commitTransaction())
                    .then(Mono.just(r)))
                .doFinally((st) -> c.close()))
            .log();
    }

    private <T> Mono<T> close(Connection connection) {
        // log.info("[I96] close: conn=" + connection.hashCode());
        return Mono.from(connection.close())
            .then(Mono.empty());
    }

}
