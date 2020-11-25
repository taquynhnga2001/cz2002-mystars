package src.custom_exceptions.student_existed;

public class MatricNumExisted extends Exception{
    public MatricNumExisted(String matricNum) {
        super("The Matric Number " + matricNum + " has already existed");
    }
}
