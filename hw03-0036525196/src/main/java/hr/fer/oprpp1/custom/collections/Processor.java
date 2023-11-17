package hr.fer.oprpp1.custom.collections;

/**
 * The Processor is a model of an object capable 
 * of performing some operation on the passed object.
 * 
 * Used to define concrete processor which inherits 
 * from this class.
 * 
 * @author Florijan Rusac
 * @version 1.0
 */
public interface Processor<T> {

	/**
	 * Used to do something with the argument.
	 * 
	 * @param value object that is used for something
	 */
	public void process(T value);

}
