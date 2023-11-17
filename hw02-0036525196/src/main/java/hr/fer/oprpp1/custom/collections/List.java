package hr.fer.oprpp1.custom.collections;

/**
 * Defines methods that are commonly used in lists.
 * 
 * @author Florijan Rusac
 * @version 1.0
 */
public interface List extends Collection {
	/**
	 * Returns the object that is stored in 
	 * backing array at position index. 
	 * Valid indexes are 0 to size-1.
	 * 
	 * @param index of object to be returned
	 * @return object at passed index
	 */
	Object get(int index);

	/**
	 * Inserts (does not overwrite) the given value 
	 * at the given position in array. The legal 
	 * positions are 0 to size (both are included).
	 * 
	 * @param value to be inserted
	 * @param position where to be inserted
	 */
	void insert(Object value, int position);

	/**
	 * Searches the collection and returns the index 
	 * of the first occurrence of the given value 
	 * or -1 if the value is not found.
	 * 
	 * @param value of object whose index is sought after
	 * @return index of value
	 */
	int indexOf(Object value);

	/**
	 * Removes element at specified index from collection. 
	 * Element that was previously at location index+1 
	 * after this operation is on location index, etc.
	 * 
	 * @param index of element to be removed
	 */
	void remove(int index);
}
