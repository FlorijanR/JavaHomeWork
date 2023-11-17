package hr.fer.oprpp1.custom.scripting.elems;

/**
 * Used to store a parsed variable.
 * 
 * @author Florijan Rusac
 * @version 1.0
 */
public class ElementVariable extends Element {
	/**
	 * Name of variable.
	 */
	private String name;
	
	/**
	 * Constructor that sets name of variable.
	 * 
	 * @param name of variable
	 */
	public ElementVariable(String name) {
		this.name = name;
	}
	
	/**
	 * @return name of variable
	 */
	public String getName() {
		return name;
	}
	
	@Override
	/**
	 * @return name of variable
	 */
	public String asText() {
		return name;
	}

}
