package custom_exceptions;

public class ClashTime extends Exception {
    public ClashTime() {
        super("Clash with other enrolled course");
    }
}
