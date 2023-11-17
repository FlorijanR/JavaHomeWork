package hr.fer.oprpp1.hw02;

import hr.fer.oprpp1.custom.scripting.nodes.DocumentNode;
import hr.fer.oprpp1.custom.scripting.parser.SmartScriptParser;
import hr.fer.oprpp1.custom.scripting.parser.SmartScriptParserException;
import java.nio.file.Files;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;

/**
 * Reads text from a command line argument 
 * and parses it.
 * 
 * Used to test the parser.
 * 
 * @author Florijan Rusac
 * @version 1.0
 */
public class SmartScriptTester {

	/**
	 * Parses text and converts created document to string.
	 * Then parses created string and checks if the new
	 * document is equal to the first one.
	 * 
	 * @param args at index 0 is text to be parsed
	 */
	public static void main(String[] args) {
		
		String docBody = null;
		try {
			docBody = new String(Files.readAllBytes(Paths.get(args[0])),
					StandardCharsets.UTF_8);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		//System.out.println(docBody);
		//System.exit(1);
		
		SmartScriptParser parser = null;
		try {
			parser = new SmartScriptParser(docBody);
		} catch (SmartScriptParserException e) {
			System.out.println("Unable to parse document!");
			System.exit(-1);
		} catch (Exception e) {
			System.out.println("If this line ever executes, you have failed this class!");
			System.exit(-1);
		}

		DocumentNode document = parser.getDocumentNode();
		String originalDocumentBody = document.toString();
		//System.out.println(originalDocumentBody);
		
		SmartScriptParser parser2 = new SmartScriptParser(originalDocumentBody);
		DocumentNode document2 = parser2.getDocumentNode();
		//System.out.println(document2);
		// now document and document2 should be structurally identical trees
		boolean same = document.equals(document2);  // ==> "same" must be true
		System.out.println((same));
	}

}
