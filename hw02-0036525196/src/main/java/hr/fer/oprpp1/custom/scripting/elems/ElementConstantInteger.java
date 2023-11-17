package hr.fer.oprpp1.custom.scripting.elems;

/**
 * Used to store a parsed integer.
 * 
 * @author Florijan Rusac
 * @version 1.0
 */
public class ElementConstantInteger extends Element {
	/**
	 * Value of integer.
	 */
	private int value;
	
	/**
	 * Constructor that sets the integer.
	 * 
	 * @param value of integer
	 */
	public ElementConstantInteger(int value) {
		this.value = value;
	}

	/**
	 * @return value of integer
	 */
	public int getValue() {
		return value;
	}
	
	@Override
	/**
	 * @return value of integer
	 */
	public String asText() {
		return Integer.toString(value);
	}

}
