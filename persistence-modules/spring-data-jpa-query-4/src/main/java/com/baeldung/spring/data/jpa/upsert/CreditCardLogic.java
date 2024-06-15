package com.baeldung.spring.data.jpa.upsert;

import java.math.BigInteger;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
            BigInteger nextVal = (BigInteger) em.createNativeQuery("SELECT nextval('credit_card_id_seq')")
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
