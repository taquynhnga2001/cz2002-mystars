package view;

import java.util.Date;
import java.io.IOException;
import java.util.*;

import authentication.Auth;
import entity.*;
import registration_controller.AdminController;
import registration_controller.StudentController;
import text_manager.AccessPeriodTextMng;
import text_manager.CourseTextMng;
import constants.Color;


public class SystemView {
	
	public static ArrayList<Course> courseDB;
	public static void main(String[] args) {

		try {
			// load common database
			courseDB = CourseTextMng.readFile();
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println(Color.CYAN_BOLD);
		System.out.println("+===========================================================+");
		System.out.println("|                     WELCOME TO MYSTARS                    |");
		System.out.println("+===========================================================+" + Color.RESET);
		User user = Auth.login();
		if (user!=null) {
			if (user instanceof Student) {
				Date accessPeriod = AccessPeriodTextMng.getAccessPeriod();
				if (accessPeriod.compareTo(new Date()) >= 0) {
					System.out.print(Color.RED);
					System.out.print(">>> Cannot access MyStars now. Access Period is " + AccessPeriodTextMng.toString(accessPeriod) + ".");
					System.out.println(Color.RESET);
				} else {
					Student student = (Student) user;
					StudentController.loadCourseDB();
					StudentView.view(student);
				}
			} else {  // user instance of Admin
				Admin admin = (Admin) user;
				AdminController.loadCourseDB();
				AdminController.loadStudentDB();
				AdminView.view(admin);
			}
		}
	}
	
	public static Date accessPeriod() {
		return null;
	}
}
