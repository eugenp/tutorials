package hexagonal.architecture.exception;

public class ErrorMessage {

    public static final String ERROR_INVALID_ICCID = "Iccid is invalid.";
    public static final String ERROR_INVALID_PIN = "Pin is invalid.";
    public static final String ERROR_CARD_IS_BLOCKED = "SIM card is blocked.";

    private ErrorMessage() {
    }
}
