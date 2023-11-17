package hr.fer.oprpp1.custom.scripting.parser;

import hr.fer.oprpp1.custom.collections.ObjectStack;
import hr.fer.oprpp1.custom.scripting.elems.*;
import hr.fer.oprpp1.custom.scripting.lexer.Lexer;
import hr.fer.oprpp1.custom.scripting.lexer.Token;
import hr.fer.oprpp1.custom.scripting.lexer.TokenType;
import hr.fer.oprpp1.custom.scripting.nodes.*;

/**
 * Parser that accepts text in a constructor
 * to parse. To generate tokens, a lexer is used.
 * 
 * Used to parse given text and create a
 * document model.
 * 
 * @author Florijan Rusac
 * @version 1.0
 */
public class SmartScriptParser {
	/**
	 * Lexer used to generate tokens.
	 */
	Lexer lexer;
	/**
	 * Document node used to build document model.
	 */
	DocumentNode docNode;
	/**
	 * Stack used to keep track of parent node.
	 */
	ObjectStack stack;
	/**
	 * Current token.
	 */
	Token token;
	/**
	 * Counts the number of For tags and end tags.
	 */
	int countFor;

	/**
	 * Constructor that accepts text which will be
	 * parsed. Passed to the text to lexer and 
	 * starts parsing.
	 * 
	 * @param text to be parsed
	 * @throws SmartScriptParserException
	 */
	public SmartScriptParser(String text) {
		try {
			lexer = new Lexer(text);
			docNode = new DocumentNode();
			stack = new ObjectStack();
			stack.push(docNode);

			parse();
		} catch (Exception e) {
			throw new SmartScriptParserException();
		}
	}

	/**
	 * Checks what the next token is and processes it.
	 * 
	 * @throws SmartScriptParserException if tokens
	 * 		are in an invalid order.
	 */
	private void parse() {
		while (true) {
			token = lexer.nextToken();
			if (token.getType() == TokenType.EOF) {
				if (countFor == 0)
					break;
				else
					throw new SmartScriptParserException();
			}

			if (token.getType() == TokenType.TEXT) {
				createTextNode();

			} else if (token.getType() == TokenType.TAG) {
				token = lexer.nextToken();

				if (token.getType() == TokenType.TAGTYPE && ((String) token.getValue()).equals("END")) {
					stack.pop();
					token = lexer.nextToken();
					if (!(((String) token.getValue()).equals("$}"))) {
						throw new SmartScriptParserException();
					}
					countFor--;
					if (countFor < 0) {
						throw new SmartScriptParserException();
					}

				} else if (token.getType() == TokenType.TAGTYPE && ((String) token.getValue()).equals("FOR")) {
					countFor++;
					createForNode();

				} else if (token.getType() == TokenType.TAGTYPE && ((String) token.getValue()).equals("=")) {
					createEqualsNode();

				} else {
					throw new SmartScriptParserException();
				}

			} else {
				throw new SmartScriptParserException();
			}

		}
	}

	/**
	 * Creates equals node and adds it as a child to
	 * the parent node.
	 * 
	 * @throws SmartScriptParserException if tokens
	 * 		are in an invalid order.
	 */
	private void createEqualsNode() {
		Node parentNode = (Node) stack.peek();
		ObjectStack helper = new ObjectStack();
		Token eachToken;
		String str;
		Element[] elements;

		token = lexer.nextToken();
		while (token.getType() != TokenType.TAG) {
			helper.push(token);
			token = lexer.nextToken();
		}

		elements = new Element[helper.size()];

		for (int i = elements.length - 1; i >= 0; i--) {
			eachToken = (Token) helper.pop();
			if (eachToken.getType() == TokenType.DOUBLE) {
				elements[i] = new ElementConstantDouble((Double) eachToken.getValue());
			} else if (eachToken.getType() == TokenType.INTEGER) {
				elements[i] = new ElementConstantInteger((Integer) eachToken.getValue());
			} else if (eachToken.getType() == TokenType.STRING) {
				str = (String) eachToken.getValue();
				elements[i] = new ElementString(str.substring(1, str.length() - 1));
			} else if (eachToken.getType() == TokenType.OPERATOR) {
				elements[i] = new ElementOperator((String) eachToken.getValue());
			} else if (eachToken.getType() == TokenType.VARIABLE) {
				elements[i] = new ElementVariable((String) eachToken.getValue());
			} else if (eachToken.getType() == TokenType.FUNCTION) {
				str = (String) eachToken.getValue();
				elements[i] = new ElementFunction(str.substring(1, str.length()));
			} else {
				throw new SmartScriptParserException();
			}
		}

		parentNode.addChildNode(new EchoNode(elements));
	}

	/**
	 * Creates for node and adds it as a child to
	 * the parent node.
	 * 
	 * @throws SmartScriptParserException if tokens
	 * 		are in an invalid order.
	 */
	private void createForNode() {
		ElementVariable variable = null;
		Element startExpression = null;
		Element endExpression = null;
		Element stepExpression = null;
		ForLoopNode newNode;

		Node parentNode = (Node) stack.peek();

		token = lexer.nextToken();
		if (token.getType() == TokenType.VARIABLE) {
			variable = new ElementVariable((String) token.getValue());
		} else {
			throw new SmartScriptParserException();
		}

		token = lexer.nextToken();
		startExpression = setElement();

		token = lexer.nextToken();
		endExpression = setElement();

		token = lexer.nextToken();
		if (token.getType() == TokenType.TAG && ((String) token.getValue()).equals("$}")) {
			// do nothing
		} else {
			stepExpression = setElement();
		}

		token = lexer.nextToken();
		if (token.getType() == TokenType.TAG && ((String) token.getValue()).equals("$}")) {
			// do nothing
		} else {
			throw new SmartScriptParserException();
		}

		newNode = new ForLoopNode(variable, startExpression, endExpression, stepExpression);
		parentNode.addChildNode(newNode);
		stack.push(newNode);

	}

	/**
	 * create an element based on the current token.
	 * 
	 * @return created element
	 * @throws SmartScriptParserException if current
	 * 		token is of invalid type
	 */
	private Element setElement() {
		Element element = null;
		if (token.getType() == TokenType.VARIABLE || token.getType() == TokenType.STRING
				|| token.getType() == TokenType.INTEGER || token.getType() == TokenType.DOUBLE) {

			if (token.getType() == TokenType.VARIABLE) {
				element = new ElementVariable((String) token.getValue());
			}
			if (token.getType() == TokenType.STRING) {
				String str = (String) token.getValue();
				element = new ElementString(str.substring(1, str.length() - 1));
			}
			if (token.getType() == TokenType.INTEGER) {
				element = new ElementConstantInteger((Integer) token.getValue());
			}
			if (token.getType() == TokenType.DOUBLE) {
				element = new ElementConstantDouble((Double) token.getValue());
			}
			
			return element;
		} else {
			throw new SmartScriptParserException();
		}
	}

	/**
	 * Creates text node and adds it as a child to
	 * the parent node.
	 */
	private void createTextNode() {
		Node parentNode = (Node) stack.peek();

		parentNode.addChildNode(new TextNode((String) token.getValue()));
	}

	/**
	 * @return document node representing document model
	 */
	public DocumentNode getDocumentNode() {
		return docNode;
	}

}
