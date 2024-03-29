package com.baeldung.spring.data.jpa.upsert;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

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

    private List<CreditCard> existingCards = new ArrayList<>();

    @BeforeEach
    public void create() {
        existingCards = createAndReturnCreditCards();
    }

    @AfterEach
    public void clean() {
        creditCardRepository.deleteAll();
    }

    @Test
    public void givenCreditCards_whenUpdateOrInsert0Executed_thenUpserted() {
        List<CreditCard> newCreditCards = buildCreditCards();
        CreditCard cardForUpdate = existingCards.get(0);
        String beforeExpiryDate = cardForUpdate.getExpiryDate();
        cardForUpdate.setExpiryDate("2029-08-29");
        newCreditCards.add(existingCards.get(0)); // adding existing card for triggering update

        creditCardLogic.updateOrInsert0(newCreditCards);
        CreditCard updatedCard = creditCardRepository.findById(cardForUpdate.getId())
            .get();

        System.out.println(existingCards.get(0)
            .getExpiryDate());
        assertNotEquals("2029-08-29", beforeExpiryDate);
        assertEquals("2029-08-29", updatedCard.getExpiryDate());
        assertEquals(4, creditCardRepository.findAll()
            .size());
    }

    @Test
    public void givenCreditCards_whenUpdateOrInsert1Executed_thenUpserted() {
        List<CreditCard> newCreditCards = buildCreditCards();
        CreditCard cardForUpdate = existingCards.get(0);
        String beforeExpiryDate = cardForUpdate.getExpiryDate();
        cardForUpdate.setExpiryDate("2029-08-29");
        newCreditCards.add(existingCards.get(0)); // adding existing card for triggering update

        creditCardLogic.updateOrInsert1(newCreditCards);
        CreditCard updatedCard = creditCardRepository.findById(cardForUpdate.getId())
            .get();

        assertNotEquals("2029-08-29", beforeExpiryDate);
        assertEquals("2029-08-29", updatedCard.getExpiryDate());
        assertEquals(4, creditCardRepository.findAll()
            .size());
    }

    @Test
    public void givenCreditCards_whenUpdateOrInsert2Executed_thenUpserted() {
        List<CreditCard> newCreditCards = buildCreditCards();
        CreditCard cardForUpdate = existingCards.get(0);
        String beforeExpiryDate = cardForUpdate.getExpiryDate();
        cardForUpdate.setExpiryDate("2029-08-29");
        newCreditCards.add(existingCards.get(0)); // adding existing card for triggering update

        creditCardLogic.updateOrInsert2(newCreditCards);
        CreditCard updatedCard = creditCardRepository.findById(cardForUpdate.getId())
            .get();

        assertNotEquals("2029-08-29", beforeExpiryDate);
        assertEquals("2029-08-29", updatedCard.getExpiryDate());
        assertEquals(4, creditCardRepository.findAll()
            .size());
    }

    private List<CreditCard> buildCreditCards() {
        CreditCard card = new CreditCard();
        card.setCardNumber("9994323432112222");
        card.setExpiryDate("2024-06-21");
        card.setCustomerId(10L);

        CreditCard card1 = new CreditCard();
        card1.setCardNumber("4444323432112222");
        card1.setExpiryDate("2025-06-21");
        card1.setCustomerId(10L);

        return new ArrayList<>(Arrays.asList(card, card1));
    }

    @Transactional
    private List<CreditCard> createAndReturnCreditCards() {
        CreditCard card = new CreditCard();
        card.setCardNumber("3494323432112222");
        card.setExpiryDate("2024-06-21");
        card.setCustomerId(10L);
        card = creditCardRepository.save(card);

        CreditCard card1 = new CreditCard();
        card1.setCardNumber("9474323432112222");
        card1.setExpiryDate("2025-06-21");
        card1.setCustomerId(10L);
        card1 = creditCardRepository.save(card1);

        return List.of(card, card1);
    }
}
