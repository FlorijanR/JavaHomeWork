package hr.fer.oprpp1.hw04.db;

/**
 * This class creates tokens based on the query that it receives.
 * 
 * Used as a lexer, the first part of a language processor.
 * 
 * @author Florijan Rusac
 * @version 1.0
 */
public class QueryLexer {
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
	 * Constructor that accepts text as an argument.
	 * 
	 * @param text which will be tokenized
	 * @throws NullPointerException if passed text is null
	 */
	public QueryLexer(String text) {
		if (text == null)
			throw new NullPointerException();

		data = text.toCharArray();
	}

	/**
	 * Generates and returnes next token.
	 * 
	 * @return next token from initial text
	 * @throws QueryException if text is in invalid format
	 */
	public Token nextToken() {
		ignoreBlanks();

		if (currentIndex >= data.length) {
			token = new Token(TokenType.EOF, null);
			return token;
		}

		StringBuilder sb = new StringBuilder();
		while (Character.isLetter(data[currentIndex])) {
			sb.append(data[currentIndex]);
			currentIndex++;
			if (currentIndex == data.length)
				break;
		}
		if (sb.length() > 0) {
			String str = sb.toString();
			if (str.equals("jmbag") || str.equals("lastName") || str.equals("firstName")) {
				return token = new Token(TokenType.ATTR, str);
			} else if (str.equalsIgnoreCase("and")) {
				return token = new Token(TokenType.AND, str);
			} else if (str.equals("LIKE")) {
				return token = new Token(TokenType.OP, str);
			} else {
				throw new QueryException();
			}
		}

		if (data[currentIndex] == '>') {
			if (currentIndex + 1 >= data.length)
				throw new QueryException();

			currentIndex++;
			if (data[currentIndex] == '=') {
				currentIndex++;
				return token = new Token(TokenType.OP, ">=");
			}

			return token = new Token(TokenType.OP, ">");
		}
		if (data[currentIndex] == '<') {
			if (currentIndex + 1 >= data.length)
				throw new QueryException();

			currentIndex++;
			if (data[currentIndex] == '=') {
				currentIndex++;
				return token = new Token(TokenType.OP, "<=");
			}

			return token = new Token(TokenType.OP, "<");
		}
		if (data[currentIndex] == '=') {
			currentIndex++;
			return token = new Token(TokenType.OP, "=");
		}
		if (data[currentIndex] == '!') {
			if (currentIndex + 1 >= data.length)
				throw new QueryException();

			currentIndex++;
			if (data[currentIndex] != '=')
				throw new QueryException();

			currentIndex++;
			return token = new Token(TokenType.OP, "!=");
		}

		if (data[currentIndex] == '"') {
			if (currentIndex + 1 >= data.length)
				throw new QueryException();

			currentIndex++;
			boolean wildcardUsed = false;
			while (data[currentIndex] != '"') {
				if (data[currentIndex] == '*' && wildcardUsed == false) {
					wildcardUsed = true;
				} else if (data[currentIndex] == '*' && wildcardUsed == true) {
					throw new QueryException();
				}

				sb.append(data[currentIndex]);
				if (currentIndex + 1 >= data.length)
					throw new QueryException();

				currentIndex++;
			}
			currentIndex++;

			return token = new Token(TokenType.STRING, sb.toString());
		}

		throw new QueryException();
	}

	/**
	 * Advances currentIndex from current position to the first non-whitespace
	 * character.
	 */
	private void ignoreBlanks() {
		while (currentIndex < data.length) {
			char current = data[currentIndex];
			if (current == ' ' || current == '\t') {
				currentIndex++;
				continue;
			}
			break;
		}
	}

}
