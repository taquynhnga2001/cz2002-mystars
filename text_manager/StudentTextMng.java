package text_manager;

import java.io.*;
import java.util.*;
import java.util.ArrayList;

import constants.FilePath;
import entity.*;
import custom_exceptions.*;
import custom_exceptions.WrongUsername;

public class StudentTextMng extends TextManager {

    private static final String FILEPATH = FilePath.STUDENT;

    /** Read String from text file and return list of Student objects */
    public static ArrayList<Student> readFile() throws IOException {
        // read String from text file
        ArrayList<String> stringArray = read(FILEPATH);
        ArrayList<Student> alr = new ArrayList<Student>();// to store Professors data

        for (int i = 1; i < stringArray.size(); i++) { // not to get the first line (heading)
            String st = (String) stringArray.get(i);
            // get individual 'fields' of the string separated by SEPARATOR
            StringTokenizer star = new StringTokenizer(st, SEPERATOR); // pass in the string to the string
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
    public static Student returnStudent(String username, String password)
            throws IOException, WrongPassword, WrongUsername {
        ArrayList<String> stringArray = read(FILEPATH);
        String username_;
        String password_;

        for (int i = 1; i < stringArray.size(); i++) {
            String st = (String) stringArray.get(i);
            // get individual 'fields' of the string separated by SEPARATOR
            StringTokenizer star = new StringTokenizer(st, SEPERATOR); // pass in the string to the string
                                                                       // tokenizer // using delimiter ","
            star.nextToken().trim(); // first token: name
            username_ = star.nextToken().trim(); // second token
            star.nextToken().trim(); // fourth token: matricNum
            password_ = star.nextToken().trim();

            if (username_.equals(username) && password_.equals(password)) {
                return new Student(username_, password_);
            } else if (username_.equalsIgnoreCase(username) && !password_.equals(password)) {
                throw new WrongPassword();
            } else if (password_.equals(password) && !username_.equalsIgnoreCase(username)) {
                throw new WrongUsername();
            } else
                continue;
        }
        return null;
    }

    // Overload
    /** Read String from text file and return Student's attributes */
    public static ArrayList<String> readStudent(String username, String password) throws IOException {
        ArrayList<String> stringArray = read(FILEPATH); // lines of Students' data
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
            StringTokenizer star = new StringTokenizer(st, SEPERATOR); // pass in the string to the string
                                                                       // tokenizer // using delimiter ","
            name = star.nextToken().trim(); // first token
            username_ = star.nextToken().trim(); // second token...
            mail = star.nextToken().trim();
            password_ = star.nextToken().trim();

            if (username_.equalsIgnoreCase(username) && password_.equals(password)) {
                matricNum = star.nextToken().trim();
                gender = star.nextToken().trim();
                nationality = star.nextToken().trim();
                alr.add(name);
                alr.add(mail);
                alr.add(matricNum);
                alr.add(gender);
                alr.add(nationality);
                return alr;
            }
        }
        return null;
    }

    /** Save a list of Student objects to database */
    public static void saveStudents(List<Student> students) throws IOException {
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
        write(FILEPATH, al);
    }

    /**Add a student from String information to the database*/
    public static void addStudent(String name, String username, String mail, String password, String matricNum,
            String gender, String nationality) throws IOException {
        // read String from text file
        ArrayList<String> stringArray = read(FILEPATH);
        String newStudentString = name + SEPERATOR + 
                                username + SEPERATOR + 
                                mail + SEPERATOR + 
                                password + SEPERATOR +
                                matricNum + SEPERATOR + 
                                gender + SEPERATOR + 
                                nationality;
        stringArray.add(newStudentString);
        write(FILEPATH, stringArray);
    }
}
