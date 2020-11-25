package src.entity;

import java.io.*;
import java.util.ArrayList;

import src.text_manager.*;

/**
 * Represents a student enrolled in the school. A student can be enrolled in
 * many courses.
 * 
 * @author Ta Quynh Nga, Tan Ching Fhen
 */
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

	public Student(String username, String password) throws IOException {
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

	/**
	 * implement compareTo method
	 * @param student object that this student compares to
	 * @return Returns a negative integer, zero, or a positive integer as this
	 *         object is less than, equal to, or greater than the specified object.
	 */
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

	/**
	 * @return name of the Student
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return email address of the student
	 */
	public String getMail() {
		return mail;
	}

	/**
	 * @return matric number of the student
	 */
	public String getMatricNum() {
		return matricNum;
	}

	/**
	 * @return the gender of the student (M/F)
	 */
	public String getGender() {
		return gender;
	}

	/**
	 * @return the nationality of the student
	 */
	public String getNationality() {
		return nationality;
	}

	/**
	 * Should call getCourseEnrolled() before call this method to update the
	 * registeredAU unless have called method setRegisteredAU() before
	 * 
	 * @return the total AUs registered by this student
	 */
	public int getRegisteredAU() {
		return registeredAU;
	}

	/**
	 * set the registered au to the total AUs after setting of the student
	 * @param i an integer
	 */
	public void setRegisteredAU(int i) {
		registeredAU = i;
	}

	/**
	 * Filter from course DB to point to Enrolled courses
	 * 
	 * @param courseDB the reference of the courses created at the beginning of the
	 *                 sequence system
	 * @return the a list of course indexes that the student has enrolled
	 */
	public ArrayList<CourseIndex> getCourseEnrolled(ArrayList<Course> courseDB) {
		registeredAU = 0;
		ArrayList<CourseIndex> al = new ArrayList<>();
		// loop through all courses in the database that have student registered
		for (int i = 0; i < courseDB.size(); i++) {
			Course course = courseDB.get(i);
			ArrayList<CourseIndex> courseIndexs = course.getCourseIndexs();
			boolean tookThisCouse = false;

			// loop through all courseIndexs of that course in the database
			for (int j = 0; j < courseIndexs.size(); j++) {
				CourseIndex courseIndex = courseIndexs.get(j);

				// loop through all courseEnrolled of this Student and check if it is the same
				// as this courseIndex
				for (int k = 0; k < this.courseEnrolled.size(); k++) {
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

	/** Filter from course DB to point to waitlist courses
	 * @param courseDB the reference of the courses created at the beginning of the
	 *                 sequence system
	 * @return the a list of course indexes that the student has been in waitlist
	 */
	public ArrayList<CourseIndex> getCourseWaitlist(ArrayList<Course> courseDB) {
		ArrayList<CourseIndex> al = new ArrayList<>();
		// loop through all courses in the database that still have students in waitlist
		for (int i = 0; i < courseDB.size(); i++) {
			ArrayList<CourseIndex> courseIndexs = courseDB.get(i).getCourseIndexs();
			boolean waitThisCourse = false;

			// loop through all courseIndexs of that course in the database
			for (int j = 0; j < courseIndexs.size(); j++) {
				CourseIndex courseIndex = courseIndexs.get(j);

				// loop through all courseEnrolled of this Student and check if it is the same
				// as this courseIndex
				for (int k = 0; k < this.courseWaitlist.size(); k++) {
					if (courseWaitlist.get(k).equalsIgnoreCase(courseIndex.getIndex())) {
						al.add(courseIndex);
						waitThisCourse = true;
						break;
					}
				}
				if (waitThisCourse) {
					// no need to loop to other courseIndexs if the Student in waitlist of this
					// course
					// continue to loop through other courses
					break;
				}
			}
		}
		return al;
	}

	/** Add the courseIndex string to the ArrayList of String courseEnrolled
	 * @param courseIndex string of the course index that the student enrolls in the course
	 */
	public void enrollCourse(String courseIndex) {
		courseEnrolled.add(courseIndex);
	}

	/** Add the courseIndex string to the ArrayList of String courseWaitlist
	 * @param courseIndex string of the course index that the student is in the waitlist
	 */
	public void waitCourse(String courseIndex) {
		courseWaitlist.add(courseIndex);
	}

	/**
	 * Drop the courseIndex string in the ArrayList of String courseEnrolled and
	 * courseWaitlist If true: drop successfully If false: throw Exception
	 * @param courseIndex string of the courseIndex that the student drops
	 */
	public void dropCourse(String courseIndex) {
		courseEnrolled.remove(courseIndex);
		courseWaitlist.remove(courseIndex);
	}

}
