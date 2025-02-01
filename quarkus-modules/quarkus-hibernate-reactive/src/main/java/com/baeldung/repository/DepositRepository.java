package com.baeldung.repository;

import com.baeldung.entity.Deposit;

import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.jdbcclient.JDBCPool;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import org.hibernate.reactive.mutiny.Mutiny;

@ApplicationScoped
public class DepositRepository {

    @Inject
    Mutiny.SessionFactory sessionFactory;

    @Inject
    JDBCPool client;

    public Uni<Deposit> save(Deposit deposit) {
        return sessionFactory.withTransaction((session, transaction) -> session.persist(deposit)
            .replaceWith(deposit));
    }

    public Multi<Deposit> streamAll() {
        return client.query("SELECT depositCode, currency,amount FROM Deposit ")
            .execute()
            .onItem()
            .transformToMulti(set -> Multi.createFrom()
                .iterable(set))
            .onItem()
            .transform(Deposit::from);
    }

}
