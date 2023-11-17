package hr.fer.oprpp1.hw04.db;

/**
 * Inteface for filtering student records.
 * 
 * @author Florijan Rusac
 * @version 1.0
 */
public interface IFilter {
	/**
	 * Checks if the record is accepted
	 * 
	 * @param record to be checked
	 * @return true if record satisfies condition,
	 * 		false otherwise
	 */
	public boolean accepts(StudentRecord record);
}
