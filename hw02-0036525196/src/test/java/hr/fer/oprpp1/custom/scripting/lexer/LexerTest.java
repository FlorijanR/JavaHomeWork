package hr.fer.oprpp1.custom.scripting.lexer;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Disabled;

public class LexerTest {

	@Test
	public void testNotNull() {
		Lexer lexer = new Lexer("");

		assertNotNull(lexer.nextToken(), "Token was expected but null was returned.");
	}

	@Test
	public void testNullInput() {
		// must throw!
		assertThrows(NullPointerException.class, () -> new Lexer(null));
	}

	@Test
	public void testEmpty() {
		Lexer lexer = new Lexer("");

		assertEquals(TokenType.EOF, lexer.nextToken().getType(), "Empty input must generate only EOF token.");
	}

	@Test
	public void testGetReturnsLastNext() {
		// Calling getToken once or several times after calling nextToken must return
		// each time what nextToken returned...
		Lexer lexer = new Lexer("");

		Token token = lexer.nextToken();
		assertEquals(token, lexer.getToken(), "getToken returned different token than nextToken.");
		assertEquals(token, lexer.getToken(), "getToken returned different token than nextToken.");
	}

	@Test
	public void testRadAfterEOF() {
		Lexer lexer = new Lexer("");

		// will obtain EOF
		lexer.nextToken();
		// will throw!
		assertThrows(LexerException.class, () -> lexer.nextToken());
	}

	@Test
	public void testBlanksInBasicState() {
		// When input is only of spaces, tabs, newlines, etc...
		Lexer lexer = new Lexer("   \r\n\t    ");

		lexer.nextToken();
		assertEquals("   \r\n\t    ", lexer.getToken().getValue());
		assertEquals(TokenType.TEXT, lexer.getToken().getType());
	}

	@Test
	public void testPrimjer1() {
		String text = readExample(1);
		Lexer lexer = new Lexer(text);

		// We expect the following stream of tokens
		Token correctData[] = { new Token(TokenType.TEXT, "Ovo je \nsve jedan text node\n"),
				new Token(TokenType.EOF, null) };

		checkTokenStream(lexer, correctData);
	}

	@Test
	public void testPrimjer2() {
		String text = readExample(2);
		Lexer lexer = new Lexer(text);

		// We expect the following stream of tokens
		Token correctData[] = { new Token(TokenType.TEXT, "Ovo je \nsve jedan {$ text node\n"),
				new Token(TokenType.EOF, null) };

		checkTokenStream(lexer, correctData);
	}

	@Test
	public void testPrimjer3() {
		String text = readExample(3);
		Lexer lexer = new Lexer(text);

		// We expect the following stream of tokens
		Token correctData[] = { new Token(TokenType.TEXT, "Ovo je \nsve jedan \\{$text node\n"),
				new Token(TokenType.EOF, null) };

		checkTokenStream(lexer, correctData);
	}

	@Test
	public void testPrimjer4() {
		String text = readExample(4);
		Lexer lexer = new Lexer(text);

		// will throw!
		assertThrows(LexerException.class, () -> lexer.nextToken());
	}

	@Test
	public void testPrimjer5() {
		String text = readExample(5);
		Lexer lexer = new Lexer(text);

		// will throw!
		assertThrows(LexerException.class, () -> lexer.nextToken());
	}

	@Test
	public void testPrimjer6() {
		String text = readExample(6);
		Lexer lexer = new Lexer(text);

		// We expect the following stream of tokens
		Token correctData[] = { new Token(TokenType.TEXT, "Ovo je OK "), new Token(TokenType.TAG, "{$"),
				new Token(TokenType.TAGTYPE, "="),
				new Token(TokenType.STRING, "\"String ide\nu više redaka\nčak tri\""), new Token(TokenType.TAG, "$}"),
				new Token(TokenType.TEXT, "\n"), new Token(TokenType.EOF, null) };

		checkTokenStream(lexer, correctData);
	}

	@Test
	public void testPrimjer7() {
		String text = readExample(7);
		Lexer lexer = new Lexer(text);

		// We expect the following stream of tokens
		Token correctData[] = { new Token(TokenType.TEXT, "Ovo je isto OK "), new Token(TokenType.TAG, "{$"),
				new Token(TokenType.TAGTYPE, "="),
				new Token(TokenType.STRING, "\"String ide\n" + "u \"više\" \nredaka\n" + "ovdje a stvarno četiri\""),
				new Token(TokenType.TAG, "$}"), new Token(TokenType.TEXT, "\n"), new Token(TokenType.EOF, null) };

		checkTokenStream(lexer, correctData);
	}

	@Test
	public void testPrimjer8() {
		String text = readExample(8);
		Lexer lexer = new Lexer(text);

		lexer.nextToken();
		lexer.nextToken();
		lexer.nextToken();
		// will throw!
		assertThrows(LexerException.class, () -> lexer.nextToken());
	}

	@Test
	public void testPrimjer9() {
		String text = readExample(9);
		Lexer lexer = new Lexer(text);

		lexer.nextToken();
		lexer.nextToken();
		lexer.nextToken();
		// will throw!
		assertThrows(LexerException.class, () -> lexer.nextToken());
	}

	// Helper method for checking if lexer generates the same stream of tokens
	// as the given stream.
	private void checkTokenStream(Lexer lexer, Token[] correctData) {
		int counter = 0;
		for (Token expected : correctData) {
			Token actual = lexer.nextToken();
			String msg = "Checking token " + counter + ":";
			assertEquals(expected.getType(), actual.getType(), msg);
			assertEquals(expected.getValue(), actual.getValue(), msg);
			counter++;
		}
	}

	private String readExample(int n) {
		try (InputStream is = this.getClass().getClassLoader().getResourceAsStream("extra/primjer" + n + ".txt")) {
			if (is == null)
				throw new RuntimeException("Datoteka extra/primjer" + n + ".txt je nedostupna.");
			byte[] data = is.readAllBytes();
			String text = new String(data, StandardCharsets.UTF_8);
			return text;
		} catch (IOException ex) {
			throw new RuntimeException("Greška pri čitanju datoteke.", ex);
		}
	}

}
