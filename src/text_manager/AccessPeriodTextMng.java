package src.text_manager;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import src.constants.*;

/** Read and write the access period of student and store the information in the database
 * @author Ta Quynh Nga
 */
public class AccessPeriodTextMng {
    private static final String FILEPATH = FilePath.ACCESS_PERIOD;

    
    /** 
     * get the access period information in the database
     * @return Date
     */
    public static Date getAccessPeriod() {
        Scanner scanner;
        Date date;
        try {
            scanner = new Scanner(new File(FILEPATH));
            try {
                String dateInString = scanner.nextLine();
                SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
                date = formatter.parse(dateInString);
                return date;
            } catch (ParseException e) {
                e.printStackTrace();
                return null;
            } finally {
                scanner.close();
            }
        } catch (FileNotFoundException e1) {
            e1.printStackTrace();
            return null;
        }
    }

    
    /** 
     * edit the access period and store the data in the database
     * @param datetime
     */
    public static void setAccessPeriod(String datetime) {
        PrintWriter out;
        try {
            out = new PrintWriter(new FileWriter(FILEPATH));
            out.print(datetime);
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    
    /** 
     * @param date
     * @return String
     */
    public static String toString(Date date) {
        SimpleDateFormat formatter = new SimpleDateFormat("E, dd-MM-yyyy HH:mm:ss");
        return formatter.format(date);
    }
}
