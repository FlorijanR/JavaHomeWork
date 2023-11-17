package hr.fer.oprpp1.custom.scripting.nodes;

import hr.fer.oprpp1.custom.scripting.elems.*;

/**
 * Node used to store elements of a echo tag.
 * 
 * @author Florijan Rusac
 * @version 1.0
 */
public class EchoNode extends Node {
	/**
	 * All elements in an echo tag.
	 */
	private Element[] elements;
	
	/**
	 * Constructor that sets the elements
	 * variable to the passed value.
	 * 
	 * @param elements in an echo tag
	 */
	public EchoNode(Element[] elements) {
		this.elements = elements;
	}
	
	/**
	 * @return elements in an echo tag.
	 */
	public Element[] getElements() {
		return elements;
	}

}
