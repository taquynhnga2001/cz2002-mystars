package entity;

import java.io.IOException;
import java.util.ArrayList;

import custom_exceptions.*;
import text_manager.*;

public class Course {
    protected String courseCode;
    protected String school;
    protected int AU;
    private ArrayList<CourseIndex> courseIndexs;

    // private final int courseCodeIdx = 0;
    private final int schoolIdx = 0;
    private final int AUIdx = 1;

    public Course(String courseCode) {
        this.courseCode = courseCode;
        try {
            ArrayList<String> attributes = CourseTextMng.readCourse(this.courseCode);
            this.school = attributes.get(schoolIdx);
            this.AU = Integer.parseInt(attributes.get(AUIdx));
            // construct CourseIndexs for this Course
            this.courseIndexs = CourseIndexTextMng.readCourseIndexsOfCourse(this.courseCode);
            for (int i=0; i < this.courseIndexs.size(); i++) {
                CourseIndex courseIndex = this.courseIndexs.get(i);
                courseIndex.setCourseCode(this.courseCode);
            }
        } catch (WrongCourseCode e) {
            System.out.println(">>> Error: " + e.getMessage());
            e.printStackTrace();
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
    public ArrayList<CourseIndex> getCourseIndexs(){
        return courseIndexs;
    }
    
    public void setCouseCode(String courseCode) {
        this.courseCode = courseCode;
    }
    public void setSchool(String school) {
        this.school = school;
    }
    public void setAU(int AU) {
        this.AU = AU;
    }

}
