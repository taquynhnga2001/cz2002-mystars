package src.custom_exceptions;

public class DidntEnrollOrWait extends Exception {
    public DidntEnrollOrWait() {
        super("Did not enroll or be in the waitlist of this course");
    }
    public DidntEnrollOrWait(int student_i, String index) {
        super("Student #" + student_i + " did not enrolled in the Index Number " + index);
    }
}
