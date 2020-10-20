package view;

import java.util.Date;

// import java.io.*;
// import java.util.*;

import authentication.Auth;
import entity.*;


public class SystemManager {
	
	public static void main(String[] args) {

		System.out.println("\n========== WELCOME TO MYSTARS ==========");
		User user = Auth.login();
		if (user!=null) {
			if (user instanceof Student) {
				Student student = (Student) user;
				// call StudentMng here
				System.out.println("student matric num: " + student.getMatricNum() + "\n");
				StudentMng.printCouses();
			} else {
				// user instance of Admin
				Admin admin = (Admin) user;
				// call AdminMng here
				System.out.println("admin phone num: " + admin.getPhoneNume());
			}
		}
	}
	
	public static Date accessPeriod() {
		return null;
	}
}
