package demo.exceptions;

public class CustomException extends RuntimeException {

	/**
	 * Unique ID for Serialized object
	 */
	private static final long serialVersionUID = 4657491283614755649L;

	public CustomException(String msg) {
		super(msg);
	}

	public CustomException(String msg, Throwable t) {
		super(msg, t);
	}

}
