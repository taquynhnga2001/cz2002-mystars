package custom_exceptions;

import java.lang.Exception;

public class WrongCourseIndex extends Exception {
    public WrongCourseIndex() {
        super("Typed in wrong Course Index\n");
    }
}
