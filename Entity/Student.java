package entity;

import java.io.*;
import java.util.ArrayList;

import custom_exceptions.AlreadyEnrolled;
import custom_exceptions.AlreadyInWaitlist;
import custom_exceptions.DidntEnrollOrWait;
import custom_exceptions.NoVacancy;
import custom_exceptions.WrongCourseIndex;

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
	private final int mailIdx = 1;
	private final int matricNumIdx = 2;
	private final int genderIdx = 3;
	private final int nationalityIdx = 4;

	private ArrayList<String> courseEnrolled = new ArrayList<>();
	private ArrayList<String> courseWaitlist = new ArrayList<>();
	private int registeredAU;

	public Student(String username, String password) throws IOException{
		super(username, password);
		ArrayList<String> attributes = StudentTextMng.readStudent(username, password);
		this.name = attributes.get(nameIdx);
		this.mail = attributes.get(mailIdx);
		this.matricNum = attributes.get(matricNumIdx);
		this.gender = attributes.get(genderIdx);
		this.nationality = attributes.get(nationalityIdx);

		this.courseEnrolled = EnrolledTextMng.readFile(this.matricNum);
		this.courseWaitlist = WailistTextMng.readFile(this.matricNum);
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
	public int getRegisteredAU() {
		return registeredAU;
	}
	public void setRegisteredAU(int i) {
		registeredAU = i;
	}

	/** Filter from course DB to point to Enrolled courses */
	public ArrayList<CourseIndex> getCourseEnrolled(ArrayList<Course> courseDB) {
		registeredAU = 0;
		ArrayList<CourseIndex> al = new ArrayList<>();
		// loop through all courses in the database that have student registered
		for (int i=0; i<courseDB.size(); i++) {
			Course course = courseDB.get(i);
			ArrayList<CourseIndex> courseIndexs = course.getCourseIndexs();
			boolean tookThisCouse = false;

			// loop through all courseIndexs of that course in the database
			for (int j=0; j<courseIndexs.size(); j++) {
				CourseIndex courseIndex = courseIndexs.get(j);

				// loop through all courseEnrolled of this Student and check if it is the same as this courseIndex
				for (int k=0; k<this.courseEnrolled.size(); k++) {
					if (courseEnrolled.get(k).equalsIgnoreCase(courseIndex.getIndex())) {
						al.add(courseIndex);
						registeredAU += course.getAU();
						tookThisCouse = true;
						break;
					}
				}
				if (tookThisCouse) {
					// no need to loop to other courseIndexs if the Student took this course
					// continue to loop through other courses
					break;
				}
			}
		}
		return al;
	}
	
	/** Filter from course DB to point to waitlist courses */
	public ArrayList<CourseIndex> getCourseWaitlist(ArrayList<Course> courseDB) {
		ArrayList<CourseIndex> al = new ArrayList<>();
		// loop through all courses in the database that still have students in waitlist
		for (int i=0; i<courseDB.size(); i++) {
			ArrayList<CourseIndex> courseIndexs = courseDB.get(i).getCourseIndexs();
			boolean waitThisCourse = false;

			// loop through all courseIndexs of that course in the database
			for (int j=0; j<courseIndexs.size(); j++) {
				CourseIndex courseIndex = courseIndexs.get(j);

				// loop through all courseEnrolled of this Student and check if it is the same as this courseIndex
				for (int k=0; k<this.courseWaitlist.size(); k++) {
					if (courseWaitlist.get(k).equalsIgnoreCase(courseIndex.getIndex())) {
						al.add(courseIndex);
						waitThisCourse = true;
						break;
					}
				}
				if (waitThisCourse) {
					// no need to loop to other courseIndexs if the Student in waitlist of this course
					// continue to loop through other courses
					break;
				}
			}
		}
		return al;
	}

	/** Add the courseIndex string to the ArrayList<String> courseEnrolled */
	public void enrollCourse(String courseIndex) {
		courseEnrolled.add(courseIndex);
	}
	/** Add the courseIndex string to the ArrayList<String> courseWaitlist */
	public void waitCourse(String courseIndex) {
		courseWaitlist.add(courseIndex);
	}
	
	/**
	 * Drop the courseIndex string in the ArrayList<String> courseEnrolled and courseWaitlist 
	 * If true: drop successfully 
	 * If false: throw Exception*/
	public void dropCourse(String courseIndex) {
		courseEnrolled.remove(courseIndex);
		courseWaitlist.remove(courseIndex);
	}
		
}
