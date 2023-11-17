package hr.fer.oprpp1.hw04.db;

/**
 * QueryException that is thrown when
 * query is in the wrong format.
 * 
 * @author Florijan Rusac
 * @version 1.0
 */
public class QueryException extends RuntimeException {
	/**
	 * serialVersionUID of QueryException.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Simple constructor.
	 */
	public QueryException() {
	}

	/**
	 * Constructor that supports passing message.
	 * 
	 * @param message with context of cause of exception
	 */
	public QueryException(String message) {
		super(message);
	}

	/**
	 * Constructor that supports passing of 
	 * cause of first exception.
	 * 
	 * @param cause of first exception
	 */
	public QueryException(Throwable cause) {
		super(cause);
	}

	/**
	 * Constructor that supports passing message and
	 * passing of cause of first exception.
	 * 
	 * @param message with context of cause of exception
	 * @param causeof first exception
	 */
	public QueryException(String message, Throwable cause) {
		super(message, cause);
	}

}
