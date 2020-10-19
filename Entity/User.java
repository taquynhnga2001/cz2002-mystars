package Entity;

import java.io.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class User implements Serializable { // realises serializable so that password can be hashed
	private String username;
	private String password;
	protected String studentDatabase = "database/Student.txt";
	protected String courseDatabase = "database/CouseIndex.txt";

	public User(String username, String password) { // Users will have a username and password to login
		this.username = username;
		this.password = password; // Store hashed password
	}

	// protected String getHash(String password) throws NoSuchAlgorithmException { // takes password and returns hashed
	// 																			// password
	// 	MessageDigest md = MessageDigest.getInstance("SHA-256"); // algorithm for hashing is SHA-256 by default
	// 	md.update(password.getBytes());
	// 	byte[] digestedByteArray = md.digest();

	// 	StringBuffer hashValue = new StringBuffer();
	// 	for (byte b : digestedByteArray) {
	// 		hashValue.append(String.format("%02x", b & 0xff));
	// 	}

	// 	return hashValue.toString();
	// }

	// boolean checkLoginInfo(String username, String password) throws NoSuchAlgorithmException {// checks if username and
	// 																							// password are correct
	// 	if (this.username != username)
	// 		return false;
	// 	if (this.password != getHash(password))
	// 		return false;
	// 	return true;
	// }

	CourseIndex readCourseIndex(int index) throws FileNotFoundException, IOException, ClassNotFoundException {
		String path = courseDatabase + "\\" + index + ".txt";
		ObjectInputStream in = new ObjectInputStream(new FileInputStream(path));
		CourseIndex courseIndex = (CourseIndex) in.readObject();
		in.close();
		return courseIndex;
	}

	void saveChanges(CourseIndex courseIndex, int index) throws FileNotFoundException, IOException {
		String path = courseDatabase + "\\" + index + ".txt"; // save courseIndex as text file in course folder
		ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(path));
		out.writeObject(courseIndex);
		out.close();
	}

	void saveChanges(Student student) throws FileNotFoundException, IOException {
		String path = studentDatabase + "\\" + student.getName() + ".txt"; // save Student profile in studentDatabase
		ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(path));
		out.writeObject(student);
		out.close();
	}

	int checkVacancy(int index) throws FileNotFoundException, ClassNotFoundException, IOException {
		CourseIndex courseIndex = readCourseIndex(index);
		return courseIndex.checkVacancy();
	}

	public String getUsername() {
		return username;
	}
	public String getPassword() {
		return password;
	}

}
