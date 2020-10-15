import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Serializable;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import javax.swing.text.html.HTMLDocument.Iterator;

public class Student extends User implements Serializable, Comparable<Student>{      // Students are comparable and can be sorted by name>gender>natioanlity
	
	private String name;
	private String gender;
	private String matID;
	private String nationality;
	private HashMap <Integer,String> coursesEnrolled=new HashMap <Integer,String>();  	 
	
	public Student(String username, String password, String name, String gender, String matID , String nationality) throws NoSuchAlgorithmException{
		super(username,password);
		this.name=name;
		this.gender=gender;
		this.matID=matID;
		this.nationality=nationality;
		
	}
	
	
	void addCourseIndex(int index) throws FileNotFoundException, ClassNotFoundException, IOException {
		CourseIndex courseIndex = readCourseIndex(index);
		if(courseIndex.checkVacancy()>0) {
			courseIndex.enrollStudent(this);					// if slot available, enroll student and update profile
			coursesEnrolled.put(index,courseIndex.getCourseName());
			
		}else {
			courseIndex.joinWaitlist(this);					    // if slots unavailable, join them to waitlist  										
		}
		saveChanges(courseIndex,index);
		saveChanges(this);										// update changes to databases
	}
	void dropCourseIndex(int index) throws FileNotFoundException, ClassNotFoundException, IOException {
		CourseIndex courseIndex = readCourseIndex(index);
		courseIndex.dropStudent(this);							// drop student and enroll the first student in waitlist
		
		if(courseIndex.checkWaitlist()>0) {
			courseIndex.enrollFromWaitist();
		}
		saveChanges(courseIndex,index);
		saveChanges(this);										// update changes to databases
	}
	void printCoursesEnrolled() {								// prints courses enrolled and indexes
		for(Integer key: coursesEnrolled.keySet()) {
			System.out.printf("Course Name: %s, Index: %d",coursesEnrolled.get(key),key);
		}
	}

	

	@Override
	public int compareTo(Student student) {											// compares in the order, Name, gender, nationality
		// TODO Auto-generated method stub
		if(this.getName().compareTo(student.getName())!=0) {
			return this.getName().compareTo(student.getName());
		}
		if(this.getGender().compareTo(student.getGender())!=0) {
			return this.getGender().compareTo(student.getGender());
		}
		return this.getNationality().compareTo(student.getNationality());
	}
	
	public String toString() {															// Student can be printed to show name, gender, nationality
		return "Name: %s, Gender: %s, Nationality: %s".formatted(name, gender,nationality);
	}
	
	
	public String getName() {
		return name;
	}
	public String getGender() {
		return gender;
	}
	public String getNationality() {
		return nationality;
	}
	
	
	

}
