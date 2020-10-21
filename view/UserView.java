package view;

import java.io.IOException;
import java.util.*;
import entity.*;
import text_manager.*;

public class UserView {
    public static ArrayList<Course> loadCourses() {
        try {
            ArrayList<Course> courses = CourseTextMng.readFile();
            return courses;
        } catch (IOException e) {
            e.printStackTrace();
        } 
        return null;
    }
}
