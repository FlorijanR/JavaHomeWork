package hr.fer.oprpp1.custom.scripting.nodes;

import hr.fer.oprpp1.custom.scripting.elems.*;

/**
 * Document node supports methods toString()
 * and equals() for comparing different nodes.
 * 
 * Used as a top level node which stores other nodes.
 * 
 * @author Florijan Rusac
 * @version 1.0
 */
public class DocumentNode extends Node {
	@Override
	/**
	 * Converts the contents of this node 
	 * in a string.
	 * 
	 * @return this node in a string format
	 */
	public String toString() {
		// int level = 0;
		StringBuilder sb = new StringBuilder();
		// sb.append("Document node\n");

		printRecursively(this, sb);

		return sb.toString();
	}

	@Override
	/**
	 * Calculates hash code based on the
	 * string returned by the toString()
	 * method.
	 * 
	 * @return hash code of this node
	 */
	public int hashCode() {
		return toString().hashCode();
	}

	@Override
	/**
	 * Compares this node to another one based on
	 * the string that is returned by toString()
	 * method.
	 * 
	 * @param obj that need to be compared
	 * @return true if passed object is equal to this
	 * 		one, false otherwise
	 */
	public boolean equals(Object obj) {
		if (!(obj instanceof DocumentNode))
			return false;
		DocumentNode other = (DocumentNode) obj;
		return this.toString().equals(other.toString());
	}

	/**
	 * Goes through all child nodes and extracts their
	 * values as strings and appends them.
	 * 
	 * @param node whose contents will be extracted
	 * @param sb stringbuilder used to append 
	 * 		contents of node to
	 */
	private void printRecursively(Node node, StringBuilder sb) {
		if (node instanceof TextNode) {
			TextNode textNode = (TextNode) node;
			sb.append(textNode.getText());
		}
		if (node instanceof EchoNode) {
			EchoNode echoNode = (EchoNode) node;
			sb.append("{$=");

			Element[] elements = echoNode.getElements();
			for (int i = 0, size = elements.length; i < size; i++) {
				if (elements[i] instanceof ElementFunction) {
					sb.append("@");
					sb.append(elements[i].asText());
					sb.append(" ");
				} else if (elements[i] instanceof ElementString) {
					sb.append("\"");
					sb.append(elements[i].asText());
					sb.append("\"");
					sb.append(" ");
				} else {
					sb.append(elements[i].asText());
					sb.append(" ");
				}
			}

			sb.append("$}");
		}
		if (node instanceof ForLoopNode) {
			ForLoopNode forLoopNode = (ForLoopNode) node;
			sb.append("{$FOR");

			sb.append(forLoopNode.getVariable().asText());
			sb.append(" ");
			if (forLoopNode.getStartExpression() instanceof ElementString) {
				sb.append("\"");
				sb.append(forLoopNode.getStartExpression().asText());
				sb.append("\"");
				sb.append(" ");
			} else {
				sb.append(forLoopNode.getStartExpression().asText());
				sb.append(" ");
			}

			if (forLoopNode.getEndExpression() instanceof ElementString) {
				sb.append("\"");
				sb.append(forLoopNode.getEndExpression().asText());
				sb.append("\"");
				sb.append(" ");
			} else {
				sb.append(forLoopNode.getEndExpression().asText());
				sb.append(" ");
			}

			if (forLoopNode.getStepExpression() != null) {
				if (forLoopNode.getStepExpression() instanceof ElementString) {
					sb.append("\"");
					sb.append(forLoopNode.getStepExpression().asText());
					sb.append("\"");
					sb.append(" ");
				} else {
					sb.append(forLoopNode.getStepExpression().asText());
					sb.append(" ");
				}
			}

			sb.append("$}");
		}

		for (int i = 0; i < node.numberOfChildren(); i++) {
			printRecursively(node.getChild(i), sb);
		}

		if (node instanceof ForLoopNode)
			sb.append("{$END$}");
	}

	/*
	 * //print tree private void printRecursively(Node node, StringBuilder sb, int
	 * level) { StringBuilder sbLevel = new StringBuilder(); for (int i = 0; i <
	 * level; i++) { sbLevel.append("-"); }
	 * 
	 * if (node instanceof TextNode) { TextNode textNode = (TextNode) node;
	 * sb.append(sbLevel.toString()) .append("TextNode content: {")
	 * .append(textNode.getText()) .append("}\n"); } if (node instanceof EchoNode) {
	 * EchoNode echoNode = (EchoNode) node; sb.append(sbLevel.toString())
	 * .append("EchoNode content: {");
	 * 
	 * Element[] elements = echoNode.getElements(); for (int i = 0, size =
	 * elements.length; i < size; i++) { if (elements[i] instanceof ElementFunction)
	 * { sb.append("@"); sb.append(elements[i].asText()); sb.append(" "); } else if
	 * (elements[i] instanceof ElementString) { sb.append("\"");
	 * sb.append(elements[i].asText()); sb.append("\""); sb.append(" "); } else {
	 * sb.append(elements[i].asText()); sb.append(" "); } }
	 * 
	 * sb.append("}\n"); } if (node instanceof ForLoopNode) { ForLoopNode
	 * forLoopNode = (ForLoopNode) node;
	 * sb.append(sbLevel.toString()).append("ForLoopNode content: {");
	 * 
	 * sb.append(forLoopNode.getVariable().asText()); sb.append(" "); if
	 * (forLoopNode.getStartExpression() instanceof ElementString) {
	 * sb.append("\""); sb.append(forLoopNode.getStartExpression().asText());
	 * sb.append("\""); sb.append(" "); } else {
	 * sb.append(forLoopNode.getStartExpression().asText()); sb.append(" "); }
	 * 
	 * if (forLoopNode.getEndExpression() instanceof ElementString) {
	 * sb.append("\""); sb.append(forLoopNode.getEndExpression().asText());
	 * sb.append("\""); sb.append(" "); } else {
	 * sb.append(forLoopNode.getEndExpression().asText()); sb.append(" "); }
	 * 
	 * if (forLoopNode.getStepExpression() != null) { if
	 * (forLoopNode.getStepExpression() instanceof ElementString) { sb.append("\"");
	 * sb.append(forLoopNode.getStepExpression().asText()); sb.append("\"");
	 * sb.append(" "); } else { sb.append(forLoopNode.getStepExpression().asText());
	 * sb.append(" "); } }
	 * 
	 * sb.append("}\n"); }
	 * 
	 * for (int i = 0; i < node.numberOfChildren(); i++) {
	 * printRecursively(node.getChild(i), sb, level + 1); } }
	 * 
	 */
}
