package src.custom_exceptions;

import java.lang.Exception;

public class WrongUsername extends Exception {
    public WrongUsername() {
        super("Typed in wrong Username. Type again.\n");
    }
}
