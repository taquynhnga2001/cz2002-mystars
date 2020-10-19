package Entity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

import CustomException.WrongCourseIndex;
import TextManager.CourseIndexTextMng;
// import jdk.internal.jimage.decompressor.SignatureParser.ParseResult;

public class CourseIndex extends Course implements Comparable <CourseIndex> {					
	// course index information
	private String index;
	private String classType;
	private String group;
	private String day;
	private String time;
	private String venue;
	private String remark;
	// course code - course index information
	private int capacity; // maximum students in this courseIndex
	private int vacancy;
	private int waitlistSize;

	private final int indexIdx = 0;
	private final int classTypeIdx = 1;
	private final int groupIdx = 2;
	private final int dayIdx = 3;
	private final int timeIdx = 4;
	private final int venueIdx = 5;
	private final int remarkIdx = 6;

	private final int courseCodeIdx = 1;
	private final int capacityIdx = 2;
	private final int vacancyIdx = 3;
	private final int waitlistIdx = 4;

	private ArrayList <Student> studentEnrolled = new ArrayList<Student>();    
	// private ArrayList <Student> waitlist = new ArrayList<Student>();    

	private static Course makeCourse(String index) {
		int courseCodeIdx = 1;
		try {
			ArrayList<String> info = new CourseIndexTextMng().readIndexInfo(index);
			String courseCode = info.get(courseCodeIdx);
			return new Course(courseCode);
		} catch (WrongCourseIndex e) {
			System.out.println(">>> Error: " + e.getMessage());
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public CourseIndex(String index){
		this(makeCourse(index));    // return super constructor
		try {
			ArrayList<String> attributes = new CourseIndexTextMng().readCourseIndex(index);
			this.classType = attributes.get(classTypeIdx);
			this.group = attributes.get(groupIdx);
			this.day = attributes.get(dayIdx);
			this.time = attributes.get(timeIdx);
			this.venue = attributes.get(venueIdx);
			this.remark = attributes.get(remarkIdx);

			ArrayList<String> info = new CourseIndexTextMng().readIndexInfo(index);
			this.capacity = Integer.parseInt(info.get(capacityIdx));
			this.vacancy = Integer.parseInt(info.get(vacancyIdx));
			this.waitlistSize = Integer.parseInt(info.get(waitlistIdx));

		} catch (WrongCourseIndex e) {
			System.out.println("Error: " + e.getMessage());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private CourseIndex(Course course) {
		super(course.getCode());
	}
	
	
	int checkVacancy() {
		return capacity-studentEnrolled.size();
	}
	// int checkWaitlist() {
	// 	return waitlist.size();
	// }
	
	void enrollStudent(Student student) {                           // enroll student 
		studentEnrolled.add(student);
	}
	void dropStudent(Student student) {
		studentEnrolled.remove(student);							// drop student
	}
	void joinWaitlist(Student student) {							// add to waitlist
		waitlistSize.add(student);
	}
	void enrollFromWaitist() {										
		studentEnrolled.add(waitlistSize.get(0));						// enroll the first student in waitlist		
		waitlistSize.remove(0);
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

	public String getIndex() {
		return index;
	}
	public String getClassType() {
		return classType;
	}
	public String getGroup() {
		return group;
	}
	public String getDay() {
		return day;
	}
	public String getTime() {
		return time;
	}
	public String getVenue() {
		return venue;
	}
	public String getRemark() {
		return remark;
	}
	
	
	
}
