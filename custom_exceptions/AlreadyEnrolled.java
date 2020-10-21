package custom_exceptions;

import java.lang.Exception;

public class AlreadyEnrolled extends Exception {
    public AlreadyEnrolled() {
        super("Already enrolled");
    }
}
