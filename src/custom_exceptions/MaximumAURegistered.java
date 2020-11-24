package custom_exceptions;

public class MaximumAURegistered extends Exception {
    public MaximumAURegistered() {
        super("Cannot add course. Maximum AU has reached (21 AUs).");
    }
}
