package hr.fer.oprpp1.hw04.db;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import org.junit.jupiter.api.Test;

class QueryFilterTest {

	@Test
	void testDirect() {
		List<String> lines = null;
		try {
			lines = Files.readAllLines(Paths.get("./database.txt"), StandardCharsets.UTF_8);
		} catch (IOException e) {
			e.printStackTrace();
		}
		StudentDatabase sdb = new StudentDatabase(lines);

		QueryParser parser = new QueryParser("jmbag=\"0000000060\"");
		List<StudentRecord> expressions = sdb.filter(new QueryFilter(parser.getQuery()));
		assertEquals(1, expressions.size());

		StudentRecord sr = expressions.get(0);
		// System.out.println(sr.getJmbag() + ";" + sr.getLastName() + ";" +
		// sr.getFirstName() + ";" + sr.getFinalGrade());

		assertEquals("0000000060", sr.getJmbag());
		assertEquals("Vignjević", sr.getLastName());
		assertEquals("Irena", sr.getFirstName());
		assertEquals(5, sr.getFinalGrade());
	}

	@Test
	void testNonDirect() {
		List<String> lines = null;
		try {
			lines = Files.readAllLines(Paths.get("./database.txt"), StandardCharsets.UTF_8);
		} catch (IOException e) {
			e.printStackTrace();
		}
		StudentDatabase sdb = new StudentDatabase(lines);

		QueryParser parser = new QueryParser("lastName > \"Akšamović\" and lastName < \"C\"");
		List<StudentRecord> expressions = sdb.filter(new QueryFilter(parser.getQuery()));
		
		assertEquals(4, expressions.size());
		
		assertEquals("Bakamović", expressions.get(0).getLastName());
		assertEquals("Bosnić", expressions.get(1).getLastName());
		assertEquals("Božić", expressions.get(2).getLastName());
		assertEquals("Brezović", expressions.get(3).getLastName());
		
//		for (StudentRecord r : expressions) {
//			System.out.println(r.getLastName());
//		}
	}

}
