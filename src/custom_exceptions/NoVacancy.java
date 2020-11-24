package custom_exceptions;

public class NoVacancy extends Exception {
    public NoVacancy() {
        super("No vacancy for this course index.");
    }
}
