package com.baeldung.jpa.hibernateunproxy;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.ManyToOne;
import java.math.BigDecimal;
import java.util.Objects;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    protected WebUser webUser;

    protected BigDecimal amount;

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;

        Payment payment = (Payment) o;

        return Objects.equals(id, payment.id);
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

    protected Payment() {
    }

}
