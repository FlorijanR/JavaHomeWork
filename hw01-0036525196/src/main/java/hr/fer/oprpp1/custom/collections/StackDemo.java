package hr.fer.oprpp1.custom.collections;

/**
 * Uses ObjectStack instance to demonstrate
 * its capabilities.
 * 
 * Used to calculate postfix expressions.
 * 
 * @author Florijan Rusac
 * @version 1.0
 */
public class StackDemo {

	/**
	 * Checks if given String can be parsed
	 * to numeric value.
	 * 
	 * @param str to be checked
	 * @return true if str can be parsed to 
	 * numeric value, false otherwise
	 */
	public static boolean isNumeric(String str) {
		return str.matches("-?\\d+"); // match a number with optional '-'
	}

	/**
	 * Calculates postfix expression taken from terminal
	 * argument at position 0. Uses stack to perform
	 * the calculation. If trying to divide by zero or 
	 * if the postfix expression is invalid the user is 
	 * notified and the program ends.
	 * 
	 * @param args first argument in array from terminal 
	 * 		is a postfix expression
	 */
	public static void main(String[] args) {
		ObjectStack stack = new ObjectStack();

		int num1;
		int num2;
		for (String symbol : args[0].split("\\s+")) {
			if (isNumeric(symbol)) {
				stack.push(Integer.parseInt(symbol));
				continue;
			} else {
				if (stack.size() < 2) {
					System.out.println("Invalid postfix expression");
					System.exit(1);
				}

				num2 = (Integer) stack.pop();
				num1 = (Integer) stack.pop();

				switch (symbol) {
				case "+":
					stack.push(Integer.valueOf(num1 + num2));
					break;
				case "-":
					stack.push(Integer.valueOf(num1 - num2));
					break;
				case "/":
					if (num2 == 0) {
						System.out.println("Can't divide by zero");
						System.exit(1);
					}
					stack.push(Integer.valueOf(num1 / num2));
					break;
				case "*":
					stack.push(Integer.valueOf(num1 * num2));
					break;
				case "%":
					stack.push(Integer.valueOf(num1 % num2));
					break;
				}
			}
		}

		if (stack.size() != 1) {
			System.out.println("Invalid postfix expression");
			System.exit(1);
		}

		System.out.println("Expression evaluates to " + stack.pop() + ".");
	}

}
