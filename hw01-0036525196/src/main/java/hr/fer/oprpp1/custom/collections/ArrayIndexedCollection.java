package hr.fer.oprpp1.custom.collections;

/**
 *  Resizable array-backed collection of objects
 *  that extends Collection. If capacity is full a
 *  new array with double the size will be created
 *  and objects will be copied in it. Duplicate
 *  elements are allowed, null elements are not.
 *  
 *  Used to conveniently store and manipulate 
 *  multiple objects.
 * 
 * @author Florijan Rusac
 * @version 1.0
 */
public class ArrayIndexedCollection extends Collection {
	/**
	 * Number of objects in collection.
	 */
	private int size;
	/**
	 * Array used to store objects.
	 */
	private Object[] elements;
	
	/**
	 * Initializes capacity to 16.
	 */
	public ArrayIndexedCollection() {
		this(16);
	}
	
	/**
	 * Sets capacity of array to value of argument.
	 * 
	 * @param initialCapacity of array
	 * @throws IllegalArgumentException if initialCapacity
	 * 		is less than 1
	 */
	public ArrayIndexedCollection(int initialCapacity) {
		if (initialCapacity < 1) {
			throw new IllegalArgumentException();
		}
		elements = new Object[initialCapacity];
	}
	
	/**
	 * Initializes capacity to 16 and adds objects of other
	 * collection to this one.
	 * 
	 * @param other collection whose content will be added 
	 * 		to this one
	 */
	public ArrayIndexedCollection(Collection other) {
		this(other, 16);
	}
	
	/**
	 * Initializes capacity to initialCapacity and adds 
	 * objects of other collection to this one.
	 * 
	 * @param other collection whose content will be added 
	 * 		to this one
	 * @param initialCapacity of array
	 * @throws IllegalArgumentException if initialCapacity 
	 * 		is less than 1
	 */
	public ArrayIndexedCollection(Collection other, int initialCapacity) {
		if (initialCapacity < 1) {
			throw new IllegalArgumentException();
		}
		if (other.size() > initialCapacity) {
			initialCapacity = other.size();
		}
		elements = new Object[initialCapacity];
		addAll(other);
	}
	
	/**
	 * Returns number of elements in array.
	 * 
	 * @return number of elements in array.
	 */
	@Override
	public int size() {
		return size;
	}
	
	/**
	 * Adds the given object into this collection 
	 * (reference is added into first empty place in 
	 * the elements array; if the elements array is 
	 * full, it is reallocated by doubling its size).
	 * 
	 * @param value to be added
	 * @throws NullPointerException if value is null
	 */
	@Override
	public void add(Object value) {
		if (value == null) throw new NullPointerException();
		
		if (size == elements.length) {
			Object[] elementsTemp = new Object[2*elements.length];
			
			for(int i = 0; i < size; i++) {
				elementsTemp[i] = elements[i];
			}
			
			elements = elementsTemp;
		}
		
		elements[size] = value;
		size++;
	}
	
	/**
	 * Checks if argument is in collection.
	 * 
	 * @param value to be checked
	 * @return true if value is in collection, 
	 * 		false otherwise
	 */
	@Override
	public boolean contains(Object value) {
		if (value == null) return false;
		
		for(int i = 0; i < size; i++) {
			if (elements[i].equals(value)) return true;
		}
		
		return false;
	}
	
	/**
	 * Removes passed element from collection.
	 * Return false if element is not in collection.
	 * 
	 * @param value to be removed
	 * @return true if value is removed, false otherwise
	 * @throws NullPointerException if value is null
	 */
	@Override
	public boolean remove(Object value) {
		if (value == null) throw new NullPointerException();
		
		for(int i = 0; i < size; i++) {
			if (elements[i].equals(value)) {
				for(int j = i; j < size - 1; j++) {
					elements[j] = elements[j+1];
				}
				elements[size - 1] = null;
				size--;
				return true;
			}
		}
		
		return false;
	}
	
	/**
	 * Removes element at specified index from collection. 
	 * Element that was previously at location index+1 
	 * after this operation is on location index, etc.
	 * 
	 * @param index of element to be removed
	 * @throws IndexOutOfBoundsException if index is < 0 or
	 * 		greater than size-1
	 */
	public void remove(int index) {
		if (index < 0 || index > size-1) 
			throw new IndexOutOfBoundsException();
		
		for(int i = index; i < size - 1; i++) {
			elements[i] = elements[i+1];
		}
		elements[size - 1] = null;
		size--;
	}
	
	/**
	 * Puts all objects that are in collection in an array
	 * and returns it.
	 * 
	 * @return array of all objects in collection
	 */
	@Override
	public Object[] toArray() {
		Object[] arr = new Object[size];
		
		for(int i = 0; i < size; i++) {
			arr[i] = elements[i];
		}
		
		return arr;
	}
	
	/**
	 * Calls method process from class Processor on
	 * each element in collection.
	 * 
	 * @param processor whose method .process will
	 * 		be called on each object in collection
	 */
	@Override
	public void forEach(Processor processor) {
		for(int i = 0; i < size; i++) {
			processor.process(elements[i]);
		}
	}
	
	/**
	 * Removes all elements from the collection. 
	 * The allocated array is left at current capacity. 
	 */
	@Override
	public void clear() {
		for(int i = 0; i < size; i++) {
			elements[i] = null;
		}
		size = 0;
	}
	
	/**
	 * Returns the object that is stored in 
	 * backing array at position index. 
	 * Valid indexes are 0 to size-1.
	 * 
	 * @param index of object to be returned
	 * @return object at passed index
	 * @throws IndexOutOfBoundsException if index is
	 * 		less than zero or more than size-1
	 */
	public Object get(int index) {
		if (index < 0 || index > size-1) {
			throw new IndexOutOfBoundsException();
		}
		
		return elements[index];
	}
	
	/**
	 * Inserts (does not overwrite) the given value 
	 * at the given position in array. The legal 
	 * positions are 0 to size (both are included).
	 * 
	 * @param value to be inserted
	 * @param position where to be inserted
	 * @throws IndexOutOfBoundsException if position is less
	 * 		than zero or greater than size
	 * @throws NullPointerException if value is null
	 */
	public void insert(Object value, int position) {
		if (value == null) throw new NullPointerException();
		if (position < 0 || position > size) 
			throw new IndexOutOfBoundsException();
		
		if (elements.length == size) {
			add(elements[size - 1]);
			elements[size - 1] = null;
			size--;
		}
		
		for(int i = size - 1; i > position - 1; i--) {
			elements[i + 1] = elements[i];
		}
		elements[position] = value;
		size++;
	}
	
	/**
	 * Searches the collection and returns the index 
	 * of the first occurrence of the given value 
	 * or -1 if the value is not found.
	 * 
	 * @param value of object whose index is sought after
	 * @return index of value
	 */
	public int indexOf(Object value) {
		if (value == null) return -1;
		
		for(int i = 0; i < size; i++) {
			if(elements[i].equals(value)) return i;
		}
		
		return -1;
	}

}












