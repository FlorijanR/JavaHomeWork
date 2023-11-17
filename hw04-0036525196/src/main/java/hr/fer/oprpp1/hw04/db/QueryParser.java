package hr.fer.oprpp1.hw04.db;

import java.util.ArrayList;
import java.util.List;

/**
 * Parser that accepts text in a constructor
 * to parse. To generate tokens, a lexer is used.
 * 
 * Used to parse given query and create a
 * list of conditional expressions.
 * 
 * @author Florijan Rusac
 * @version 1.0
 */
public class QueryParser {
	/**
	 * True if query is of direct type, false otherwise.
	 */
	boolean direct = true;
	/**
	 * Lexer used to generate tokens.
	 */
	QueryLexer lexer;
	/**
	 * Current token.
	 */
	Token token;
	/**
	 * Last generated conditional expression.
	 */
	ConditionalExpression ce;
	/**
	 * List of all conditional expressions from query.
	 */
	List<ConditionalExpression> expressions = new ArrayList<>();

	/**
	 * Constructor that accepts text which will be parsed. Passes the text to
	 * lexer and starts parsing.
	 * 
	 * @param text to be parsed
	 */
	public QueryParser(String text) {
		lexer = new QueryLexer(text);
		parse();
	}

	/**
	 * Checks what the next token is and processes it.
	 * 
	 * @throws QueryException if tokens are in an invalid order.
	 */
	private void parse() {
		boolean first = true;

		while (true) {
			token = lexer.nextToken();
			if (token.getType() == TokenType.EOF) {
				if (first == true)
					throw new QueryException();

				break;
			}

			if (first) {
				processExpression();
				if (ce.getComparisonOperator() != ComparisonOperators.EQUALS
						|| ce.getFieldGetter() != FieldValueGetters.JMBAG) {
					direct = false;
				}
				first = false;
			} else {
				if (direct)
					direct = false;

				if (token.getType() != TokenType.AND)
					throw new QueryException();

				token = lexer.nextToken();
				processExpression();
			}
		}
	}

	/**
	 * Process the following expression.
	 * 
	 * @throws QueryException if the query is in invalid format
	 */
	private void processExpression() {
		IFieldValueGetter fieldGetter = null;
		String stringLiteral = null;
		IComparisonOperator comparisonOperator = null;

		if (token.getType() != TokenType.ATTR)
			throw new QueryException();

		if ("jmbag".equals(token.getValue())) {
			fieldGetter = FieldValueGetters.JMBAG;
		} else if ("lastName".equals(token.getValue())) {
			fieldGetter = FieldValueGetters.LAST_NAME;
		} else if ("firstName".equals(token.getValue())) {
			fieldGetter = FieldValueGetters.FIRST_NAME;
		} else {
			throw new QueryException();
		}

		token = lexer.nextToken();
		if (token.getType() != TokenType.OP)
			throw new QueryException();

		if ("<".equals(token.getValue())) {
			comparisonOperator = ComparisonOperators.LESS;
		} else if (">".equals(token.getValue())) {
			comparisonOperator = ComparisonOperators.GREATER;
		} else if (">=".equals(token.getValue())) {
			comparisonOperator = ComparisonOperators.GREATER_OR_EQUALS;
		} else if ("<=".equals(token.getValue())) {
			comparisonOperator = ComparisonOperators.LESS_OR_EQUALS;
		} else if ("=".equals(token.getValue())) {
			comparisonOperator = ComparisonOperators.EQUALS;
		} else if ("!=".equals(token.getValue())) {
			comparisonOperator = ComparisonOperators.NOT_EQUALS;
		} else if ("LIKE".equals(token.getValue())) {
			comparisonOperator = ComparisonOperators.LIKE;
		} else {
			throw new QueryException();
		}

		token = lexer.nextToken();
		if (token.getType() != TokenType.STRING)
			throw new QueryException();

		stringLiteral = (String) token.getValue();

		ce = new ConditionalExpression(fieldGetter, stringLiteral, comparisonOperator);
		expressions.add(ce);
	}

	/**
	 * @return true if the query is direct, false otherwise
	 */
	public boolean isDirectQuery() {
		return direct;
	}

	/**
	 * @return jmag from query if the query was direct
	 * @throws IllegalStateException if the query was not direct
	 */
	public String getQueriedJMBAG() {
		if (!direct)
			throw new IllegalStateException();

		return ce.getStringLiteral();
	}

	/**
	 * @return a list of all conditional expressions from query
	 */
	public List<ConditionalExpression> getQuery() {
		List<ConditionalExpression> expressionsCopy = new ArrayList<>();
		expressions.forEach(x -> expressionsCopy.add(x));
		return expressionsCopy;
	}
}
