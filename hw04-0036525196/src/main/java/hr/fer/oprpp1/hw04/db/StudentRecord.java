package hr.fer.oprpp1.hw04.db;

import java.util.Objects;

/**
 * A class that stores information about a student.
 * 
 * @author Florijan Rusac
 * @version 1.0
 */
public class StudentRecord {
	/**
	 * Jmbag of a student.
	 */
	private String jmbag;
	/**
	 * First name of a student.
	 */
	private String firstName;
	/**
	 * Last name of a student.
	 */
	private String lastName;
	/**
	 * Final grade of a student.
	 */
	private int finalGrade;
	
	/**
	 * A constructor that initializes values for a student.
	 * 
	 * @param jmbag of a student
	 * @param lastName of a student
	 * @param firstName of a student
	 * @param finalGrade of a student
	 */
	public StudentRecord(String jmbag, String lastName, String firstName, int finalGrade) {
		this.jmbag = jmbag;
		this.lastName = lastName;
		this.firstName = firstName;
		this.finalGrade = finalGrade;
	}
	
	/**
	 * @return the jmbag
	 */
	public String getJmbag() {
		return jmbag;
	}

	/**
	 * @return the firstName
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * @return the lastName
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * @return the finalGrade
	 */
	public int getFinalGrade() {
		return finalGrade;
	}

	/**
	 * @return hash value based on jmbag
	 */
	@Override
	public int hashCode() {
		return Objects.hash(jmbag);
	}
	
	/**
	 * Check if two student records are equal based on jmbag.
	 * 
	 * @param obj to be checked
	 * @return true if passed argument is equal to this one
	 */
	@Override
	public boolean equals(Object obj) {
		if (obj == null)
			return false;
		if (this == obj)
			return true;
		if (!(obj instanceof StudentRecord))
			return false;
		StudentRecord other = (StudentRecord) obj;
		return jmbag.equals(other.jmbag);
	}
	
	
}
