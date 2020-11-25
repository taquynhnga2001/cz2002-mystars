package src.entity;

import java.io.IOException;
import java.util.ArrayList;

import src.custom_exceptions.*;
import src.text_manager.*;

/**
 * Represents a course index in the course. An index has 1 to many course index types
 * 
 * @author Ta Quynh Nga, Tan Ching Fhen
 */
public class CourseIndex {
    private String index;
	private String courseCode;
	private int AU;
	private String courseName;
    private ArrayList<CourseIndexType> classTypes;

    // course code - course index information
	private int capacity; // maximum students in this courseIndex
	private int vacancy;
    private int waitlistSize;
    
    private final int courseCodeIdx = 0;
	private final int capacityIdx = 1;
	private final int vacancyIdx = 2;
	private final int waitlistIdx = 3;

    public CourseIndex(String index) {
		this.index = index;
        try {
            ArrayList<String> info = CourseIndexTextMng.readCourseIndex(index);
			this.courseCode = info.get(courseCodeIdx);
			this.capacity = Integer.parseInt(info.get(capacityIdx));
			this.vacancy = Integer.parseInt(info.get(vacancyIdx));
			this.waitlistSize = Integer.parseInt(info.get(waitlistIdx));
			// contruct CourseIndexType objects for this CourseIndex
			classTypes = CourseIndexTypeTextMng.readClassTypesOfCourseIndex(this.index);
			for (int i = 0; i<classTypes.size(); i++) {
				CourseIndexType classType = classTypes.get(i);
				classType.setCourseCode(this.courseCode); // set courseCode for each CourseIndexType object of this CourseIndex
			}

        } catch (WrongCourseIndex e) {
			System.out.println("Error: " + e.getMessage());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	/** 
	 * @return String
	 */
	public String toString() {
		return "Index " + this.index + " | " + this.courseCode + " " + this.courseName;
	}

    
	/** 
	 * @return index of the course index in string
	 */
	public String getIndex() {
        return index;
    }
    
	/** 
	 * @return the course code of the course it belongs to
	 */
	public String getCourseCode() {
        return courseCode;
	}
	
	/** 
	 * @return the AU of the course it belongs to
	 */
	public int getAU() {
		return AU;
	}
	
	/** 
	 * @return the course name of the course it belongs to
	 */
	public String getCourseName() {
		return courseName;
	}
    
	/** 
	 * @return the capacity of the index for tut/lab class
	 */
	public int getCapacity() {
		return capacity;
	}
	
	/** 
	 * @return the vacancy of the index available
	 */
	public int getVacancy() {
		return vacancy;
	}
	
	/** 
	 * @return the number of students in the waitlist of this index
	 */
	public int getWaitlistSize() {
		return waitlistSize;
	}
	
	/** 
	 * @return a list of classes in this index
	 */
	public ArrayList<CourseIndexType> getClassTypes() {
		return classTypes;
	}

    
	/** 
	 * set the capacity to the specified number
	 * @param capacity
	 */
	public void setCapacity(int capacity) {
		this.capacity = capacity;
	}
	
	/** 
	 * set the vacancy to the specified number
	 * @param vacancy
	 */
	public void setVacancy(int vacancy) {
		this.vacancy = vacancy;
	}
	
	/** 
	 * set the waitlist size to the specified number
	 * @param waitlist
	 */
	public void setWaitlistSize(int waitlist) {
		this.waitlistSize = waitlist;
    }
    
	/** 
	 * set the course code to the specified string
	 * @param courseCode
	 */
	public void setCourseCode(String courseCode) {
        this.courseCode = courseCode;
	}
	
	/** 
	 * set the AU to the AU of the course that it belongs to
	 * @param AU
	 */
	public void setAU(int AU) {
		this.AU = AU;
	}
	
	/** 
	 * set the course name to the course name of the course that it belongs to
	 * @param courseName
	 */
	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}
	
	/** 
	 * set the index to a specified string
	 * @param index
	 */
	public void setCourseIndex(String index) {
		this.index = index;
	}
}
