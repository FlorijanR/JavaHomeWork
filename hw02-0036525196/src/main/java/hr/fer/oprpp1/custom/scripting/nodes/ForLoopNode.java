package hr.fer.oprpp1.custom.scripting.nodes;

import hr.fer.oprpp1.custom.scripting.elems.*;

/**
 * Node that stores the contents of a for loop tag.
 * 
 * @author Florijan Rusac
 * @version 1.0
 */
public class ForLoopNode extends Node {
	/**
	 * First value of a for loop tag.
	 */
	private ElementVariable variable;
	/**
	 * Second value of a for loop tag.
	 */
	private Element startExpression;
	/**
	 * Third value of a for loop tag.
	 */
	private Element endExpression;
	/**
	 * Fourth value of a for loop tag.
	 */
	private Element stepExpression;

	/**
	 * Initializes all member variables.
	 * 
	 * @param variable first value of a for loop tag
	 * @param startExpresion second value of a for loop tag
	 * @param endExpression third value of a for loop tag
	 * @param stepExpression fourth value of a for loop tag
	 */
	public ForLoopNode(ElementVariable variable, 
			Element startExpresion, 
			Element endExpression,
			Element stepExpression) {
		this.variable = variable;
		this.startExpression = startExpresion;
		this.endExpression = endExpression;
		this.stepExpression = stepExpression;
	}

	/**
	 * @return first value of a for loop tag
	 */
	public ElementVariable getVariable() {
		return variable;
	}

	/**
	 * @return second value of a for loop tag
	 */
	public Element getStartExpression() {
		return startExpression;
	}

	/**
	 * @return third value of a for loop tag
	 */
	public Element getEndExpression() {
		return endExpression;
	}

	/**
	 * @return fourth value of a for loop tag
	 */
	public Element getStepExpression() {
		return stepExpression;
	}

}
