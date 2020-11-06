package registration_controller;

import entity.*;
import view.*;
import custom_exceptions.*;
import text_manager.*;

import java.io.IOException;
import java.util.*;

public class StudentController {

    private static String dropCourseFrom;
    private static boolean confirmAddCourse;
    private static boolean confirmDropCourse;
    private static String clashedCourseStr;
    private static ArrayList<Course> courseDB = new ArrayList<>();

    /** Add course to the originally registered courses */
    public static void addCourse(Student student, String courseIndexStr) throws WrongCourseIndex, AlreadyEnrolled,
            AlreadyInWaitlist, NoVacancy, MaximumAURegistered, IOException, ClashTime {
        // load the courseDB
        ArrayList<Course> courseDB = getCourseDB();

        // check if this chosen courseIndex is in database
        CourseIndex chosenCourseIndex = CourseTextMng.getCourseIndex(courseDB, courseIndexStr);

        // check if this Course in courseEnrolled of that student (if this/another
        // CourseIndex enrolled)
        ArrayList<CourseIndex> courseIndexEnrolled = student.getCourseEnrolled(courseDB);
        for (int i = 0; i < courseIndexEnrolled.size(); i++) {
            CourseIndex courseIndex_ = courseIndexEnrolled.get(i);
            if (chosenCourseIndex.getCourseCode().equalsIgnoreCase(courseIndex_.getCourseCode())) {
                throw new AlreadyEnrolled();
            }
        }
        // check if this Course in Waitlist of that student (this/another CourseIndex in
        // waitlist)
        ArrayList<CourseIndex> courseIndexWaitlist = student.getCourseWaitlist(courseDB);
        for (int i = 0; i < courseIndexWaitlist.size(); i++) {
            CourseIndex courseIndex_ = courseIndexWaitlist.get(i);
            if (chosenCourseIndex.getCourseCode().equalsIgnoreCase(courseIndex_.getCourseCode())) {
                throw new AlreadyInWaitlist();
            }
        }
        // check if the courseIndex clashes time with any enrolled courses
        CourseIndex clashedCourse = ClashWith(courseIndexEnrolled, chosenCourseIndex);
        if (clashedCourse != null) {
            clashedCourseStr = clashedCourse.getIndex();
            throw new ClashTime();
        }

        // check vacancy
        if (chosenCourseIndex.getVacancy() == 0) {
            // check if registeredAU > 21
            if (student.getRegisteredAU() + chosenCourseIndex.getAU() > 21) {
                throw new MaximumAURegistered();
            }
            // add to waitlist
            WailistTextMng.addWaitlist(student.getMatricNum(), courseIndexStr);
            student.waitCourse(courseIndexStr);
            // update database
            chosenCourseIndex.setWaitlistSize(chosenCourseIndex.getWaitlistSize() + 1);
            CourseIndexTextMng.saveCourseIndex(courseDB);
            throw new NoVacancy();
        }

        // if go through all check above, can display chosen course index
        TableView.displayCourseInfo(chosenCourseIndex);

        Scanner sc = new Scanner(System.in);
        String choice;
        do {
            System.out.print("Confirm to Add Course [Y/N]? ");
            choice = sc.nextLine();
        } while (!choice.equalsIgnoreCase("Y") && !choice.equalsIgnoreCase("N"));

        if (choice.equalsIgnoreCase("Y")) {
            // if go through all check above, can add course index
            EnrolledTextMng.addEnrolledCourses(student.getMatricNum(), courseIndexStr);
            student.enrollCourse(courseIndexStr);
            student.setRegisteredAU(student.getRegisteredAU() + chosenCourseIndex.getAU());
            chosenCourseIndex.setVacancy(chosenCourseIndex.getVacancy() - 1); // vacancy--
            // save courseIndexs in the database
            CourseIndexTextMng.saveCourseIndex(courseDB);
            System.out.println("Add course successfully!");
        }
    }

    public static CourseIndex dropCourse(Student student, String courseIndexStr)
            throws WrongCourseIndex, IOException, DidntEnrollOrWait {
        // load the courseDB
        ArrayList<Course> courseDB = getCourseDB();

        // check if this chosen courseIndex is in database
        CourseIndex chosenCourseIndex = CourseTextMng.getCourseIndex(courseDB, courseIndexStr);

        try {
            // check if this Course in courseEnrolled of that student (if this/another
            // CourseIndex enrolled)
            ArrayList<CourseIndex> courseIndexEnrolled = student.getCourseEnrolled(courseDB);
            for (int i = 0; i < courseIndexEnrolled.size(); i++) {
                CourseIndex courseIndex_ = courseIndexEnrolled.get(i);
                if (chosenCourseIndex.getCourseCode().equalsIgnoreCase(courseIndex_.getCourseCode())) {
                    throw new AlreadyEnrolled();
                }
            }
            // check if this Course in Waitlist of that student (this/another CourseIndex in
            // waitlist)
            ArrayList<CourseIndex> courseIndexWaitlist = student.getCourseWaitlist(courseDB);
            for (int i = 0; i < courseIndexWaitlist.size(); i++) {
                CourseIndex courseIndex_ = courseIndexWaitlist.get(i);
                if (chosenCourseIndex.getCourseCode().equalsIgnoreCase(courseIndex_.getCourseCode())) {
                    throw new AlreadyInWaitlist();
                }
            }
            // reach here when didn't enroll or in waitlist
            throw new DidntEnrollOrWait();
        } catch (AlreadyEnrolled e) {
            // if go through all check above, can display chosen course index
            TableView.displayCourseInfo(chosenCourseIndex);
            Scanner sc = new Scanner(System.in);
            String choice;
            do {
                System.out.print("Confirm to Drop Course [Y/N]? ");
                choice = sc.nextLine();
            } while (!choice.equalsIgnoreCase("Y") && !choice.equalsIgnoreCase("N"));
            if (choice.equalsIgnoreCase("Y")) {
                // drop the enrolled course
                EnrolledTextMng.delEnrolledCourses(student.getMatricNum(), courseIndexStr);
                student.dropCourse(courseIndexStr);
                chosenCourseIndex.setVacancy(chosenCourseIndex.getVacancy() + 1); // vacancy++
                System.out.println("Drop course successfully!");
                student.setRegisteredAU(student.getRegisteredAU() - chosenCourseIndex.getAU());
            } 
        } catch (AlreadyInWaitlist e) {
            // if go through all check above, can display chosen course index
            TableView.displayCourseInfo(chosenCourseIndex);
            Scanner sc = new Scanner(System.in);
            String choice;
            do {
                System.out.print("Confirm to Drop Course [Y/N]? ");
                choice = sc.nextLine();
            } while (!choice.equalsIgnoreCase("Y") && !choice.equalsIgnoreCase("N"));
            if (choice.equalsIgnoreCase("Y")) {
                // drop the course in waitlist
                WailistTextMng.delWaitlistCourses(student.getMatricNum(), courseIndexStr);
                student.dropCourse(courseIndexStr);
                chosenCourseIndex.setWaitlistSize(chosenCourseIndex.getWaitlistSize() - 1); // waitlistSize--
                System.out.println("Drop course successfully!");
            } 
        } finally {
            // save courseIndexs in the database
            CourseIndexTextMng.saveCourseIndex(courseDB);
        }
        return chosenCourseIndex;
    }

    /**Return an array of 2 ArrayLists. The 1st ArrayList is enrolled courses, the 2nd is waitlist courses */
    public static ArrayList<CourseIndex>[] getCourseRegistered(Student student) {
        ArrayList<CourseIndex> courseEnrolled = student.getCourseEnrolled(getCourseDB());
        ArrayList<CourseIndex> courseWaitList = student.getCourseWaitlist(getCourseDB());
        ArrayList<CourseIndex>[] courseIndexs = new ArrayList[2];
        courseIndexs[0] = courseEnrolled;
        courseIndexs[1] = courseWaitList;
        return courseIndexs;
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
            ArrayList<String[]> waitlist = WailistTextMng.readFile();
            for (String[] row : waitlist) {
                if (row[1].equalsIgnoreCase(courseIndexStr)) counter++;
            }
            result[2] = counter;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static void changeCourseIndex(Student student, String currentIndexStr, String newIndexStr) throws WrongCourseIndex,
            NotSameCourse, NoVacancy, DidntEnrollOrWait, ClashTime, IOException, AlreadyEnrolled {
        // load the courseDB
        ArrayList<Course> courseDB = getCourseDB();
        // get the chosen courseIndexs in database
        CourseIndex curIndex = CourseTextMng.getCourseIndex(courseDB, currentIndexStr);
        CourseIndex newIndex = CourseTextMng.getCourseIndex(courseDB, newIndexStr);

        if (!student.getCourseEnrolled(courseDB).contains(curIndex)) throw new DidntEnrollOrWait();
        if (!curIndex.getCourseCode().equalsIgnoreCase(newIndex.getCourseCode())) throw new NotSameCourse();
        if (newIndex.getVacancy() == 0) throw new NoVacancy();
        CourseIndex clashedCourse = ClashWith(student.getCourseEnrolled(courseDB), newIndex);
        if (clashedCourse != null) {
            clashedCourseStr = clashedCourse.getIndex();
            throw new ClashTime();
        }

        // if no exceptions above, then:
        // display table course index information
        TableView.display2CourseIndexs(curIndex, newIndex);
        // confirm to change course index
        String choice;
        Scanner sc = new Scanner(System.in);
        do {
            System.out.print("Confirm to Change Index Number [Y/N]? ");
            choice = sc.nextLine();
        } while (!choice.equalsIgnoreCase("Y") && !choice.equalsIgnoreCase("N"));
        // if confirm, update the database and attribute of the Student
        if (choice.equalsIgnoreCase("Y")) {
            // if go through all check above, can add course index
            EnrolledTextMng.addEnrolledCourses(student.getMatricNum(), newIndexStr);
            student.enrollCourse(newIndexStr);
            newIndex.setVacancy(newIndex.getVacancy() - 1); // vacancy--
            // drop the enrolled course
            EnrolledTextMng.delEnrolledCourses(student.getMatricNum(), currentIndexStr);
            student.dropCourse(currentIndexStr);
            curIndex.setVacancy(curIndex.getVacancy() + 1); // vacancy++
            // save courseIndexs in the database
            CourseIndexTextMng.saveCourseIndex(courseDB);
            System.out.println("Index Number " + currentIndexStr + " has been changed to " + newIndexStr);
        }
    }

    public static void swopCourseIndex(String fromMatricNum, String toMatricNum, String fromCourseIndex,
            String toCourseIndex) {

    }

    public static String readNotification(Student student) {
        return "";
    }

    /**Return the CourseIndex that is clashed with the chosen course index. Return null if no clash.
     * Only compare with CourseIndex of other Couses*/
    private static CourseIndex ClashWith(ArrayList<CourseIndex> courseIndexEnrolled, CourseIndex chosenCourseIndex) {
        // check if the courseIndex clashes time with any enrolled courses
        for (int i = 0; i < courseIndexEnrolled.size(); i++) {
            CourseIndex courseIndex = courseIndexEnrolled.get(i);
            if (courseIndex.getCourseCode().equals(chosenCourseIndex.getCourseCode())) continue;
            ArrayList<CourseIndexType> classTypes = courseIndex.getClassTypes();
            // loop through all classTypes in the enrolled index class
            for (CourseIndexType classEnrolled : classTypes) {
                // and check with all classType of the chosen index
                for (CourseIndexType c : chosenCourseIndex.getClassTypes()) {
                    if (c.getDay().equalsIgnoreCase(classEnrolled.getDay()) // clash day
                            && isClashedTime(c.getTime(), classEnrolled.getTime()) // clash time
                            && isClashedWeek(c.getWeek(), classEnrolled.getWeek())) { // clash week
                        return courseIndex;
                    }
                }
            }
        }
        return null;
    }
    private static boolean isClashedWeek(ArrayList<Integer> weeks1, ArrayList<Integer> weeks2) {
        for (int w1 : weeks1) {
            if (weeks2.contains(w1))
                return true;
        }
        return false;
    }
    private static boolean isClashedTime(String time1, String time2) {
        int from1 = Integer.parseInt(time1.split("-")[0]);
        int to1 = Integer.parseInt(time1.split("-")[1]);
        int from2 = Integer.parseInt(time2.split("-")[0]);
        int to2 = Integer.parseInt(time2.split("-")[1]);

        if (from2 <= from1 && from1 < to2) {
            return true;
        }
        if (from2 < to1 && to1 <= to2) {
            return true;
        }
        return false;
    }
    public static String getClashedCourse() {
        return clashedCourseStr;
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

}
