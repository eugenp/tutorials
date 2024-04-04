package com.baeldung.spring.data.jpa.upsert;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest(classes = UpsertApplication.class)
public class UpdateOrInsertUnitTest {

    @Autowired
    private CreditCardRepository creditCardRepository;

    @Autowired
    private CreditCardLogic creditCardLogic;

    private CreditCard existingCard = null;

    @BeforeEach
    public void create() {
        existingCard = createAndReturnCreditCards();
    }

    @AfterEach
    public void clean() {
        creditCardRepository.deleteAll();
    }

    @Test
    public void givenCreditCards_whenUpdateOrInsert0Executed_thenUpserted() {
        // insert test
        CreditCard newCreditCard = buildCreditCard();
        CreditCard cardExists = creditCardRepository.findByCardNumber(newCreditCard.getCardNumber());
        assertNull(cardExists);

        creditCardLogic.updateOrInsert0(newCreditCard);

        cardExists = creditCardRepository.findByCardNumber(newCreditCard.getCardNumber());
        assertNotNull(cardExists);

        // update test
        CreditCard cardForUpdate = existingCard;
        String beforeExpiryDate = cardForUpdate.getExpiryDate();
        cardForUpdate.setExpiryDate("2029-08-29");
        cardExists = creditCardRepository.findByCardNumber(cardForUpdate.getCardNumber());
        assertNotNull(cardExists);

        creditCardLogic.updateOrInsert0(cardForUpdate);

        assertNotEquals("2029-08-29", beforeExpiryDate);
        CreditCard updatedCard = creditCardRepository.findById(cardForUpdate.getId()).get();
        assertEquals("2029-08-29", updatedCard.getExpiryDate());
    }

    @Test
    public void givenCreditCards_whenUpdateOrInsert1Executed_thenUpserted() {
        // insert test
        CreditCard newCreditCard = buildCreditCard();
        CreditCard cardExists = creditCardRepository.findByCardNumber(newCreditCard.getCardNumber());
        assertNull(cardExists);

        creditCardLogic.updateOrInsert1(newCreditCard);

        cardExists = creditCardRepository.findByCardNumber(newCreditCard.getCardNumber());
        assertNotNull(cardExists);

        // update test
        CreditCard cardForUpdate = existingCard;
        String beforeExpiryDate = cardForUpdate.getExpiryDate();
        cardForUpdate.setExpiryDate("2029-08-29");

        creditCardLogic.updateOrInsert1(cardForUpdate);

        assertNotEquals("2029-08-29", beforeExpiryDate);
        CreditCard updatedCard = creditCardRepository.findById(cardForUpdate.getId()).get();
        assertEquals("2029-08-29", updatedCard.getExpiryDate());
    }

    @Test
    public void givenCreditCards_whenUpdateOrInsert2Executed_thenUpserted() {
        // insert test
        CreditCard newCreditCard = buildCreditCard();
        CreditCard cardExists = creditCardRepository.findByCardNumber(newCreditCard.getCardNumber());
        assertNull(cardExists);

        creditCardLogic.updateOrInsert2(newCreditCard);

        cardExists = creditCardRepository.findByCardNumber(newCreditCard.getCardNumber());
        assertNotNull(cardExists);

        // update test
        CreditCard cardForUpdate = existingCard;
        String beforeExpiryDate = cardForUpdate.getExpiryDate();
        cardForUpdate.setExpiryDate("2029-08-29");

        creditCardLogic.updateOrInsert2(cardForUpdate);

        assertNotEquals("2029-08-29", beforeExpiryDate);
        CreditCard updatedCard = creditCardRepository.findById(cardForUpdate.getId()).get();
        assertEquals("2029-08-29", updatedCard.getExpiryDate());
    }

    private CreditCard buildCreditCard() {
        CreditCard card = new CreditCard();
        card.setCardNumber("9994323432112222");
        card.setExpiryDate("2024-06-21");
        card.setCustomerId(10L);

        return card;
    }

    @Transactional
    private CreditCard createAndReturnCreditCards() {
        CreditCard card = new CreditCard();
        card.setCardNumber("3494323432112222");
        card.setExpiryDate("2024-06-21");
        card.setCustomerId(10L);
        return creditCardRepository.save(card);
    }
}
