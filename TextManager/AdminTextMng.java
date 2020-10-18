package TextManager;

import java.io.*;
import java.util.*;
import Entity.Admin;
import CustomException.*;

public class AdminTextMng extends TextManager {
    public ArrayList readFile(String filePath) throws IOException {
        // read String from text file
        ArrayList stringArray = (ArrayList) read(filePath);
        ArrayList<Admin> alr = new ArrayList<Admin>();// to store Professors data

        for (int i = 0; i < stringArray.size(); i++) {
            String st = (String) stringArray.get(i);
            // get individual 'fields' of the string separated by SEPARATOR
            StringTokenizer star = new StringTokenizer(st, this.SEPERATOR); // pass in the string to the string
                                                                            // tokenizer // using delimiter ","

            String name = star.nextToken().trim(); // first token
            String username = star.nextToken().trim(); // second token...
            String mail = star.nextToken().trim();
            String password = star.nextToken().trim();
            String phoneNum = star.nextToken().trim();
            // create Professor object from file data
            Admin prof = new Admin(name, username, mail, password, phoneNum);
            // add to Professor list
            alr.add(prof);
        }
        return alr; // list of Professors
    }

    public Admin readAdmin(String filePath, String username, String password) throws IOException, WrongPassword, WrongUsername {
        // read String from text file
        ArrayList stringArray = (ArrayList) read(filePath);
        ArrayList<Admin> alr = new ArrayList<Admin>();// to store Professors data
        String name_;
        String username_;
        String mail_;
        String password_;
        String phoneNum_;

        for (int i = 0; i < stringArray.size(); i++) {
            String st = (String) stringArray.get(i);
            // get individual 'fields' of the string separated by SEPARATOR
            StringTokenizer star = new StringTokenizer(st, this.SEPERATOR); // pass in the string to the string
                                                                            // tokenizer // using delimiter ","
            name_ = star.nextToken().trim(); // first token
            username_ = star.nextToken().trim(); // second token...
            mail_ = star.nextToken().trim();
            password_ = star.nextToken().trim();
            phoneNum_ = star.nextToken().trim();

            if (username_ == username && password_ == password) {
                System.out.println(">>> Login successfully!");
                break;
            } else if (password_ == password && username_ != username) {
                throw new WrongUsername();
            } else if (username_ == username && password_ != password) {
                throw new WrongPassword();
            } else
                continue;
        // create Professor object from file data
        Admin prof = new Admin(name_, username_, mail_, password_, phoneNum_);
        return prof;
        }
    }
}
