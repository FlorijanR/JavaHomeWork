package hr.fer.oprpp1.hw04.db;

/**
 * Concrete strategies for IComparisonOperator.
 * 
 * @author Florijan Rusac
 * @version 1.0
 */
public class ComparisonOperators {
	/**
	 * Compares two strings using operator less.
	 */
	public static final IComparisonOperator LESS = (x, y) -> {
		return x.compareTo(y) < 0;
	};
	/**
	 * Compares two strings using operator less or equals.
	 */
	public static final IComparisonOperator LESS_OR_EQUALS = (x, y) -> {
		return x.compareTo(y) <= 0;
	};
	/**
	 * Compares two strings using operator greater.
	 */
	public static final IComparisonOperator GREATER = (x, y) -> {
		return x.compareTo(y) > 0;
	};
	/**
	 * Compares two strings using operator greater or equals.
	 */
	public static final IComparisonOperator GREATER_OR_EQUALS = (x, y) -> {
		return x.compareTo(y) >= 0;
	};
	/**
	 * Compares two strings using operator equals.
	 */
	public static final IComparisonOperator EQUALS = (x, y) -> {
		return x.compareTo(y) == 0;
	};
	/**
	 * Compares two strings using operator not equals.
	 */
	public static final IComparisonOperator NOT_EQUALS = (x, y) -> {
		return x.compareTo(y) != 0;
	};
	/**
	 * Checks if the first string is like the second string.
	 * the second string can have a * character which replaces
	 * zero or more characters in the first string.
	 */
	public static final IComparisonOperator LIKE = (x, y) -> {
		if (!y.contains("*")) {
			return x.equals(y);
		}
		
		if (y.charAt(0) == '*') {
			return x.contains(y.subSequence(1, y.length()));
		}

		if (y.charAt(y.length() - 1) == '*') {
			return x.contains(y.subSequence(0, y.length() - 1));
		}

		String parts[] = y.split("\\*");
		String firstPart = parts[0];
		String lastPart = parts[1];

		if (x.contains(firstPart)) {
			int pos = x.indexOf(firstPart) + firstPart.length();
			if (pos >= x.length())
				return false;
			
			x = x.substring(pos);
			if (x.contains(lastPart)) {
				return true;
			}			
		}
		return false;
	};
}






