package entity;

import java.util.*;

import custom_exceptions.WrongCourseIndex;

public class User{ // realises serializable so that password can be hashed
	private String username;
	private String password;

	public User(String username, String password) { // Users will have a username and password to login
		this.username = username;
		this.password = password; // Store hashed password
	}

	public String getUsername() {
		return username;
	}
	public String getPassword() {
		return password;
	}
}
