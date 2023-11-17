package hr.fer.oprpp1.hw04.db;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import org.junit.jupiter.api.Test;

class FieldValueGettersTest {

	@Test
	void testIFieldValueGetter() {
		List<String> lines = null;

		try {
			lines = Files.readAllLines(Paths.get("./database.txt"), StandardCharsets.UTF_8);
		} catch (IOException e) {
			e.printStackTrace();
		}

		StudentDatabase sdb = new StudentDatabase(lines);

		StudentRecord sr = sdb.forJMBAG("0000000060");
		assertEquals("0000000060", FieldValueGetters.JMBAG.get(sr));
		assertEquals("Vignjević", FieldValueGetters.LAST_NAME.get(sr));
		assertEquals("Irena", FieldValueGetters.FIRST_NAME.get(sr));

		sr = sdb.forJMBAG("0000000050");
		assertEquals("0000000050", FieldValueGetters.JMBAG.get(sr));
		assertEquals("Sikirica", FieldValueGetters.LAST_NAME.get(sr));
		assertEquals("Alen", FieldValueGetters.FIRST_NAME.get(sr));
	}

}
