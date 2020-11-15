package custom_exceptions;

import java.lang.Exception;

import entity.CourseIndex;

public class AlreadyInWaitlist extends Exception {
    public AlreadyInWaitlist(CourseIndex courseIndex) {
        super("Already in Waitlist of this course " + courseIndex.getCourseCode() + " " + courseIndex.getCourseName());
    }

    public AlreadyInWaitlist(String courseIndexStr) {
        super("Already in Waitlist of Index" + courseIndexStr);
    }
}
