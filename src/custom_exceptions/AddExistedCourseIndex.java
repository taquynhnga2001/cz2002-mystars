package custom_exceptions;

import java.lang.Exception;

public class AddExistedCourseIndex extends Exception {
    public AddExistedCourseIndex() {
        super("This Course Index has existed.");
    }
}