package custom_exceptions;

import java.lang.Exception;

public class AddExistedCourse extends Exception {
    public AddExistedCourse() {
        super("This Course has existed.");
    }
}
