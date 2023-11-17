package hr.fer.oprpp1.custom.collections;

/**
 * An implementation of stack structure. Supports 
 * methods like push, pop, size, isEmpty..
 * 
 * You can use this to calculate postfix expressions.
 * 
 * @author Florijan Rusac
 * @version 1.0
 */
public class ObjectStack<T> {
	/**
	 * An instance of ArrayIndexedCollection used
	 * to simulate the stack.
	 */
	private ArrayIndexedCollection<T> stack;
	
	/**
	 * Constructor that initializes the stack.
	 */
	public ObjectStack() {
		stack = new ArrayIndexedCollection<T>();
	}
	
	/**
	 * Checks if stack has any elements inside.
	 * 
	 * @return true if stack is empty, false otherwise.
	 */
	public boolean isEmpty() {
		return stack.isEmpty();
	}
	
	/**
	 * Return the number of elements in the stack.
	 * 
	 * @return the number of elements in the stack.
	 */
	public int size() {
		return stack.size();
	}
	
	/**
	 * Pushes given value on stack.
	 * 
	 * @param value to be pushed in stack
	 * @throws NullPointerException if value is null
	 */
	public void push(T value) {
		if (value == null)
			throw new NullPointerException();
		
		stack.add(value);
	}
	
	/**
	 * Removes last value pushed on stack 
	 * from stack and returns it.
	 * 
	 * @return last value pushed on stack
	 * @throws EmptyStackException if pop() is 
	 * 		called when stack is empty
	 */
	public T pop() {
		if (stack.isEmpty())
			throw new EmptyStackException();
		
		T temp = stack.get(stack.size() - 1);
		stack.remove(stack.size() - 1);
		return temp;
	}
	
	/**
	 * Return last value pushed on stack 
	 * but does not delete it from stack.
	 * 
	 * @return last value pushed on stack
	 * @throws EmptyStackException if peek() is 
	 * 		called when stack is empty
	 */
	public T peek() {
		if (stack.isEmpty())
			throw new EmptyStackException();
		
		return stack.get(stack.size() - 1);
	}
	
	/**
	 * Deletes all elements in the stack.
	 */
	public void clear() {
		stack.clear();
	}

}
