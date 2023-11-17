package hr.fer.zemris.java.hw06.shell;

/**
 * ShellIOException that is thrown when
 * writing or reading in an environment fails.
 * 
 * @author Florijan Rusac
 * @version 1.0
 */
public class ShellIOException extends RuntimeException {

	/**
	 * serialVersionUID of ShellIOException.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Simple constructor.
	 */
	public ShellIOException() {
	}

	/**
	 * Constructor that supports passing message.
	 * 
	 * @param message with context of cause of exception
	 */
	public ShellIOException(String message) {
		super(message);
	}

	/**
	 * Constructor that supports passing of 
	 * cause of first exception.
	 * 
	 * @param cause of first exception
	 */
	public ShellIOException(Throwable cause) {
		super(cause);
	}

	/**
	 * Constructor that supports passing message and
	 * passing of cause of first exception.
	 * 
	 * @param message with context of cause of exception
	 * @param causeof first exception
	 */
	public ShellIOException(String message, Throwable cause) {
		super(message, cause);
	}


}
