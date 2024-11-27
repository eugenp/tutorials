package com.baeldung.upsert;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;

@Entity
@Table(name = "credit_card")
public class CreditCard {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "credit_card_id_seq")
    @SequenceGenerator(name = "credit_card_id_seq", sequenceName = "credit_card_id_seq", allocationSize = 1)
    private Long id;
    private String cardNumber;
    private String expiryDate;

    private Long customerId;

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(String expiryDate) {
        this.expiryDate = expiryDate;
    }

    public CreditCard() {
    }

    @Override
    public String toString() {
        return "CreditCard{" + "id=" + id + ", cardNumber='" + cardNumber + '\'' + ", expiryDate='" + expiryDate + '\'' + ", customerId=" + customerId + '}';
    }
}
