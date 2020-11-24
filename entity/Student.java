package entity;

import java.io.*;
import java.util.ArrayList;

import text_manager.*;

public class Student extends User implements Comparable<Student> { // Students are comparable and can be
																   // sorted by name>gender>natioanlity
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
		this.courseWaitlist = WaitlistTextMng.readFile(this.matricNum);
	}

	@Override
	public int compareTo(Student student) { // compares in the order, Name, gender, nationality
		if (this.getName().compareTo(student.getName()) != 0) {
			return this.getName().compareTo(student.getName());
		}
		if (this.getGender().compareTo(student.getGender()) != 0) {
			return this.getGender().compareTo(student.getGender());
		}
		return this.getNationality().compareTo(student.getNationality());
	}


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
	/**Should call getCourseEnrolled() before call this method to update the registeredAU 
	 * unless have called method setRegisteredAU() before*/
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

	/** Add the courseIndex string to the ArrayList of String courseEnrolled */
	public void enrollCourse(String courseIndex) {
		courseEnrolled.add(courseIndex);
	}
	/** Add the courseIndex string to the ArrayList of String courseWaitlist */
	public void waitCourse(String courseIndex) {
		courseWaitlist.add(courseIndex);
	}
	
	/**
	 * Drop the courseIndex string in the ArrayList of String courseEnrolled and courseWaitlist 
	 * If true: drop successfully 
	 * If false: throw Exception*/
	public void dropCourse(String courseIndex) {
		courseEnrolled.remove(courseIndex);
		courseWaitlist.remove(courseIndex);
	}
		
}
