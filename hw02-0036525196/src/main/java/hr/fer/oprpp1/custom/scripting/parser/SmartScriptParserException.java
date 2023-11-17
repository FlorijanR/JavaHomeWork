package hr.fer.oprpp1.custom.scripting.parser;

/**
 * SmartScriptParserException that is thrown when
 * the parser comes upon an unexpected problem.
 * 
 * @author Florijan Rusac
 * @version 1.0
 */
public class SmartScriptParserException extends RuntimeException {

	/**
	 * serialVersionUID of LexerException.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Simple constructor.
	 */
	public SmartScriptParserException() {
	}

	/**
	 * Constructor that supports passing message.
	 * 
	 * @param message with context of cause of exception
	 */
	public SmartScriptParserException(String message) {
		super(message);
	}

	/**
	 * Constructor that supports passing of 
	 * cause of first exception.
	 * 
	 * @param cause of first exception
	 */
	public SmartScriptParserException(Throwable cause) {
		super(cause);
	}

	/**
	 * Constructor that supports passing message and
	 * passing of cause of first exception.
	 * 
	 * @param message with context of cause of exception
	 * @param causeof first exception
	 */
	public SmartScriptParserException(String message, Throwable cause) {
		super(message, cause);
	}
}
