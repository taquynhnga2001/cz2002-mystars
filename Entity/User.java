package entity;

import java.util.*;
// import java.security.MessageDigest;
// import java.security.NoSuchAlgorithmException;

import custom_exceptions.WrongCourseIndex;

public class User{ // realises serializable so that password can be hashed
	private String username;
	private String password;

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

	// CourseIndexType readCourseIndex(int index) throws FileNotFoundException, IOException, ClassNotFoundException {
	// 	String path = courseDatabase + "\\" + index + ".txt";
	// 	ObjectInputStream in = new ObjectInputStream(new FileInputStream(path));
	// 	CourseIndexType courseIndex = (CourseIndexType) in.readObject();
	// 	in.close();
	// 	return courseIndex;
	// }

	// void saveChanges(CourseIndexType courseIndex, int index) throws FileNotFoundException, IOException {
	// 	String path = courseDatabase + "\\" + index + ".txt"; // save courseIndex as text file in course folder
	// 	ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(path));
	// 	out.writeObject(courseIndex);
	// 	out.close();
	// }

	// void saveChanges(Student student) throws FileNotFoundException, IOException {
	// 	String path = studentDatabase + "\\" + student.getName() + ".txt"; // save Student profile in studentDatabase
	// 	ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(path));
	// 	out.writeObject(student);
	// 	out.close();
	// }

	// int checkVacancy(int index) throws FileNotFoundException, ClassNotFoundException, IOException {
	// 	CourseIndexType courseIndex = readCourseIndex(index);
	// 	return courseIndex.getVacancy();
	// }

	public String getUsername() {
		return username;
	}
	public String getPassword() {
		return password;
	}
	public int checkVacancy(ArrayList<Course> courses, String courseIndex) throws WrongCourseIndex {
		// loop through all courses in the database
		for (int i=0; i<courses.size(); i++) {
			Course course = courses.get(i);
			ArrayList<CourseIndex> courseIndexs = course.getCourseIndexs();

			// loop through all courseIndexs of that course in the database
			for (int j=0; j<courseIndexs.size(); j++) {
				CourseIndex courseIndex_ = courseIndexs.get(j);
				if (courseIndex_.getIndex().equalsIgnoreCase(courseIndex)) {
					return courseIndex_.getVacancy();
				}
			}
		}
		throw new WrongCourseIndex();
	}
}
