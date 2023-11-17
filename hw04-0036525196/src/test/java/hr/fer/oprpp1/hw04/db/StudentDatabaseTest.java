package hr.fer.oprpp1.hw04.db;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import org.junit.jupiter.api.Test;

class StudentDatabaseTest {

	@Test
	void testForJMBAG() {
		List<String> lines = null;

		try {
			lines = Files.readAllLines(Paths.get("./database.txt"), StandardCharsets.UTF_8);
		} catch (IOException e) {
			e.printStackTrace();
		}

		StudentDatabase sdb = new StudentDatabase(lines);

		StudentRecord sr = sdb.forJMBAG("0000000060");
		assertEquals("0000000060", sr.getJmbag());
		assertEquals("Vignjević", sr.getLastName());
		assertEquals("Irena", sr.getFirstName());
		assertEquals(5, sr.getFinalGrade());

		sr = sdb.forJMBAG("0000000050");
		assertEquals("0000000050", sr.getJmbag());
		assertEquals("Sikirica", sr.getLastName());
		assertEquals("Alen", sr.getFirstName());
		assertEquals(3, sr.getFinalGrade());
	}

	@Test
	void testFilter() {
		IFilter filterTrue = x -> true;
		IFilter filterFalse = x -> false;
		List<String> lines = null;

		try {
			lines = Files.readAllLines(Paths.get("./database.txt"), StandardCharsets.UTF_8);
		} catch (IOException e) {
			e.printStackTrace();
		}

		StudentDatabase sdb = new StudentDatabase(lines);
		
		List<StudentRecord> filteredList = sdb.filter(filterTrue);
		assertEquals(63, filteredList.size());
		StudentRecord sr = filteredList.get(49);
		assertEquals("0000000050", sr.getJmbag());
		assertEquals("Sikirica", sr.getLastName());
		assertEquals("Alen", sr.getFirstName());
		assertEquals(3, sr.getFinalGrade());
		
		filteredList = sdb.filter(filterFalse);
		assertEquals(0, filteredList.size());
	}

}






