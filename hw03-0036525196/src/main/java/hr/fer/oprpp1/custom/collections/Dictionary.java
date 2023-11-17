package hr.fer.oprpp1.custom.collections;

/**
 * This class models a map.
 * 
 * @author Florijan Rusac
 * @version 1.0
 *
 * @param <K> key in map
 * @param <V> value in map
 */
public class Dictionary<K, V> {
	/**
	 * This class is used to create a
	 * pair of a key and value.
	 * 
	 * @author Florijan Rusac
	 * @version 1.0
	 *
	 * @param <K> key in map
	 * @param <V> value in map
	 */
	private static class Pair<K, V> {
		/**
		 * Key.
		 */
		K key;
		/**
		 * Value.
		 */
		V value;

		/**
		 * Sets key and value to the values of
		 * passed arguments.
		 * 
		 * @param key
		 * @param value
		 * 
		 * @throws NullPointerException if key is null
		 */
		public Pair(K key, V value) {
			if (key == null)
				throw new NullPointerException();

			this.key = key;
			this.value = value;
		}
	}

	/**
	 * A collection of pairs.
	 */
	ArrayIndexedCollection<Pair<K, V>> pairs;

	/**
	 * Initializes collection.
	 */
	public Dictionary() {
		pairs = new ArrayIndexedCollection<>();
	}

	/**
	 * Checks if there are pairs in dictionary.
	 * 
	 * @return true if there are pairs in Dictionary,
	 * 		false otherwise
	 */
	public boolean isEmpty() {
		return pairs.isEmpty();
	}

	/**
	 * @return number of stored pairs
	 */
	public int size() {
		return pairs.size();
	}

	/**
	 * Removes all pairs from dictionary.
	 */
	public void clear() {
		pairs.clear();
	}

	/**
	 * Inserts a new pair in dictionary.
	 * 
	 * @param key of new pair
	 * @param value of new pair
	 * @return null if passed key was not in dictionary, 
	 * 		otherwise return previous value
	 * 
	 * @throws NullPointerException if key is null
	 */
	public V put(K key, V value) {
		if (key == null) 
			throw new NullPointerException();
		
		ElementsGetter<Pair<K, V>> eg = pairs.createElementsGetter();
		Pair<K, V> current = null;

		while (eg.hasNextElement()) {
			current = eg.getNextElement();
			if (current.key == key) {
				break;
			}
		}
		if (current == null || current.key != key) {
			pairs.add(new Pair<K, V>(key, value));
			return null;
		}

		V temp = current.value;
		current.value = value;
		return temp;
	}

	/**
	 * Return value of a pair whose key is the passed argument.
	 * 
	 * @param key of pair whose value will be returned
	 * @return value of pair whose key is passed argument,
	 * 		returns null if there is no key in dictionary equal to
	 * 		the passed key
	 */
	public V get(Object key) {
		ElementsGetter<Pair<K, V>> eg = pairs.createElementsGetter();
		Pair<K, V> current = null;

		while (eg.hasNextElement()) {
			current = eg.getNextElement();
			if (current.key == key) {
				break;
			}
		}
		if (current == null || current.key != key)
			return null;

		return current.value;
	}

	/**
	 * Remove pair whose key is the passed argument from 
	 * dictionary.
	 * 
	 * @param key of a pair to be removed
	 * @return value of a deleted pair, return null if
	 * 		there was no pair with a passed key
	 */
	public V remove(K key) {
		ElementsGetter<Pair<K, V>> eg = pairs.createElementsGetter();
		Pair<K, V> current = null;

		while (eg.hasNextElement()) {
			current = eg.getNextElement();
			if (current.key == key) {
				break;
			}
		}
		if (current == null || current.key != key)
			return null;

		V temp = current.value;
		pairs.remove(current);
		return temp;
	}

}
