package entity;

import java.io.*;

// import java.nio.file.Files;
// import java.nio.file.Path;
// import java.nio.file.Paths;

// import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
// import java.util.Collections;

import text_manager.*;

public class Admin extends User {

	private String name;
	private String username;
	private String mail;
	private String password;
	private String phoneNum;

	private final int nameIdx = 0;
	private final int usernameIdx = 1;
	private final int mailIdx = 2;
	private final int passwordIdx = 3;
	private final int phoneNumIdx = 4;

	// private String adminDatabase= "database/Admin.txt";
	// private ArrayList <CourseIndexType> tempIndexList = new ArrayList <CourseIndexType>();

	public Admin(String username, String password) throws IOException {
		super(username, password);
		try {
			ArrayList<String> attributes = AdminTextMng.readAdmin(username, password);
			this.name = attributes.get(nameIdx);
			this.mail = attributes.get(mailIdx);
			this.phoneNum = attributes.get(phoneNumIdx);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

	
	
	// void addStudent(String name, String username, String mail, String password, String gender, String matricNum , String nationality) throws NoSuchAlgorithmException, IOException {
	// 	ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(studentDatabase+"\\"+name+".txt"));         // adds student into the database as text file
	// 	out.writeObject(new Student(username, password));
	// 	out.close();  
	// }
	
	
	// void addCourseIndex(String courseName, String schoolName, String index, int maxStudents) throws FileNotFoundException, IOException{               
	// 	saveChanges(new CourseIndexType(index),index);                        // instantiate courseIndex and save as text file                                  
	// }
	
	
	// void printStudentsByCourse(String courseName) throws FileNotFoundException, ClassNotFoundException, IOException {  
	// 	File folder = new File(courseDatabase);
	// 	for (File fileEntry : folder.listFiles()) {
	// 		CourseIndexType courseIndex = readCourseIndex(Integer.parseInt(fileEntry.getName().split(".")[0]));
	// 		if(courseIndex.getCourseCode().equals(courseName)) {
	// 			tempIndexList.add(courseIndex);
	// 		}
	//     }
	// 	Collections.sort(tempIndexList);													
	// 	for(CourseIndexType I: tempIndexList) {											// prints students from a course
	// 		for(Student s: I.getStudentArray()) {
	// 			System.out.println(s);									
	// 		}
	// 	}
	// }

		
	
	// void printStudentsByCourseIndex(int index) throws FileNotFoundException, ClassNotFoundException, IOException  {  
	// 	CourseIndexType courseIndex = readCourseIndex(index);
	// 	for(Student s: courseIndex.getStudentArray()){
	// 		System.out.println(s);					                        // prints students(sorted)							
	// 	}
	// }

	public String getName() {
		return name;
	}
	public String getMail() {
		return mail;
	}
	public String getPhoneNume() {
		return phoneNum;
	}

}
