package com.baeldung.boot.architecture.hexagonal.entity;


import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name="TRANSACTIONS")
public class Transaction implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    int id;
    int amount;
    String fromUser;
    String toUser;

    public Transaction() {
    }

    public Transaction(int amount, String fromUser, String toUser) {
        this.amount = amount;
        this.fromUser = fromUser;
        this.toUser = toUser;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getFromUser() {
        return fromUser;
    }

    public void setFromUser(String fromUser) {
        this.fromUser = fromUser;
    }

    public String getToUser() {
        return toUser;
    }

    public void setToUser(String toUser) {
        this.toUser = toUser;
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "id=" + id +
                ", amount=" + amount +
                ", fromUser='" + fromUser + '\'' +
                ", toUser='" + toUser + '\'' +
                '}';
    }
}
