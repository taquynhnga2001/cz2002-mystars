package Authentication;

import TextManager.*;
import Entity.*;
import CustomException.*;

import java.io.IOException;
import java.util.Scanner;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Authentication {

    public static User login() {
        String domain;
        String username;
        String password;
        Scanner sc = new Scanner(System.in);

        // read Student and Admin database
        try {
            StudentTextMng stm = new StudentTextMng();
            stm.readFile();
            AdminTextMng atm = new AdminTextMng();
            atm.readFile();
        } catch (IOException e) {
            System.out.println(">>> Error! " + e.getMessage());
        }

        System.out.print("Domain (Staff/Student): ");
        domain = sc.next();
        while (domain != "Staff" || domain != "Student") {
            System.out.println(">>> Error! Wrong input!");
            System.out.print("Domain (Staff/Student): ");
            domain = sc.next();
        }

        System.out.print("Username: ");
        username = sc.next();
        System.out.print("Password: ");
        password = getHash(sc.next());

        int haveRead = 0;

        if (domain == "Student") {
            do {
                try {
                    Student student = new StudentTextMng().returnStudent(username, password);
                    if (student == null)
                        System.out.println(">>> Error! Typed in wrong username and password. Type again.");
                    else {
                        haveRead = 1;
                        System.out.println(">>> Login successfully!");
                        return student;
                    }
                } catch (WrongUsername e) {
                    System.out.println(">>> Error! " + e.getMessage());
                    System.out.print("Username: ");
                    username = sc.next();
                    System.out.print("Password: ");
                    password = getHash(sc.next());
                } catch (WrongPassword e) {
                    System.out.println(">>> Error! " + e.getMessage());
                    System.out.print("Username: ");
                    username = sc.next();
                    System.out.print("Password: ");
                    password = getHash(sc.next());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } while (haveRead == 0);

        } else {
            do {
                try {
                    Admin prof = new AdminTextMng().returnAdmin(username, password);
                    if (prof == null)
                        System.out.println(">>> Error! Typed in wrong username and password. Type again.");
                    else {
                        haveRead = 1;
                        System.out.println(">>> Login successfully!");
                        return prof;
                    }
                } catch (WrongUsername e) {
                    System.out.println(">>> Error! " + e.getMessage());
                    System.out.print("Username: ");
                    username = sc.next();
                    System.out.print("Password: ");
                    password = getHash(sc.next());
                } catch (WrongPassword e) {
                    System.out.println(">>> Error! " + e.getMessage());
                    System.out.print("Username: ");
                    username = sc.next();
                    System.out.print("Password: ");
                    password = getHash(sc.next());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } while (haveRead == 0);
        }
        return null;
    }

    private static String getHash(String pwString) {
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
