package hr.fer.oprpp1.hw05.crypto;

import java.util.HashMap;

/**
 * Class with utils for class Crypto.
 * 
 * @author Florijan Rusac
 * @version 1.0
 */
public class Util {
	/**
	 * Converts hex number in a string format to an array of 
	 * bytes.
	 * 
	 * @param keyText to be turned in an array of bytes
	 * @return an array of bytes corresponding to the passed hex 
	 * 		text
	 * @throws IllegalArgumentException if passed text is in an
	 * 		invalid format
	 */
	public static byte[] hextobyte(String keyText) {
		if (keyText.length() == 0) {
			return new byte[0];
		}
		if (keyText.length() % 2 != 0) {
			throw new IllegalArgumentException();
		}
		
		int cIndex = 0;
		int bIndex = 0;
		char[] chars = keyText.toCharArray();
		byte[] bytes = new byte[keyText.length() / 2];
		HashMap<String, Integer> map = new HashMap<>();
		fillMapToInt(map);
		
		int first = 0;
		int second = 0;
		while (cIndex < keyText.length()) {
			first = (int) chars[cIndex];
			if (first < 58 && first > 47) {
				first = first - 48;
			} else if ((first > 96 && first < 103) || (first > 64 && first < 71)) {
				first = map.get(String.valueOf(chars[cIndex]));
			} else {
				throw new IllegalArgumentException();
			}
			cIndex++;
			
			second = (int) chars[cIndex];
			if (second < 58 && second > 47) {
				second = second - 48;
			} else if ((second > 96 && second < 103) || (second > 64 && second < 71)) {
				second = map.get(String.valueOf(chars[cIndex]));
			} else {
				throw new IllegalArgumentException();
			}
			cIndex++;
			
			bytes[bIndex++] = (byte) (first * 16 + second);
		}
		
		return bytes;
	}
	
	/**
	 * Converts a byte array to a string hex number.
	 * 
	 * @param bytearray to be converted
	 * @return a hex value in a string format
	 */
	public static String bytetohex(byte[] bytearray) {
		StringBuilder sb = new StringBuilder();
		HashMap<Integer, String> map = new HashMap<>();
		fillMapToString(map);
		
		for (int i = 0; i < bytearray.length; i++) {
			sb.append(map.get((bytearray[i] & 0xf0) >> 4));
			sb.append(map.get(bytearray[i] & 0xf));
		}
		
		return sb.toString();
	}
	
	/**
	 * Fills passed map with hex letters as keys and 
	 * values with their respective decimal values.
	 * 
	 * @param map to be filled
	 */
	public static void fillMapToInt(HashMap<String, Integer> map) {
		map.put("a", 10);
		map.put("A", 10);
		map.put("b", 11);
		map.put("B", 11);
		map.put("c", 12);
		map.put("C", 12);
		map.put("d", 13);
		map.put("D", 13);
		map.put("e", 14);
		map.put("E", 14);
		map.put("f", 15);
		map.put("F", 15);
	}
	
	/**
	 * Fills passed map with decimals as keys and 
	 * values with their respective hex values.
	 * 
	 * @param map to be filled
	 */
	public static void fillMapToString(HashMap<Integer, String> map) {
		map.put(0, "0");
		map.put(1, "1");
		map.put(2, "2");
		map.put(3, "3");
		map.put(4, "4");
		map.put(5, "5");
		map.put(6, "6");
		map.put(7, "7");
		map.put(8, "8");
		map.put(9, "9");
		map.put(10, "a");
		map.put(11, "b");
		map.put(12, "c");
		map.put(13, "d");
		map.put(14, "e");
		map.put(15, "f");
	}

}
