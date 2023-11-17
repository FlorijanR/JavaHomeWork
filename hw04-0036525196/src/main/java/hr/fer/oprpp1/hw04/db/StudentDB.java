package hr.fer.oprpp1.hw04.db;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * Used to query the database and print out requested data.
 * 
 * @author Florijan Rusac
 * @version 1.0
 */
public class StudentDB {

	/**
	 * Reads records from a file and creates a student database
	 * which can be queried. Prints out requested records.
	 * 
	 * @param args not used
	 */
	public static void main(String[] args) {
		List<String> lines = null;
		try {
			lines = Files.readAllLines(Paths.get("./database.txt"), StandardCharsets.UTF_8);
		} catch (IOException e) {
			e.printStackTrace();
		}
		StudentDatabase sdb = new StudentDatabase(lines);
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String line = null;
		QueryParser parser = null;
		List<StudentRecord> studentRecords = null;
		List<String> output = null;

		while (true) {
			try {
				System.out.print("> ");
				line = br.readLine();
				if (line.equals("exit")) {
					System.out.println("Goodbye!");
					break;
				} else if (line.startsWith("query ")) {

					try {
						parser = new QueryParser(line.substring(6));
						if (parser.isDirectQuery()) {
							System.out.println("Using index for record retrieval.");
						}
						
						studentRecords = sdb.filter(new QueryFilter(parser.getQuery()));

						output = formatOutput(studentRecords);
						output.forEach(System.out::println);
					} catch (QueryException qe) {
						System.out.println("Invalid format of query command. Please try again.");
					}

				} else {
					System.out.println("Invalid command. Please try again.");
				}
			} catch (IOException ioe) {
				System.out.println(ioe);
			}
		}

	}

	/**
	 * Creates a list of strings which contain values of student records 
	 * in a correct format.
	 * 
	 * @param studentRecords to convert to a string format
	 * @return a list of strings which contain values of student 
	 * 		records in a correct format
	 */
	private static List<String> formatOutput(List<StudentRecord> studentRecords) {
		List<String> rows = new ArrayList<>();
		int lastNameLength = -1;
		int firstNameLength = -1;

		for (var studentRecord : studentRecords) {
			if (studentRecord.getLastName().length() > lastNameLength) {
				lastNameLength = studentRecord.getLastName().length();
			}
			if (studentRecord.getFirstName().length() > firstNameLength) {
				firstNameLength = studentRecord.getFirstName().length();
			}
		}

		if (studentRecords.size() > 0)
			printFirstLastLine(lastNameLength, firstNameLength, rows);

		for (var studentRecord : studentRecords) {
			StringBuilder sb = new StringBuilder();
			sb.append(String.format("| %s | %s", studentRecord.getJmbag(), studentRecord.getLastName()));

			printSpaces(lastNameLength - studentRecord.getLastName().length(), sb);

			sb.append(String.format(" | %s", studentRecord.getFirstName()));

			printSpaces(firstNameLength - studentRecord.getFirstName().length(), sb);

			sb.append(String.format(" | %d |", studentRecord.getFinalGrade()));
			rows.add(sb.toString());
		}

		if (studentRecords.size() > 0)
			printFirstLastLine(lastNameLength, firstNameLength, rows);
		rows.add(String.format("Records selected: " + studentRecords.size()));
		rows.add("");
		return rows;
	}

	/**
	 * Appends a passed number of spaces to a string builder.
	 * 
	 * @param numOfSpaces number of spaces to append to string builder
	 * @param sb to append spaces to
	 */
	private static void printSpaces(int numOfSpaces, StringBuilder sb) {
		for (int i = 0; i < numOfSpaces; i++) {
			sb.append(" ");
		}
	}

	/**
	 * Adds the first and last line in a correct format 
	 * to the passed list of strings.
	 * 
	 * @param lastNameLength length of last name
	 * @param firstNameLength length of first name
	 * @param rows list to add the first and last row to
	 */
	private static void printFirstLastLine(int lastNameLength, int firstNameLength, List<String> rows) {
		StringBuilder sb = new StringBuilder();
		sb.append("+============+=");
		for (int i = 0; i < lastNameLength; i++) {
			sb.append("=");
		}
		sb.append("=+=");
		for (int i = 0; i < firstNameLength; i++) {
			sb.append("=");
		}
		sb.append("=+===+");

		rows.add(sb.toString());
	}

}
