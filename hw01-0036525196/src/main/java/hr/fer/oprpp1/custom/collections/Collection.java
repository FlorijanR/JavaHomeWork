package hr.fer.oprpp1.custom.collections;

/**
 * Represents a general collection of objects.
 * For example, you can add objects, remove them,
 * and check if an object is in the collection.
 * 
 * Used to conveniently store and manipulate 
 * multiple objects. 
 * 
 * @author Florijan Rusac
 * @version 1.0
 */
public class Collection {
	
	/**
	 * Simple constructor.
	 */
	Collection() { 
	}
	
	/**
	 * Check if collection is empty.
	 * 
	 * @return true if collection contains 
	 * no objects and false otherwise.
	 */
	public boolean isEmpty() {
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
	public int size() {
		return 0;
	}
	
	/**
	 * Add an element in collection.
	 * 
	 * @param value to be added in collection
	 */
	public void add(Object value) {
		//do nothing
	}
	
	/**
	 * Checks if passed argument is in collection.
	 * 
	 * @param value to be checked if it is in collection
	 * @return true if value is in collection, false otherwise
	 */
	public boolean contains(Object value) {
		return false;
	}
	
	/**
	 * Removes passed argument from collection.
	 * 
	 * @param value to be removed from collection.
	 * @return true if object was removed, false otherwise
	 */
	public boolean remove(Object value) {
		return false;
	}
	
	/**
	 * Puts all objects that are in collection in an array
	 * and returns it.
	 * 
	 * @return array of all objects in collection
	 * @throws UnsupportedOperationException because 
	 * the method isn't implemented here
	 */
	public Object[] toArray() {
		throw new UnsupportedOperationException();
	}
	
	/**
	 * Calls method process from class Processor on
	 * each element in collection.
	 * 
	 * @param processor whose method .process will
	 * 		be called on each object in collection
	 */
	public void forEach(Processor processor) {
		
	}
	
	/**
	 * Adds all objects from other collection in 
	 * this collection.
	 * 
	 * @param other collection from which to add 
	 * 		object to this collection
	 */
	public void addAll(Collection other) {
		class AddProcessor extends Processor {
			
			@Override
			public void process(Object value) {
				add(value);
			}
			
		}
		
		other.forEach(new AddProcessor());
	}

	/**
	 * Deletes all elements in collection.
	 */
	public void clear() {
		
	}
}



























