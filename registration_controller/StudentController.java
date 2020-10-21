package registration_controller;

import entity.*;

import java.io.IOException;
import java.util.ArrayList;

import custom_exceptions.*;
import text_manager.*;

public class StudentController {

    public static ArrayList<Course> courseDB() {
        ArrayList<Course> courseDB = new ArrayList<>();
        try {
            courseDB = CourseTextMng.readFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return courseDB;
    }

    public static void addCourse(Student student, String courseIndexStr) throws WrongCourseIndex, AlreadyEnrolled,
            AlreadyInWaitlist, NoVacancy, MaximumAURegistered, IOException, ClashTime {
        // load the courseDB
        ArrayList<Course> courseDB = courseDB();

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
        for (int i = 0; i < courseIndexEnrolled.size(); i++) {
            CourseIndex courseIndex = courseIndexEnrolled.get(i);
            ArrayList<CourseIndexType> classTypes = courseIndex.getClassTypes();
            // loop through all classTypes in the enrolled index class
            for (CourseIndexType classEnrolled : classTypes) {
                // and check with all classType of the chosen index
                for (CourseIndexType c : chosenCourseIndex.getClassTypes()) {
                    if (c.getDay().equalsIgnoreCase(classEnrolled.getDay()) // clash day
                            && isClashedTime(c.getTime(), classEnrolled.getTime()) // clash time
                            && isClashedWeek(c.getWeek(), classEnrolled.getWeek())) { // clash week
                        throw new ClashTime();
                    }
                }
            }
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

        // if go through all check above, can add course index
        EnrolledTextMng.addEnrolledCourses(student.getMatricNum(), courseIndexStr);
        student.enrollCourse(courseIndexStr);
        student.setRegisteredAU(student.getRegisteredAU() + chosenCourseIndex.getAU());
        chosenCourseIndex.setVacancy(chosenCourseIndex.getVacancy() - 1); // vacancy--
        // save courseIndexs in the database
        CourseIndexTextMng.saveCourseIndex(courseDB);
    }

    public static CourseIndex dropCourse(Student student, String courseIndexStr)
            throws WrongCourseIndex, IOException, DidntEnrollOrWait {
        // load the courseDB
        ArrayList<Course> courseDB = courseDB();

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
            // drop the enrolled course
            EnrolledTextMng.delEnrolledCourses(student.getMatricNum(), courseIndexStr);
            student.dropCourse(courseIndexStr);
            chosenCourseIndex.setVacancy(chosenCourseIndex.getVacancy() + 1); // vacancy++

        } catch (AlreadyInWaitlist e) {
            // drop the course in waitlist
            WailistTextMng.delWaitlistCourses(student.getMatricNum(), courseIndexStr);
            student.dropCourse(courseIndexStr);
            chosenCourseIndex.setWaitlistSize(chosenCourseIndex.getWaitlistSize() - 1); // waitlistSize--
        } finally {
            // save courseIndexs in the database
            CourseIndexTextMng.saveCourseIndex(courseDB);
        }
        return chosenCourseIndex;

    }

    public static ArrayList<Course> getCourseRegistered(Student student) {
        return null;
    }

    public static String checkVacancy(String courseIndex) {
        return null;
    }

    public static void changeCourseIndex(String courseIndex) {

    }

    public static void swopCourseIndex(String fromMatricNum, String toMatricNum, String fromCourseIndex,
            String toCourseIndex) {

    }

    public static String readNotification(Student student) {
        return "";
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

}
