package com.baeldung.jmix.expensetracker.entity;

import io.jmix.core.entity.annotation.JmixGeneratedValue;
import io.jmix.core.metamodel.annotation.JmixEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;

import java.time.LocalDate;
import java.util.UUID;

@JmixEntity
@Table(name = "USER_EXPENSE", indexes = {
        @Index(name = "IDX_USER_EXPENSE_USER", columnList = "USER_ID"),
        @Index(name = "IDX_USER_EXPENSE_EXPENSE", columnList = "EXPENSE_ID")
})
@Entity
public class UserExpense {
    @JmixGeneratedValue
    @Column(name = "ID", nullable = false)
    @Id
    private UUID id;

    @JoinColumn(name = "USER_ID", nullable = false)
    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private User user;

    @JoinColumn(name = "EXPENSE_ID", nullable = false)
    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Expense expense;

    @Column(name = "AMOUNT", nullable = false)
    @NotNull
    private Double amount;

    @PastOrPresent(message = "{msg://com.baeldung.jmix.expensetracker.entity/UserExpense.date.validation.PastOrPresent}")
    @Column(name = "DATE_", nullable = false)
    @NotNull
    private LocalDate date;

    @Column(name = "DETAILS")
    private String details;

    @Column(name = "VERSION", nullable = false)
    @Version
    private Integer version;

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Expense getExpense() {
        return expense;
    }

    public void setExpense(Expense expense) {
        this.expense = expense;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }
}