package TextManager;

import java.io.*;
import java.util.*;
import java.util.ArrayList;

import Constants.FilePath;
import Entity.*;
import CustomException.*;

public class StudentTextMng extends TextManager {

    private final String FILEPATH = FilePath.STUDENT;

    /** Read String from text file and return list of Student objects */
    public ArrayList readFile() throws IOException {
        // read String from text file
        ArrayList stringArray = (ArrayList) read(FILEPATH);
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
            Student student = new Student(username, password);
            // add to Students list
            alr.add(student);
        }
        return alr; // list of Students
    }

    /** Read String from text file and return a Student object */
    public Student returnStudent(String username, String password)
            throws IOException, WrongPassword, WrongUsername {
        ArrayList stringArray = (ArrayList) read(FILEPATH);
        String username_;
        String password_;

        for (int i = 1; i < stringArray.size(); i++) {
            String st = (String) stringArray.get(i);
            // get individual 'fields' of the string separated by SEPARATOR
            StringTokenizer star = new StringTokenizer(st, this.SEPERATOR); // pass in the string to the string
                                                                            // tokenizer // using delimiter ","
            star.nextToken().trim(); // first token: name
            username_ = star.nextToken().trim(); // second token
            star.nextToken().trim(); // fourth token: matricNum
            password_ = star.nextToken().trim();

            if (username_ == username && password_ == password) {
                return new Student(username_, password_);
            } else if (password_ == password && username_ != username) {
                throw new WrongUsername();
            } else if (username_ == username && password_ != password) {
                throw new WrongPassword();
            } else
                continue;
        }
        return null;
    }

    /** Read String from text file and return Student's attributes */
    public ArrayList<String> readStudent(String matricNum) throws IOException {
        ArrayList stringArray = (ArrayList) read(FILEPATH); // lines of Students' data
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
    public ArrayList<String> readStudent(String username, String password) throws IOException {
        ArrayList stringArray = (ArrayList) read(FILEPATH); // lines of Students' data
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
            }
        }
        return null;
    }

    /** Save a list of Student objects to database */
    public void saveStudents(List<Student> students) throws IOException {
        List<String> al = new ArrayList<>(); // to store Student data
        String HEADING = "name,username,mail,password,matricNum,gender,nationality";
        al.add(HEADING);

        for (int i = 0; i < students.size(); i++) {
            Student student = students.get(i);
            StringBuilder st = new StringBuilder();
            st.append(student.getName().trim());
            st.append(SEPERATOR);
            st.append(student.getUsername().trim());
            st.append(SEPERATOR);
            st.append(student.getMail().trim());
            st.append(SEPERATOR);
            st.append(student.getPassword().trim());
            st.append(SEPERATOR);
            st.append(student.getMatricNum().trim());
            st.append(SEPERATOR);
            st.append(student.getGender().trim());
            st.append(SEPERATOR);
            st.append(student.getNationality().trim());
            al.add(st.toString());
        }
        write("../database/Student.csv", al);
    }
}
