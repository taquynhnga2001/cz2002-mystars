package view;

import java.io.IOException;
import java.util.*;
import entity.*;
import text_manager.*;

public class UserMng {
    public static List<Course> loadCourses() {
        List<Course> courses = new ArrayList<>();
        try {
            courses = CourseTextMng.readFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return courses;
    }
}
