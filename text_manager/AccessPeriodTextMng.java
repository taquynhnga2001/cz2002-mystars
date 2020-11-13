package text_manager;

import java.io.File;
import java.io.FileNotFoundException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import constants.*;

public class AccessPeriodTextMng {
    private static final String FILEPATH = FilePath.ACCESS_PERIOD;
    private static final String SEPERATOR = ",";

    public static Date getAccessPeriod() {
        Scanner scanner;
        Date date;
        try {
            scanner = new Scanner(new File(FILEPATH));
            try {
                String dateInString = scanner.nextLine();
                SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss a");
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
}
