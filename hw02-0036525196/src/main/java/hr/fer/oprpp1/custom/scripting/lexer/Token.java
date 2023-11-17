package hr.fer.oprpp1.custom.scripting.lexer;

/**
 * This class can store and return
 * the value and type of a token.
 * 
 * Used to store tokens that Lexer
 * creates.
 * 
 * @author Florijan Rusac
 * @version 1.0
 */
public class Token {
	/**
	 * Value of token.
	 */
	private Object value;
	/**
	 * Type of token.
	 */
	private TokenType type;
	
	/**
	 * Constructor that initializes the 
	 * value and type of token.
	 * 
	 * @param type of token
	 * @param value of token
	 */
	public Token(TokenType type, Object value) {
		this.value = value;
		this.type = type;
	}

	/**
	 * @return value of token
	 */
	public Object getValue() {
		return value;
	}

	/**
	 * @return type of token
	 */
	public TokenType getType() {
		return type;
	}
}
