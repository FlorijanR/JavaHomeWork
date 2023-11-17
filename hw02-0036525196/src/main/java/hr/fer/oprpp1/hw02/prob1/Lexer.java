package hr.fer.oprpp1.hw02.prob1;

/**
 * This class creates tokens based 
 * on the text that it receives. It 
 * supports two different ways of 
 * interpreting the text.
 * 
 * Used as a lexer, the first part of 
 * a language processor.
 * 
 * @author Florijan Rusac
 * @version 1.0
 */
public class Lexer {
	/**
	 * Passed text to be tokenized.
	 */
	private char[] data;
	/**
	 * Last generated token.
	 */
	private Token token;
	/**
	 * Index of a first non-processed char.
	 */
	private int currentIndex;
	/**
	 * Current state of lexer.
	 */
	LexerState state;

	/**
	 * Constructor that accepts text as an argument and 
	 * sets the state of lexer to basic.
	 * 
	 * @param text which will be tokenized
	 * @throws NullPointerException if passed text is null
	 */
	public Lexer(String text) {
		if (text == null)
			throw new NullPointerException();

		data = text.toCharArray();
		state = LexerState.BASIC;
	}

	/**
	 * Generates and returnes next token. Supports
	 * Basic and Extended state of lexer.
	 * 
	 * @return next token from initial text
	 * @throws LexerException if text is in invalid format
	 */
	public Token nextToken() {
		if (token != null && token.getType() == TokenType.EOF)
			throw new LexerException();

		ignoreBlanks();

		if (currentIndex >= data.length) {
			token = new Token(TokenType.EOF, null);
			return token;
		}

		if (state == LexerState.BASIC) {

			StringBuilder sb = new StringBuilder();
			while (Character.isLetter(data[currentIndex]) || data[currentIndex] == '\\') {
				if (data[currentIndex] == '\\') {
					currentIndex++;
					if (currentIndex >= data.length
							|| (!Character.isDigit(data[currentIndex]) && data[currentIndex] != '\\')) {
						throw new LexerException();
					}
				}

				sb.append(data[currentIndex]);
				currentIndex++;
				if (currentIndex == data.length)
					break;
			}

			if (sb.length() > 0)
				return token = new Token(TokenType.WORD, sb.toString());

			while (Character.isDigit(data[currentIndex])) {
				sb.append(data[currentIndex]);
				currentIndex++;
				if (currentIndex == data.length)
					break;
			}

			if (sb.length() > 0) {
				try {
					return token = new Token(TokenType.NUMBER, Long.parseLong(sb.toString()));
				} catch (NumberFormatException e) {
					throw new LexerException();
				}
			}

			return token = new Token(TokenType.SYMBOL, data[currentIndex++]);
		} else {

			StringBuilder sb = new StringBuilder();
			while (data[currentIndex] != ' '
					&& data[currentIndex] != '\r' 
					&& data[currentIndex] != '\n'
					&& data[currentIndex] != '\t'
					&& data[currentIndex] != '#') {

				sb.append(data[currentIndex]);
				currentIndex++;
				if (currentIndex == data.length)
					break;
			}

			if (sb.length() > 0)
				return token = new Token(TokenType.WORD, sb.toString());

			return token = new Token(TokenType.SYMBOL, data[currentIndex++]);
		}
	}
	
	/**
	 * Returns last generated token. Can be called
	 * multiple times without generating next token.
	 * 
	 * @return last generated token.
	 */
	public Token getToken() {
		return token;
	}

	/**
	 * Sets the state of lexer.
	 * 
	 * @param state which to set the lexer to
	 * @throws NullPointerException if passed 
	 * 		state is null
	 */
	public void setState(LexerState state) {
		if (state == null)
			throw new NullPointerException();

		this.state = state;
	}

	/**
	 * Advances currentIndex from current position
	 * to the first non-whitespace character.
	 */
	private void ignoreBlanks() {
		while (currentIndex < data.length) {
			char current = data[currentIndex];
			if (current == ' ' || current == '\r' || current == '\n' || current == '\t') {
				currentIndex++;
				continue;
			}
			break;
		}
	}

}
