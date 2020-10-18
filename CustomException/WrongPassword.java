package CustomException;

import java.lang.Exception;

public class WrongPassword extends Exception {
    public WrongPassword() {
        super("Typed in wrong Password. Type again.");
    }
}
