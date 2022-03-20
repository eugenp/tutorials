package hexagonal.architecture.exception;

public class InvalidPinException extends RuntimeException {

    public InvalidPinException(String message) {
        super(message);
    }
}
