package com.baeldung.upsert;

import java.math.BigInteger;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baeldung.upsert.CreditCardRepository;

@Service
public class CreditCardLogic {

    @Autowired
    private CreditCardRepository creditCardRepository;

    @Autowired
    private EntityManager em;

    public void updateOrInsertUsingCustomLogic(CreditCard creditCard) {
        CreditCard existingCard = creditCardRepository.findByCardNumber(creditCard.getCardNumber());
        if (existingCard != null) {
            existingCard.setExpiryDate(creditCard.getExpiryDate());
            creditCardRepository.save(creditCard);
        } else {
            creditCardRepository.save(creditCard);
        }
    }

    @Transactional
    public void updateOrInsertUsingBuiltInFeature(CreditCard creditCard) {
        Long id = creditCard.getId();
        if (creditCard.getId() == null) {
            Long nextVal = (Long) em.createNativeQuery("SELECT nextval('credit_card_id_seq')")
                .getSingleResult();
            id = nextVal.longValue();
        }

        String upsertQuery = """
            MERGE INTO credit_card (id, card_number, expiry_date, customer_id)
            KEY(card_number)
            VALUES (?, ?, ?, ?)
            """;

        Query query = em.createNativeQuery(upsertQuery);
        query.setParameter(1, id);
        query.setParameter(2, creditCard.getCardNumber());
        query.setParameter(3, creditCard.getExpiryDate());
        query.setParameter(4, creditCard.getCustomerId());

        query.executeUpdate();
    }

    public void updateOrInsertUsingRepository(CreditCard creditCard) {
        creditCardRepository.updateOrInsert(creditCard);
    }
}
