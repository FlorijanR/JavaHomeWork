package hr.fer.oprpp1.custom.scripting.elems;

/**
 * Used to store a parsed operator.
 * 
 * @author Florijan Rusac
 * @version 1.0
 */
public class ElementOperator extends Element {
	/**
	 * Stored operator.
	 */
	private String symbol;
	
	/**
	 * Constructor that sets the operator.
	 * 
	 * @param symbol is the received operator
	 */
	public ElementOperator(String symbol) {
		this.symbol = symbol;
	}
	
	/**
	 * @return operator
	 */
	public String getSymbol() {
		return symbol;
	}
	
	@Override
	/**
	 * @return operator
	 */
	public String asText() {
		return symbol;
	}

}
