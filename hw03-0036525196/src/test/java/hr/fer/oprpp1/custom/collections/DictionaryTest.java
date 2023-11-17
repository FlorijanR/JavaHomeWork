package hr.fer.oprpp1.custom.collections;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;


class DictionaryTest {

	@Test
	void testEmpty() {
		Dictionary<Integer, String> dict = new Dictionary<>();
		
		assertEquals(true, dict.isEmpty());
	}
	
	@Test
	void testSize() {
		Dictionary<Integer, String> dict = new Dictionary<>();
		
		dict.put(1, "jedan");
		assertEquals(1, dict.size());
		
		dict.put(2, "dva");
		assertEquals(2, dict.size());
	}
	
	@Test
	void testClear() {
		Dictionary<Integer, String> dict = new Dictionary<>();
		
		dict.put(1, "jedan");
		dict.clear();
		
		assertEquals(0, dict.size());
	}
	
	@Test
	void testPutGet() {
		Dictionary<Integer, String> dict = new Dictionary<>();
		
		assertEquals(null, dict.put(1, "jedan"));
		assertEquals(dict.get(1), "jedan");
		
		assertEquals(null, dict.put(2, "dva"));
		assertEquals(dict.get(2), "dva");
		
		assertEquals("jedan", dict.put(1, "tri"));
		assertEquals(dict.get(1), "tri");
		
		assertEquals(null, dict.get(3));
	}
	
	
	@Test
	void testRemove() {
		Dictionary<Integer, String> dict = new Dictionary<>();
		
		dict.put(1, "jedan");
		assertEquals(null, dict.remove(2));
		
		dict.put(2, "dva");
		assertEquals("dva", dict.remove(2));
		
		assertEquals(null, dict.get(2));
	}
}










