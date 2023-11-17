package hr.fer.oprpp1.custom.scripting.lexer;

/**
 * This class creates tokens based on the text that it receives. It supports two
 * different ways of interpreting the text.
 * 
 * Used as a lexer, the first part of a language processor.
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
	 * Constructor that accepts text as an argument and sets the state of lexer to
	 * basic.
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
	 * Generates and returnes next token. Supports Basic and Tag state of lexer.
	 * 
	 * @return next token from initial text
	 * @throws LexerException if text is in invalid format
	 */
	public Token nextToken() {
		if (token != null && token.getType() == TokenType.EOF)
			throw new LexerException();

		if (state == LexerState.TAG)
			ignoreBlanks();

		if (currentIndex >= data.length) {
			token = new Token(TokenType.EOF, null);
			return token;
		}

		if (state == LexerState.TAG) {

			StringBuilder sb = new StringBuilder();

			if (data[currentIndex] == '{') {
				if (currentIndex + 1 == data.length) {
					throw new LexerException();
				}

				if (data[currentIndex + 1] == '$') {
					currentIndex = currentIndex + 2;
					return token = new Token(TokenType.TAG, "{$");
				}
			}

			if (data[currentIndex] == '=') {
				currentIndex++;
				return token = new Token(TokenType.TAGTYPE, "=");
			}

			if (currentIndex + 2 < data.length) {
				if (String.valueOf(data[currentIndex]).toUpperCase().equals("F")
						&& String.valueOf(data[currentIndex + 1]).toUpperCase().equals("O")
						&& String.valueOf(data[currentIndex + 2]).toUpperCase().equals("R")) {
					currentIndex = currentIndex + 3;
					return token = new Token(TokenType.TAGTYPE, "FOR");
				}

				if (String.valueOf(data[currentIndex]).toUpperCase().equals("E")
						&& String.valueOf(data[currentIndex + 1]).toUpperCase().equals("N")
						&& String.valueOf(data[currentIndex + 2]).toUpperCase().equals("D")) {
					currentIndex = currentIndex + 3;
					return token = new Token(TokenType.TAGTYPE, "END");
				}
			}

			if (data[currentIndex] == '$') {
				if (currentIndex + 1 < data.length) {
					if (data[currentIndex + 1] == '}') {
						currentIndex = currentIndex + 2;
						state = LexerState.BASIC;
						return token = new Token(TokenType.TAG, "$}");
					}
				}
			}

			if (Character.isLetter(data[currentIndex])) {
				do {
					sb.append(data[currentIndex]);
					currentIndex++;
					if (currentIndex == data.length)
						break;
				} while (Character.isLetter(data[currentIndex]) || Character.isDigit(data[currentIndex])
						|| data[currentIndex] == '_');
			}

			if (sb.length() > 0)
				return token = new Token(TokenType.VARIABLE, sb.toString());

			if (data[currentIndex] == '@') {
				sb.append(data[currentIndex]);
				currentIndex++;

				if (currentIndex < data.length && Character.isLetter(data[currentIndex])) {
					do {

						sb.append(data[currentIndex]);
						currentIndex++;
						if (currentIndex == data.length)
							break;
					} while (Character.isLetter(data[currentIndex]) || Character.isDigit(data[currentIndex])
							|| data[currentIndex] == '_');
				}
			}

			if (sb.length() > 0)
				return token = new Token(TokenType.FUNCTION, sb.toString());

			boolean doubleNum = false;
			int dotCount = 0;
			if (data[currentIndex] == '-' || Character.isDigit(data[currentIndex])) {
				if (data[currentIndex] == '-') {
					if (currentIndex + 1 < data.length) {
						if (Character.isDigit(data[currentIndex + 1])) {
							sb.append("-");
							currentIndex++;

							while (Character.isDigit(data[currentIndex]) || data[currentIndex] == '.') {

								if (data[currentIndex] == '.') {
									doubleNum = true;
									dotCount++;
								}
								if (dotCount > 1)
									throw new LexerException();

								sb.append(data[currentIndex]);
								currentIndex++;
								if (currentIndex == data.length)
									break;
							}
						}
					}
				} else {
					while (Character.isDigit(data[currentIndex]) || data[currentIndex] == '.') {

						if (data[currentIndex] == '.') {
							doubleNum = true;
							dotCount++;
						}
						if (dotCount > 1)
							throw new LexerException();

						sb.append(data[currentIndex]);
						currentIndex++;
						if (currentIndex == data.length)
							break;
					}
				}
			}

			if (sb.length() > 0) {
				if (doubleNum)
					return token = new Token(TokenType.DOUBLE, Double.parseDouble(sb.toString()));
				return token = new Token(TokenType.INTEGER, Integer.parseInt(sb.toString()));
			}

			if (data[currentIndex] == '+') {
				currentIndex++;
				return token = new Token(TokenType.OPERATOR, "+");
			}
			if (data[currentIndex] == '-') {
				currentIndex++;
				return token = new Token(TokenType.OPERATOR, "-");
			}
			if (data[currentIndex] == '*') {
				currentIndex++;
				return token = new Token(TokenType.OPERATOR, "*");
			}
			if (data[currentIndex] == '/') {
				currentIndex++;
				return token = new Token(TokenType.OPERATOR, "/");
			}
			if (data[currentIndex] == '^') {
				currentIndex++;
				return token = new Token(TokenType.OPERATOR, "^");
			}

			boolean skip = false;
			if (data[currentIndex] == '"') {
				sb.append(data[currentIndex]);
				currentIndex++;
				if (currentIndex < data.length) {
					while (data[currentIndex] != '"') {
						skip = false;
						if (data[currentIndex] == '\\') {
							currentIndex++;
							if (currentIndex >= data.length || (data[currentIndex] != '"' && data[currentIndex] != '\\'
									&& data[currentIndex] != 'n' && data[currentIndex] != 'r'
									&& data[currentIndex] != 't')) {
								throw new LexerException();
							}
							if (data[currentIndex] == 'n') {
								sb.append('\n');
								skip = true;
								currentIndex++;
								if (currentIndex == data.length)
									break;
							} else if (data[currentIndex] == 'r') {
								sb.append('\r');
								skip = true;
								currentIndex++;
								if (currentIndex == data.length)
									break;
							} else if (data[currentIndex] == 't') {
								sb.append('\t');
								skip = true;
								currentIndex++;
								if (currentIndex == data.length)
									break;
							}
						}

						if (skip == false) {
							sb.append(data[currentIndex]);
							currentIndex++;
							if (currentIndex == data.length)
								break;
						}
					}
				}
			}

			if (sb.length() > 0) {
				sb.append(data[currentIndex]);
				currentIndex++;
				return token = new Token(TokenType.STRING, sb.toString());
			}

			throw new LexerException();
		} else {

			StringBuilder sb = new StringBuilder();
			while (true) {
				if (data[currentIndex] == '\\') {
					if (currentIndex == data.length - 1) {
						throw new LexerException();
					}

					if (data[currentIndex + 1] == '\\') {
						sb.append(data[currentIndex]);
						currentIndex = currentIndex + 2;
						if (currentIndex == data.length)
							break;
						continue;
					}

					if (data[currentIndex + 1] == '{') {
						sb.append(data[++currentIndex]);
						currentIndex++;
						if (currentIndex == data.length)
							break;
						continue;
					}

					throw new LexerException();
				}

				if (data[currentIndex] == '{') {
					if (currentIndex + 1 < data.length) {
						if (data[currentIndex + 1] == '$') {
							state = LexerState.TAG;
							break;
						}
					}
				}

				sb.append(data[currentIndex]);
				currentIndex++;
				if (currentIndex == data.length)
					break;
			}

			if (sb.length() > 0)
				return token = new Token(TokenType.TEXT, sb.toString());

			currentIndex = currentIndex + 2;
			return token = new Token(TokenType.TAG, "{$");
		}
	}

	/**
	 * Returns last generated token. Can be called multiple times without generating
	 * next token.
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
	 * @throws NullPointerException if passed state is null
	 */
	public void setState(LexerState state) {
		if (state == null)
			throw new NullPointerException();

		this.state = state;
	}

	/**
	 * Advances currentIndex from current position to the first non-whitespace
	 * character.
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
