package com.baeldung.entity;

import jakarta.persistence.Entity;

import io.quarkus.hibernate.reactive.panache.PanacheEntity;
import io.vertx.mutiny.sqlclient.Row;

@Entity
public class Deposit extends PanacheEntity {

    private String depositCode;
    private String currency;
    private String amount;

    public static Deposit from(Row row) {
        return new Deposit(row.getString("DEPOSITCODE"), row.getString("CURRENCY"), row.getString("AMOUNT"));
    }

    public Deposit() {
    }

    public Deposit(String depositCode, String currency, String amount) {
        this.depositCode = depositCode;
        this.currency = currency;
        this.amount = amount;
    }

    public String getDepositCode() {
        return depositCode;
    }

    public void setDepositCode(String depositCode) {
        this.depositCode = depositCode;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }
}
