package text_manager;

import java.io.*;
import java.util.*;
import java.util.ArrayList;

import constants.FilePath;
import custom_exceptions.*;
import entity.*;

public class CourseTextMng extends TextManager {

    private static final String FILEPATH = FilePath.COURSE;
    private static final String SEPERATOR = ",";

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
        return alr; // list of CourseIdx
    }

    /** Read string from text file and return a Course object */
    public static Course returnCourse(String courseCode_) throws IOException, WrongCourseCode {
        ArrayList<String> stringArray = read(FILEPATH);
        // ArrayList<Course> alr = new ArrayList<Course>();// to store Course data
        String courseCode;

        for (int i = 1; i < stringArray.size(); i++) {
            String st = (String) stringArray.get(i);
            StringTokenizer star = new StringTokenizer(st, SEPERATOR); // pass in the string to the string
                                                                            // tokenizer // using delimiter ","
            courseCode = star.nextToken().trim(); // first token

            if (courseCode.equalsIgnoreCase(courseCode_)) {
                return new Course(courseCode);
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

        for (int i = 1; i < stringArray.size(); i++) {
            String st = (String) stringArray.get(i);
            StringTokenizer star = new StringTokenizer(st, SEPERATOR); // pass in the string to the string
                                                                            // tokenizer // using delimiter ","
            courseCode = star.nextToken().trim(); // first token

            if (courseCode.equalsIgnoreCase(courseCode_)) {
                school = star.nextToken().trim(); // second token...
                AU = star.nextToken().trim();
                // alr.add(courseCode);
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
        String HEADING = "courseCode,School,AU";
        al.add(HEADING);

        for (int i = 0; i < courses.size(); i++) {
            Course course = courses.get(i);
            StringBuilder st = new StringBuilder();
            st.append(course.getCourseCode().trim());
            st.append(SEPERATOR);
            st.append(course.getSchool().trim());
            st.append(SEPERATOR);
            st.append(course.getAU());
            al.add(st.toString());
        }
        write("../database/Course.csv", al);
    }
}
