package hr.fer.oprpp1.custom.collections;

/**
 * EmtpyStackException that is thrown when
 * the stack is empty and .pop() method
 * is called on it.
 * 
 * @author Florijan Rusac
 * @version 1.0
 */
public class EmptyStackException extends RuntimeException {

	/**
	 * serialVersionUID of EmptyStackException.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Simple constructor.
	 */
	public EmptyStackException() {
	}

	/**
	 * Constructor that supports passing message.
	 * 
	 * @param message with context of cause of exception
	 */
	public EmptyStackException(String message) {
		super(message);
	}

	/**
	 * Constructor that supports passing of 
	 * cause of first exception.
	 * 
	 * @param cause of first exception
	 */
	public EmptyStackException(Throwable cause) {
		super(cause);
	}

	/**
	 * Constructor that supports passing message and
	 * passing of cause of first exception.
	 * 
	 * @param message with context of cause of exception
	 * @param causeof first exception
	 */
	public EmptyStackException(String message, Throwable cause) {
		super(message, cause);
	}

}
