package hr.fer.oprpp1.custom.demo;

import hr.fer.oprpp1.custom.collections.*;

/**
 * Checks whether the passed object is 
 * an instance of Integer and even.
 * 
 * @author Florijan Rusac
 * @version 1.0
 */
public class EvenIntegerTester implements Tester {

	/**
	 * Checks whether the passed object 
	 * is an instance of Integer and even.
	 * 
	 * @param obj to be tested
	 * @return true if obj is an instance of 
	 * 		Integer and is even, false otherwise
	 */
	@Override
	public boolean test(Object obj) {
		if (!(obj instanceof Integer))
			return false;

		Integer i = (Integer) obj;
		return i % 2 == 0;
	}

	/**
	 * Creates an instance of EvenIntegerTester
	 * and demonstrates its use.
	 * 
	 * @param args not used
	 */
	public static void main(String[] args) {
		Tester t = new EvenIntegerTester();

		System.out.println(t.test("Ivo"));
		System.out.println(t.test(22));
		System.out.println(t.test(3));
	}

}
