package hr.fer.zemris.java.hw06.shell.commands;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Used as a utils class for shell commands.
 * 
 * @author Florijan Rusac
 * @version 1.0
 */
public class Util {
	/**
	 * First index from which token creation continues.
	 */
	private static int currentIndex;
	/**
	 * An array that contains characters of a text that
	 * needs to be converted to tokens.
	 */
	private static char[] data;

	/**
	 * Splits passed text in a way that keeps only words
	 * with no empty strings or spaces around words.
	 * 
	 * @param arguments to be split
	 * @return an array of strings containing arguments
	 */
	public static String[] splitArguments(String arguments) {
		String[] args = arguments.split(" ");
		List<String> list = new ArrayList<String>(Arrays.asList(args));
		list.removeAll(Arrays.asList(""));

		Object[] objects = list.toArray();
		String[] argsToReturn = new String[objects.length];
		for (int i = 0; i < objects.length; i++) {
			argsToReturn[i] = (String) objects[i];
		}

		return argsToReturn;
	}

	/**
	 * Splits passed text in a way that keeps only arguments
	 * with no empty strings. Supports escaping inside 
	 * quotes and treats everything inside quotes as one
	 * argument.
	 * 
	 * @param arguments to be split
	 * @return an array of strings containing arguments
	 */
	public static String[] splitArgumentsEscape(String arguments) {
		arguments = arguments.trim();
		if (arguments.length() == 0) {
			return new String[0];
		}

		List<String> list = new ArrayList<String>();

		data = arguments.toCharArray();
		currentIndex = 0;
		String token;

		do {
			token = nextToken();
			if (token != null) {
				list.add(token);
			}
		} while (token != null);

		Object[] objects = list.toArray();
		String[] argsToReturn = new String[objects.length];
		for (int i = 0; i < objects.length; i++) {
			argsToReturn[i] = (String) objects[i];
		}
		return argsToReturn;
	}

	/**
	 * Generates and returns a next token. If
	 * there is no more tokens returns null.
	 * 
	 * @return a next string token
	 * @throws IllegalArgumentException if text is in an
	 * 		invalid format
	 */
	private static String nextToken() {
		while (currentIndex < data.length) {
			char current = data[currentIndex];
			if (current == ' ') {
				currentIndex++;
				continue;
			}
			break;
		}

		if (currentIndex >= data.length) {
			return null;
		}

		StringBuilder sb = new StringBuilder();

		if (data[currentIndex] == '"') {
			if (currentIndex + 1 == data.length) {
				throw new IllegalArgumentException();
			}

			currentIndex++;
			while (data[currentIndex] != '"') {
				if (data[currentIndex] == '\\') {
					if (currentIndex + 1 == data.length)
						throw new IllegalArgumentException();

					if (data[currentIndex + 1] == '"' || data[currentIndex + 1] == '\\') {
						currentIndex++;
					}
				}

				sb.append(data[currentIndex]);
				currentIndex++;
				if (currentIndex == data.length)
					throw new IllegalArgumentException();
			}
			currentIndex++;
			if (currentIndex >= data.length || data[currentIndex] != ' ') {
				return sb.toString();
			}
			throw new IllegalArgumentException();
		} else {
			while (data[currentIndex] != ' ') {
				sb.append(data[currentIndex]);
				currentIndex++;
				if (currentIndex == data.length)
					break;
			}

			return sb.toString();
		}
	}

}
