package com.baeldung.entity;

import io.quarkus.hibernate.reactive.panache.PanacheEntity;
import io.vertx.mutiny.sqlclient.Row;

import jakarta.persistence.Entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Deposit extends PanacheEntity {

    private String depositCode;
    private String currency;
    private String amount;

    public static Deposit from(Row row) {
        return new Deposit(row.getString("DEPOSITCODE"), row.getString("CURRENCY"), row.getString("AMOUNT"));
    }

}
