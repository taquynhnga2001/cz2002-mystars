package src.custom_exceptions;

import java.lang.Exception;

public class WrongCourseCode extends Exception {
    public WrongCourseCode() {
        super("Typed in wrong Course Code. Type again.\n");
    }
}
