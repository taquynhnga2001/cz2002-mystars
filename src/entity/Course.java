package src.entity;

import java.io.IOException;
import java.util.ArrayList;

import src.custom_exceptions.*;
import src.text_manager.*;

/**
 * Represents a course in the school. A course has 1 to many indexes
 * 
 * @author Ta Quynh Nga, Tan Ching Fhen
 */
public class Course {
    private String courseCode;
    private String courseName;
    private String school;
    private int AU;
    private ArrayList<CourseIndex> courseIndexs;

    private final int courseNameIdx = 0;
    private final int schoolIdx = 1;
    private final int AUIdx = 2;
    

    public Course(String courseCode) {
        this.courseCode = courseCode;
        try {
            ArrayList<String> attributes = CourseTextMng.readCourse(this.courseCode);
            this.school = attributes.get(schoolIdx);
            this.AU = Integer.parseInt(attributes.get(AUIdx));
            this.courseName = attributes.get(courseNameIdx);
            // construct CourseIndexs for this Course
            this.courseIndexs = CourseIndexTextMng.readCourseIndexsOfCourse(this.courseCode);
            for (int i=0; i < this.courseIndexs.size(); i++) {
                CourseIndex courseIndex = this.courseIndexs.get(i);
                courseIndex.setCourseCode(this.courseCode);
                courseIndex.setAU(this.AU);
                courseIndex.setCourseName(this.courseName);
            }
        } catch (WrongCourseCode e) {
            System.out.println(">>> Error: " + e.getMessage());
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    
    /** 
     * @return course code of this course
     */
    public String getCourseCode() {
        return courseCode;
    }
    
    /** 
     * @return school that the course belongs to
     */
    public String getSchool() {
        return school;
    }
    
    /** 
     * @return the AU of the course in integer
     */
    public int getAU() {
        return AU;
    }
    
    /** 
     * @return the name of the course
     */
    public String getCourseName() {
        return courseName;
    }
    
    /** 
     * @return a list of CourseIndex objects that belongs to this course
     */
    public ArrayList<CourseIndex> getCourseIndexs(){
        return courseIndexs;
    }
    
    
    /** 
     * set the course code of the course to the specified course code
     * @param courseCode to be updated
     */
    public void setCouseCode(String courseCode) {
        this.courseCode = courseCode;
    }
    
    /** 
     * set the school of the course to the specified school
     * @param school
     */
    public void setSchool(String school) {
        this.school = school;
    }
    
    /** 
     * set the AU in integer of the course to the specified AU
     * @param AU
     */
    public void setAU(int AU) {
        this.AU = AU;
    }
    
    /** 
     * set the course name of the course to the specified course name
     * @param courseName
     */
    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

}
