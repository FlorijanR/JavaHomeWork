package hr.fer.oprpp1.hw04.db;

/**
 * A class that stores a conditional expression.
 * Used to save conditional expressions from query.
 * 
 * @author Florijan Rusac
 * @version 1.0
 */
public class ConditionalExpression {
	/**
	 * IFieldValueGetter used in expression.
	 */
	IFieldValueGetter fieldGetter;
	/**
	 * String literal used in expression.
	 */
	String stringLiteral;
	/**
	 * IComparisonOperator used in expression.
	 */
	IComparisonOperator comparisonOperator;

	/**
	 * Initializes the variables.
	 * 
	 * @param fieldGetter used in expression
	 * @param stringLiteral used in expression
	 * @param comparisonOperator used in expression
	 */
	public ConditionalExpression(IFieldValueGetter fieldGetter, String stringLiteral,
			IComparisonOperator comparisonOperator) {
		this.fieldGetter = fieldGetter;
		this.stringLiteral = stringLiteral;
		this.comparisonOperator = comparisonOperator;
	}

	/**
	 * @return the fieldGetter
	 */
	public IFieldValueGetter getFieldGetter() {
		return fieldGetter;
	}

	/**
	 * @return the stringLiteral
	 */
	public String getStringLiteral() {
		return stringLiteral;
	}

	/**
	 * @return the comparisonOperator
	 */
	public IComparisonOperator getComparisonOperator() {
		return comparisonOperator;
	}

}
