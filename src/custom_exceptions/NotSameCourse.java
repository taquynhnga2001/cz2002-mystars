package custom_exceptions;

import java.lang.Exception;

public class NotSameCourse extends Exception {
    public NotSameCourse() {
        super("The chosen Course Indexs are not from the same Course.");
    }
}
