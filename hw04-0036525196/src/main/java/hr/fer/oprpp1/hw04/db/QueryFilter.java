package hr.fer.oprpp1.hw04.db;

import java.util.List;

/**
 * A class that has a filter based on 
 * conditional expressions in the query.
 * 
 * @author Florijan Rusac
 * @version 1.0
 */
public class QueryFilter implements IFilter {
	/**
	 * List of conditional expressions.
	 */
	List<ConditionalExpression> expressions;

	/**
	 * Initializes expressions.
	 * 
	 * @param expressions list of conditional expressions from query.
	 */
	public QueryFilter(List<ConditionalExpression> expressions) {
		this.expressions = expressions;
	}

	/**
	 * Checks if the record is accepted or not based on
	 * a list of conditional expressions.
	 * 
	 * @param record to be checked if it is accepted
	 * @return true if the record is accepted, false otherwise
	 */
	@Override
	public boolean accepts(StudentRecord record) {
		for (ConditionalExpression expression : expressions) {
			if (!expression.getComparisonOperator()
					.satisfied(expression.getFieldGetter().get(record),
					expression.getStringLiteral())) {
				return false;
			}
		}
		return true;
	}

}
