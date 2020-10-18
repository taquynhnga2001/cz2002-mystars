import java.util.Scanner;
import java.io.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class CreatePw {
    protected static String getHash(String password) throws NoSuchAlgorithmException { // takes password and returns
                                                                                       // hashed
        try {
            // password
            MessageDigest md = MessageDigest.getInstance("SHA-256"); // algorithm for hashing is SHA-256 by default
            md.update(password.getBytes());
            byte[] digestedByteArray = md.digest();

            StringBuffer hashValue = new StringBuffer();
            for (byte b : digestedByteArray) {
                hashValue.append(String.format("%02x", b & 0xff));
            }

            return hashValue.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return "Noooooo";
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int count = 0;
        String username;
        String mail;
        String pwString;
        String pwHash = "";

        try {
            FileWriter fw = new FileWriter("database/Password.csv", true);
            BufferedWriter bw = new BufferedWriter(fw);
            PrintWriter pw = new PrintWriter(bw);

            do {
                System.out.print("Username: ");
                username = sc.next();
                mail = username + "@e.ntu.edu.sg";
                pwString = username;

                try {
                    // Create MessageDigest instance for MD5
                    MessageDigest md = MessageDigest.getInstance("MD5");
                    //Add password bytes to digest
                    md.update(pwString.getBytes());
                    //Get the hash's bytes 
                    byte[] bytes = md.digest();
                    //This bytes[] has bytes in decimal format;
                    //Convert it to hexadecimal format
                    StringBuilder sb = new StringBuilder();
                    for(int i=0; i< bytes.length ;i++)
                    {
                        sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
                    }
                    //Get complete hashed password in hex format
                    pwHash = sb.toString();
                } 
                catch (NoSuchAlgorithmException e) 
                {
                    e.printStackTrace();
                }
                System.out.println(pwHash);

                pw.append(username + "," + mail + "," + pwString + "," + pwHash + "\n");
                count++;

            } while (count <= 2);
            pw.close();
        } catch (IOException e) {
            System.out.println("Error!!!!!: " + e.getMessage());
        }
        sc.close();

    }
}
