package hr.fer.oprpp1.hw04.db;

/**
 * Concrete strategies for IFieldValueGetter.
 * 
 * @author Florijan Rusac
 * @version 1.0
 */
public class FieldValueGetters {
	/**
	 * Returns first name from student record.
	 */
	public static final IFieldValueGetter FIRST_NAME = (x) -> {
		return x.getFirstName();
	};
	/**
	 * Returns last name from student record.
	 */
	public static final IFieldValueGetter LAST_NAME = (x) -> {
		return x.getLastName();
	};
	/**
	 * Returns jmbag from student record.
	 */
	public static final IFieldValueGetter JMBAG = (x) -> {
		return x.getJmbag();
	};

}
