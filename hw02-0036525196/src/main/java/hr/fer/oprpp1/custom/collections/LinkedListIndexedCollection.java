package hr.fer.oprpp1.custom.collections;

import java.util.ConcurrentModificationException;
import java.util.NoSuchElementException;

/**
 * Linked list-backed collection of objects.
 * Duplicate elements are allowed, null elements are not.
 * 
 * Used to conveniently store and manipulate 
 * multiple objects.
 * 
 * @author Florijan Rusac
 * @version 1.0
 */
public class LinkedListIndexedCollection implements List{
	
	/**
	 * ListNode represents one node in a list with value and
	 * references to previous and next node.
	 * 
	 * Used in LinkedListIndexedCollection as a node in a list.
	 * 
	 * @author Florijan Rusac
	 * @version 1.0
	 */
	private static class ListNode {
		/**
		 * Reference to previous node.
		 */
		ListNode previous;
		/**
		 * Reference to next node.
		 */
		ListNode next;
		/**
		 * Value of this node.
		 */
		Object value;
	}
	
	/**
	 *  Current size of collection.
	 */
	private int size;
	/**
	 *  Reference to the first node of the linked list.
	 */
	private ListNode first;
	/**
	 *  Reference to the last node of the linked list.
	 */
	private ListNode last;
	
	/**
	 * Count the number of modifications
	 * of the list.
	 */
	private long modificationCount;
	
	/**
	 * Initializes first and last fields to null.
	 */
	public LinkedListIndexedCollection() {
		first = null;
		last = null;
	}
	
	/**
	 * Adds all elements from other collection to this one.
	 * 
	 * @param other collection from which elements
	 * 		are added to this one.
	 */
	public LinkedListIndexedCollection(Collection other) {
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
	 * at the end of collection; newly added element 
	 * becomes the element at the biggest index.
	 * 
	 * @param value to be added
	 * @throws NullPointerException if value is null
	 */
	@Override
	public void add(Object value) {
		if (value == null) throw new NullPointerException();
		
		if (size == 0) {
			first = new ListNode();
			last = first;
			first.value = value;
		} else {
			ListNode temp = new ListNode();
			temp.previous = last;
			temp.value = value;
			last.next = temp;
			last = temp;
		}
		modificationCount++;
		size++;
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
		if (value == null) 
			throw new NullPointerException();
		if (position < 0 || position > size)
			throw new IndexOutOfBoundsException();
		
		if (position == size) {
			add(value);
			modificationCount++;
			return;
		}
		
		ListNode temp = new ListNode();
		temp.value = value;
		if (position == 0) {
			temp.next = first;
			first.previous = temp;
			first = temp;
			size++;
			modificationCount++;
			return;
		}
		
		ListNode current = first;
		for(int i = 0; i < position; i++) {
			current = current.next;
		}
		temp.next = current;
		temp.previous = current.previous;
		current.previous.next = temp;
		current.previous = temp;
		size++;
		modificationCount++;
		return;
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
		
		ListNode current = first;
		for(int i = 0; i < size; i++) {
			if (current.value.equals(value)) return true;
			current = current.next;
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
		if (index < 0 || index > size - 1) {
			throw new IndexOutOfBoundsException();
		}		
		
		if (size == 1) {
			first = last = null;
			size--;
			modificationCount++;
			return;
		}
		
		if (index == 0) {
			first = first.next;
			first.previous = null;
			size--;
			modificationCount++;
			return;
		}
		
		if (index == size - 1) {
			last = last.previous;
			last.next = null;
			size--;
			modificationCount++;
			return;
		}
		
		ListNode current = first;
		for(int i = 0; i < index; i++) {
			current = current.next;
		}
		current.previous.next = current.next;
		current.next.previous = current.previous;
		size--;
		modificationCount++;
		return;
		
	}
	
	/**
	 * Removes passed element from collection.
	 * Return false if element is not in collection.
	 * 
	 * @param value to be removed
	 * @return true if value is removed, false otherwise
	 */
	@Override
	public boolean remove(Object value) {
		if (value == null || size == 0) return false;
		if (size == 1 && first.value.equals(value)) {
			size--;
			first = last = null;
			modificationCount++;
			return true;
		};
		
		ListNode current = first;
		for(int i = 0; i < size; i++) {
			if (current.value.equals(value)) {
				if (i == 0) {
					current.next.previous = null;
					first = current.next;
					size--;
					modificationCount++;
					return true;
				}
				
				if (i == size - 1) {
					current.previous.next = null;
					last = current.previous;
					size--;
					modificationCount++;
					return true;
				}
				
				current.previous.next = current.next;
				current.next.previous = current.previous;
				size--;
				modificationCount++;
				return true;
			}
			current = current.next;
		}
		
		return false;
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
		
		ListNode current = first;
		for(int i = 0; i < size; i++) {
			arr[i] = current.value;
			current = current.next;
		}
		
		return arr;
	}
	
	/**
	 * Removes all elements from the collection. 
	 */
	@Override
	public void clear() {
		first = last = null;
		size = 0;
		modificationCount++;
	}
	
	/**
	 * Returns the object that is stored in 
	 * backing list at position index. 
	 * Valid indexes are 0 to size-1.
	 * 
	 * @param index of object to be returned
	 * @return object at passed index
	 * @throws IndexOutOfBoundsException if index is
	 * 		less than zero or more than size-1
	 */
	public Object get(int index) {
		if (index < 0 || index > size - 1) {
			throw new IndexOutOfBoundsException();
		}
		
		ListNode current;
		if (index < size/2) {
			current = first;
			for(int i = 0; i < index; i++) {
				current = current.next;
			}
		} else {
			current = last;
			int lastIndex = size - 1;
			for(int i = lastIndex; i > index; i--) {
				current = current.previous;
			}
		}
		return current.value;
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
		
		ListNode current = first;
		for(int i = 0; i < size; i++) {
			if (current.value.equals(value)) return i;
			current = current.next;
		}
		
		return -1;
	}
	
	/**
	 * @return an object that implements ElementsGetter
	 */
	public ElementsGetter createElementsGetter() {
		return new ElementsGetterList(this);
	}
	
	/**
	 * A static class whose main purpose is to 
	 * implement ElementsGetter. It is connected to
	 * an instance of a collection by a reference
	 * which it gets in its constructor. Returns objects 
	 * by moving through the internal linked list 
	 * from first node onwards.
	 * 
	 * Used to return an element of a collection and 
	 * check if there are more elements to return.
	 * 
	 * @author Florijan Rusac
	 * @version 1.0
	 */
	private static class ElementsGetterList implements ElementsGetter{
		/**
		 * Saves current node in collection 
		 * whose value is next to return.
		 */
		private ListNode currentNode;
		/**
		 * Number of remaining objects in collection.
		 */
		private int numberOfRemaining;
		/**
		 * Count the number of modifications
		 * of the list.
		 */
		private long savedModificationCount;
		/**
		 * Reference to an instance of LinkedListIndexedCollection
		 * whose elements we will be getting and checking if 
		 * there are more.
		 */
		private LinkedListIndexedCollection llc;
		
		/**
		 * Constructor that initializes member variables and links
		 * this instance of ElementsGetterArray to 
		 * LinkedListIndexedCollection.
		 * 
		 * @param llc collection that will be used to get elements
		 */
		public ElementsGetterList(LinkedListIndexedCollection llc) {
			currentNode = llc.first;
			savedModificationCount = llc.modificationCount;
			numberOfRemaining = llc.size;
			this.llc = llc;
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
			if (savedModificationCount != llc.modificationCount)
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
			if (savedModificationCount != llc.modificationCount)
				throw new ConcurrentModificationException();
			
			if (!hasNextElement()) {
				throw new NoSuchElementException();
			}
			
			Object object = currentNode.value;
			currentNode = currentNode.next;
			numberOfRemaining--;
			return object;
		}
		
	}

}












