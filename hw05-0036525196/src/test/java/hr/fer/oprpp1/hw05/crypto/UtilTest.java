package hr.fer.oprpp1.hw05.crypto;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class UtilTest {

	@Test
	void testHextobyte() {
		byte[] b = new byte[3];
		b[0] = (byte) 1;
		b[1] = (byte) 174;
		b[2] = (byte) 34;
		
		byte[] returnedBytes = Util.hextobyte("01aE22");
		
		assertEquals(b[0], returnedBytes[0]);
		assertEquals(b[1], returnedBytes[1]);
		assertEquals(b[2], returnedBytes[2]);
	}
	
	@Test
	void testHextobyteSizeZero() {		
		byte[] returnedBytes = Util.hextobyte("");
		
		assertEquals(0, returnedBytes.length);
	}
	
	@Test
	void testHextobyteOdd() {		
		assertThrows(IllegalArgumentException.class, () -> Util.hextobyte("01aE22d"));
	}

	@Test
	void testBytetohex() {
		byte[] b = new byte[3];
		b[0] = (byte) 1;
		b[1] = (byte) 174;
		b[2] = (byte) 34;
		
		assertEquals("01ae22", Util.bytetohex(b));
	}
	
	@Test
	void testBytetohexSizeZero() {		
		byte[] b = new byte[0];
		
		assertEquals("", Util.bytetohex(b));
	}

}
