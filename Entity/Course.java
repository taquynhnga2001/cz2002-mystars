package Entity;

import java.io.IOException;
import java.util.ArrayList;

import CustomException.WrongCourseCode;
import TextManager.CourseTextMng;

public class Course {
    private String courseCode;
    private String school;
    private int AU;

    private final int courseCodeIdx = 0;
    private final int schoolIdx = 1;
    private final int AUIdx = 2;

    public Course(String courseCode) {
        this.courseCode = courseCode;
        try {
            ArrayList<String> attributes = new CourseTextMng().readCourse(this.courseCode);
            this.school = attributes.get(schoolIdx);
            this.AU = Integer.parseInt(attributes.get(AUIdx));
        } catch (WrongCourseCode e) {
            System.out.println(">>> Error: " + e.getMessage());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getCourseCode() {
        return courseCode;
    }
    public String getSchool() {
        return school;
    }
    public int getAU() {
        return AU;
    }

}
