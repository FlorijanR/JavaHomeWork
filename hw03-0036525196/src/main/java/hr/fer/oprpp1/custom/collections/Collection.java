package hr.fer.oprpp1.custom.collections;

/**
 * Represents a general collection of objects.
 * It has methods such as add objects, remove them,
 * and check if an object is in the collection.
 * 
 * Used to implement a collections that 
 * can store and manipulate multiple objects. 
 * 
 * @author Florijan Rusac
 * @version 1.0
 */
public interface Collection<T> {
	
	/**
	 * Check if collection is empty.
	 * 
	 * @return true if collection contains 
	 * no objects and false otherwise.
	 */
	default boolean isEmpty() {
		if (size() == 0) {
			return true;
		}
		else {
			return false;
		}
	}
	
	/**
	 * Return number of elements in collection.
	 * 
	 * @return number of elements in collection.
	 */
	int size();
	
	/**
	 * Add an element in collection.
	 * 
	 * @param value to be added in collection
	 */
	void add(T value);
	
	/**
	 * Checks if passed argument is in collection.
	 * 
	 * @param value to be checked if it is in collection
	 * @return true if value is in collection, false otherwise
	 */
	boolean contains(T value);
	
	/**
	 * Removes passed argument from collection.
	 * 
	 * @param value to be removed from collection.
	 * @return true if object was removed, false otherwise
	 */
	boolean remove(T value);
	
	/**
	 * Puts all objects that are in collection in an array
	 * and returns it.
	 * 
	 * @return array of all objects in collection
	 */
	T[] toArray();
	
	/**
	 * Calls method process from class Processor on
	 * each element in collection.
	 * 
	 * @param processor whose method .process will
	 * 		be called on each object in collection
	 */
	default void forEach(Processor<? super T> processor) {
		ElementsGetter<T> eg = this.createElementsGetter();
		
		while(eg.hasNextElement()) {
			processor.process(eg.getNextElement());
		}
	}
	
	/**
	 * Adds all objects from other collection in 
	 * this collection.
	 * 
	 * @param other collection from which to add 
	 * 		object to this collection
	 */
	default void addAll(Collection<? extends T> other) {		
		other.forEach(value -> add(value));
	}
	
	/**
	 * Adds all objects from passed collection to this collection
	 * if they pass the test from passed tester.
	 * 
	 * @param col from where elements will be tested and added 
	 * 		to this collection
	 * @param tester calls method test on all objects from col
	 * 		and adds them if they pass the test
	 */
	default void addAllSatisfying(Collection<? extends T> col, Tester<? super T> tester) {
		ElementsGetter<? extends T> eg = col.createElementsGetter();
		T obj;
		
		while(eg.hasNextElement()) {
			obj = eg.getNextElement();
			if (tester.test(obj)) {
				add(obj);
			}
		}
	}

	/**
	 * Deletes all elements in collection.
	 */
	void clear();
	
	/**
	 * Creates and return an object that implements 
	 * ElementsGetter.
	 * 
	 * @return ElementsGetter used to get
	 * 		one by one elements of collection
	 */
	ElementsGetter<T> createElementsGetter();
}



























