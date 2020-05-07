package com.baeldung.hexagonal.banking.output.adapter;

import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name = "account")
@Data
public class AccountJpaEntity {

    @Id
    private Long accountNumber;
    private int pin;
    private BigDecimal balance;
    private int accountHolderIdNumber;
    private String firstName;
    private String lastName;

    public AccountJpaEntity() {
        super();
    }

    public AccountJpaEntity(Long accountNumber, int pin, BigDecimal balance, int accountHolderIdNumber, String firstName, String lastName) {
        super();
        this.accountNumber = accountNumber;
        this.pin = pin;
        this.balance = balance;
        this.accountHolderIdNumber = accountHolderIdNumber;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public Long getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(Long accountNumber) {
        this.accountNumber = accountNumber;
    }

    public int getPin() {
        return pin;
    }

    public void setPin(int pin) {
        this.pin = pin;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public int getAccountHolderIdNumber() {
        return accountHolderIdNumber;
    }

    public void setAccountHolderIdNumber(int accountHolderIdNumber) {
        this.accountHolderIdNumber = accountHolderIdNumber;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

}
