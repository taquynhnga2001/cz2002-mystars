package src.view;

import java.util.Date;

import src.authentication.Auth;
import src.entity.*;
import src.registration_controller.AdminController;
import src.registration_controller.StudentController;
import src.text_manager.*;

/**
 * Display the first view to the user
 * @author Ta Quynh Nga
 */
public class SystemView {

	public static void main(String[] args) {
		System.out.println();
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
				}
			} else { // user instance of Admin
				Admin admin = (Admin) user;
				AdminController.loadCourseDB();
				AdminController.loadStudentDB();
				AdminView.view(admin);
			}
		}
	}
}
