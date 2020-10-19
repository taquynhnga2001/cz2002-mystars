package TextManager;

import java.io.*;
import java.util.*;

abstract public class TextManager {
    public final String SEPERATOR = ",";

    /** Write fixed content to the given file. */
    public static void write(String filePath, List data) throws IOException {
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
    public static List read(String filePath) throws IOException {
        List data = new ArrayList();
        Scanner scanner = new Scanner(new FileInputStream(filePath));
        try {
            while (scanner.hasNextLine()) {
                data.add(scanner.nextLine());
            }
        } finally {
            scanner.close();
        }
        return data;        // each data row is a line record
    }

    abstract public ArrayList readFile() throws IOException;
    // abstract public void saveFile(String filename, List al) throws IOException;
}
