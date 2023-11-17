package hr.fer.oprpp1.custom.scripting.elems;

/**
 * Used to store a parsed Function.
 * 
 * @author Florijan Rusac
 * @version 1.0
 */
public class ElementFunction extends Element {
	/**
	 * Name of function.
	 */
	private String name;
	
	/**
	 * Constructor that sets name of function.
	 * 
	 * @param name of function
	 */
	public ElementFunction(String name) {
		this.name = name;
	}
	
	/**
	 * @return name of function
	 */
	public String getName() {
		return name;
	}
	
	@Override
	/**
	 * @return name of function
	 */
	public String asText() {
		return name;
	}

}
