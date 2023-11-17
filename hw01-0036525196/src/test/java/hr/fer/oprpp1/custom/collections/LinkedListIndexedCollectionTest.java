package hr.fer.oprpp1.custom.collections;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class LinkedListIndexedCollectionTest {
	
	@Test
	void testLinkedListIndexedCollection() {
		LinkedListIndexedCollection li = new LinkedListIndexedCollection();
		assertEquals(li.size(), 0);
	}

	@Test
	void testLinkedListIndexedCollectionCollection() {
		Object[] objectArr = new Object[2];
		objectArr[0] = 0;
		objectArr[1] = 1;
		
		LinkedListIndexedCollection li = new LinkedListIndexedCollection();
		li.add(0);
		li.add(1);
		
		LinkedListIndexedCollection li2 = new LinkedListIndexedCollection(li);
		assertArrayEquals(objectArr, li2.toArray());
	}

	@Test
	void testAddingOneElement() {
		Object[] objectArr = new Object[1];
		objectArr[0] = 0;

		LinkedListIndexedCollection li = new LinkedListIndexedCollection();
		li.add(0);

		assertArrayEquals(objectArr, li.toArray());
	}

	@Test
	void testAddingTwoElements() {
		Object[] objectArr = new Object[2];
		objectArr[0] = 0;
		objectArr[1] = 1;

		LinkedListIndexedCollection li = new LinkedListIndexedCollection();
		li.add(0);
		li.add(1);

		assertArrayEquals(objectArr, li.toArray());
	}

	@Test
	void testAddingNull() {		
		LinkedListIndexedCollection li = new LinkedListIndexedCollection();
		assertThrows(NullPointerException.class, () -> {
			li.add(null);
		});
	}

	@Test
	void testClear() {
		LinkedListIndexedCollection li = new LinkedListIndexedCollection();
		li.add(0);
		li.add(1);
		
		li.clear();
		assertEquals(li.size(), 0);
	}

	@Test
	void testRemoveIntBegining() {
		LinkedListIndexedCollection li = new LinkedListIndexedCollection();
		li.add(0);
		li.add(1);
		li.add(2);
		li.remove(0);
		
		Object[] objectArr = new Object[2];
		objectArr[0] = 1;
		objectArr[1] = 2;
		
		assertArrayEquals(objectArr, li.toArray());
	}
	
	@Test
	void testRemoveIntMiddle() {
		LinkedListIndexedCollection li = new LinkedListIndexedCollection();
		li.add(0);
		li.add(1);
		li.add(2);
		li.remove(1);
		
		Object[] objectArr = new Object[2];
		objectArr[0] = 0;
		objectArr[1] = 2;
		
		assertArrayEquals(objectArr, li.toArray());
	}
	
	@Test
	void testRemoveIntEnd() {
		LinkedListIndexedCollection li = new LinkedListIndexedCollection();
		li.add(0);
		li.add(1);
		li.add(2);
		li.remove(2);
		
		Object[] objectArr = new Object[2];
		objectArr[0] = 0;
		objectArr[1] = 1;
		
		assertArrayEquals(objectArr, li.toArray());
	}
	
	@Test
	void testRemoveIntInvalidPosition() {		
		LinkedListIndexedCollection li = new LinkedListIndexedCollection();
		assertThrows(IndexOutOfBoundsException.class, () -> {
			li.remove(-1);
		});
	}

	@Test
	void testGet() {
		LinkedListIndexedCollection li = new LinkedListIndexedCollection();
		li.add(0);
		li.add(1);
		li.add(2);
		
		assertEquals(li.get(1), 1);
	}
	
	@Test
	void testGetInvalidPosition() {		
		LinkedListIndexedCollection li = new LinkedListIndexedCollection();
		assertThrows(IndexOutOfBoundsException.class, () -> {
			li.get(-1);
		});
	}

	@Test
	void testInsertFirst() {
		Object[] objectArr = new Object[4];
		objectArr[0] = 3;
		objectArr[1] = 0;
		objectArr[2] = 1;
		objectArr[3] = 2;
		
		LinkedListIndexedCollection li = new LinkedListIndexedCollection();
		li.add(0);
		li.add(1);
		li.add(2);
		
		li.insert(3, 0);
		
		assertArrayEquals(objectArr, li.toArray());
	}
	
	@Test
	void testInsertMiddle() {
		Object[] objectArr = new Object[4];
		objectArr[0] = 0;
		objectArr[1] = 3;
		objectArr[2] = 1;
		objectArr[3] = 2;
		
		LinkedListIndexedCollection li = new LinkedListIndexedCollection();
		li.add(0);
		li.add(1);
		li.add(2);
		
		li.insert(3, 1);
		
		assertArrayEquals(objectArr, li.toArray());
	}
	
	@Test
	void testInsertOnLast() {
		Object[] objectArr = new Object[4];
		objectArr[0] = 0;
		objectArr[1] = 1;
		objectArr[2] = 3;
		objectArr[3] = 2;
		
		LinkedListIndexedCollection li = new LinkedListIndexedCollection();
		li.add(0);
		li.add(1);
		li.add(2);
		
		li.insert(3, 2);
		
		assertArrayEquals(objectArr, li.toArray());
	}
	
	@Test
	void testInsertAfterLast() {
		Object[] objectArr = new Object[4];
		objectArr[0] = 0;
		objectArr[1] = 1;
		objectArr[2] = 2;
		objectArr[3] = 3;
		
		LinkedListIndexedCollection li = new LinkedListIndexedCollection();
		li.add(0);
		li.add(1);
		li.add(2);
		
		li.insert(3, 3);
		
		assertArrayEquals(objectArr, li.toArray());
	}
	
	@Test
	void testInsertInvalidPosition() {		
		LinkedListIndexedCollection li = new LinkedListIndexedCollection();
		assertThrows(IndexOutOfBoundsException.class, () -> {
			li.insert(1, -1);
		});
	}
	
	@Test
	void testInsertNullObject() {		
		LinkedListIndexedCollection li = new LinkedListIndexedCollection();
		assertThrows(NullPointerException.class, () -> {
			li.insert(null, 0);
		});
	}

	@Test
	void testIndexOfFound() {		
		LinkedListIndexedCollection li = new LinkedListIndexedCollection();
		li.add(0);
		li.add(1);
		li.add(2);

		assertEquals(1, li.indexOf(1));
	}
	
	@Test
	void testIndexOfNotFound() {		
		LinkedListIndexedCollection li = new LinkedListIndexedCollection();
		li.add(0);
		li.add(1);
		li.add(2);

		assertEquals(-1, li.indexOf(3));
	}
	
	@Test
	void testIndexOfNull() {
		LinkedListIndexedCollection li = new LinkedListIndexedCollection();
		assertEquals(-1, li.indexOf(null));;
	}

}
