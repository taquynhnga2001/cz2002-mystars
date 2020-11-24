package text_manager;

import java.io.*;
import java.util.*;

import constants.FilePath;
import entity.*;
import custom_exceptions.*;

public class AdminTextMng extends TextManager {

    private static final String FILEPATH = FilePath.ADMIN;
    private static final String SEPERATOR = ",";
    
    public static ArrayList<Admin> readFile() throws IOException {
        // read String from text file
        ArrayList<String> stringArray = read(FILEPATH);
        ArrayList<Admin> alr = new ArrayList<Admin>();// to store Admins data

        for (int i = 0; i < stringArray.size(); i++) {
            String st = (String) stringArray.get(i);
            // get individual 'fields' of the string separated by SEPARATOR
            StringTokenizer star = new StringTokenizer(st, SEPERATOR); // pass in the string to the string
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

    public static Admin returnAdmin(String username, String password) throws IOException, WrongPassword, WrongUsername {
        // read String from text file and return Admin object
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
            star.nextToken().trim(); // third token: mail
            password_ = star.nextToken().trim();

            if (username_.equals(username) && password_.equals(password)) {
                return new Admin(username_, password_);
            } else if (password_.equals(password) && !username_.equals(username)) {
                throw new WrongUsername();
            } else if (username_.equals(username) && !password_.equals(password)) {
                throw new WrongPassword();
            } else
                continue;
        }
        return null;
    }

    public static ArrayList<String> readAdmin(String username, String password) throws IOException {
        // read String from text file and return Admin's attributes
        ArrayList<String> stringArray = read(FILEPATH); // lines of Admins' data
        ArrayList<String> alr = new ArrayList<String>();// to store Admin's attributes
        String name;
        String username_;
        String mail;
        String password_;
        String phoneNum;

        for (int i = 1; i < stringArray.size(); i++) {
            String st = (String) stringArray.get(i);
            // get individual 'fields' of the string separated by SEPARATOR
            StringTokenizer star = new StringTokenizer(st, SEPERATOR); // pass in the string to the string
                                                                            // tokenizer // using delimiter ","
            name = star.nextToken().trim(); // first token
            username_ = star.nextToken().trim(); // second token...
            mail = star.nextToken().trim();
            password_ = star.nextToken().trim();
            phoneNum = star.nextToken().trim();

            if (username_.equals(username) && password_.equals(password)) {
                alr.add(name);
                alr.add(mail);
                alr.add(phoneNum);
                return alr;
            } else
                continue;
        }
        return null;
    }

    /** Save a list of Admin objects to database */
    public static void saveAdmins(List<Admin> admins) throws IOException {
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
