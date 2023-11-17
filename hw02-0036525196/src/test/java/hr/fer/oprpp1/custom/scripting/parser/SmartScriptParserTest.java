package hr.fer.oprpp1.custom.scripting.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import org.junit.jupiter.api.Test;

import hr.fer.oprpp1.custom.scripting.elems.*;
import hr.fer.oprpp1.custom.scripting.nodes.*;

public class SmartScriptParserTest {

	@Test
	public void testNullInput() {
		// must throw!
		assertThrows(SmartScriptParserException.class, () -> new SmartScriptParser(null));
	}


	@Test
	public void testBlanksInBasicState() {
		// When input is only of spaces, tabs, newlines, etc...
		SmartScriptParser parser = new SmartScriptParser("   \r\n\t    ");
		DocumentNode docNode = new DocumentNode();

		docNode.addChildNode(new TextNode("   \r\n\t    "));
		
		assertEquals(docNode, parser.getDocumentNode());
	}
	
	@Test
	public void testPrimjer1() {
		String text = readExample(1);
		SmartScriptParser parser = new SmartScriptParser(text);
		DocumentNode docNode = new DocumentNode();
	
		docNode.addChildNode(new TextNode("Ovo je \nsve jedan text node\n"));
		
		assertEquals(docNode, parser.getDocumentNode());
	}
	

	@Test
	public void testPrimjer2() {
		String text = readExample(2);
		SmartScriptParser parser = new SmartScriptParser(text);
		DocumentNode docNode = new DocumentNode();
	
		docNode.addChildNode(new TextNode("Ovo je \nsve jedan {$ text node\n"));
		
		assertEquals(docNode, parser.getDocumentNode());
	}


	@Test
	public void testPrimjer3() {
		String text = readExample(3);
		SmartScriptParser parser = new SmartScriptParser(text);
		DocumentNode docNode = new DocumentNode();
	
		docNode.addChildNode(new TextNode("Ovo je \nsve jedan \\{$text node\n"));
		
		assertEquals(docNode, parser.getDocumentNode());
	}
	
	@Test
	public void testPrimjer4() {
		String text = readExample(4);

		assertThrows(SmartScriptParserException.class, () -> new SmartScriptParser(text));
	}

	@Test
	public void testPrimjer5() {
		String text = readExample(5);

		// will throw!
		assertThrows(SmartScriptParserException.class, () -> new SmartScriptParser(text));
	}

	@Test
	public void testPrimjer6() {
		String text = readExample(6);
		SmartScriptParser parser = new SmartScriptParser(text);
		DocumentNode docNode = new DocumentNode();
	
		docNode.addChildNode(new TextNode("Ovo je OK "));
		Element[] elements = {new ElementString("String ide\nu više redaka\nčak tri")};
		docNode.addChildNode(new EchoNode(elements));
		docNode.addChildNode(new TextNode("\n"));
		
		assertEquals(docNode, parser.getDocumentNode());
	}
	
	@Test
	public void testPrimjer7() {
		String text = readExample(7);
		SmartScriptParser parser = new SmartScriptParser(text);
		DocumentNode docNode = new DocumentNode();
	
		docNode.addChildNode(new TextNode("Ovo je isto OK "));
		Element[] elements = {new ElementString("String ide\nu \"više\" \nredaka\novdje a stvarno četiri")};
		docNode.addChildNode(new EchoNode(elements));
		docNode.addChildNode(new TextNode("\n"));
		
		assertEquals(docNode, parser.getDocumentNode());
	}

	@Test
	public void testPrimjer8() {
		String text = readExample(8);

		// will throw!
		assertThrows(SmartScriptParserException.class, () -> new SmartScriptParser(text));
	}

	@Test
	public void testPrimjer9() {
		String text = readExample(9);

		// will throw!
		assertThrows(SmartScriptParserException.class, () -> new SmartScriptParser(text));
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
