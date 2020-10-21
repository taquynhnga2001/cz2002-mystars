package custom_exceptions;

public class DidntEnrollOrWait extends Exception {
    public DidntEnrollOrWait() {
        super("Did not enroll or be in the waitlist of this course");
    }
}
