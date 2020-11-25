package src.text_manager;

import java.io.*;
import java.util.*;

/**
 * base class to read and write the database
 */
public class TextManager {
    
    public static final String SEPERATOR = ",";

    /** Write fixed content to the given file. */
    public static void write(String filePath, List<String> data) throws IOException {
        PrintWriter out = new PrintWriter(new FileWriter(filePath));
        try {
            for (int i = 0; i < data.size(); i++) {
                out.println((String) data.get(i));
            } // write a line 
        } finally {
            out.close();
        }
    }

    /** Read the contents of the given file. */
    public static ArrayList<String> read(String filePath) throws IOException {
        ArrayList<String> data = new ArrayList<>();
        Scanner scanner = new Scanner(new File(filePath));
        try {
            while (scanner.hasNextLine()) {
                data.add(scanner.nextLine());
            }
        } finally {
            scanner.close();
        }
        return data;        // each data row is a line record
    }

}
