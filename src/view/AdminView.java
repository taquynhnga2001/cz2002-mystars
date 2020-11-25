package src.view;

import java.io.IOException;
import java.text.ParseException;
import java.time.DateTimeException;
import java.util.*;

import src.constants.Color;
import src.custom_exceptions.*;
import src.custom_exceptions.course_existed.*;
import src.custom_exceptions.student_existed.*;
import src.entity.*;
import src.registration_controller.AdminController;
import src.text_manager.AccessPeriodTextMng;
import src.text_manager.CourseTextMng;

/**
 * Display the view to admin
 * @author Ta Quynh Nga
 */
public class AdminView {

    private static Scanner sc = new Scanner(System.in);

    /**Main view to switch to other views */
    public static void view(Admin admin) {
        String choice;
        do {
            System.out.println();
            PrintColor.println("+===========================================================+", "BLUE_BOLD");
            PrintColor.println("|                            HOME                           |", "BLUE_BOLD");
            PrintColor.println("+===========================================================+", "BLUE_BOLD");
            System.out.println("(E) Edit student access period");
            System.out.println("(S) Add a student");
            System.out.println("(C) Add/Update a course");
            System.out.println("(V) Check availble slot for a Course Index");
            System.out.println("(PI) Print student list by Course Index");
            System.out.println("(PC) Print student list by Course Code");
            System.out.println("(X) Logout");
            System.out.print("Your choice: ");
            choice = sc.next().toUpperCase();

            switch (choice) {
                case "E": {
                    editAccessPeriod();
                    break;
                }
                case "S": {
                    addStudent();
                    break;
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

    /**Edit student access period to the system */
    public static void editAccessPeriod() {
        System.out.println();
        PrintColor.println("+-----------------------------------------------------------+", "BLUE_BOLD");
        PrintColor.println("|                  Edit Student Access Period               |", "BLUE_BOLD");
        PrintColor.println("+-----------------------------------------------------------+", "BLUE_BOLD");
        Date date = AccessPeriodTextMng.getAccessPeriod();
        System.out.println("Old Access Period: " + AccessPeriodTextMng.toString(date));
        System.out.println("New Access Period: ");
        System.out.print("\tDate (dd-MM-yyyy): ");
        String dateStr = sc.next();
        System.out.print("\tTime (HH:mm:ss): ");
        String timeStr = sc.next();
        PrintColor.print("Confirm to edit the Access Period to " + dateStr + " " + timeStr + " [Y/N]? ", "YELLOW");
        String choice = sc.next().toUpperCase();
        if (choice.equals("Y")) {
            try {
                AdminController.editAccessPeriod(dateStr, timeStr);
                PrintColor.println("Edited Student Access Period successfully! ", "GREEN");
            } catch (ParseException | DateTimeException e) {
                PrintColor.println(">>> Wrong Input: Typed in wrong format for date and time", "RED");
            }
        }
    }

    /** Add a new student */
    public static void addStudent() {
        System.out.println();
        PrintColor.println("+-----------------------------------------------------------+", "BLUE_BOLD");        
        PrintColor.println("|                        Add a Student                      |", "BLUE_BOLD");
        PrintColor.println("+-----------------------------------------------------------+", "BLUE_BOLD");
        System.out.println("Enter Student's Information: ");
        System.out.print("\tName: ");
        sc.nextLine();
        String name = sc.nextLine();
        System.out.print("\tUsername: ");
        String username = sc.next();
        System.out.println("\t<Mail is automatically created as " + username + "@e.ntu.edu.sg>");
        System.out.print("\tMatric Number: ");
        String matricNum = sc.next();
        System.out.println("\t<Password is automatically created as same as Matric Number>");
        System.out.print("\tGender [M/F]: ");
        String gender = sc.next().toUpperCase();
        System.out.print("\tNationality: ");
        String nationality = sc.next();
        PrintColor.print("Confirm to add this Student [Y/N]? ", "YELLOW");
        String choice = sc.next().toUpperCase();

        if (choice.equals("Y")) {
            try {
                Student newStudent = AdminController.addStudent(name, username, matricNum, gender, nationality);
                if (newStudent != null)
                PrintColor.println("Student has been added successfully!", "GREEN");
                TableView.displayStudentDB(AdminController.getStudentDB(), newStudent);
            } catch (UsernameExisted e) {
                PrintColor.println(">>> " + e.getMessage(), "RED");
            } catch (StudentExisted e) {
                PrintColor.println(">>> " + e.getMessage(), "RED");
            } catch (MatricNumExisted e) {
                PrintColor.println(">>> " + e.getMessage(), "RED");
            } catch (IOException e) {
                System.out.println(Color.RED);
                e.printStackTrace();
                System.out.println(Color.RESET);
            } catch (WrongInputGender e) {
                PrintColor.println(">>> Invalid data entries: " + e.getMessage(), "RED");
            }
        }
    }

    /**Add or update a course */
    public static void addCourse() {
        System.out.println();
        PrintColor.println("+-----------------------------------------------------------+", "BLUE_BOLD");
        PrintColor.println("|                    Add/Update a Course                    |", "BLUE_BOLD");
        PrintColor.println("+-----------------------------------------------------------+", "BLUE_BOLD");
        System.out.print("Add or Update [A/U]? ");
        String choice = sc.next().toUpperCase();
        switch (choice) {
            // adding a new course
            case "A": {
                PrintColor.println("=============== Add a Course ===============", "CYAN_BOLD");
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
                PrintColor.print("Confirm to add the Course " + courseCode + " " + courseName + " [Y/N]? ", "YELLOW");
                String confirmCourse = sc.next();
                if (confirmCourse.equalsIgnoreCase("Y"))
                    try {
                        AdminController.addCourse(courseCode, courseName, school, AU);
                        // add their CourseIndex
                        String courseIndex;
                        do {
                            PrintColor.println("-------- Add Index Numbers --------", "CYAN_BOLD");
                            System.out.println("(enter 'X' to stop adding Index Number)");
                            System.out.print("\tIndex Number: ");
                            courseIndex = sc.next();
                            if (courseIndex.equalsIgnoreCase("X"))
                                break;
                            System.out.print("\tCapacity: ");
                            String capacity = sc.next();
                            // Confirm to add the Index Number
                            PrintColor.print("Confirm to add the Index Number " + courseIndex + " to this course [Y/N]?", "YELLOW");
                            String confirmIndex = sc.next();
                            if (confirmIndex.equalsIgnoreCase("Y"))
                                try {
                                    AdminController.addCourseIndex(courseIndex, courseCode, capacity);
                                    Course newCourse = null;
                                    // add their CourseIndexType
                                    String classType;
                                    do {
                                        PrintColor.println("-------- Add Classses for this Index Number --------", "CYAN_BOLD");
                                        System.out.println("(enter 'X' to stop adding Classes)");
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
                                        PrintColor.print("Confirm to add the Class to this Index Number [Y/N]?", "YELLOW");
                                        String confirmClass = sc.next();
                                        if (confirmClass.equalsIgnoreCase("Y")) {
                                            newCourse = AdminController.addCourseIndexType(courseIndex, classType,
                                                    group, day, time, venue, remark);
                                        }
                                    } while (!(classType.equalsIgnoreCase("X")));
                                    // display in table
                                    TableView.displayCourseAfterAdding(AdminController.getCourseDB(), newCourse, "A");
                                } catch (CourseIndexExisted e) {
                                    PrintColor.println(">>> " + e.getMessage(), "RED");
                                }
                        } while (!(courseIndex.equalsIgnoreCase("X")));
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (CourseExisted e) {
                        PrintColor.println(">>> " + e.getMessage(), "RED");
                    } catch (NumberFormatException e) {
                        PrintColor.println(">>> Invalid data entries: AU must be a positive integer number.", "RED");
                    }
                break;
            }
            // updating an existing course
            case "U": {
                PrintColor.println("=============== Update a Course ===============", "CYAN_BOLD");
                System.out.print("\tCourse code: ");
                String courseCode = sc.next().toUpperCase();
                try {
                    Course updateCourse = CourseTextMng.getCourse(AdminController.getCourseDB(), courseCode);
                    TableView.displayCourseB4Updating(updateCourse);
                    // update general info
                    PrintColor.println("-------- Update General Course Information ----------", "CYAN");
                    System.out.print("Enter Attributes of the Course to update (code/name/school/AU) (Enter 'X' if no update on Couse): ");
                    sc.nextLine();
                    String[] attributes = sc.nextLine().toLowerCase().trim().split(" ");
                    if (!attributes[0].equalsIgnoreCase("X")) AdminController.updateCourse(updateCourse, attributes);

                    // update index info
                    PrintColor.println("--------- Update Course Index Information --------", "CYAN");
                    System.out.print("Enter Index Numbers of the Course to update (Enter 'X' if no update on Couse Index): ");
                    String[] indexs = sc.nextLine().toLowerCase().trim().split(" ");
                    if (!indexs[0].equalsIgnoreCase("X"))
                        for (String index : indexs) {
                            try {
                                CourseIndex updateIndex = CourseTextMng.getCourseIndex(AdminController.getCourseDB(), index);
                                if (!updateCourse.getCourseIndexs().contains(updateIndex)) {
                                    PrintColor.println(">>> The Index Number " + index + " does not belong to this Course", "RED");
                                    continue;
                                }
                                System.out.print("Enter Attributes of the Index to update (index/capacity): ");
                                String att = sc.nextLine();
                                String[] attributes1 = att.trim().toLowerCase().split(" ");
                                AdminController.updateCourseIndex(updateIndex, attributes1);
                            } catch (WrongCourseIndex e) {
                                PrintColor.println(">>> " + e.getMessage(), "RED");
                            }
                        }
                    // update class info
                    // too tired, not done yet (T_T)
                    TableView.displayCourseAfterAdding(AdminController.getCourseDB(), updateCourse, "U");
                } catch (WrongCourseCode e) {
                    PrintColor.println(">>> " + e.getMessage(), "RED");
                } catch (IOException e) {
                    e.printStackTrace();
                }
                sc.nextLine();
                break;
            }
            default: PrintColor.println(">>> Wrong Input: Type 'A' or 'U'", "RED");
        } 

    }

    /** Check vacancies available by course index */
    public static void checkVacancy() {
        String courseIndexStr;
        do {
            System.out.println();
            PrintColor.println("+-----------------------------------------------------------+", "BLUE_BOLD");
            PrintColor.println("|                 Check Vacancies Available                 |", "BLUE_BOLD");
            PrintColor.println("+-----------------------------------------------------------+", "BLUE_BOLD");
            System.out.print("Enter Course Index that you want to check (Enter 'X' to exit): ");
            courseIndexStr = sc.next();
            if (courseIndexStr.equalsIgnoreCase("X"))
                continue;
            try {
                int[] result = AdminController.checkVacancy(courseIndexStr);
                System.out.println("Places available " + ": [" + result[0] + "/" + result[1] + "]");
                System.out.println("Length of Waitlist: " + result[2]);
            } catch (WrongCourseIndex e) {
                PrintColor.println(">>> " + e.getMessage(), "RED");
            }
        } while (!courseIndexStr.equalsIgnoreCase("X"));
    }
    
    /**Print students by course index in ascending order */
    public static void printStudentByIndex() {
        String courseIndexStr;
        do {
            System.out.println();
            PrintColor.println("+-----------------------------------------------------------+", "BLUE_BOLD");
            PrintColor.println("|             Print student list by Course Index            |", "BLUE_BOLD");
            PrintColor.println("+-----------------------------------------------------------+", "BLUE_BOLD");
            System.out.print("Enter Course Index (Enter 'X' to exit): ");
            courseIndexStr = sc.next().toUpperCase();
            if (courseIndexStr.equalsIgnoreCase("X"))
                continue;
            try {
                AdminController.printStudentByIndex(courseIndexStr);
            } catch (WrongCourseIndex e) {
                PrintColor.println(">>> " + e.getMessage(), "RED");
            }
        } while (!courseIndexStr.equalsIgnoreCase("X"));
    }

    /**Print students by course in ascending order */
    public static void printStudentByCourse() {
        String course;
        do {
            System.out.println();
            PrintColor.println("+-----------------------------------------------------------+", "BLUE_BOLD");
            PrintColor.println("|                Print student list by Course               |", "BLUE_BOLD");
            PrintColor.println("+-----------------------------------------------------------+", "BLUE_BOLD");
            System.out.print("Enter Course Code (Enter 'X' to exit): ");
            course = sc.next().toUpperCase();
            if (course.equalsIgnoreCase("X"))
                continue;
            try {
                AdminController.printStudentByCourse(course);
            } catch (WrongCourseCode e) {
                PrintColor.println(">>> " + e.getMessage(), "RED");
            } catch (WrongCourseIndex e) {
                e.printStackTrace();
            }
        } while (!course.equalsIgnoreCase("X"));
    }
}
