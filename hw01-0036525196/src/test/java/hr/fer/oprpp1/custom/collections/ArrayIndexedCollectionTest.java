package hr.fer.oprpp1.custom.collections;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ArrayIndexedCollectionTest {

	@Test
	void testAddingOneElement() {
		Object[] objectArr = new Object[1];
		objectArr[0] = 0;

		ArrayIndexedCollection ai = new ArrayIndexedCollection();
		ai.add(0);

		assertArrayEquals(objectArr, ai.toArray());
	}
	
	@Test
	void testAddExceedingCapacity() {
		Object[] objectArr = new Object[17];
		for (int i = 0; i < 17; i++) {
			objectArr[i] = i;
		}
		
		ArrayIndexedCollection ai = new ArrayIndexedCollection();
		for (int i = 0; i < 17; i++) {
			ai.add(i);
		}
		
		assertArrayEquals(objectArr, ai.toArray());
	}

	@Test
	void testAddingTwoElements() {
		Object[] objectArr = new Object[2];
		objectArr[0] = 0;
		objectArr[1] = 1;

		ArrayIndexedCollection ai = new ArrayIndexedCollection();
		ai.add(0);
		ai.add(1);

		assertArrayEquals(objectArr, ai.toArray());
	}

	@Test
	void testAddingNull() {		
		ArrayIndexedCollection ai = new ArrayIndexedCollection();
		assertThrows(NullPointerException.class, () -> {
			ai.add(null);
		});
	}

	@Test
	void testClear() {
		ArrayIndexedCollection ai = new ArrayIndexedCollection();
		ai.add(0);
		ai.add(1);
		
		ai.clear();
		assertEquals(ai.size(), 0);
	}

	@Test
	void testArrayIndexedCollection() {
		ArrayIndexedCollection ai = new ArrayIndexedCollection();
		assertEquals(ai.size(), 0);
	}

	@Test
	void testArrayIndexedCollectionInt() {
		ArrayIndexedCollection ai = new ArrayIndexedCollection(22);
		assertEquals(ai.size(), 0);
	}

	@Test
	void testArrayIndexedCollectionCollection() {
		Object[] objectArr = new Object[2];
		objectArr[0] = 0;
		objectArr[1] = 1;
		
		ArrayIndexedCollection ai = new ArrayIndexedCollection();
		ai.add(0);
		ai.add(1);
		
		ArrayIndexedCollection ai2 = new ArrayIndexedCollection(ai);
		assertArrayEquals(objectArr, ai2.toArray());
	}

	@Test
	void testArrayIndexedCollectionCollectionInt() {
		Object[] objectArr = new Object[2];
		objectArr[0] = 0;
		objectArr[1] = 1;
		
		ArrayIndexedCollection ai = new ArrayIndexedCollection();
		ai.add(0);
		ai.add(1);
		
		ArrayIndexedCollection ai2 = new ArrayIndexedCollection(ai, 22);
		assertArrayEquals(objectArr, ai2.toArray());
	}

	@Test
	void testRemoveIntBegining() {
		ArrayIndexedCollection ai = new ArrayIndexedCollection();
		ai.add(0);
		ai.add(1);
		ai.add(2);
		ai.remove(0);
		
		Object[] objectArr = new Object[2];
		objectArr[0] = 1;
		objectArr[1] = 2;
		
		assertArrayEquals(objectArr, ai.toArray());
	}
	
	@Test
	void testRemoveIntMiddle() {
		ArrayIndexedCollection ai = new ArrayIndexedCollection();
		ai.add(0);
		ai.add(1);
		ai.add(2);
		ai.remove(1);
		
		Object[] objectArr = new Object[2];
		objectArr[0] = 0;
		objectArr[1] = 2;
		
		assertArrayEquals(objectArr, ai.toArray());
	}
	
	@Test
	void testRemoveIntEnd() {
		ArrayIndexedCollection ai = new ArrayIndexedCollection();
		ai.add(0);
		ai.add(1);
		ai.add(2);
		ai.remove(2);
		
		Object[] objectArr = new Object[2];
		objectArr[0] = 0;
		objectArr[1] = 1;
		
		assertArrayEquals(objectArr, ai.toArray());
	}
	
	@Test
	void testRemoveIntInvalidPosition() {		
		ArrayIndexedCollection ai = new ArrayIndexedCollection();
		assertThrows(IndexOutOfBoundsException.class, () -> {
			ai.remove(-1);
		});
	}

	@Test
	void testGet() {
		ArrayIndexedCollection ai = new ArrayIndexedCollection();
		ai.add(0);
		ai.add(1);
		ai.add(2);
		
		assertEquals(ai.get(1), 1);
	}
	
	@Test
	void testGetInvalidPosition() {		
		ArrayIndexedCollection ai = new ArrayIndexedCollection();
		assertThrows(IndexOutOfBoundsException.class, () -> {
			ai.get(-1);
		});
	}

	@Test
	void testInsert() {
		Object[] objectArr = new Object[4];
		objectArr[0] = 0;
		objectArr[1] = 3;
		objectArr[2] = 1;
		objectArr[3] = 2;
		
		ArrayIndexedCollection ai = new ArrayIndexedCollection();
		ai.add(0);
		ai.add(1);
		ai.add(2);
		
		ai.insert(3, 1);
		
		assertArrayEquals(objectArr, ai.toArray());
	}
	
	@Test
	void testInsertResizeArray() {
		Object[] objectArr = new Object[17];
		objectArr[0] = 0;
		objectArr[1] = 17;
		for (int i = 0; i < 15; i++) {
			objectArr[i + 2] = i + 1;
		}
		
		ArrayIndexedCollection ai = new ArrayIndexedCollection();
		for (int i = 0; i < 16; i++) {
			ai.add(i);
		}
		
		ai.insert(17, 1);
		
		assertArrayEquals(objectArr, ai.toArray());
	}
	
	@Test
	void testInsertInvalidPosition() {		
		ArrayIndexedCollection ai = new ArrayIndexedCollection();
		assertThrows(IndexOutOfBoundsException.class, () -> {
			ai.insert(1, -1);
		});
	}
	
	@Test
	void testInsertNullObject() {		
		ArrayIndexedCollection ai = new ArrayIndexedCollection();
		assertThrows(NullPointerException.class, () -> {
			ai.insert(null, 0);
		});
	}

	@Test
	void testIndexOfFound() {		
		ArrayIndexedCollection ai = new ArrayIndexedCollection();
		ai.add(0);
		ai.add(1);
		ai.add(2);

		assertEquals(1, ai.indexOf(1));
	}
	
	@Test
	void testIndexOfNotFound() {		
		ArrayIndexedCollection ai = new ArrayIndexedCollection();
		ai.add(0);
		ai.add(1);
		ai.add(2);

		assertEquals(-1, ai.indexOf(3));
	}
	
	@Test
	void testIndexOfNull() {
		ArrayIndexedCollection ai = new ArrayIndexedCollection();
		assertEquals(-1, ai.indexOf(null));;
	}

}
