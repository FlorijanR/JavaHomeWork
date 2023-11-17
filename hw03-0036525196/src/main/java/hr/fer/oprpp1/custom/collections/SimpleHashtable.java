package hr.fer.oprpp1.custom.collections;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * This class models a hash map.
 * 
 * @author Florijan Rusac
 * @version 1.0
 *
 * @param <K> key in map
 * @param <V> value in map
 */
public class SimpleHashtable<K, V> implements Iterable<SimpleHashtable.TableEntry<K, V>> {

	/**
	 * This class is used to create a pair of a key and value.
	 * 
	 * @author Florijan Rusac
	 * @version 1.0
	 *
	 * @param <K> key in map
	 * @param <V> value in map
	 */
	public static class TableEntry<K, V> {
		/**
		 * Key.
		 */
		private K key;
		/**
		 * Value.
		 */
		private V value;
		/**
		 * Next TableEntry.
		 */
		private TableEntry<K, V> next;

		/**
		 * Sets key and value to the values of passed arguments. Can also set next
		 * TableEntry or set it to null.
		 * 
		 * @param key
		 * @param value
		 * @param next  TableEntry
		 * 
		 * @throws NullPointerException if key is null
		 */
		public TableEntry(K key, V value, TableEntry<K, V> next) {
			if (key == null)
				throw new NullPointerException();

			this.key = key;
			this.value = value;
			this.next = next;
		}

		/**
		 * @return key
		 */
		public K getKey() {
			return key;
		}

		/**
		 * @return value
		 */
		public V getValue() {
			return value;
		}

		/**
		 * @param value to be set
		 */
		public void setValue(V value) {
			this.value = value;
		}

		/**
		 * @return a string in format: "key=value"
		 */
		public String toString() {
			return key + "=" + value;
		}
	}

	/**
	 * An array of pairs.
	 */
	TableEntry<K, V>[] table;
	/**
	 * Number of store pairs.
	 */
	int size;
	/**
	 * Increases every time this hashtable is modified.
	 */
	int modificationCount;

	/**
	 * Constructor. Initializes table to size 16.
	 */
	public SimpleHashtable() {
		table = (TableEntry<K, V>[]) new TableEntry[16];
	}

	/**
	 * Constructor. Initialize table to the first equal or greater power of 2 than
	 * tableSize.
	 * 
	 * @param tableSize used to initialize table to the first equal or greater power
	 *                  of 2 than tableSize
	 * @throws IllegalArgumentException if tableSize is less than 1
	 */
	public SimpleHashtable(int tableSize) {
		if (tableSize < 1)
			throw new IllegalArgumentException();

		int sizePow2 = 1;
		while (sizePow2 < tableSize) {
			sizePow2 *= 2;
		}
		table = (TableEntry<K, V>[]) new TableEntry[sizePow2];
	}

	/**
	 * Checks if there are pairs in hashtable.
	 * 
	 * @return true if there are pairs in Dictionary, false otherwise
	 */
	public boolean isEmpty() {
		return (size == 0);
	}

	/**
	 * @return number of stored pairs
	 */
	public int size() {
		return size;
	}

	/**
	 * Inserts a new pair in hashtable.
	 * 
	 * @param key   of new pair
	 * @param value of new pair
	 * @return null if passed key was not in dictionary, otherwise return previous
	 *         value
	 * 
	 * @throws NullPointerException if key is null
	 */
	public V put(K key, V value) {
		if (key == null)
			throw new NullPointerException();

		if (size / table.length >= 0.75) {
			TableEntry<K, V>[] newTable = (TableEntry<K, V>[]) new TableEntry[table.length * 2];

			TableEntry<K, V>[] temp = toArray();

			for (int i = 0; i < temp.length; i++) {
				add(temp[i].getKey(), temp[i].getValue(), newTable);
				// System.out.println(Math.abs(temp[i].getKey().hashCode()) % newTable.length);
				size--;
			}

			table = newTable;
		}

		return add(key, value, table);
	}

	/**
	 * Helper method for inserting a new pair in hashtable.
	 * 
	 * @param key       of new pair
	 * @param value     of new pair
	 * @param currTable table to add current pair to
	 * @return null if passed key was not in dictionary, otherwise return previous
	 *         value
	 */
	private V add(K key, V value, TableEntry<K, V>[] currTable) {
		TableEntry<K, V> current = null;
		TableEntry<K, V> sameKey = null;
		int slot = Math.abs(key.hashCode()) % currTable.length;

		current = currTable[slot];
		if (current != null) {
			do {
				if (current.getKey().equals(key)) {
					sameKey = current;
				}

				current = current.next;
			} while (current != null);
		}

		if (sameKey == null) {
			if (currTable[slot] == null) {
				currTable[slot] = new TableEntry<>(key, value, null);
				modificationCount++;
				size++;
				return null;
			} else {
				current = currTable[slot];
				while (current.next != null) {
					current = current.next;
				}

				current.next = new TableEntry<>(key, value, null);
				modificationCount++;
				size++;
				return null;
			}
		} else {
			V temp = sameKey.getValue();
			sameKey.setValue(value);
			return temp;
		}
	}

	/**
	 * Return value of a pair whose key is the passed argument.
	 * 
	 * @param key of pair whose value will be returned
	 * @return value of pair whose key is passed argument, returns null if there is
	 *         no key in hashtable equal to the passed key
	 */
	public V get(Object key) {
		if (key == null)
			return null;

		TableEntry<K, V> current = null;
		TableEntry<K, V> sameKey = null;
		int slot = Math.abs(key.hashCode()) % table.length;

		current = table[slot];
		if (current != null) {
			do {
				if (current.getKey().equals(key)) {
					sameKey = current;
				}

				current = current.next;
			} while (current != null);
		}

		if (sameKey == null) {
			return null;
		} else {
			return sameKey.getValue();
		}
	}

	/**
	 * Remove pair whose key is the passed argument from hashtable.
	 * 
	 * @param key of a pair to be removed
	 * @return value of a deleted pair, return null if there was no pair with a
	 *         passed key
	 */
	public V remove(Object key) {
		if (key == null)
			return null;

		TableEntry<K, V> current = null;
		TableEntry<K, V> sameKey = null;
		int slot = Math.abs(key.hashCode()) % table.length;

		current = table[slot];
		if (current != null) {
			do {
				if (current.getKey().equals(key)) {
					sameKey = current;
				}

				current = current.next;
			} while (current != null);
		}

		if (sameKey == null) {
			return null;
		} else {
			if (table[slot].getKey().equals(sameKey.getKey())) {
				table[slot] = sameKey.next;
			} else {
				current = table[slot];
				while (!current.next.getKey().equals(sameKey.getKey())) {
					current = current.next;
				}

				current.next = sameKey.next;
			}

			modificationCount++;
			size--;
			return sameKey.getValue();
		}
	}

	/**
	 * Checks if there is a pair with passed key in hashtable.
	 * 
	 * @param key of a pair to be checked is it in hashtable
	 * @return true if hashtable contains a pair with passed key, false otherwise
	 */
	public boolean containsKey(Object key) {
		if (key == null)
			return false;

		TableEntry<K, V> current = null;
		int slot = Math.abs(key.hashCode()) % table.length;

		current = table[slot];
		if (current != null) {
			do {
				if (current.getKey().equals(key)) {
					return true;
				}

				current = current.next;
			} while (current != null);
		}

		return false;
	}

	/**
	 * Checks if there is a pair with passed value in hashtable.
	 * 
	 * @param vale of a pair to be checked is it in hashtable
	 * @return true if hashtable contains a pair with passed value, false otherwise
	 */
	public boolean containsValue(Object value) {
		TableEntry<K, V> current = null;

		for (int i = 0; i < table.length; i++) {
			if (table[i] == null)
				continue;

			current = table[i];
			do {
				if (current.getValue().equals(value)) {
					return true;
				}

				current = current.next;
			} while (current != null);
		}

		return false;
	}

	/**
	 * Returns all keys and values in hashtable in a string format.
	 * 
	 * @return a string of all keys and values
	 */
	public String toString() {
		StringBuilder sb = new StringBuilder();
		boolean first = true;
		sb.append("[");

		TableEntry<K, V> current = null;
		for (int i = 0; i < table.length; i++) {
			if (table[i] == null)
				continue;

			current = table[i];
			do {
				if (first) {
					sb.append(current.toString());
					first = false;
				} else {
					sb.append(", ");
					sb.append(current.toString());
				}

				current = current.next;
			} while (current != null);
		}

		sb.append("]");
		return sb.toString();
	}

	/**
	 * @return an array that contains all pairs in this hashtable
	 */
	public TableEntry<K, V>[] toArray() {
		TableEntry<K, V>[] arr = (TableEntry<K, V>[]) new TableEntry[size];
		int index = 0;

		TableEntry<K, V> current = null;
		for (int i = 0; i < table.length; i++) {
			if (table[i] == null)
				continue;

			current = table[i];
			do {
				arr[index++] = current;

				current = current.next;
			} while (current != null);
		}

		return arr;
	}

	/**
	 * Deletes all elements in hashtable.
	 */
	public void clear() {
		modificationCount++;
		size = 0;
		for (int i = 0; i < table.length; i++) {
			table[i] = null;
		}
	}

	/**
	 * @return a new iterator
	 */
	public Iterator<SimpleHashtable.TableEntry<K, V>> iterator() {
		return new IteratorImpl();
	}

	/**
	 * A class that implements Iterator inteface.
	 * 
	 * Used to iterate through the hashtable.
	 * 
	 * @author Florijan Rusac
	 * @version 1.0
	 */
	private class IteratorImpl implements Iterator<SimpleHashtable.TableEntry<K, V>> {
		/**
		 * Saved modification count.
		 * Increases if remove() is called.
		 */
		private int modificationCount;
		/**
		 * Counts the number of returned items.
		 */
		private int countReturned;
		/**
		 * Keeps track of the current index in the table array.
		 */
		private int index;
		/**
		 * Last returned item.
		 */
		TableEntry<K, V> current;
		/**
		 * Keeps track if the remove() function can remove
		 * an item.
		 */
		boolean canRemove = false;

		/**
		 * Constructor. Saved modification count of the 
		 * hashtable.
		 */
		public IteratorImpl() {
			this.modificationCount = SimpleHashtable.this.modificationCount;
		}
		
		/**
		 * @return true if there are more items to be returned,
		 * 		false otherwise
		 * @throws ConcurrentModificationException if the hashtable
		 * 		was modified
		 */
		public boolean hasNext() {
			if (modificationCount != SimpleHashtable.this.modificationCount) {
				throw new ConcurrentModificationException();
			}
			
			if (size() > countReturned) {
				return true;
			}
			return false;
		}

		/**
		 * @return the next item in the hashtable
		 * @throws ConcurrentModificationException if the hashtable
		 * 		was modified
		 * @throws NoSuchElementException if there are no more elements
		 * 		to return
		 */
		public SimpleHashtable.TableEntry<K, V> next() {
			if (modificationCount != SimpleHashtable.this.modificationCount) {
				throw new ConcurrentModificationException();
			}
			
			if (!hasNext())
				throw new NoSuchElementException();

			canRemove = true;
			if (current == null) {
				for (int i = 0; i < table.length; i++) {
					if (table[i] == null)
						continue;

					current = table[i];
					index = i;
					countReturned++;
					return current;
				}
			}
			
			if (current.next == null) {
				for (int i = index + 1; i < table.length; i++) {
					if (table[i] == null)
						continue;

					current = table[i];
					index = i;
					countReturned++;
					return current;
				}
			}

			current = current.next;
			countReturned++;
			return current;						
		}

		/**
		 * Removes the last returned item by this iterator.
		 * 
		 * @throws ConcurrentModificationException if the hashtable
		 * 		was modified
		 * @throws IllegalStateException if it was not called after
		 * 		method next() or was called twice after next()
		 */
		public void remove() {
			if (modificationCount != SimpleHashtable.this.modificationCount) {
				throw new ConcurrentModificationException();
			}
			
			if (!canRemove)
				throw new IllegalStateException();
			
			SimpleHashtable.this.remove(current.getKey());
			canRemove = false;
			countReturned--;
			modificationCount++;
		}
	}
}
