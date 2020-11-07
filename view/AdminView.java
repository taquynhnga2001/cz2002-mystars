package view;

import java.io.IOException;
import java.util.*;

import custom_exceptions.*;
import custom_exceptions.course_existed.*;
import custom_exceptions.student_existed.*;
import entity.*;
import registration_controller.AdminController;

public class AdminView extends UserView {

    private static Scanner sc = new Scanner(System.in);

    public static void view(Admin admin) {
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
            System.out.print("Your choice: ");
            choice = sc.next();

            switch (choice) {
                case "E":
                case "S": {
                    addStudent();
                }
                case "C": {
                    addCourse();
                    break;
                }
                case "V": {
                    checkVacancy();
                    break;
                }
                case "PI":
                case "PC":
            }
        } while (!choice.equalsIgnoreCase("X"));
        sc.close();
    }

    public static void editAccessPeriod() {

    }

    public static void addStudent() {
        System.out.println("\n----- Add a Student -----");
        System.out.println("Enter Student's Information: ");
        System.out.print("\tName: ");
        String name = sc.next();
        System.out.print("\tUsername: ");
        String username = sc.next();
        System.out.println("\t<Mail is automatically created as " + username + "@e.ntu.edu.sg>");
        System.out.print("\tMatric Number: ");
        String matricNum = sc.next();
        System.out.println("\t<Password is automatically created as same as Matric Number>");
        System.out.print("\tGender [M/F]: ");
        String gender = sc.next();
        System.out.print("\tNationality: ");
        String nationality = sc.next();

        try {
            Student newStudent = AdminController.addStudent(name, username, matricNum, gender, nationality);
            if (newStudent != null)
                System.out.println("Student has been added successfully!");
            TableView.displayStudentDB(AdminController.getStudentDB(), newStudent);
        } catch (UsernameExisted e) {
            System.out.println(">>> " + e.getMessage());
        } catch (StudentExisted e) {
            System.out.println(">>> " + e.getMessage());
        } catch (MatricNumExisted e) {
            System.out.println(">>> " + e.getMessage());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (WrongInputGender e) {
            System.out.println(">>> " + e.getMessage());
        }
    }

    public static void addCourse() {
        System.out.println("\n----- Add/Update a Course -----");
        System.out.print("Add or Update [A/U]? ");
        String choice = sc.next().toUpperCase();
        switch (choice) {
            case "A": {
                System.out.println("Add a Course ");
                System.out.print("\tCourse code: ");
                String courseCode = sc.next().toUpperCase();
                sc.nextLine();
                System.out.print("\tCourse name: ");
                String courseName = sc.nextLine();
                System.out.print("\tSchool: ");
                String school = sc.next().toUpperCase();
                System.out.print("\tAU: ");
                String AU = sc.next();
                // Confirm to add the Course
                System.out.print("Confirm to add the Course " + courseCode + " " + courseName + " [Y/N]? ");
                String confirmCourse = sc.next();
                if (confirmCourse.equalsIgnoreCase("Y"))
                    try {
                        AdminController.addCourse(courseCode, courseName, school, AU);
                        // add their CourseIndex
                        String courseIndex;
                        do {
                            System.out.println("Add Index Numbers (enter 'X' to stop adding Index Number)");
                            System.out.print("\tIndex Number: ");
                            courseIndex = sc.next();
                            if (courseIndex.equalsIgnoreCase("X")) break;
                            System.out.print("\tCapacity: ");
                            String capacity = sc.next();
                            // Confirm to add the Index Number
                            System.out.print("Confirm to add the Index Number " + courseIndex + " to this course [Y/N]?");
                            String confirmIndex = sc.next();
                            if (confirmIndex.equalsIgnoreCase("Y"))
                                try {
                                    AdminController.addCourseIndex(courseIndex, courseCode, capacity);
                                    Course newCourse = null;
                                    // add their CourseIndexType
                                    String classType;
                                    do {
                                        System.out.println("Add Classses for this Index Number (enter 'X' to stop adding Classes)");
                                        System.out.print("\tClass [LEC/STUDIO or TUT or LAB]: ");
                                        classType = sc.next().toUpperCase();
                                        if (classType.equalsIgnoreCase("X")) break;
                                        System.out.print("\tGroup: ");
                                        String group = sc.next().toUpperCase();
                                        System.out.print("\tDay: ");
                                        String day = sc.next().toUpperCase();
                                        System.out.print("\tTime: ");
                                        String time = sc.next();
                                        System.out.print("\tVenue: ");
                                        String venue = sc.next().toUpperCase();
                                        sc.nextLine();
                                        System.out.print("\tRemark: ");
                                        String remark = sc.nextLine();
                                        // Confirm to add the Class
                                        System.out.print("Confirm to add the Class to this Index Number [Y/N]?");
                                        String confirmClass = sc.next();
                                        if (confirmClass.equalsIgnoreCase("Y")) {
                                            newCourse = AdminController.addCourseIndexType(courseIndex, classType, group, day, time, venue, remark);
                                        }
                                    } while (!(classType.equalsIgnoreCase("X")));
                                    // display in table
                                    TableView.displayCourseAfterAdding(AdminController.getCourseDB(), newCourse, "A");
                                } catch (CourseIndexExisted e) {
                                    System.out.println(">>> " + e.getMessage());
                                }
                        } while (!(courseIndex.equalsIgnoreCase("X")));
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (CourseExisted e) {
                        System.out.println(">>> " + e.getMessage());
                    }
                break;
            }
            case "U": {

            }
            default: System.out.println(">>> Wrong Input: Type 'A' or 'X'");
        } 

    }
    public static void addCourseIndex(CourseIndex courseIndex) {
        
    }
    /** Check vacancies available by course index */
    public static void checkVacancy() {
        String courseIndexStr;
        do {
            System.out.println("\n----- Check Vacancies Available -----");
            System.out.print("Enter Course Index that you want to check (Enter 'X' to exit): ");
            courseIndexStr = sc.next();
            if (courseIndexStr.equalsIgnoreCase("X"))
                continue;
            try {
                int[] result = AdminController.checkVacancy(courseIndexStr);
                System.out.println("Places available " + ": [" + result[0] + "/" + result[1] + "]");
                System.out.println("Length of Waitlist: " + result[2]);
            } catch (WrongCourseIndex e) {
                System.out.println(">>> " + e.getMessage());
            }
        } while (!courseIndexStr.equalsIgnoreCase("X"));
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
