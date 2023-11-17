package hr.fer.oprpp1.hw02.prob1;

/**
 * LexerException that is thrown when
 * the text that the Lexer receives is 
 * in the wrong format.
 * 
 * @author Florijan Rusac
 * @version 1.0
 */
public class LexerException extends RuntimeException {

	/**
	 * serialVersionUID of LexerException.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Simple constructor.
	 */
	public LexerException() {
	}

	/**
	 * Constructor that supports passing message.
	 * 
	 * @param message with context of cause of exception
	 */
	public LexerException(String message) {
		super(message);
	}

	/**
	 * Constructor that supports passing of 
	 * cause of first exception.
	 * 
	 * @param cause of first exception
	 */
	public LexerException(Throwable cause) {
		super(cause);
	}

	/**
	 * Constructor that supports passing message and
	 * passing of cause of first exception.
	 * 
	 * @param message with context of cause of exception
	 * @param causeof first exception
	 */
	public LexerException(String message, Throwable cause) {
		super(message, cause);
	}

}
