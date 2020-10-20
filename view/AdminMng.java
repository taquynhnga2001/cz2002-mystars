package view;

import java.util.*;
import entity.*;

public class AdminMng extends UserMng {
    public static void view(Admin admin) {
        Scanner sc = new Scanner(System.in);
        String choice;
        do {
            System.out.println("\n========== HOME ==========\n");
            System.out.println("(E) Edit student access period");
            System.out.println("(S) Add a student");
            System.out.println("(C) Add/Update a course");
            System.out.println("(V) Check availble slot for a Course Index");
            System.out.println("(PI) Print student list by Course Index");
            System.out.println("(PC) Print student list by Course Code");
            System.out.println("(X) Exit");
            choice = sc.next();

            switch (choice) {
                case "E":
                case "S":
                case "C":
                case "V":
                case "PI":
                case "PC":
            }
        } while (!choice.equalsIgnoreCase("X"));
        sc.close();
    }

    public static void editAccessPeriod() {

    }
    public static void addStudent(Student student) {

    }
    public static void addCourse(Course course) {

    }
    public static void addCourseIndex(CourseIndex courseIndex) {
        
    }
    public static void printStudentByIndex(CourseIndex courseIndex) {

    }
    public static void printStudentByCourse(Course course) {

    }

    public static Map<String, List<Object>> loadDatabase() {
        Map<String, List<Object>> database = new HashMap<>();
        return database;
    }

}
