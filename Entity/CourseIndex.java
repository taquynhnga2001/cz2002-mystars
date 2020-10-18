package Entity;

// import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;

import TextManager.CourseIndexTextMng;

public class CourseIndex implements Comparable <CourseIndex> {					
	private int maxStudents;
	private String courseCode;
	private String index;
	private String classType;
	private String group;
	private String day;
	private String time;
	private String venue;
	private String remark;

	private final int indexIdx = 0;
	private final int classTypeIdx = 1;
	private final int groupIdx = 2;
	private final int dayIdx = 3;
	private final int timeIdx = 4;
	private final int venueIdx = 5;
	private final int remarkIdx = 6;

	private ArrayList <Student> studentEnrolled = new ArrayList<Student>();    
	private ArrayList <Student> waitlist = new ArrayList<Student>();    
	
	public CourseIndex(String index){
		this.index=index;
		ArrayList<String> attributes = new CourseIndexTextMng().readCourseIndex("../database/CourseIndex.csv", index);
		this.classType = attributes.get(classTypeIdx); // second token...
		this.group = attributes.get(groupIdx);
		this.day = attributes.get(dayIdx);
		this.time = attributes.get(timeIdx);
		this.venue = attributes.get(venueIdx);
		this.remark = attributes.get(remarkIdx);
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
        return "Course Name: %s, Index: %d".formatted(courseCode,index); 
    } 

	
	public int getMaxStudents() {
		return maxStudents;
	}
	public ArrayList <Student> getStudentArray() {                             // returns StudentArray is sorted form
		Collections.sort(studentEnrolled);
		return studentEnrolled;
	}

	public String getCourseName() {
		return courseCode;
	}

	public int getIndex() {
		return index;
	}

	
	
	
}
