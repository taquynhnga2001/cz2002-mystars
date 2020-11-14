package view;

import java.util.Date;
import java.util.*;

import authentication.Auth;
import entity.*;
import registration_controller.AdminController;
import registration_controller.StudentController;
import text_manager.*;

public class SystemView {

	public static ArrayList<Course> courseDB;

	public static void main(String[] args) {
		boolean logout = false;
		do {
			PrintColor.println("+===========================================================+", "BLUE_BOLD");
			PrintColor.println("|                     WELCOME TO MYSTARS                    |", "BLUE_BOLD");
			PrintColor.println("+===========================================================+", "BLUE_BOLD");
			User user = Auth.login();
			if (user != null) {
				if (user instanceof Student) {
					Date accessPeriod = AccessPeriodTextMng.getAccessPeriod();
					if (accessPeriod.compareTo(new Date()) >= 0) {
						PrintColor.println(">>> Cannot access MyStars now. Access Period is "
								+ AccessPeriodTextMng.toString(accessPeriod) + ".", "RED");
					} else {
						Student student = (Student) user;
						StudentController.loadCourseDB();
						StudentView.view(student);
						logout = StudentView.getLogout();
					}
				} else { // user instance of Admin
					Admin admin = (Admin) user;
					AdminController.loadCourseDB();
					AdminController.loadStudentDB();
					AdminView.view(admin);
					logout = AdminView.getLogout();
				}
			}
		} while (logout);
	}

	public static Date accessPeriod() {
		return null;
	}
}
