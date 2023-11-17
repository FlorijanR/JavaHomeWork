package hr.fer.oprpp1.custom.demo;

import hr.fer.oprpp1.custom.collections.*;

/**
 * Demonstrates method addAllSatisfying().
 * 
 * @author Florijan Rusac
 * @version 1.0
 */
public class AddAllSatisfyingDemo {

	/**
	 * Creates two new collections and ads elements to them.
	 * On the second collection uses method addAllSatisfying()
	 * and then prints the elements of the collection.
	 * 
	 * @param args not used
	 */
	public static void main(String[] args) {
		Collection col1 = new LinkedListIndexedCollection();
		Collection col2 = new ArrayIndexedCollection();
		
		col1.add(2);
		col1.add(3);
		col1.add(4);
		col1.add(5);
		col1.add(6);
		
		col2.add(12);
		col2.addAllSatisfying(col1, new EvenIntegerTester());
		
		col2.forEach(System.out::println);
	}

}
