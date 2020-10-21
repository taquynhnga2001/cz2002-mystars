package registration_controller;

import entity.*;

import java.io.IOException;
import java.util.ArrayList;

import custom_exceptions.*;
import text_manager.*;
import view.SystemView;

public class AdminController {
    
    public static ArrayList<Course> courseDB(){
        ArrayList<Course> courseDB = new ArrayList<>();
        try {
            courseDB = CourseTextMng.readFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return courseDB;
    }
}
