package hr.fer.oprpp1.custom.collections;


/**
 * This interface has methods:
 * hasNextElement(), getNextElement() 
 * and processRemaining():
 * 
 * For example used to iterate through
 * object of a collection.
 * 
 * @author Florijan Rusac
 * @version 1.0
 */
public interface ElementsGetter {
	/**
	 * Check if collection has at least
	 * one more element to return.
	 * 
	 * @return true if it does,
	 * 		false otherwise
	 */
	boolean hasNextElement();
	
	/**
	 * @return object from collection that
	 * 		was not yet returned
	 */
	Object getNextElement();
	
	/**
	 * @param p passed processor to be used on all
	 * 		remaining objects in ElementsGetter
	 */
	default void processRemaining(Processor p) {
		while (hasNextElement()) {
			p.process(getNextElement());
		}
	}
}
