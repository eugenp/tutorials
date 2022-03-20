package hexagonal.architecture.domain;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import hexagonal.architecture.exception.ErrorMessage;
import hexagonal.architecture.exception.InvalidPinException;

class SimCardServiceUnitTest {

    private static final String DEFAULT_ICCID_VALUE = "541229239819955936M";
    private static final String DEFAULT_MSISDN = "6589417812";
    private static final String DEFAULT_PIN = "1234";
    private static final String DEFAULT_PUK = "12345678";

    @Mock
    private SimCards simCards;

    private SimCardService simCardService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        simCardService = new DefaultSimCardService(simCards);
    }

    @Test
    void givenInvalidPin_whenValidatePin_thenThrowException() {
        when(simCards.find(DEFAULT_ICCID_VALUE)).thenReturn(createInvalidPinSimCard());

        doNothing().when(simCards)
            .update(any());

        InvalidPinException thrown = assertThrows(InvalidPinException.class, () -> simCardService.validatePin(DEFAULT_ICCID_VALUE, DEFAULT_PIN));
        assertEquals(ErrorMessage.ERROR_INVALID_PIN, thrown.getMessage());
    }

    @Test
    void givenInvalidPinAttemptsLimitReached_whenValidatePin_thenThrowException() {
        when(simCards.find(DEFAULT_ICCID_VALUE)).thenReturn(createInvalidPinAttemptsLimitReachedSimCard());

        doNothing().when(simCards)
            .update(any());

        InvalidPinException thrown = assertThrows(InvalidPinException.class, () -> simCardService.validatePin(DEFAULT_ICCID_VALUE, DEFAULT_PIN));
        assertEquals(ErrorMessage.ERROR_INVALID_PIN, thrown.getMessage());
    }

    @Test
    void givenValidPin_whenValidatePin_thenOk() {
        when(simCards.find(DEFAULT_ICCID_VALUE)).thenReturn(createValidSimCard());

        doNothing().when(simCards)
            .update(any());

        assertDoesNotThrow(() -> simCardService.validatePin(DEFAULT_ICCID_VALUE, DEFAULT_PIN));
    }

    @Test
    void givenBlockedSim_whenValidatePin_thenThrowException() {
        when(simCards.find(DEFAULT_ICCID_VALUE)).thenReturn(createBlockedSimCard());
        when(simCards.isBlocked(DEFAULT_ICCID_VALUE)).thenReturn(true);

        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> simCardService.validatePin(DEFAULT_ICCID_VALUE, DEFAULT_PIN));
        assertEquals(ErrorMessage.ERROR_CARD_IS_BLOCKED, thrown.getMessage());
    }

    private SimCard createInvalidPinSimCard() {
        SimCard simCard = createBaseSimCard();
        simCard.setPin("4569");
        return simCard;
    }

    private SimCard createInvalidPinAttemptsLimitReachedSimCard() {
        SimCard simCard = createBaseSimCard();
        simCard.setPin("4569");
        simCard.setInvalidPinCounter(DefaultSimCardService.MAX_NUMBER_OF_INVALID_PIN_ATTEMPTS);
        return simCard;
    }

    private SimCard createBlockedSimCard() {
        SimCard simCard = createBaseSimCard();
        simCard.setInvalidPinCounter(DefaultSimCardService.MAX_NUMBER_OF_INVALID_PIN_ATTEMPTS);
        simCard.setStatus(SimCard.Status.BLOCKED.name());
        return simCard;
    }

    SimCard createValidSimCard() {
        return createBaseSimCard();
    }

    private SimCard createBaseSimCard() {
        SimCard simCard = new SimCard();
        simCard.setIccid(DEFAULT_ICCID_VALUE);
        simCard.setMsisdn(DEFAULT_MSISDN);
        simCard.setPin(DEFAULT_PIN);
        simCard.setPuk(DEFAULT_PUK);
        simCard.setInvalidPinCounter(0);
        simCard.setStatus(SimCard.Status.ACTIVE.name());
        return simCard;
    }

}
