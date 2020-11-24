package text_manager;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import constants.*;

public class AccessPeriodTextMng {
    private static final String FILEPATH = FilePath.ACCESS_PERIOD;

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

    public static String toString(Date date) {
        SimpleDateFormat formatter = new SimpleDateFormat("E, dd-MM-yyyy HH:mm:ss");
        return formatter.format(date);
    }
}
