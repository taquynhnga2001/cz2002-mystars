package entity;

import java.io.IOException;
import java.util.ArrayList;

import custom_exceptions.*;
import text_manager.*;

public class CourseIndex {
    private String index;
    private String courseCode;
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

    public String getIndex() {
        return index;
    }
    public String getCourseCode() {
        return courseCode;
    }
    public int getCapacity() {
		return capacity;
	}
	public int getVacancy() {
		return vacancy;
	}
	public int getWaitlistSize() {
		return waitlistSize;
	}
	public ArrayList<CourseIndexType> getClassTypes() {
		return classTypes;
	}

    public void setCapacity(int capacity) {
		this.capacity = capacity;
	}
	public void setVacancy(int vacancy) {
		this.vacancy = vacancy;
	}
	public void getWaitlistSize(int waitlist) {
		this.waitlistSize = waitlist;
    }
    public void setCourseCode(String courseCode) {
        this.courseCode = courseCode;
    }
}
