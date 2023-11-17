package hr.fer.oprpp1.custom.demo;

import hr.fer.oprpp1.custom.collections.*;

/**
 * Class used to demonstrate the use of
 * processRemaining() method.
 * 
 * @author Florijan Rusac
 * @version 1.0
 */
public class ProcessRemainingDemo {

	/**
	 * The program adds items in a new collection.
	 * Then it creates an ElementsGetter and gets one
	 * element. Then it calls the method 
	 * processRemaining(). The output should be:
	 * 
	 * Ana
	 * Jasna
	 * 
	 * @param args not used
	 */
	public static void main(String[] args) {
		Collection col = new ArrayIndexedCollection();
		
		col.add("Ivo");
		col.add("Ana");
		col.add("Jasna");
		
		ElementsGetter getter = col.createElementsGetter();
		
		getter.getNextElement();
		
		getter.processRemaining(System.out::println);
	}

}
