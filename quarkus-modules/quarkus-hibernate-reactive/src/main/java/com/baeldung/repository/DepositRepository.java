package com.baeldung.repository;

import com.baeldung.entity.Deposit;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.jdbcclient.JDBCPool;
import io.vertx.mutiny.sqlclient.Row;
import io.vertx.mutiny.sqlclient.RowSet;
import io.vertx.mutiny.sqlclient.Tuple;
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
        return sessionFactory.withTransaction((session, transaction) -> session.persist(deposit).replaceWith(deposit));
    }

    public Multi<Deposit> streamAll() {
        return sessionFactory.withSession(session -> session
                        .createQuery("FROM Deposit ", Deposit.class)
                        .getResultList())
                .onItem().transformToMulti(deposits -> Multi.createFrom().iterable(deposits));
    }

}
