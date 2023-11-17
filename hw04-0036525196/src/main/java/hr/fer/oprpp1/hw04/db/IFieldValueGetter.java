package hr.fer.oprpp1.hw04.db;

/**
 * Interface for field value getters.
 * 
 * @author Florijan Rusac
 * @version 1.0
 */
public interface IFieldValueGetter {
	/**
	 * Get a field value from student record.
	 * 
	 * @param record from which to get field value
	 * @return field value
	 */
	public String get(StudentRecord record);
}
