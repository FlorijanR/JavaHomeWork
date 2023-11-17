package hr.fer.oprpp1.custom.scripting.elems;

/**
 * Used to store a parsed string.
 * 
 * @author Florijan Rusac
 * @version 1.0
 */
public class ElementString extends Element {
	/**
	 * Value of string.
	 */
	private String value;
	
	/**
	 * Constructor that sets the string value.
	 * 
	 * @param value of string
	 */
	public ElementString(String value) {
		this.value = value;
	}
	
	/**
	 * @return value of string
	 */
	public String getValue() {
		return value;
	}
	
	@Override
	/**
	 * @return value of string
	 */
	public String asText() {
		return value;
	}
}
