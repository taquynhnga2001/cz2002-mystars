package src.entity;

/**
 * Represents a user  the school. 
 * 
 * @author Ta Quynh Nga, Tan Ching Fhen
 */
public class User{ // realises serializable so that password can be hashed
	private String username;
	private String password;

	public User(String username, String password) { // Users will have a username and password to login
		this.username = username;
		this.password = password; // Store hashed password
	}

	
	/** 
	 * @return String of user name
	 */
	public String getUsername() {
		return username;
	}
	
	/** 
	 * @return String of user password in hashed format
	 */
	public String getPassword() {
		return password;
	}
}
