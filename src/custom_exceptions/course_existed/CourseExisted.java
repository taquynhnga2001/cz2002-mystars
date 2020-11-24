package custom_exceptions.course_existed;

public class CourseExisted extends Exception{
    public CourseExisted(String courseCode) {
        super("The Course code " + courseCode + " has already existed");
    }
}
