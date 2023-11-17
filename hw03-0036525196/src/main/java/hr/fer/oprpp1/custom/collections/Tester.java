package hr.fer.oprpp1.custom.collections;

/**
 * Used to test an object in some way.
 * 
 * @author Florijan Rusac
 * @version 1.0
 */
public interface Tester<T> {
	/**
	 * Tests an object in some way.
	 * 
	 * @param obj to be tested
	 * @return true if obj passes the
	 * 		test, false otherwise
	 */
	boolean test(T obj);
}
