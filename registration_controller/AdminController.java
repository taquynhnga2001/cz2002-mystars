package registration_controller;

import entity.*;

import java.io.IOException;
import java.text.*;
import java.time.DateTimeException;
import java.util.*;

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

    /**Add a new course index. After adding a course index, add their class types*/
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


    /**Updating an existing course. Return true if confirm to update */
    public static boolean updateCourse(Course updateCourse, String[] attributes) throws IOException {
        Scanner sc = new Scanner(System.in);
        String newCode = "";
        String newName = "";
        String newSchool = "";
        String newAU = "";
        boolean checkCF = true;
        for (String att : attributes) {
            switch (att) {
                case "code": {
                    System.out.print("\tNew Course code: ");
                    newCode = sc.next();
                    break;
                }
                case "name": {
                    System.out.print("\tNew Course name: ");
                    sc.nextLine();
                    newName = sc.nextLine();
                    break;
                }
                case "school": {
                    System.out.print("\tNew School: ");
                    newSchool = sc.next();
                    break;
                }
                case "au": {
                    System.out.print("\tNew AUs: ");
                    newAU = sc.next();
                    break;
                }
                default: {
                    System.out.println(">>> Wrong Input: Course does not have " + att + " value!");
                    checkCF = false;
                    break;
                }
            }
        }
        if (checkCF) {
            System.out.print("Confirm to update this Course [Y/N]? ");
            String choice = sc.next().toUpperCase();
            if (choice.equals("Y")) {
                for (String att: attributes) {
                    switch (att) {
                        case "code": {
                            updateCourse.setCouseCode(newCode);
                            break;
                        }
                        case "name": {
                            updateCourse.setCourseName(newName);
                            break;
                        }
                        case "school": {
                            updateCourse.setSchool(newSchool);
                            break;
                        }
                        case "au": {
                            updateCourse.setAU(Integer.parseInt(newAU));
                            break;
                        }
                    }
                }
                System.out.println("Updated Course successfully!");
                CourseTextMng.saveCourses(getCourseDB());
                return true;
            }
        }
        return false;
    }
    
    /**Update an existing course index. Return true if confirm to update */
    public static boolean updateCourseIndex(CourseIndex updateIndex, String[] attributes) throws IOException {
        Scanner sc = new Scanner(System.in);
        String newIndex = "";
        String capacity = "";
        boolean checkCF = true;
        for (String att : attributes) {
            switch (att) {
                case "index": {
                    System.out.print("\tNew index number: ");
                    newIndex = sc.next();
                    break;
                }
                case "capacity": {
                    System.out.print("\tNew capacity: ");
                    capacity = sc.next();
                    break;
                }
                default: {
                    System.out.println(">>> Wrong Input: Course Index does not have " + att + " value!");
                    checkCF = false;
                    break;
                }
            }
        }
        if (checkCF) {
            System.out.print("Confirm to update this Course Index [Y/N]? ");
            String choice = sc.next().toUpperCase();
            if (choice.equals("Y")) {
                for (String att : attributes) {
                    switch (att) {
                        case "index": {
                            updateIndex.setCourseIndex(newIndex);
                            // update index of EnrolledCourse.csv and Waitlist.csv if change index
                            // then send email to registered students to inform index number changed
                            // not done yet
                            break;
                        }
                        case "capacity": {
                            updateIndex.setCapacity(Integer.parseInt(capacity));
                            break;
                        }
                    }
                }
                System.out.println("Updated Course Index successfully!");
                CourseIndexTextMng.saveCourseIndex(getCourseDB());
                CourseIndexTypeTextMng.saveCourseIndexTypes(getCourseDB());
                return true;
            }
        }
        return false;
    }


    /** Return int[3] array includes {vacancy, capacity, lenth of waitlist} */
    public static int[] checkVacancy(String courseIndexStr) throws WrongCourseIndex {
        // load the courseDB
        ArrayList<Course> courseDB = getCourseDB();
        // get the chosen courseIndex is in database
        CourseIndex courseIndex = CourseTextMng.getCourseIndex(courseDB, courseIndexStr);
        TableView.displayCourseIndexInfo(courseIndex);
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

    /** Print student list by Course Index*/
    public static void printStudentByIndex(String courseIndexStr) throws WrongCourseIndex {
        CourseIndex courseIndex = CourseTextMng.getCourseIndex(getCourseDB(), courseIndexStr);
        ArrayList<Student> listStudents = new ArrayList<>();
        for (Student s : getStudentDB()) {
            if (s.getCourseEnrolled(getCourseDB()).contains(courseIndex)) listStudents.add(s);
        }
        Comparable[] listComparables = new Student[listStudents.size()];
        for (int i = 0; i<listStudents.size(); i++) {
            listComparables[i] = listStudents.get(i);
        }
        selectionSort(listComparables);
        TableView.displayStudentSorting(listComparables, "Index " + courseIndexStr);
    }

    /**Print student list by Course Index*/
    public static void printStudentByCourse(String courseStr) throws WrongCourseIndex, WrongCourseCode {
        Course course = CourseTextMng.getCourse(getCourseDB(), courseStr);
        ArrayList<Student> listStudents = new ArrayList<>();
        for (CourseIndex courseIndex : course.getCourseIndexs()) {
            for (Student s : getStudentDB()) {
                if (s.getCourseEnrolled(getCourseDB()).contains(courseIndex)) listStudents.add(s);
            }
        }
        Comparable[] listComparables = new Student[listStudents.size()];
        for (int i = 0; i<listStudents.size(); i++) {
            listComparables[i] = listStudents.get(i);
        }
        selectionSort(listComparables);
        TableView.displayStudentSorting(listComparables, "Course " + courseStr + " " + course.getCourseName());
    }

    /**Edit student access period */
    public static void editAccessPeriod(String dateStr, String timeStr) throws ParseException, DateTimeException {
        try {
            SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
            formatter.parse(dateStr + " " + timeStr);
            String[] d = dateStr.split("-");
            String[] t = timeStr.split(":");
            if (Integer.parseInt(d[0])>=32 || Integer.parseInt(d[1])>12) throw new DateTimeException("");
            if (Integer.parseInt(t[0])>=24 || Integer.parseInt(t[1])>=60 || Integer.parseInt(t[2])>=60) throw new DateTimeException("");
            AccessPeriodTextMng.setAccessPeriod(dateStr + " " + timeStr);
        } catch (ParseException e) {
            throw new ParseException(" ", 0);
        }
    }

    private static void selectionSort (Comparable[] list) {
		int min;
		Comparable temp;
		for (int index = 0; index < list.length-1; index++){
			min = index;
			for (int scan = index+1; scan < list.length; scan++)
				if (list[scan].compareTo(list[min]) < 0)
					min = scan;
			// Swap the values
			temp = list[min];
			list[min] = list[index];
			list[index] = temp;
		}
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
