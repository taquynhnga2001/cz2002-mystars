package view;

import java.io.IOException;
import java.util.*;

import custom_exceptions.*;
import entity.*;
import text_manager.*;
import registration_controller.*;

public class StudentView extends UserView {

    // private static ArrayList<Course> courseDB;
    // private static ArrayList<CourseIndex> courseEnrolled;
    // private static ArrayList<CourseIndex> courseWaitlist;
    private static Scanner sc = new Scanner(System.in);
    
    public static void view(Student student) {
        // retrieve DataTime now, if is in access period then continue, otherwise notify student access period

        // load database for courses
        // courseDB = loadCourses();
        // courseEnrolled = student.getCourseEnrolled(courseDB);
        // courseWaitlist = student.getCourseWaitlist(courseDB);

        // Scanner sc = new Scanner(System.in);
        String choice;
        do {
            System.out.println("\n========== HOME ==========\n");
            System.out.println("(A) Add Course");
            System.out.println("(D) Drop Course");
            System.out.println("(R) Check/Print Courses Registered");
            System.out.println("(V) Check Vacancies Available");
            System.out.println("(I) Change Index Number of Course");
            System.out.println("(S) Swop Index Number with Another Student");
            System.out.println("(N) Read notification");
            System.out.println("(X) Exit");
            System.out.print("Your choice: ");
            choice = sc.next().toUpperCase();

            switch (choice) {
                case "A": {
                    addCourse(student);
                    break;
                }
                case "D":
                case "R":
                case "V":
                case "I":
                case "S":
            }
        } while (!choice.equalsIgnoreCase("X"));
        // sc.close();
    }

    public static void addCourse(Student student) {
        System.out.println("\n----- Add Course -----");
        System.out.print("Enter Course Index that you want to add: ");
        String courseIndex = sc.next();
        try {
            // CourseIndex courseIndex = CourseTextMng.getCourseIndex(courseDB, courseIndex_);
            StudentController.addCourse(student, courseIndex);
            System.out.println("Add course successfully!");
            
        } catch (WrongCourseIndex e) {
            System.out.println(">>> Error! " + e.getMessage());
        } catch (AlreadyEnrolled e) {
            System.out.println(">>> " + e.getMessage());
        } catch (AlreadyInWaitlist e) {
            System.out.println(">>> " + e.getMessage());
        } catch (ClashTime e) {
            System.out.println(">>> " + e.getMessage());
        } catch (NoVacancy e) {
			System.out.println(">>> " + e.getMessage() + " You are added in waitlist of this couse index.");
		} catch (MaximumAURegistered e) {
            System.out.println(">>> " + e.getMessage());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void dropCourse(Student student) {

    }
    public static void courseRegistered(Student student) {

    }
    public static void checkVacancy(CourseIndex courseIndex) {
        
    }
    public static void changeCourseIndex(CourseIndex courseIndex) {

    }
    public static void swopCourseIndex(CourseIndex courseIndex, Student student) {

    }

    public static void printCouses() {
        System.out.println("Course\tIndex\tGroup\tDay\tTime\tVenue\tRemark");
        try {
            ArrayList<Course> courses = CourseTextMng.readFile();
            for (int i = 0; i < courses.size(); i++) {
                ArrayList<CourseIndex> courseIndexs = courses.get(i).getCourseIndexs();
                for (int j=0; j<courseIndexs.size(); j++) {
                    ArrayList<CourseIndexType> classTypes = courseIndexs.get(j).getClassTypes();
                    for (int k=0; k<classTypes.size(); k++) {
                        CourseIndexType classType = classTypes.get(k);
                        System.out.print(classType.getCourseCode());
                        System.out.print("\t" + classType.getIndex());
                        System.out.print("\t" + classType.getGroup());
                        System.out.print("\t" + classType.getDay());
                        System.out.print("\t" + classType.getTime());
                        System.out.print("\t\t" + classType.getVenue());
                        System.out.print("\t" + classType.getRemark() + "\n");
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } 
    }
}
