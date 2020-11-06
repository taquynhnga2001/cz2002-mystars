package custom_exceptions;

public class ClashTime extends Exception {
    public ClashTime() {
        super("Clash with other enrolled course");
    }
    public ClashTime(int student_i) {
        super("Student #" + student_i + " has course index clashed with other enrolled course");
    }
}
