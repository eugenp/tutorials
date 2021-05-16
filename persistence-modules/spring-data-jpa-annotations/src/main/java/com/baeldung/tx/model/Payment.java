package com.baeldung.tx.model;

import javax.persistence.*;

@Entity
public class Payment {

    @Id
    @GeneratedValue
    private Long id;

    private Long amount;

    @Column(unique = true)
    private String referenceNumber;

    @Enumerated(EnumType.STRING)
    private State state;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getAmount() {
        return amount;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }

    public String getReferenceNumber() {
        return referenceNumber;
    }

    public void setReferenceNumber(String referenceNumber) {
        this.referenceNumber = referenceNumber;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public enum State {
        STARTED, FAILED, SUCCESSFUL
    }
}
