package hr.fer.zemris.java.hw06.shell;

import java.util.SortedMap;

/**
 * Interface that models a shell environment.
 * 
 * @author Florijan Rusac
 * @version 1.0
 */
public interface Environment {
	/**
	 * Reads a line from standard input.
	 * 
	 * @return a string that was read
	 * @throws ShellIOException if reading fails
	 */
	String readLine() throws ShellIOException;

	/**
	 * Writes to standard output.
	 * 
	 * @param text to be written
	 * @throws ShellIOException if writing fails
	 */
	void write(String text) throws ShellIOException;

	/**
	 * Writes to standard input passed text and a newline.
	 * 
	 * @param text to be written
	 * @throws ShellIOException if writing fails
	 */
	void writeln(String text) throws ShellIOException;

	/**
	 * @return an unmodifiable map of supported commands.
	 */
	SortedMap<String, ShellCommand> commands();

	/**
	 * @return current multiline symbol
	 */
	Character getMultilineSymbol();

	/**
	 * @param symbol to which multiline symbol 
	 * 		will be set to
	 */
	void setMultilineSymbol(Character symbol);

	/**
	 * @return current prompt symbol
	 */
	Character getPromptSymbol();

	/**
	 * @param symbol to which prompt symbol 
	 * 		will be set to
	 */
	void setPromptSymbol(Character symbol);

	/**
	 * @return current morelines symbol
	 */
	Character getMorelinesSymbol();

	/**
	 * @param symbol to which morelines symbol
	 * 		will be set to
	 */
	void setMorelinesSymbol(Character symbol);
}
