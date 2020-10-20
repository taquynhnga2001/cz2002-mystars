package entity;

import java.io.*;
// import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
// import java.util.Collections;
import java.util.HashMap;

// import javax.swing.text.html.HTMLDocument.Iterator;

import text_manager.*;

public class Student extends User{ // Students are comparable and can be
																   // sorted by name>gender>natioanlity
																//    implements Comparable<Student> 
	private String name;
	private String mail;
	private String gender;
	private String matricNum;
	private String nationality;

	private final int nameIdx = 0;
	private final int usernameIdx = 1;
	private final int mailIdx = 2;
	private final int passwordIdx = 3;
	private final int matricNumIdx = 4;
	private final int genderIdx = 5;
	private final int nationalityIdx = 6;

	// private HashMap<Integer, String> coursesEnrolled = new HashMap<Integer, String>();

	public Student(String username, String password) throws IOException{
		super(username, password);
		ArrayList<String> attributes = StudentTextMng.readStudent(username, password);
		this.name = attributes.get(nameIdx);
		// this.username = attributes.get(usernameIdx);
		this.mail = attributes.get(mailIdx);
		// this.password = attributes.get(passwordIdx);
		this.matricNum = attributes.get(matricNumIdx);
		this.gender = attributes.get(genderIdx);
		this.nationality = attributes.get(nationalityIdx);
	}

	// public Student(String matricNum) {
	// ArrayList<String> attributes = new StudentTextMng().readStudent("database/Student.csv", matricNum);
	// this.name = attributes.get(nameIdx);
	// String username = attributes.get(usernameIdx);
	// this.mail = attributes.get(mailIdx);
	// String password = attributes.get(passwordIdx);
	// this.matricNum = matricNum;
	// this.gender = attributes.get(genderIdx);
	// this.nationality = attributes.get(nationalityIdx);
	// 	this(username, password);
	// }

	// void addCourseIndex(int index) throws FileNotFoundException, ClassNotFoundException, IOException {
	// 	CourseIndexType courseIndex = readCourseIndex(index);
	// 	if (courseIndex.getVacancy() > 0) {
	// 		courseIndex.enrollStudent(this); // if slot available, enroll student and update profile
	// 		coursesEnrolled.put(index, courseIndex.getCourseCode());

	// 	} else {
	// 		courseIndex.joinWaitlist(this); // if slots unavailable, join them to waitlist
	// 	}
	// 	saveChanges(courseIndex, index);
	// 	saveChanges(this); // update changes to databases
	// }

	// void dropCourseIndex(int index) throws FileNotFoundException, ClassNotFoundException, IOException {
	// 	CourseIndexType courseIndex = readCourseIndex(index);
	// 	courseIndex.dropStudent(this); // drop student and enroll the first student in waitlist

	// 	if (courseIndex.getWaitlistSize() > 0) {
	// 		courseIndex.enrollFromWaitist();
	// 	}
	// 	saveChanges(courseIndex, index);
	// 	saveChanges(this); // update changes to databases
	// }

	// void printCoursesEnrolled() { // prints courses enrolled and indexes
	// 	for (Integer key : coursesEnrolled.keySet()) {
	// 		System.out.printf("Course Name: %s, Index: %d", coursesEnrolled.get(key), key);
	// 	}
	// }

	// @Override
	// public int compareTo(Student student) { // compares in the order, Name, gender, nationality
	// 	// TODO Auto-generated method stub
	// 	if (this.getName().compareTo(student.getName()) != 0) {
	// 		return this.getName().compareTo(student.getName());
	// 	}
	// 	if (this.getGender().compareTo(student.getGender()) != 0) {
	// 		return this.getGender().compareTo(student.getGender());
	// 	}
	// 	return this.getNationality().compareTo(student.getNationality());
	// }

	// public String toString() { // Student can be printed to show name, gender, nationality
	// 	return "Name: %s, Gender: %s, Nationality: %s".formatted(name, gender, nationality);
	// }

	public String getName() {
		return name;
	}
	public String getMail() {
		return mail;
	}
	public String getMatricNum() {
		return matricNum;
	}
	public String getGender() {
		return gender;
	}
	public String getNationality() {
		return nationality;
	}

}
