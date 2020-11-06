package view;

import java.util.Date;
import java.io.IOException;
// import java.io.*;
import java.util.*;

import authentication.Auth;
import entity.*;
import registration_controller.StudentController;
import text_manager.CourseTextMng;


public class SystemView {
	
	public static ArrayList<Course> courseDB;
	public static void main(String[] args) {

		try {
			// load common database
			courseDB = CourseTextMng.readFile();
		} catch (IOException e) {
			e.printStackTrace();
		}

		System.out.println("\n========== WELCOME TO MYSTARS ==========");
		User user = Auth.login();
		if (user!=null) {
			if (user instanceof Student) {
				Student student = (Student) user;
				// call StudentMng here
				StudentController.loadCourseDB();
				StudentView.view(student);
			} else {  // user instance of Admin
				Admin admin = (Admin) user;
				// call AdminMng here
				AdminView.view(admin);
			}
		}
	}
	
	public static Date accessPeriod() {
		return null;
	}
}
