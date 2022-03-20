package hexagonal.architecture.domain;

import org.springframework.stereotype.Service;

import hexagonal.architecture.exception.ErrorMessage;
import hexagonal.architecture.exception.InvalidPinException;

@Service
public class DefaultSimCardService implements SimCardService {

    protected static final int PIN_ATTEMPTS_COUNTER_STARTING_VALUE = 0;
    protected static final int MAX_NUMBER_OF_INVALID_PIN_ATTEMPTS = 3;

    private final SimCards simCards;

    public DefaultSimCardService(SimCards simCards) {
        this.simCards = simCards;
    }

    @Override
    public void validatePin(String iccid, String pin) {
        SimCard simCard = simCards.find(iccid);

        if (simCards.isBlocked(iccid)) {
            throw new IllegalArgumentException(ErrorMessage.ERROR_CARD_IS_BLOCKED);
        }

        if (!simCard.getPin()
            .equals(pin)) {
            processInvalidPin(simCard);
            return;
        }
        processValidPin(simCard);
    }

    private void processInvalidPin(SimCard simCard) {
        if (MAX_NUMBER_OF_INVALID_PIN_ATTEMPTS == simCard.getInvalidPinCounter()) {
            simCard.setStatus(SimCard.Status.BLOCKED.name());
        } else {
            simCard.setInvalidPinCounter(simCard.getInvalidPinCounter() + 1);
        }
        simCards.update(simCard);
        throw new InvalidPinException(ErrorMessage.ERROR_INVALID_PIN);
    }

    private void processValidPin(SimCard simCard) {
        simCard.setInvalidPinCounter(PIN_ATTEMPTS_COUNTER_STARTING_VALUE);
        simCards.update(simCard);
    }

}
