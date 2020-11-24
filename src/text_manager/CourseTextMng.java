package text_manager;

import java.io.*;
import java.util.*;
import java.util.ArrayList;

import constants.FilePath;
import custom_exceptions.*;
import custom_exceptions.course_existed.*;
import entity.*;

public class CourseTextMng extends TextManager {

    private static final String FILEPATH = FilePath.COURSE;
    // private static final String SEPERATOR = ",";

    /** Read String from text file and return list of Course objects */
    public static ArrayList<Course> readFile() throws IOException {
        ArrayList<String> stringArray = read(FILEPATH);
        ArrayList<Course> alr = new ArrayList<Course>(); // to store Course data

        for (int i = 1; i < stringArray.size(); i++) {
            String st = (String) stringArray.get(i);
            StringTokenizer star = new StringTokenizer(st, SEPERATOR); // pass in the string to the string
                                                                            // tokenizer // using delimiter ","
            String courseCode = star.nextToken().trim(); // first token: courseCode
            Course course = new Course(courseCode);
            alr.add(course);
        }
        return alr; // list of Course
    }

    /** Read string from text file and return a Course object */
    public static Course returnCourse(String courseCode) throws IOException, WrongCourseCode {
        ArrayList<String> stringArray = read(FILEPATH);
        // ArrayList<Course> alr = new ArrayList<Course>();// to store Course data
        String courseCode_;

        for (int i = 1; i < stringArray.size(); i++) {
            String st = (String) stringArray.get(i);
            StringTokenizer star = new StringTokenizer(st, SEPERATOR); // pass in the string to the string
                                                                            // tokenizer // using delimiter ","
            courseCode_ = star.nextToken().trim(); // first token

            if (courseCode_.equalsIgnoreCase(courseCode)) {
                return new Course(courseCode_);
            }
        }
        throw new WrongCourseCode();
    }

    /** Read String from text file and return Course attributes */
    public static ArrayList<String> readCourse(String courseCode_) throws IOException, WrongCourseCode {
        ArrayList<String> stringArray = read(FILEPATH);
        ArrayList<String> alr = new ArrayList<String>();// to store Course's attributes
        String courseCode;
        String school;
        String AU;
        String courseName;

        for (int i = 1; i < stringArray.size(); i++) {
            String st = (String) stringArray.get(i);
            StringTokenizer star = new StringTokenizer(st, SEPERATOR); // pass in the string to the string
                                                                            // tokenizer // using delimiter ","
            courseCode = star.nextToken().trim(); // first token

            if (courseCode.equalsIgnoreCase(courseCode_)) {
                courseName = star.nextToken().trim();
                school = star.nextToken().trim(); // second token...
                AU = star.nextToken().trim();
                alr.add(courseName);
                alr.add(school);
                alr.add(AU);
                return alr;
            }
        }
        throw new WrongCourseCode();
    }

    /** Save courses in database after adding or updating */
    public static void saveCourses(List<Course> courses) throws IOException {
        List<String> al = new ArrayList<String>(); // to store Course data
        String HEADING = "courseCode,courseName,School,AU";
        al.add(HEADING);
        for (int i = 0; i < courses.size(); i++) {
            Course course = courses.get(i);
            StringBuilder st = new StringBuilder();
            st.append(course.getCourseCode().trim());
            st.append(SEPERATOR);
            st.append(course.getCourseName());
            st.append(SEPERATOR);
            st.append(course.getSchool().trim());
            st.append(SEPERATOR);
            st.append(course.getAU());
            al.add(st.toString());
        }
        write(FILEPATH, al);
    }

    /**Save Course in database after adding */
    public static void addCourse(String courseCode, String courseName, String school, String AU)
            throws IOException, CourseExisted {
        ArrayList<String> stringArray = read(FILEPATH); // to store the old database

        for (int i = 1; i < stringArray.size(); i++) {
            String st = (String) stringArray.get(i);
            StringTokenizer star = new StringTokenizer(st, SEPERATOR); // pass in the string to the string
                                                                       // tokenizer using delimiter ","
            String courseCode_ = star.nextToken().trim(); // first token: courseCode
            if (courseCode_.equalsIgnoreCase(courseCode)) {
                throw new CourseExisted(courseCode);
            }
        }
        // if courseCode is new, add to enrolled
        StringBuilder st = new StringBuilder();
        st.append(courseCode).append(SEPERATOR);
        st.append(courseName).append(SEPERATOR);
        st.append(school).append(SEPERATOR);
        st.append(AU);
        stringArray.add(st.toString());
        write(FILEPATH, stringArray);
    }

    /**Return a CourseIndex object in the CourseDB */
    public static CourseIndex getCourseIndex(ArrayList<Course> courseDB, String courseIndex) throws WrongCourseIndex {

        for (int i=0; i<courseDB.size(); i++) {
            Course course = courseDB.get(i);
            ArrayList<CourseIndex> courseIndexs = course.getCourseIndexs();
            for (int j=0; j<courseIndexs.size(); j++) {
                CourseIndex courseIndex_ = courseIndexs.get(j);
                if (courseIndex_.getIndex().equals(courseIndex)) {
                    return courseIndex_;
                }
            }
        }
        throw new WrongCourseIndex();
    }

    public static Course getCourse(ArrayList<Course> courseDB, String courseCode)
            throws custom_exceptions.WrongCourseCode {
        for (int i=0; i<courseDB.size(); i++) {
            Course course = courseDB.get(i);
            if (course.getCourseCode().equals(courseCode)) return course;
        }
        throw new WrongCourseCode();
    }
}
