package TextManager;

import java.io.*;
import java.util.*;
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

    public ArrayList readFile(String filePath) throws IOException {
        // read String from text file
        ArrayList stringArray = (ArrayList) read(filePath);
        ArrayList<Student> alr = new ArrayList<Student>();// to store Professors data

        for (int i = 0; i < stringArray.size(); i++) {
            String st = (String) stringArray.get(i);
            // get individual 'fields' of the string separated by SEPARATOR
            StringTokenizer star = new StringTokenizer(st, this.SEPERATOR); // pass in the string to the string
                                                                            // tokenizer // using delimiter ","

            String name = star.nextToken().trim(); // first token
            String username = star.nextToken().trim(); // second token
            String mail = star.nextToken().trim(); // third token...
            String password = star.nextToken().trim();
            String matricNum = star.nextToken().trim();
            String gender = star.nextToken().trim();
            String nationality = star.nextToken().trim();
            // create Professor object from file data
            Student student = new Student(name, username, mail, password, gender, matricNum, nationality);
            // add to Students list
            alr.add(student);
        }
        return alr; // list of Students
    }

    public Student readStudent(String filePath, String username, String password) throws IOException, WrongPassword, WrongUsername {
        // read String from text file
        ArrayList stringArray = (ArrayList) read(filePath);
        ArrayList<Student> alr = new ArrayList<Student>();// to store Professors data
        String name_;
        String username_;
        String mail_;
        String password_;
        String matricNum_;
        String gender_;
        String nationality_;

        for (int i = 0; i < stringArray.size(); i++) {
            String st = (String) stringArray.get(i);
            // get individual 'fields' of the string separated by SEPARATOR
            StringTokenizer star = new StringTokenizer(st, this.SEPERATOR); // pass in the string to the string
                                                                            // tokenizer // using delimiter ","
            name_ = star.nextToken().trim(); // first token
            username_ = star.nextToken().trim(); // second token...
            mail_ = star.nextToken().trim();
            password_ = star.nextToken().trim();
            matricNum_ = star.nextToken().trim();
            gender_ = star.nextToken().trim();
            nationality_ = star.nextToken().trim();

            if (username_ == username && password_ == password) {
                System.out.println(">>> Login successfully!");
            } else if (password_ == password && username_ != username) {
                throw new WrongUsername();
            } else if (username_ == username && password_ != password) {
                throw new WrongPassword();
            } else
                continue;
        // create Student object from file data
        Student student = new Student(name_, username_, mail_, password_, gender_, matricNum_, nationality_);
        return student;
        }
    }

    // public void saveFile(String filename, List al) throws IOException {
    //     List alw = new ArrayList();// to store Students data

    //     for (int i = 0; i < al.size(); i++) {
    //         Student student = (Student) al.get(i);
    //         StringBuilder st = new StringBuilder();
    //         st.append(student.getName().trim());
    //         st.append(SEPARATOR);
    //         st.append(student.getEmail().trim());
    //         st.append(SEPARATOR);
    //         st.append(student.getContact());
    //         alw.add(st.toString());
    //     }
    //     write(filename, alw);
    // }
}
