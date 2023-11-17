package hr.fer.oprpp1.custom.collections;

import java.util.ConcurrentModificationException;
import java.util.NoSuchElementException;

/**
 *  Resizable array-backed collection of objects. 
 *  If capacity is full a new array with double 
 *  the size will be created and objects will 
 *  be copied in it. Duplicate elements are allowed, 
 *  null elements are not.
 *  
 *  Used to conveniently store and manipulate 
 *  multiple objects.
 * 
 * @author Florijan Rusac
 * @version 1.0
 */
public class ArrayIndexedCollection implements List {
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
	 * Count the number of modifications
	 * of the list.
	 */
	private long modificationCount;
	
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
		modificationCount++;
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
				modificationCount++;
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
		modificationCount++;
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
	 * Removes all elements from the collection. 
	 * The allocated array is left at current capacity. 
	 */
	@Override
	public void clear() {
		for(int i = 0; i < size; i++) {
			elements[i] = null;
		}
		size = 0;
		modificationCount++;
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
		modificationCount++;
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
	
	/**
	 * @return an object that implements ElementsGetter
	 */
	public ElementsGetter createElementsGetter() {
		return new ElementsGetterArray(this);
	}
	
	/**
	 * A static class whose main purpose is to 
	 * implement ElementsGetter. It is connected to
	 * an instance of a collection by a reference
	 * which it gets in its constructor. Returns objects 
	 * by moving through the internal reference field 
	 * from position zero onwards.
	 * 
	 * Used to return an element of a collection and 
	 * check if there are more elements to return.
	 * 
	 * @author Florijan Rusac
	 * @version 1.0
	 */
	private static class ElementsGetterArray implements ElementsGetter{
		/**
		 * Saves current index of an object 
		 * of collection which is next to return.
		 */
		private int currentIndex;
		/**
		 * Number of remaining objects in collection.
		 */
		private int numberOfRemaining;
		/**
		 * Reference to an instance of ArrayIndexedCollection
		 * whose elements we will be getting and checking if 
		 * there are more.
		 */
		private ArrayIndexedCollection aic;
		/**
		 * Count the number of modifications
		 * of the list.
		 */
		private long savedModificationCount;
		
		/**
		 * Constructor that initializes member variables and links
		 * this instance of ElementsGetterArray to 
		 * ArrayIndexedCollection.
		 * 
		 * @param aic collection that will be used to get elements
		 */
		public ElementsGetterArray(ArrayIndexedCollection aic) {
			currentIndex = 0;
			this.aic = aic;
			savedModificationCount = aic.modificationCount;
			numberOfRemaining = aic.size;
		}
		
		/**
		 * @return true if there is at least one more element,
		 * 		otherwise return false
		 * @throws ConcurrentModificationException if the 
		 * 		collection has been modified since the instance 
		 * 		of ElementsGetter was created
		 */
		@Override
		public boolean hasNextElement() {
			if (savedModificationCount != aic.modificationCount)
				throw new ConcurrentModificationException();
			
			if (numberOfRemaining > 0)
				return true;
			else
				return false;
		}
		
		/**
		 * Method returns next element from collection,
		 * which was not returned yet.
		 * 
		 * @return one object from collection
		 * @throws NoSuchElementException if there are no more
		 * 		elements left to return.
		 * @throws ConcurrentModificationException if the 
		 * 		collection has been modified since the instance 
		 * 		of ElementsGetter was created
		 */
		@Override
		public Object getNextElement() {
			if (savedModificationCount != aic.modificationCount)
				throw new ConcurrentModificationException();
			
			if (!hasNextElement()) {
				throw new NoSuchElementException();
			}
			
			Object object = aic.get(currentIndex);
			currentIndex++;
			numberOfRemaining--;
			return object;
		}
		
	}

}












