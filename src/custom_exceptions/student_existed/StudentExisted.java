package custom_exceptions.student_existed;

public class StudentExisted extends Exception{
    public StudentExisted() {
        super("The Student has already existed in the database");
    }
}
