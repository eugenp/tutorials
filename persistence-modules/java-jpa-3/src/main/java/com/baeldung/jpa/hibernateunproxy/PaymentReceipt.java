package com.baeldung.jpa.hibernateunproxy;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import java.util.Objects;
import java.util.UUID;

@Entity
public class PaymentReceipt {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    private Payment payment;

    private String transactionNumber;

    PaymentReceipt(Payment payment) {
        this.payment = payment;
        this.transactionNumber = UUID.randomUUID().toString();
    }

    public Payment getPayment() {
        return payment;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;

        PaymentReceipt that = (PaymentReceipt) o;

        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

    protected PaymentReceipt() {
    }

}
