package com.baeldung.hexagonal.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.xml.bind.annotation.XmlRootElement;
import java.math.BigDecimal;

@Entity
@XmlRootElement
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private BigDecimal balance;

    public Account() {
    }

    public Account(BigDecimal initialDeposit) {
        this.balance = initialDeposit;
    }

    public Long getId() {
        return id;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public BigDecimal setBalance(BigDecimal balance) {
        this.balance = balance;
        return this.balance;
    }

    @Override
    public String toString() {
        return String.format("Account[id=%d, balance='%9.2f']", id, balance);
    }

}
