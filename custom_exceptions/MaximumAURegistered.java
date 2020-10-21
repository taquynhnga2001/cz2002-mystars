package custom_exceptions;

public class MaximumAURegistered extends Exception {
    public MaximumAURegistered() {
        super("Maximum AU has reached (21 AUs).");
    }
}
