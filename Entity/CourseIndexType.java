package Entity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

import CustomException.WrongCourseIndex;
import TextManager.CourseIndexTypeTextMng;
// import jdk.internal.jimage.decompressor.SignatureParser.ParseResult;

public class CourseIndexType {					
	// implements Comparable <CourseIndexType> 
	// course index type information
	private String index;
	private String classType;
	private String group;
	private String day;
	private String time;
	private String venue;
	private String remark;

	private String courseCode;

	private final int indexIdx = 0;
	private final int classTypeIdx = 1;
	private final int groupIdx = 2;
	private final int dayIdx = 3;
	private final int timeIdx = 4;
	private final int venueIdx = 5;
	private final int remarkIdx = 6;



	private ArrayList <Student> studentEnrolled = new ArrayList<Student>();    
	// private ArrayList <Student> waitlist = new ArrayList<Student>();    

	// private static Course makeCourse(String index) {
	// 	int courseCodeIdx = 1;
	// 	try {
	// 		ArrayList<String> info = new CourseIndexTextMng().readIndexInfo(index);
	// 		String courseCode = info.get(courseCodeIdx);
	// 		return new Course(courseCode);
	// 	} catch (WrongCourseIndex e) {
	// 		System.out.println(">>> Error: " + e.getMessage());
	// 	} catch (IOException e) {
	// 		e.printStackTrace();
	// 	}
	// 	return null;
	// }
	
	public CourseIndexType(String index){
		try {
			ArrayList<String> attributes = new CourseIndexTypeTextMng().readCourseIndexType(index);
			this.classType = attributes.get(classTypeIdx);
			this.group = attributes.get(groupIdx);
			this.day = attributes.get(dayIdx);
			this.time = attributes.get(timeIdx);
			this.venue = attributes.get(venueIdx);
			this.remark = attributes.get(remarkIdx);	

		} catch (WrongCourseIndex e) {
			System.out.println("Error: " + e.getMessage());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	
	// int checkVacancy() {
	// 	return capacity-studentEnrolled.size();
	// }
	
	// void enrollStudent(Student student) {                           // enroll student 
	// 	studentEnrolled.add(student);
	// }
	// void dropStudent(Student student) {
	// 	studentEnrolled.remove(student);							// drop student
	// }
	// void joinWaitlist(Student student) {							// add to waitlist
	// 	waitlistSize.add(student);
	// }
	// void enrollFromWaitist() {										
	// 	studentEnrolled.add(waitlistSize.get(0));						// enroll the first student in waitlist		
	// 	waitlistSize.remove(0);
	// }
	
	// @Override
	// public int compareTo(CourseIndexType o) {                             // compares by courseIndex
	// 	// TODO Auto-generated method stub
	// 	return this.getIndex()-o.getIndex();
	// }
	
	// @Override
    // public String toString() { 
    //     return "Course Name: %s, Index: %d".formatted(courseCode,index); 
    // } 
	
	// public ArrayList <Student> getStudentArray() {                             // returns StudentArray is sorted form
	// 	Collections.sort(studentEnrolled);
	// 	return studentEnrolled;
	// }

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


	public void setIndex(String index) {
		this.index = index;
	}
	public void setClassType(String type) {
		this.classType = type.toUpperCase();
	}
	public void setDay(String day) {
		this.day = day;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public void setVenue(String venue) {
		this.venue = venue;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}

	
	
}
