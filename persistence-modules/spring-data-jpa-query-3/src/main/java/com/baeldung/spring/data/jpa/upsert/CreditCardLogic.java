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

    public void updateOrInsert1(List<CreditCard> creditCards) {
        List<CreditCard> existingCards = creditCardRepository.findAll()
            .stream()
            .toList();
        List<String> existingCardNumbers = existingCards.stream()
            .map(CreditCard::getCardNumber)
            .toList();
        Map<Boolean, List<CreditCard>> partitionedCards = creditCards.stream()
            .collect(Collectors.partitioningBy(cc -> existingCardNumbers.contains(cc.getCardNumber())));
        List<CreditCard> updatesTarget = partitionedCards.get(true);
        List<CreditCard> insertsTarget = partitionedCards.get(false);
        System.out.println("inserts: " + insertsTarget);
        System.out.println("updates: " + updatesTarget);
        insertsTarget.forEach(cc -> creditCardRepository.save(cc));
        updatesTarget.forEach(cc -> {
            CreditCard creditCard = existingCards.stream()
                .filter(ecc -> Objects.equals(ecc.getCardNumber(), cc.getCardNumber()))
                .findFirst()
                .get();
            creditCard.setExpiryDate(cc.getExpiryDate());
            creditCardRepository.save(creditCard);
        });
    }

    @Transactional
    public void updateOrInsert2(List<CreditCard> creditCards) {
        creditCards.forEach(cc -> {
            Long id = cc.getId();
            if (cc.getId() == null) {
                BigInteger nextVal = (BigInteger) em.createNativeQuery("SELECT nextval('credit_card_id_seq')")
                    .getSingleResult();
                id = nextVal.longValue();
            }
            System.out.println("id: " + id);

            String upsertQuery1 = """
                MERGE INTO credit_card (id, card_number, expiry_date, customer_id)
                KEY(card_number)
                VALUES (?, ?, ?, ?)
                """;

            Query query = em.createNativeQuery(upsertQuery1);
            query.setParameter(1, id);
            query.setParameter(2, cc.getCardNumber());
            query.setParameter(3, cc.getExpiryDate());
            query.setParameter(4, cc.getCustomerId());

            query.executeUpdate();
        });
    }

    public void updateOrInsert0(List<CreditCard> creditCards) {
        creditCards.forEach(cc -> creditCardRepository.updateOrInsert(cc));
    }
}
