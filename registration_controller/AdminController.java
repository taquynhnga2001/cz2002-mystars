package registration_controller;

import entity.*;

import java.io.IOException;
import java.util.ArrayList;

import authentication.Auth;
import custom_exceptions.*;
import custom_exceptions.course_existed.*;
import custom_exceptions.student_existed.*;
import text_manager.*;
import view.*;

public class AdminController {
    private static ArrayList<Course> courseDB = new ArrayList<>();
    private static ArrayList<Student> studentDB = new ArrayList<>();

    /** Add a new student */
    public static Student addStudent(String name, String username, String matricNum, String gender, String nationality)
            throws UsernameExisted, StudentExisted, MatricNumExisted, IOException, WrongInputGender {
        // check if student existed
        for (Student oldStudent : studentDB) {
            if (oldStudent.getUsername().equalsIgnoreCase(username)) {
                if (oldStudent.getPassword().equalsIgnoreCase(Auth.getHash(matricNum)))
                    throw new StudentExisted();
                else
                    throw new UsernameExisted(username);
            }
            if (oldStudent.getMatricNum().equalsIgnoreCase(matricNum))
                throw new MatricNumExisted(matricNum);
        }
        // save to database
        if (!gender.equalsIgnoreCase("M") && !(gender.equalsIgnoreCase("F")))
            throw new WrongInputGender();
        String mail = username + "@e.ntu.edu.sg";
        String password = Auth.getHash(matricNum);
        StudentTextMng.addStudent(name, username, mail, password, matricNum, gender, nationality);
        // add the new student to the ArrayList studentDB
        Student newStudent = new Student(username, password);
        studentDB.add(newStudent);
        return newStudent;
    }

    /** Add a new course. After adding a course, add their CourseIndexs */
    public static void addCourse(String courseCode, String courseName, String school, String AU)
            throws IOException, CourseExisted {
        // add to database
        CourseTextMng.addCourse(courseCode, courseName, school, AU);
    }

    /**
     * Add a new course index. After adding a course index, add their class types
     */
    public static void addCourseIndex(String courseIndex, String courseCode, String capacity)
            throws IOException, CourseIndexExisted {
        // add to database
        CourseIndexTextMng.addCourseIndex(courseIndex, courseCode, capacity);
    }

    /** Add a new course index type and return Course object */
    public static Course addCourseIndexType(String courseIndex, String classType, String group, String day, String time,
            String venue, String remark) throws IOException, CourseIndexExisted {
        // add to database
        CourseIndexTypeTextMng.addCourseIndexType(courseIndex, classType, group, day, time, venue, remark);
        // create new courseDB ArrayList from database
        loadCourseDB();
        try {
            CourseIndex newCourseIdx = CourseTextMng.getCourseIndex(courseDB, courseIndex);
            for (Course course : getCourseDB()) {
                if (newCourseIdx.getCourseCode().equalsIgnoreCase(course.getCourseCode())) return course;
            }
        } catch (WrongCourseIndex e) {
            e.printStackTrace();
        }
        return null;
    }


    /** Return int[3] array includes {vacancy, capacity, lenth of waitlist} */
    public static int[] checkVacancy(String courseIndexStr) throws WrongCourseIndex {
        // load the courseDB
        ArrayList<Course> courseDB = getCourseDB();
        // get the chosen courseIndex is in database
        CourseIndex courseIndex = CourseTextMng.getCourseIndex(courseDB, courseIndexStr);
        TableView.displayCourseInfo(courseIndex);
        int vacancy = courseIndex.getVacancy();
        int capacity = courseIndex.getCapacity();
        int[] result = new int[3];
        result[0] = vacancy;
        result[1] = capacity;
        // calculate length of waitlist
        int counter = 0;
        try {
            ArrayList<String[]> waitlist = WaitlistTextMng.readFile();
            for (String[] row : waitlist) {
                if (row[1].equalsIgnoreCase(courseIndexStr)) counter++;
            }
            result[2] = counter;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }


    /**Load from database to create objects of courses */
    public static void loadCourseDB() {
        try {
            courseDB = CourseTextMng.readFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /**Return reference to the courseDB */
    public static ArrayList<Course> getCourseDB() {
        return courseDB;
    }
    /**Load from database to create objects of students */
    public static void loadStudentDB() {
        try {
            studentDB = StudentTextMng.readFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /**Return reference to the studentDB */
    public static ArrayList<Student> getStudentDB() {
        return studentDB;
    }
}
