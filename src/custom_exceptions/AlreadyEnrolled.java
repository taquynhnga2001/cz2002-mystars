package custom_exceptions;

import java.lang.Exception;

import entity.CourseIndex;

public class AlreadyEnrolled extends Exception {
    public AlreadyEnrolled(CourseIndex courseIndex) {
        super("Already enrolled in the course " + courseIndex.getCourseCode() + " " + courseIndex.getCourseName());
    }

    public AlreadyEnrolled(String courseIndexStr) {
        super("Already enrolled in the Index " + courseIndexStr);
    }
}
