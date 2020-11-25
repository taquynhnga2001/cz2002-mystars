package src.custom_exceptions;

public class WrongInputGender extends Exception{
    public WrongInputGender(){
        super("Gender must be 'M' (Male) or 'F' (Female)");
    }
}
