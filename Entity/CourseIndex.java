import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;

public class CourseIndex implements Comparable <CourseIndex>,Serializable {					
	private int maxStudents;
	private String courseName;
	private String schoolName;
	private int index; 
	private ArrayList <Student> studentEnrolled = new ArrayList<Student>();    
	private ArrayList <Student> waitlist = new ArrayList<Student>();    
	
	CourseIndex(String courseName,String schoolName, int index,int maxStudents){
		this.index=index;
		this.maxStudents=maxStudents;
		this.courseName=courseName;
		this.schoolName=schoolName;
	}
	
	int checkVacancy() {
		return maxStudents-studentEnrolled.size();
	}
	int checkWaitlist() {
		return waitlist.size();
	}
	
	void enrollStudent(Student student) {                           // enroll student 
		studentEnrolled.add(student);
	}
	void dropStudent(Student student) {
		studentEnrolled.remove(student);							// drop student
	}
	void joinWaitlist(Student student) {							// add to waitlist
		waitlist.add(student);
	}
	void enrollFromWaitist() {										
		studentEnrolled.add(waitlist.get(0));						// enroll the first student in waitlist		
		waitlist.remove(0);
	}
	
	@Override
	public int compareTo(CourseIndex o) {                             // compares by courseIndex
		// TODO Auto-generated method stub
		return this.getIndex()-o.getIndex();
	}
	
	@Override
    public String toString() { 
        return "Course Name: %s, Index: %d".formatted(courseName,index); 
    } 

	
	public int getMaxStudents() {
		return maxStudents;
	}
	public ArrayList <Student> getStudentArray() {                             // returns StudentArray is sorted form
		Collections.sort(studentEnrolled);
		return studentEnrolled;
	}

	public String getCourseName() {
		return courseName;
	}

	public int getIndex() {
		return index;
	}

	
	
	
}
