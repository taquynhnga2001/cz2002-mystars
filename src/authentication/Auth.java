package src.authentication;

import src.text_manager.*;
import src.entity.*;
import src.custom_exceptions.*;

import java.io.IOException;
import java.util.Scanner;

import src.constants.Color;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Handle login function and display the message to the user
 */
public class Auth {

    
    /** 
     * @return User
     */
    public static User login() {
        String domain;
        String username;
        String password;

        Scanner sc = new Scanner(System.in);
        System.out.print("Domain (Staff/Student): ");
        domain = sc.next();
        
        while (!domain.equalsIgnoreCase("Staff") && !domain.equalsIgnoreCase("Student")) {
            System.out.print(Color.RED);
            System.out.print(">>> Error! Wrong input!");
            System.out.println(Color.RESET);
            System.out.print("Domain (Staff/Student): ");
            domain = sc.next();
        }

        System.out.print("Username: ");
        username = sc.next();
        password = getHash(PasswordField.readPassword("Password:  "));

        int haveRead = 0;

        if (domain.equalsIgnoreCase("Student")) {
            do {
                try {
                    Student student = new StudentTextMng().returnStudent(username, password);
                    if (student == null) {
                        System.out.print(Color.RED);
                        System.out.print(">>> Error! Typed in wrong username and password. Type again.\n");
                        System.out.println(Color.RESET);
                        System.out.print("Username: ");
                        username = sc.next();
                        password = getHash(PasswordField.readPassword("Password:  "));
                    } else {
                        haveRead = 1;
                        System.out.print(Color.GREEN);
                        System.out.print(">>> Login successfully!\n");
                        System.out.println(Color.RESET);
                        return student;
                    }
                } catch (WrongUsername e) {
                    System.out.print(Color.RED);
                    System.out.print(">>> Error! " + e.getMessage());
                    System.out.println(Color.RESET);
                    System.out.print("Username: ");
                    username = sc.next();
                    password = getHash(PasswordField.readPassword("Password:  "));
                } catch (WrongPassword e) {
                    System.out.print(Color.RED);
                    System.out.print(">>> Error! " + e.getMessage());
                    System.out.println(Color.RESET);
                    System.out.print("Username: ");
                    username = sc.next();
                    password = getHash(PasswordField.readPassword("Password:  "));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } while (haveRead == 0);

        } else {
            do {
                try {
                    Admin prof = new AdminTextMng().returnAdmin(username, password);
                    if (prof == null) {
                        System.out.print(Color.RED);
                        System.out.print(">>> Error! Typed in wrong username and password. Type again.\n");
                        System.out.println(Color.RESET);
                    }
                    else {
                        haveRead = 1;
                        System.out.print(Color.GREEN);
                        System.out.print(">>> Login successfully!\n");
                        System.out.println(Color.RESET);
                        return prof;
                    }
                } catch (WrongUsername e) {
                    System.out.print(Color.RED);
                    System.out.print(">>> Error! " + e.getMessage());
                    System.out.println(Color.RESET);
                    System.out.print("Username: ");
                    username = sc.next();
                    password = getHash(PasswordField.readPassword("Password: "));
                } catch (WrongPassword e) {
                    System.out.print(Color.RED);
                    System.out.print(">>> Error! " + e.getMessage());
                    System.out.println(Color.RESET);
                    System.out.print("Username: ");
                    username = sc.next();
                    password = getHash(PasswordField.readPassword("Password: "));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } while (haveRead == 0);
        }
        return null;
    }

    
    /** 
     * @param pwString
     * @return String of hashed password
     */
    public static String getHash(String pwString) {
        String pwHash = new String();

        try {
            // Create MessageDigest instance for MD5
            MessageDigest md = MessageDigest.getInstance("MD5");
            // Add password bytes to digest
            md.update(pwString.getBytes());
            // Get the hash's bytes
            byte[] bytes = md.digest();
            // This bytes[] has bytes in decimal format;
            // Convert it to hexadecimal format
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < bytes.length; i++) {
                sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
            }
            // Get complete hashed password in hex format
            pwHash = sb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return pwHash;
    }
}
