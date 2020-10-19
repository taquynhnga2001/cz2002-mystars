package TextManager;

import java.io.*;
import java.util.*;

import Constants.FilePath;
import Entity.Admin;
import CustomException.*;

public class AdminTextMng extends TextManager {

    private final String FILEPATH = FilePath.ADMIN;
    
    public ArrayList readFile() throws IOException {
        // read String from text file
        ArrayList stringArray = (ArrayList) read(FILEPATH);
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
            Admin admin = new Admin(username, password);
            // add to Admins list
            alr.add(admin);
        }
        return alr; // list of Admins
    }

    public Admin returnAdmin(String username, String password) throws IOException, WrongPassword, WrongUsername {
        // read String from text file and return Admin object
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
            star.nextToken().trim(); // third token: mail
            password_ = star.nextToken().trim();

            if (username_ == username && password_ == password) {
                return new Admin(username_, password_);
            } else if (password_ == password && username_ != username) {
                throw new WrongUsername();
            } else if (username_ == username && password_ != password) {
                throw new WrongPassword();
            } else
                continue;
        }
        return null;
    }

    public ArrayList<String> readAdmin(String username, String password) throws IOException {
        // read String from text file and return Admin's attributes
        ArrayList stringArray = (ArrayList) read(FILEPATH); // lines of Admins' data
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

    /** Save a list of Admin objects to database */
    public void saveAdmins(List<Admin> admins) throws IOException {
        List<String> al = new ArrayList<>(); // to store Student data
        String HEADING = "name,username,mail,password,phoneNum";
        al.add(HEADING);

        for (int i = 0; i < admins.size(); i++) {
            Admin admin = admins.get(i);
            StringBuilder st = new StringBuilder();
            st.append(admin.getName().trim());
            st.append(SEPERATOR);
            st.append(admin.getUsername().trim());
            st.append(SEPERATOR);
            st.append(admin.getMail().trim());
            st.append(SEPERATOR);
            st.append(admin.getPassword().trim());
            st.append(SEPERATOR);
            st.append(admin.getPhoneNume().trim());
            al.add(st.toString());
        }
        write("../database/Admin.csv", al);
    }
}
