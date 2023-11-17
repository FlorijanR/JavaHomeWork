package hr.fer.oprpp1.custom.scripting.nodes;

/**
 * Node used to store text tags.
 * 
 * @author Florijan Rusac
 * @version 1.0
 */
public class TextNode extends Node {
	/**
	 * Value of text tag.
	 */
	private String text;
	
	/**
	 * Constructor that sets the text string.
	 * 
	 * @param text value of text tag.
	 */
	public TextNode(String text) {
		this.text = text;
	}
	
	/**
	 * @return text in this text node
	 */
	public String getText() {
		return text;
	}
}
