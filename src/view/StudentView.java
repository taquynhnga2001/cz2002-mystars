package view;

import java.io.IOException;
import java.util.*;

import authentication.Auth;
import authentication.PasswordField;
import custom_exceptions.*;
import entity.*;
import text_manager.*;
import registration_controller.*;

public class StudentView {

    private static Scanner sc = new Scanner(System.in);

    
    /** 
     * @param student
     */
    public static void view(Student student) {
        String choice;
        do {
            System.out.println();
            PrintColor.println("+===========================================================+", "BLUE_BOLD");
            PrintColor.println("|                            HOME                           |", "BLUE_BOLD");
            PrintColor.println("+===========================================================+", "BLUE_BOLD");
            System.out.println("(A) Add Course");
            System.out.println("(D) Drop Course");
            System.out.println("(R) Check/Print Courses Registered");
            System.out.println("(V) Check Vacancies Available");
            System.out.println("(I) Change Index Number of Course");
            System.out.println("(S) Swop Index Number with Another Student");
            System.out.println("(P) Course Planner Generator");
            System.out.println("(X) Logout");
            System.out.print("Your choice: ");
            choice = sc.next().toUpperCase();

            switch (choice) {
                case "A": {
                    addCourse(student);
                    break;
                }
                case "D": {
                    dropCourse(student);
                    break;
                }
                case "R": {
                    courseRegistered(student);
                    break;
                }
                case "V": {
                    checkVacancy();
                    break;
                }
                case "I": {
                    changeCourseIndex(student);
                    break;
                }
                case "S": {
                    swopCourseIndex(student);
                    break;
                }
                case "P": {
                    planCourses();
                    break;
                }
            }
        } while (!choice.equalsIgnoreCase("X"));
    }

    /** Add course view for student */
    public static void addCourse(Student student) {
        System.out.println();
        PrintColor.println("+-----------------------------------------------------------+", "BLUE_BOLD");
        PrintColor.println("|                         Add Course                        |", "BLUE_BOLD");
        PrintColor.println("+-----------------------------------------------------------+", "BLUE_BOLD");
        System.out.print("Enter Course Index that you want to add: ");
        String courseIndexStr = sc.next();
        try {
            StudentController.addCourse(student, courseIndexStr);
            System.out.println("Registered AUs: " + student.getRegisteredAU());

        } catch (WrongCourseIndex e) {
            PrintColor.println(">>> Error! " + e.getMessage(), "RED");
        } catch (AlreadyEnrolled e) {
            PrintColor.println(">>> " + e.getMessage(), "RED");
        } catch (AlreadyInWaitlist e) {
            PrintColor.println(">>> " + e.getMessage(), "RED");
        } catch (ClashTime e) {
            PrintColor.println(">>> " + e.getMessage() + ": " + StudentController.getClashedCourse(), "RED");
        } catch (NoVacancy e) {
            PrintColor.println(">>> " + e.getMessage() + " You are added in waitlist of this couse index.", "RED");
        } catch (MaximumAURegistered e) {
            PrintColor.println(">>> " + e.getMessage(), "RED");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /** Drop course view for student */
    public static void dropCourse(Student student) {
        System.out.println();
        PrintColor.println("+-----------------------------------------------------------+", "BLUE_BOLD");
        PrintColor.println("|                        Drop Course                        |", "BLUE_BOLD");
        PrintColor.println("+-----------------------------------------------------------+", "BLUE_BOLD");
        // display registered courses
        System.out.println("Courses Registered:");
        ArrayList<CourseIndex>[] courseIndexs = StudentController.getCourseRegistered(student);
        ArrayList<CourseIndex> courseEnrolled = courseIndexs[0];
        ArrayList<CourseIndex> courseWaitlist = courseIndexs[1];
        for (CourseIndex courseIndex : courseEnrolled) {
            String courseCode = courseIndex.getCourseCode();
            String index = courseIndex.getIndex();
            String status = "REGISTERED";
            System.out.println("\t" + index + " " + courseCode + " " + status);
        }
        for (CourseIndex courseIndex : courseWaitlist) {
            String courseCode = courseIndex.getCourseCode();
            String index = courseIndex.getIndex();
            String status = "ON WAIT";
            System.out.println("\t" + index + " " + courseCode + " " + status);
        }
        // enter course index to drop
        System.out.print("Enter Course Index that you want to drop: ");
        String courseIndexStr = sc.next();

        try {
            StudentController.dropCourse(student, courseIndexStr);
            System.out.println("Registered AUs: " + student.getRegisteredAU());
        } catch (WrongCourseIndex e) {
            PrintColor.println(">>> Error! " + e.getMessage(), "RED");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (DidntEnrollOrWait e) {
            PrintColor.println(">>> " + e.getMessage(), "RED");
        }
    }

    /** Check/Print courses registered (enrolled and on waitlist) of a student */
    public static void courseRegistered(Student student) {
        System.out.println();
        PrintColor.println("+-----------------------------------------------------------+", "BLUE_BOLD");        
        PrintColor.println("|               Check/Print Courses Registered              |", "BLUE_BOLD");
        PrintColor.println("+-----------------------------------------------------------+", "BLUE_BOLD");        
        ArrayList<CourseIndex>[] courseIndexs = StudentController.getCourseRegistered(student);
        ArrayList<CourseIndex> courseEnrolled = courseIndexs[0];
        ArrayList<CourseIndex> courseWaitlist = courseIndexs[1];
        String[] columnHeadings = { "Course", "AU", "Index Number", "Status", "Class Type", "Group", "Day", "Time",
                "Venue", "Remark" };
        ArrayList<Object[]> data = new ArrayList<>();
        // display registered courses
        for (CourseIndex courseIndex : courseEnrolled) {
            String courseCode = courseIndex.getCourseCode();
            int AU = courseIndex.getAU();
            String index = courseIndex.getIndex();
            String status = "REGISTERED";
            ArrayList<CourseIndexType> classTypes = courseIndex.getClassTypes();
            Iterator<CourseIndexType> type = classTypes.iterator();

            CourseIndexType firstClass = type.next();
            Object[] row = { courseCode, AU, index, status, firstClass.getClassType(), firstClass.getGroup(),
                    firstClass.getDay(), firstClass.getTime(), firstClass.getVenue(), firstClass.getRemark() };
            data.add(row);

            while (type.hasNext()) {
                CourseIndexType t = type.next();
                Object[] nextRow = { "", "", "", "", t.getClassType(), t.getGroup(), t.getDay(), t.getTime(),
                        t.getVenue(), t.getRemark() };
                data.add(nextRow);
            }
        }
        // display waitlist courses
        for (CourseIndex courseIndex : courseWaitlist) {
            String courseCode = courseIndex.getCourseCode();
            int AU = courseIndex.getAU();
            String index = courseIndex.getIndex();
            String status = "WAITLIST";
            ArrayList<CourseIndexType> classTypes = courseIndex.getClassTypes();
            Iterator<CourseIndexType> type = classTypes.iterator();

            CourseIndexType firstClass = type.next();
            Object[] row = { courseCode, AU, index, status, firstClass.getClassType(), firstClass.getGroup(),
                    firstClass.getDay(), firstClass.getTime(), firstClass.getVenue(), firstClass.getRemark() };
            data.add(row);

            while (type.hasNext()) {
                CourseIndexType t = type.next();
                Object[] nextRow = { "", "", "", "", t.getClassType(), t.getGroup(), t.getDay(), t.getTime(),
                        t.getVenue(), t.getRemark() };
                data.add(nextRow);
            }
        }
        String[] title = new String[1];
        title[0] = "Your Registered Courses:";
        int[] frameSize = { 1410, 500 };
        TableView.displayTable("Check/Print Registered Courses", columnHeadings, data, title,
                frameSize);
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
                int[] result = StudentController.checkVacancy(courseIndexStr);
                System.out.println("Places available " + ": [" + result[0] + "/" + result[1] + "]");
                System.out.println("Length of Waitlist: " + result[2]);
            } catch (WrongCourseIndex e) {
                PrintColor.println(">>> " + e.getMessage(), "RED");
            }
        } while (!courseIndexStr.equalsIgnoreCase("X"));
    }

    public static void changeCourseIndex(Student student) {
        System.out.println();
        PrintColor.println("+-----------------------------------------------------------+", "BLUE_BOLD");        
        PrintColor.println("|               Change Index Number of Course               |", "BLUE_BOLD");
        PrintColor.println("+-----------------------------------------------------------+", "BLUE_BOLD");        
        System.out.print("Enter Current Index Number: ");
        String curIndex = sc.next().toUpperCase();
        System.out.print("Enter New Index Number: ");
        String newIndex = sc.next().toUpperCase();
        try {
            StudentController.changeCourseIndex(student, curIndex, newIndex);
        } catch (WrongCourseIndex e) {
            PrintColor.println(">>> " + e.getMessage(), "RED");
        } catch (NotSameCourse e) {
            PrintColor.println(">>> " + e.getMessage(), "RED");
        } catch (NoVacancy e) {
            PrintColor.println(">>> " + e.getMessage(), "RED");
        } catch (DidntEnrollOrWait e) {
            PrintColor.println(">>> Did not enroll this Course.", "RED");
        } catch (ClashTime e) {
            PrintColor.println(">>> " + e.getMessage() + ": " + StudentController.getClashedCourse(), "RED");
        } catch (AlreadyEnrolled e) {
            PrintColor.println(">>> " + e.getMessage(), "RED");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void swopCourseIndex(Student student) {
        System.out.println();
        PrintColor.println("+-----------------------------------------------------------+", "BLUE_BOLD");        
        PrintColor.println("|           Swop Index Number with Another Student          |", "BLUE_BOLD");
        PrintColor.println("+-----------------------------------------------------------+", "BLUE_BOLD");        
        String yourIndex;
        Student peer = null;
        String peerUserName;
        String peerPassword;
        String peerIndex;
        do {
            System.out.println("Student #1");
            System.out.println("\tYour Matric #1: " + student.getMatricNum());
            System.out.print("\tYour Index Number #1: ");
            yourIndex = sc.next();
            System.out.println("Student #2");
            System.out.println("\t(enter 'X' for all 3 below entries to exit)");
            System.out.print("\tPeer's Username: ");
            peerUserName = sc.next();
            System.out.print("\tPeer's Password: ");
            peerPassword = Auth.getHash(PasswordField.readPassword(""));
            System.out.print("\tSwop with Peer's Index Number #2: ");
            peerIndex = sc.next();

            if (peerUserName.equals("X") && peerPassword.equals(Auth.getHash("X")) && peerIndex.equals("X")) break;
            try {
                peer = new StudentTextMng().returnStudent(peerUserName, peerPassword);
                StudentController.swopCourseIndex(student, peer, yourIndex, peerIndex);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (WrongPassword e) {
                PrintColor.println(">>> Typed in wrong password of Student #2", "RED");
            } catch (WrongUsername e) {
                PrintColor.println(">>> Typed in wrong username of Student #2", "RED");
            } catch (WrongCourseIndex | DidntEnrollOrWait | NotSameCourse | AlreadyEnrolled e) {
                PrintColor.println(">>> " + e.getMessage(), "RED");
            } catch (ClashTime e) {
                PrintColor.println(">>> " + e.getMessage() + ": " + StudentController.getClashedCourse(), "RED");
            }
        } while (peer == null);
    }

    public static void planCourses() {
        System.out.println();
        PrintColor.println("+-----------------------------------------------------------+", "BLUE_BOLD");        
        PrintColor.println("|                  Course Planner Generator                 |", "BLUE_BOLD");
        PrintColor.println("+-----------------------------------------------------------+", "BLUE_BOLD"); 
        System.out.print("Enter courses: ");
        sc.nextLine();
        String coursesStr = sc.nextLine(); 
        String[] courseCodes = coursesStr.split(" "); 
        
        CoursePlannerView.view(CoursePlanner.plan(courseCodes, StudentController.getCourseDB()));
    }
}
