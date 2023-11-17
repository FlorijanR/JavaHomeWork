package hr.fer.oprpp1.custom.scripting.elems;

/**
 * Used to store a parsed double.
 * 
 * @author Florijan Rusac
 * @version 1.0
 */
public class ElementConstantDouble extends Element {
	/**
	 * Value of double.
	 */
	private double value;

	/**
	 * Constructor that sets the double.
	 * 
	 * @param value of double
	 */
	public ElementConstantDouble(double value) {
		this.value = value;
	}
	
	/**
	 * @return value of double
	 */
	public double getValue() {
		return value;
	}
	
	@Override
	/**
	 * @return value of double
	 */
	public String asText() {
		return Double.toString(value);
	}
}
