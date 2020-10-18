package TextManager;

import java.io.*;
import java.util.*;
import Entity.Admin;
import CustomException.*;

public class AdminTextMng extends TextManager {
    public ArrayList readFile(String filePath) throws IOException {
        // read String from text file
        ArrayList stringArray = (ArrayList) read(filePath);
        ArrayList<Admin> alr = new ArrayList<Admin>();// to store Admins data

        for (int i = 0; i < stringArray.size(); i++) {
            String st = (String) stringArray.get(i);
            // get individual 'fields' of the string separated by SEPARATOR
            StringTokenizer star = new StringTokenizer(st, this.SEPERATOR); // pass in the string to the string
                                                                            // tokenizer // using delimiter ","

            star.nextToken().trim(); // first token: name
            String username = star.nextToken().trim(); // second token...
            star.nextToken().trim(); // third token: mail
            String password = star.nextToken().trim();
            // String phoneNum = star.nextToken().trim();
            // create Admin object from file data
            Admin admin = new Admin(username, password);
            // add to Admins list
            alr.add(admin);
        }
        return alr; // list of Admins
    }

    public Admin returnAdmin(String filePath, String username, String password) throws IOException, WrongPassword, WrongUsername {
        // read String from text file and return Admin object
        ArrayList stringArray = (ArrayList) read(filePath);
        ArrayList<Admin> alr = new ArrayList<Admin>();// to store Admins data
        String name_;
        String username_;
        String mail_;
        String password_;
        String phoneNum_;

        for (int i = 1; i < stringArray.size(); i++) {
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
                return new Admin(username_, password_);
            } else if (password_ == password && username_ != username) {
                throw new WrongUsername();
            } else if (username_ == username && password_ != password) {
                throw new WrongPassword();
            } else
                continue;
        }
        // create Professor object from file data
        // Admin prof = new Admin(username_, password_);
        // return prof;
        return null;
    }

    public ArrayList<String> readAdmin(String filePath, String username, String password) throws IOException {
        // read String from text file and return Admin's attributes
        ArrayList stringArray = (ArrayList) read(filePath); // lines of Admins' data
        ArrayList<String> alr = new ArrayList<String>();// to store Admin's attributes
        String name;
        String username_;
        String mail;
        String password_;
        String phoneNum;

        for (int i = 1; i < stringArray.size(); i++) {
            String st = (String) stringArray.get(i);
            // get individual 'fields' of the string separated by SEPARATOR
            StringTokenizer star = new StringTokenizer(st, this.SEPERATOR); // pass in the string to the string
                                                                            // tokenizer // using delimiter ","
            name = star.nextToken().trim(); // first token
            username_ = star.nextToken().trim(); // second token...
            mail = star.nextToken().trim();
            password_ = star.nextToken().trim();
            phoneNum = star.nextToken().trim();

            if (username_ == username && password_ == password) {
                alr.add(name);
                alr.add(username);
                alr.add(mail);
                alr.add(password);
                alr.add(phoneNum);
                return alr;
            } else
                continue;
        }
        return null;
    }
}
