package view;

import java.io.IOException;
import java.util.*;

import custom_exceptions.*;
import custom_exceptions.course_existed.*;
import custom_exceptions.student_existed.*;
import entity.*;
import registration_controller.AdminController;
import text_manager.AccessPeriodTextMng;
import text_manager.CourseTextMng;

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
            choice = sc.next().toUpperCase();

            switch (choice) {
                case "E": {
                    editAccessPeriod();
                    break;
                }
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
                case "PI": {
                    printStudentByIndex();
                    break;
                }
                case "PC": {
                    printStudentByCourse();
                    break;
                }
            }
        } while (!choice.equalsIgnoreCase("X"));
        sc.close();
    }

    public static void editAccessPeriod() {
        System.out.println("\n----- Edit Student Access Period -----");
        Date date = AccessPeriodTextMng.getAccessPeriod();
        System.out.println("Old Access Period: " + date.toString());
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
            // adding a new course
            case "A": {
                System.out.println("----- Add a Course -----");
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
                            if (courseIndex.equalsIgnoreCase("X"))
                                break;
                            System.out.print("\tCapacity: ");
                            String capacity = sc.next();
                            // Confirm to add the Index Number
                            System.out
                                    .print("Confirm to add the Index Number " + courseIndex + " to this course [Y/N]?");
                            String confirmIndex = sc.next();
                            if (confirmIndex.equalsIgnoreCase("Y"))
                                try {
                                    AdminController.addCourseIndex(courseIndex, courseCode, capacity);
                                    Course newCourse = null;
                                    // add their CourseIndexType
                                    String classType;
                                    do {
                                        System.out.println(
                                                "Add Classses for this Index Number (enter 'X' to stop adding Classes)");
                                        System.out.print("\tClass [LEC/STUDIO or TUT or LAB]: ");
                                        classType = sc.next().toUpperCase();
                                        if (classType.equalsIgnoreCase("X"))
                                            break;
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
                                            newCourse = AdminController.addCourseIndexType(courseIndex, classType,
                                                    group, day, time, venue, remark);
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
            // updating an existing course
            case "U": {
                System.out.println("----- Update a Course -----");
                System.out.print("\tCourse code: ");
                String courseCode = sc.next().toUpperCase();
                try {
                    Course updateCourse = CourseTextMng.getCourse(AdminController.getCourseDB(), courseCode);
                    TableView.displayCourseB4Updating(updateCourse);
                    // update general info
                    System.out.println("-- Update General Course Information --");
                    System.out.print("Enter Attributes of the Course to update (code/name/school/AU) (Press 'enter' if no update on Couse): ");
                    sc.nextLine();
                    String[] attributes = sc.nextLine().toLowerCase().trim().split(" ");
                    AdminController.updateCourse(updateCourse, attributes);

                    // update index info
                    System.out.println("-- Update Course Index Information --");
                    System.out.print("Enter Index Numbers of the Course to update (Press 'enter' if no update on Couse Index): ");
                    String[] indexs = sc.nextLine().toLowerCase().trim().split(" ");
                    for (String index : indexs) {
                        try {
                            CourseIndex updateIndex = CourseTextMng.getCourseIndex(AdminController.getCourseDB(), index);
                            if (!updateCourse.getCourseIndexs().contains(updateIndex)) {
                                System.out.println(">>> The Index Number " + index + " does not belong to this Course");
                                continue;
                            }
                            System.out.print("Enter Attributes of the Index to update (index/capacity): ");
                            String att = sc.nextLine();
                            String[] attributes1 = att.trim().toLowerCase().split(" ");
                            AdminController.updateCourseIndex(updateIndex, attributes1);
                        } catch (WrongCourseIndex e) {
                            System.out.println(">>> " + e.getMessage());
                        }
                    }
                    // update class info
                    // too tired, not done yet (T_T)
                    TableView.displayCourseAfterAdding(AdminController.getCourseDB(), updateCourse, "U");
                } catch (WrongCourseCode e) {
                    System.out.println(">>> " + e.getMessage());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                sc.nextLine();
                break;
            }
            default: System.out.println(">>> Wrong Input: Type 'A' or 'U'");
        } 

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
    
    /**Print students by course index in ascending order */
    public static void printStudentByIndex() {
        String courseIndexStr;
        do {
            System.out.println("\n----- Print student list by Course Index -----");
            System.out.print("Enter Course Index (Enter 'X' to exit): ");
            courseIndexStr = sc.next().toUpperCase();
            if (courseIndexStr.equalsIgnoreCase("X"))
                continue;
            try {
                AdminController.printStudentByIndex(courseIndexStr);
            } catch (WrongCourseIndex e) {
                System.out.println(">>> " + e.getMessage());
            }
        } while (!courseIndexStr.equalsIgnoreCase("X"));
    }

    /**Print students by course in ascending order */
    public static void printStudentByCourse() {
        String course;
        do {
            System.out.println("\n----- Print student list by Course -----");
            System.out.print("Enter Course Code (Enter 'X' to exit): ");
            course = sc.next().toUpperCase();
            if (course.equalsIgnoreCase("X"))
                continue;
            try {
                AdminController.printStudentByCourse(course);
            } catch (WrongCourseCode e) {
                System.out.println(">>> " + e.getMessage());
            } catch (WrongCourseIndex e) {
                e.printStackTrace();
            }
        } while (!course.equalsIgnoreCase("X"));
    }

    public static Map<String, List<Object>> loadDatabase() {
        Map<String, List<Object>> database = new HashMap<>();
        return database;
    }

}
