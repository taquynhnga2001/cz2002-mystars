package src.custom_exceptions.course_existed;

public class CourseIndexExisted extends Exception{
    public CourseIndexExisted(String courseIndex) {
        super("The Index Number " + courseIndex + " has already existed");
    }
}
