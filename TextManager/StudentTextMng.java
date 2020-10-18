package TextManager;

import java.io.*;
import java.util.*;
import java.util.ArrayList;

import Entity.*;
import CustomException.*;

public class StudentTextMng extends TextManager {
    private int nameIdx = 0;
    private int usernameIdx = 1;
    private int mailIdx = 2;
    private int passwordIdx = 3;
    private int matricNumIdx = 4;
    private int genderIdx = 5;
    private int nationalityIdx = 6;

    /** Read String from text file and return list of Student objects */
    public ArrayList readFile(String filePath) throws IOException {
        // read String from text file
        ArrayList stringArray = (ArrayList) read(filePath);
        ArrayList<Student> alr = new ArrayList<Student>();// to store Professors data

        for (int i = 1; i < stringArray.size(); i++) { // not to get the first line (heading)
            String st = (String) stringArray.get(i);
            // get individual 'fields' of the string separated by SEPARATOR
            StringTokenizer star = new StringTokenizer(st, this.SEPERATOR); // pass in the string to the string
                                                                            // tokenizer // using delimiter ","

            star.nextToken().trim(); // first token: name
            String username = star.nextToken().trim(); // second token
            star.nextToken().trim(); // third token: mail
            String password = star.nextToken().trim();
            // String matricNum = star.nextToken().trim();
            // String gender = star.nextToken().trim();
            // String nationality = star.nextToken().trim();
            // create Student object from file data
            Student student = new Student(username, password);
            // add to Students list
            alr.add(student);
        }
        return alr; // list of Students
    }

    /** Read String from text file and return a Student object */
    public Student returnStudent(String filePath, String username, String password)
            throws IOException, WrongPassword, WrongUsername {
        ArrayList stringArray = (ArrayList) read(filePath);
        // ArrayList<Student> alr = new ArrayList<Student>();// to store Professors data
        // String name_;
        String username_;
        // String mail_;
        String password_;
        String matricNum_;
        // String gender_;
        // String nationality_;

        for (int i = 1; i < stringArray.size(); i++) {
            String st = (String) stringArray.get(i);
            // get individual 'fields' of the string separated by SEPARATOR
            StringTokenizer star = new StringTokenizer(st, this.SEPERATOR); // pass in the string to the string
                                                                            // tokenizer // using delimiter ","
            star.nextToken().trim(); // first token
            username_ = star.nextToken().trim(); // second token...
            star.nextToken().trim();
            password_ = star.nextToken().trim();
            matricNum_ = star.nextToken().trim();
            // gender_ = star.nextToken().trim();
            // nationality_ = star.nextToken().trim();

            if (username_ == username && password_ == password) {
                return new Student(username_, password_);
            } else if (password_ == password && username_ != username) {
                throw new WrongUsername();
            } else if (username_ == username && password_ != password) {
                throw new WrongPassword();
            } else
                continue;
            // create Student object from file data
            // Student student = new Student(name_, username_, mail_, password_, gender_,
            // matricNum_, nationality_);
            // return student;
        }
        return null;
    }

    /** Read String from text file and return Student's attributes */
    public ArrayList<String> readStudent(String filePath, String matricNum) throws IOException {
        ArrayList stringArray = (ArrayList) read(filePath); // lines of Students' data
        ArrayList<String> alr = new ArrayList<String>(); // to store Student's attributes
        String name;
        String username;
        String mail;
        String password;
        String matricNum_;
        String gender;
        String nationality;

        for (int i = 1; i < stringArray.size(); i++) {
            String st = (String) stringArray.get(i);
            // get individual 'fields' of the string separated by SEPARATOR
            StringTokenizer star = new StringTokenizer(st, this.SEPERATOR); // pass in the string to the string
                                                                            // tokenizer // using delimiter ","
            name = star.nextToken().trim(); // first token
            username = star.nextToken().trim(); // second token...
            mail = star.nextToken().trim();
            password = star.nextToken().trim();
            matricNum_ = star.nextToken().trim();
            gender = star.nextToken().trim();
            nationality = star.nextToken().trim();

            if (matricNum_ == matricNum) {
                alr.add(name);
                alr.add(username);
                alr.add(mail);
                alr.add(password);
                alr.add(matricNum);
                alr.add(gender);
                alr.add(nationality);
                return alr;
            } else
                continue;
        }
        return null;
    }

    // Overload
    /** Read String from text file and return Student's attributes */
    public ArrayList<String> readStudent(String filePath, String username, String password) throws IOException {
        ArrayList stringArray = (ArrayList) read(filePath); // lines of Students' data
        ArrayList<String> alr = new ArrayList<String>();// to store Student's attributes
        String name;
        String username_;
        String mail;
        String password_;
        String matricNum;
        String gender;
        String nationality;

        for (int i = 0; i < stringArray.size(); i++) {
            String st = (String) stringArray.get(i);
            // get individual 'fields' of the string separated by SEPARATOR
            StringTokenizer star = new StringTokenizer(st, this.SEPERATOR); // pass in the string to the string
                                                                            // tokenizer // using delimiter ","
            name = star.nextToken().trim(); // first token
            username_ = star.nextToken().trim(); // second token...
            mail = star.nextToken().trim();
            password_ = star.nextToken().trim();
            matricNum = star.nextToken().trim();
            gender = star.nextToken().trim();
            nationality = star.nextToken().trim();

            if (username_ == username && password_ == password) {
                alr.add(name);
                alr.add(username);
                alr.add(mail);
                alr.add(password);
                alr.add(matricNum);
                alr.add(gender);
                alr.add(nationality);
                return alr;
            } else
                continue;
        }
        return null;
    }

    // public void saveFile(String filename, List al) throws IOException {
    // List alw = new ArrayList();// to store Students data

    // for (int i = 0; i < al.size(); i++) {
    // Student student = (Student) al.get(i);
    // StringBuilder st = new StringBuilder();
    // st.append(student.getName().trim());
    // st.append(SEPARATOR);
    // st.append(student.getEmail().trim());
    // st.append(SEPARATOR);
    // st.append(student.getContact());
    // alw.add(st.toString());
    // }
    // write(filename, alw);
    // }
}
