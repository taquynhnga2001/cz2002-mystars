package custom_exceptions;

import java.lang.Exception;

public class AlreadyInWaitlist extends Exception {
    public AlreadyInWaitlist() {
        super("Already in Waitlist of this Course");
    }
}
