import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collections;

public class Admin extends User implements Serializable{
	
	
	
	private String adminDatabase="C:\\Users\\tanch\\Documents\\NTU Year 2\\Sem 1\\OOP\\Project\\adminDatabase";
	private ArrayList <CourseIndex> tempIndexList = new ArrayList <CourseIndex>();

	Admin(String username, String password) throws NoSuchAlgorithmException, IOException {
		super(username, password);
		ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(adminDatabase+"\\"+username+".txt"));         // adds admin into the database as text file
		out.writeObject(new Admin(username, password));
		out.close();  
	}

	
	
	void addStudent(String username, String password, String name, String gender, String matID , String nationality) throws NoSuchAlgorithmException, IOException {
		ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(studentDatabase+"\\"+name+".txt"));         // adds student into the database as text file
		out.writeObject(new Student(username, password, name, gender, matID , nationality));
		out.close();  
	}
	
	
	void addCourseIndex(String courseName, String schoolName, int index, int maxStudents) throws FileNotFoundException, IOException{               
		saveChanges(new CourseIndex(courseName, schoolName, index, maxStudents),index);                        // instantiate courseIndex and save as text file                                  
	}
	
	
	void printStudentsByCourse(String courseName) throws FileNotFoundException, ClassNotFoundException, IOException {  
		File folder = new File(courseDatabase);
		for (File fileEntry : folder.listFiles()) {
			CourseIndex courseIndex = readCourseIndex(Integer.parseInt(fileEntry.getName().split(".")[0]));
			if(courseIndex.getCourseName().equals(courseName)) {
				tempIndexList.add(courseIndex);
			}
	    }
		Collections.sort(tempIndexList);													
		for(CourseIndex I: tempIndexList) {											// prints students from a course
			for(Student s: I.getStudentArray()) {
				System.out.println(s);									
			}
		}
	}

		
	
	void printStudentsByCourseIndex(int index) throws FileNotFoundException, ClassNotFoundException, IOException  {  
		CourseIndex courseIndex = readCourseIndex(index);
		for(Student s: courseIndex.getStudentArray()){
			System.out.println(s);					                        // prints students(sorted)							
		}
	}

}
