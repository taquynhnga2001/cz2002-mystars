package Entity;

import java.io.IOException;
import java.util.ArrayList;

import CustomException.WrongCourseCode;
import CustomException.WrongCourseIndex;
import TextManager.CourseIndexTextMng;
import TextManager.CourseTextMng;

public class CourseIndex {
    private String index;
    private String courseCode;
    private CourseIndexType[] classTypes;

    // course code - course index information
	private int capacity; // maximum students in this courseIndex
	private int vacancy;
    private int waitlistSize;
    
    private final int courseCodeIdx = 1;
	private final int capacityIdx = 2;
	private final int vacancyIdx = 3;
	private final int waitlistIdx = 4;

    public CourseIndex() {
        try {
            ArrayList<String> info = new CourseIndexTextMng().readCourseIndex(index);
			this.courseCode = info.get(courseCodeIdx);
			this.capacity = Integer.parseInt(info.get(capacityIdx));
			this.vacancy = Integer.parseInt(info.get(vacancyIdx));
			this.waitlistSize = Integer.parseInt(info.get(waitlistIdx));
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
