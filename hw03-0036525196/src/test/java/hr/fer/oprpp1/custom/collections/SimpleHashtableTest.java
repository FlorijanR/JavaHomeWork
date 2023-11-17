package hr.fer.oprpp1.custom.collections;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ConcurrentModificationException;
import java.util.Iterator;

import org.junit.jupiter.api.Test;

import hr.fer.oprpp1.custom.collections.SimpleHashtable.TableEntry;

class SimpleHashtableTest {

	@Test
	void testSimpleHashtable() {
		SimpleHashtable<String, Integer> ht = new SimpleHashtable<>();

		ht.put("Ivana", 5);
		ht.put("Ante", 2);
		ht.put("Jasna", 2);
		ht.put("Kristina", 5);

		String expected = "[Ivana=5, Kristina=5, Ante=2, Jasna=2]";

		assertEquals(expected, ht.toString());
	}

	@Test
	void testSimpleHashtableInt() {
		SimpleHashtable<String, Integer> ht = new SimpleHashtable<>(2);

		ht.put("Ivana", 5);
		ht.put("Ante", 2);
		ht.put("Jasna", 2);
		ht.put("Kristina", 5);

		String expected = "[Ante=2, Ivana=5, Jasna=2, Kristina=5]";

		assertEquals(expected, ht.toString());
	}

	@Test
	void testClear() {
		SimpleHashtable<String, Integer> ht = new SimpleHashtable<>();

		ht.put("Ivana", 5);
		ht.put("Ante", 2);
		ht.put("Jasna", 2);
		ht.put("Kristina", 5);

		ht.clear();

		assertEquals(false, ht.containsKey("Ivana"));
		assertEquals(true, ht.isEmpty());
	}

	@Test
	void testResizing() {
		SimpleHashtable<String, Integer> ht = new SimpleHashtable<>(1);

		ht.put("Ivana", 5);
		ht.put("Ante", 2);
		ht.put("Jasna", 2);
		ht.put("Kristina", 5);
		ht.put("Marko", 4);
		/*
		 * System.out.println(Math.abs("Ivana".hashCode()) % 8);
		 * System.out.println(Math.abs("Ante".hashCode()) % 8);
		 * System.out.println(Math.abs("Jasna".hashCode()) % 8);
		 * System.out.println(Math.abs("Kristina".hashCode()) % 8);
		 * System.out.println(Math.abs("Marko".hashCode()) % 8);
		 */
		String expected = "[Marko=4, Ante=2, Ivana=5, Jasna=2, Kristina=5]";

		assertEquals(expected, ht.toString());
	}

	@Test
	void testIsEmpty() {
		SimpleHashtable<String, Integer> ht = new SimpleHashtable<>();

		assertEquals(true, ht.isEmpty());
	}

	@Test
	void testSize() {
		SimpleHashtable<String, Integer> ht = new SimpleHashtable<>();

		assertEquals(0, ht.size());

		ht.put("Ivana", 5);
		ht.put("Ante", 2);
		ht.put("Jasna", 2);

		assertEquals(3, ht.size());
	}

	@Test
	void testPut() {
		SimpleHashtable<String, Integer> ht = new SimpleHashtable<>();

		ht.put("Ivana", 5);
		ht.put("Ante", 2);
		ht.put("Jasna", 2);
		ht.put("Kristina", 5);
		ht.put("Ante", 3);

		String expected = "[Ivana=5, Kristina=5, Ante=3, Jasna=2]";

		assertEquals(expected, ht.toString());
	}

	@Test
	void testGet() {
		SimpleHashtable<String, Integer> ht = new SimpleHashtable<>();

		ht.put("Ivana", 5);
		ht.put("Ante", 2);
		ht.put("Jasna", 2);
		ht.put("Kristina", 5);

		assertEquals(2, ht.get("Ante"));

		ht.put("Ante", 3);

		assertEquals(3, ht.get("Ante"));

		assertEquals(null, ht.get("Nema"));

		assertEquals(null, ht.get(null));
	}

	@Test
	void testRemove() {
		SimpleHashtable<String, Integer> ht = new SimpleHashtable<>();

		ht.put("Ivana", 5);
		ht.put("Ante", 2);
		ht.put("Jasna", 2);
		ht.put("Kristina", 5);

		assertEquals(2, ht.get("Ante"));

		assertEquals(2, ht.remove("Ante"));

		assertEquals(null, ht.get("Ante"));

		assertEquals(null, ht.remove("Nema"));
	}

	@Test
	void testContainsKey() {
		SimpleHashtable<String, Integer> ht = new SimpleHashtable<>();

		ht.put("Ivana", 5);
		ht.put("Ante", 2);
		ht.put("Jasna", 2);

		assertEquals(true, ht.containsKey("Ivana"));
		assertEquals(false, ht.containsKey("Nema"));
	}

	@Test
	void testContainsValue() {
		SimpleHashtable<String, Integer> ht = new SimpleHashtable<>();

		ht.put("Ivana", 5);
		ht.put("Ante", 2);
		ht.put("Jasna", 2);

		assertEquals(true, ht.containsValue(2));
		assertEquals(false, ht.containsValue(6));
	}

	@Test
	void testToString() {
		SimpleHashtable<String, Integer> ht = new SimpleHashtable<>();

		ht.put("Ivana", 5);
		ht.put("Ante", 2);
		ht.put("Jasna", 2);
		ht.put("Kristina", 5);

		String expected = "[Ivana=5, Kristina=5, Ante=2, Jasna=2]";

		assertEquals(expected, ht.toString());
	}

	@Test
	void testToArray() {
		SimpleHashtable<String, Integer> ht = new SimpleHashtable<>();

		ht.put("Ivana", 5);
		ht.put("Ante", 2);
		ht.put("Jasna", 2);

		TableEntry<String, Integer>[] table = (TableEntry<String, Integer>[]) new TableEntry[3];
		table[0] = new TableEntry<String, Integer>("Ivana", 5, null);
		table[1] = new TableEntry<String, Integer>("Ante", 2, null);
		table[2] = new TableEntry<String, Integer>("Jasna", 2, null);

		for (int i = 0; i < 3; i++) {
			assertEquals(table[i].toString(), ht.toArray()[i].toString());
		}
	}

	@Test
	void testIterator() {
		// create collection:
		SimpleHashtable<String, Integer> examMarks = new SimpleHashtable<>(2);
		// fill data:
		examMarks.put("Ante", 3);
		examMarks.put("Ivana", 2);
		examMarks.put("Jasna", 4);
		int i = 0;

		for (SimpleHashtable.TableEntry<String, Integer> pair : examMarks) {
			if (i == 0) {
				assertEquals("Ante3", pair.getKey() + pair.getValue());
				i++;
			} else if (i == 1) {
				assertEquals("Ivana2", pair.getKey() + pair.getValue());
				i++;
			} else if (i == 2) {
				assertEquals("Jasna4", pair.getKey() + pair.getValue());
			}
		}

		Iterator<SimpleHashtable.TableEntry<String, Integer>> iter = examMarks.iterator();
		while (iter.hasNext()) {
			iter.next();
			iter.remove();
		}
		assertEquals(0, examMarks.size());
	}

	@Test
	void TestConcurrentModificationException() {
		// create collection:
		SimpleHashtable<String, Integer> examMarks = new SimpleHashtable<>(2);
		// fill data:
		examMarks.put("Ivana", 2);
		examMarks.put("Ante", 2);
		examMarks.put("Jasna", 2);
		examMarks.put("Kristina", 5);
		examMarks.put("Ivana", 5); // overwrites old grade for Ivana

		Iterator<SimpleHashtable.TableEntry<String, Integer>> iter = examMarks.iterator();
		assertThrows(ConcurrentModificationException.class, () -> {
			while (iter.hasNext()) {
				iter.next();
				examMarks.remove("Ivana");
			}
		});

	}

	@Test
	void TestIllegalStateException() {
		// create collection:
		SimpleHashtable<String, Integer> examMarks = new SimpleHashtable<>(2);
		// fill data:
		examMarks.put("Ivana", 2);
		examMarks.put("Ante", 2);
		examMarks.put("Jasna", 2);
		examMarks.put("Kristina", 5);
		examMarks.put("Ivana", 5); // overwrites old grade for Ivana

		Iterator<SimpleHashtable.TableEntry<String, Integer>> iter = examMarks.iterator();

		while (iter.hasNext()) {
			SimpleHashtable.TableEntry<String, Integer> pair = iter.next();
			if (pair.getKey().equals("Ivana")) {
				iter.remove();
				assertThrows(IllegalStateException.class, () -> {
					iter.remove();
				});
			}
		}

	}

}
