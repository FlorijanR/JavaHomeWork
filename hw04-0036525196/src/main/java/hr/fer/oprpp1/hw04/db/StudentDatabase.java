package hr.fer.oprpp1.hw04.db;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A class that models a database which contains student records.
 * 
 * @author Florijan Rusac
 * @version 1.0
 */
public class StudentDatabase {
	/**
	 * List of student records.
	 */
	List<StudentRecord> studentRecords = new ArrayList<>();
	/**
	 * Map of student records.
	 */
	Map<String, StudentRecord> index = new HashMap<>();

	/**
	 * Creates a list of student records.
	 * 
	 * @param rows to be added in a list of student records
	 */
	public StudentDatabase(List<String> rows) {
		rows.forEach(r -> addRecord(r));
	}

	/**
	 * Creates a student record from a string and adds
	 * it to a list and a map of student records.
	 * 
	 * @param stringRecord to be added in a list
	 */
	private void addRecord(String stringRecord) {
		String[] atr = stringRecord.split("\\t");

		if (index.containsKey(atr[0])) {
			System.out.println("There are two equal JMBAG-s in database.");
			System.exit(0);
		}
		if (Integer.parseInt(atr[3]) < 1 || Integer.parseInt(atr[3]) > 5) {
			System.out.println("Invalid final grade.");
			System.exit(0);
		}

		StudentRecord sr = new StudentRecord(atr[0], atr[1], atr[2], Integer.parseInt(atr[3]));
		studentRecords.add(sr);
		index.put(atr[0], sr);
	}

	/**
	 * @param jmbag of a record that will be returned
	 * @return a student record with passed jmbag
	 */
	public StudentRecord forJMBAG(String jmbag) {
		return index.get(jmbag);
	}

	/**
	 * Filter records in this database based on the passed filter.
	 * 
	 * @param filter which will be used to filter student records
	 * @return a filtered list of student records
	 */
	public List<StudentRecord> filter(IFilter filter) {
		List<StudentRecord> filteredStudRec = new ArrayList<>();
		
		studentRecords.forEach(r -> {
			if (filter.accepts(r))
				filteredStudRec.add(r);
		});

		return filteredStudRec;
	}
	
}











