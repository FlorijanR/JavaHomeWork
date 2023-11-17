package hr.fer.oprpp1.custom.demo;

import hr.fer.oprpp1.custom.collections.*;

/**
 * Demonstrates throwing ConcurrentModificationException 
 * when modifying the collection while using 
 * ElementsGetter to go through the collection.
 * 
 * @author Florijan Rusac
 * @version 1.0
 */
public class ElementsGetterModifyDemo {

	/**
	 * Creates new collection, adds elements to it then
	 * creates ElementsGetter and gets two elements.
	 * Then it modifies the collections and tries to
	 * get one more element.
	 * 
	 * @param args not used
	 */
	public static void main(String[] args) {
		Collection col = new LinkedListIndexedCollection();
		
		col.add("Ivo");
		col.add("Ana");
		col.add("Jasna");
		
		ElementsGetter getter = col.createElementsGetter();
		
		System.out.println("Jedan element: " + getter.getNextElement());
		System.out.println("Jedan element: " + getter.getNextElement());
		
		col.clear();
		
		System.out.println("Jedan element: " + getter.getNextElement());

	}

}
