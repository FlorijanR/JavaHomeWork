package hr.fer.oprpp1.hw04.db;

/**
 * Interface for comparison operators.
 * 
 * @author Florijan Rusac
 * @version 1.0
 */
public interface IComparisonOperator {
	/**
	 * Check if value1 compared to value2 returns true or false.
	 * 
	 * @param value1 to be checked
	 * @param value2 to be checked
	 * @return true or false depending on the way the method is implemented
	 */
	public boolean satisfied(String value1, String value2);
}
