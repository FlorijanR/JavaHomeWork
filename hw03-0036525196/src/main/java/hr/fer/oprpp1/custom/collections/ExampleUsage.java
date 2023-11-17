package hr.fer.oprpp1.custom.collections;

import java.util.Arrays;

/**
 * Used to test the functionality of classes 
 * ArrayIndexedCollection and LinkedListIndexedCollection.
 * 
 * the functionality is tested by creating objects of those
 * classes and calling various methods upon them.
 * 
 * @author Florijan Rusac
 * @version 1.0
 */
public class ExampleUsage {

	/**
	 * Used to test classes ArrayIndexedCollection 
	 * and LinkedListIndexedCollection.
	 * 
	 * @param args command line arguments
	 */
	public static void main(String[] args) {
		ArrayIndexedCollection<Object> col = new ArrayIndexedCollection<>(2);
		
		col.add(Integer.valueOf(20));
		col.add("New York");
		col.add("San Francisco"); // here the internal array is reallocated to 4
		
		System.out.println(col.contains("New York")); // writes: true
		
		col.remove(1); // removes "New York"; shifts "San Francisco" to position 1
		
		System.out.println(col.get(1)); // writes: "San Francisco"
		System.out.println(col.size()); // writes: 2
		
		col.add("Los Angeles");
		
		LinkedListIndexedCollection<Object> col2 = new LinkedListIndexedCollection<>(col);
		
		System.out.println("col elements:");
		col.forEach(o -> System.out.println(o));
		
		System.out.println("col elements again:");
		System.out.println(Arrays.toString(col.toArray()));
		
		System.out.println("col2 elements:");
		col2.forEach(o -> System.out.println(o));
		
		System.out.println("col2 elements again:");
		System.out.println(Arrays.toString(col2.toArray()));
		
		System.out.println(col.contains(col2.get(1))); // true
		System.out.println(col2.contains(col.get(1))); // true
		
		col.remove(Integer.valueOf(20)); // removes 20 from collection (at position 0)
		
		System.out.println("col elements:");
		col.forEach(o -> System.out.println(o));

	}

}
