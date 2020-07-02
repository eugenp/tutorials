package com.baeldung.batch.model;

import com.baeldung.batch.service.adapter.LocalDateTimeAdapter;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.time.LocalDateTime;

@SuppressWarnings("restriction")
@XmlRootElement(name = "transactionRecord")
public class Transaction {
    private String username;
    private int userId;
    private int age;
    private String postCode;
    private LocalDateTime transactionDate;
    private double amount;

    /* getters and setters for the attributes */

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    @XmlJavaTypeAdapter(LocalDateTimeAdapter.class)
    public LocalDateTime getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(LocalDateTime transactionDate) {
        this.transactionDate = transactionDate;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getPostCode() {
        return postCode;
    }

    public void setPostCode(String postCode) {
        this.postCode = postCode;
    }

    @Override
    public String toString() {
        return "Transaction [username=" + username + ", userId=" + userId + ", age=" + age + ", postCode=" + postCode + ", transactionDate=" + transactionDate + ", amount=" + amount + "]";
    }

}
