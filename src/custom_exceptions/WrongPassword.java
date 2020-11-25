package src.custom_exceptions;

import java.lang.Exception;

public class WrongPassword extends Exception {
    public WrongPassword() {
        super("Typed in wrong Password. Type again.\n");
    }
}
