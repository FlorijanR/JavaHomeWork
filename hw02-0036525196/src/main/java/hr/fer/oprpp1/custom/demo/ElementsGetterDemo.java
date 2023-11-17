package hr.fer.oprpp1.custom.demo;

import hr.fer.oprpp1.custom.collections.*;

/**
 * Used to demonstrate ElementsGetter.
 * 
 * @author Florijan Rusac
 * @version 1.0
 */
public class ElementsGetterDemo {

	/**
	 * Creates two collections, add elements in them
	 * and tests methods createElementsGetter() and 
	 * getNextElement(). Expected output is:
	 * 
	 * Jedan element: Ivo
	 * Jedan element: Ana
	 * Jedan element: Ivo
	 * Jedan element: Jasmina
	 * Jedan element: Štefanija
	 * 
	 * @param args not used
	 */
	public static void main(String[] args) {
		Collection col1 = new ArrayIndexedCollection();
		Collection col2 = new ArrayIndexedCollection();
		
		col1.add("Ivo");
		col1.add("Ana");
		col1.add("Jasna");
		
		col2.add("Jasmina");
		col2.add("Štefanija");
		col2.add("Karmela");
		
		ElementsGetter getter1 = col1.createElementsGetter();
		ElementsGetter getter2 = col1.createElementsGetter();
		
		ElementsGetter getter3 = col2.createElementsGetter();
		
		System.out.println("Jedan element: " + getter1.getNextElement());
		System.out.println("Jedan element: " + getter1.getNextElement());
		
		System.out.println("Jedan element: " + getter2.getNextElement());
		
		System.out.println("Jedan element: " + getter3.getNextElement());
		System.out.println("Jedan element: " + getter3.getNextElement());
	}

}
