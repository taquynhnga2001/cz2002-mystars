package src.custom_exceptions.student_existed;

public class UsernameExisted extends Exception{
    public UsernameExisted(String username) {
        super("Username " + username + " has already existed.");
    }
}
