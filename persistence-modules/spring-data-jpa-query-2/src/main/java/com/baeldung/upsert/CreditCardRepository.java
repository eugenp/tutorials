package com.baeldung.upsert;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

public interface CreditCardRepository extends JpaRepository<CreditCard, Long> {
    @Transactional
    default CreditCard updateOrInsert(CreditCard entity) {
        return save(entity);
    }

    CreditCard findByCardNumber(String cardNumber);
}
