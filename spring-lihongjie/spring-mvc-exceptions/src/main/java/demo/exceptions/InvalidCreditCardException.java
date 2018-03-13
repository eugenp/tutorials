package demo.exceptions;

public class InvalidCreditCardException extends RuntimeException {

	/**
	 * Unique ID for Serialized object
	 */
	private static final long serialVersionUID = 6648725043534411041L;

	public InvalidCreditCardException(String msg) {
		super(msg);
	}

	public InvalidCreditCardException(String msg, Throwable t) {
		super(msg, t);
	}

}
