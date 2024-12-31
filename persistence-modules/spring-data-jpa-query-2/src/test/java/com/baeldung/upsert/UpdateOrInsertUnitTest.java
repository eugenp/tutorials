package com.baeldung.upsert;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

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
    void givenCreditCards_whenUpdateOrInsertUsingRepositoryExecuted_thenUpserted() {
        // insert test
        CreditCard newCreditCard = buildCreditCard();
        CreditCard existingCardByCardNumber = creditCardRepository.findByCardNumber(newCreditCard.getCardNumber());
        assertNull(existingCardByCardNumber);

        creditCardLogic.updateOrInsertUsingRepository(newCreditCard);

        existingCardByCardNumber = creditCardRepository.findByCardNumber(newCreditCard.getCardNumber());
        assertNotNull(existingCardByCardNumber);

        // update test
        CreditCard cardForUpdate = existingCard;
        String beforeExpiryDate = cardForUpdate.getExpiryDate();
        cardForUpdate.setExpiryDate("2029-08-29");
        existingCardByCardNumber = creditCardRepository.findByCardNumber(cardForUpdate.getCardNumber());
        assertNotNull(existingCardByCardNumber);

        creditCardLogic.updateOrInsertUsingRepository(cardForUpdate);

        assertNotEquals("2029-08-29", beforeExpiryDate);
        CreditCard updatedCard = creditCardRepository.findById(cardForUpdate.getId()).get();
        assertEquals("2029-08-29", updatedCard.getExpiryDate());
    }

    @Test
    void givenCreditCards_whenUpdateOrInsertUsingCustomLogicExecuted_thenUpserted() {
        // insert test
        CreditCard newCreditCard = buildCreditCard();
        CreditCard existingCardByCardNumber = creditCardRepository.findByCardNumber(newCreditCard.getCardNumber());
        assertNull(existingCardByCardNumber);

        creditCardLogic.updateOrInsertUsingCustomLogic(newCreditCard);

        existingCardByCardNumber = creditCardRepository.findByCardNumber(newCreditCard.getCardNumber());
        assertNotNull(existingCardByCardNumber);

        // update test
        CreditCard cardForUpdate = existingCard;
        String beforeExpiryDate = cardForUpdate.getExpiryDate();
        cardForUpdate.setExpiryDate("2029-08-29");

        creditCardLogic.updateOrInsertUsingCustomLogic(cardForUpdate);

        assertNotEquals("2029-08-29", beforeExpiryDate);
        CreditCard updatedCard = creditCardRepository.findById(cardForUpdate.getId()).get();
        assertEquals("2029-08-29", updatedCard.getExpiryDate());
    }

    @Test
    void givenCreditCards_whenUpdateOrInsertUsingBuiltInFeatureExecuted_thenUpserted() {
        // insert test
        CreditCard newCreditCard = buildCreditCard();
        CreditCard existingCardByCardNumber = creditCardRepository.findByCardNumber(newCreditCard.getCardNumber());
        assertNull(existingCardByCardNumber);

        creditCardLogic.updateOrInsertUsingBuiltInFeature(newCreditCard);

        existingCardByCardNumber = creditCardRepository.findByCardNumber(newCreditCard.getCardNumber());
        assertNotNull(existingCardByCardNumber);

        // update test
        CreditCard cardForUpdate = existingCard;
        String beforeExpiryDate = cardForUpdate.getExpiryDate();
        cardForUpdate.setExpiryDate("2029-08-29");

        creditCardLogic.updateOrInsertUsingBuiltInFeature(cardForUpdate);

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

    private CreditCard createAndReturnCreditCards() {
        CreditCard card = new CreditCard();
        card.setCardNumber("3494323432112222");
        card.setExpiryDate("2024-06-21");
        card.setCustomerId(10L);
        return creditCardRepository.save(card);
    }
}
