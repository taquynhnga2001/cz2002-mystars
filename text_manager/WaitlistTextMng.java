package text_manager;

import constants.FilePath;
import custom_exceptions.AlreadyInWaitlist;

// import entity.*;
import java.util.*;
import java.io.*;

public class WaitlistTextMng extends TextManager {

    private static final String FILEPATH = FilePath.WAITLIST;
    private static final String SEPERATOR = ",";

    
    /** Read String from text file and return list of CourseIndex objects that a Student in Waitlist */
    public static ArrayList<String> readFile(String matricNum) throws IOException {
        ArrayList<String> stringArray = read(FILEPATH);
        ArrayList<String> alr = new ArrayList<>(); // to store CourseIndexs

        for (int i = 1; i < stringArray.size(); i++) {
            String st = (String) stringArray.get(i);
            StringTokenizer star = new StringTokenizer(st, SEPERATOR); // pass in the string to the string
                                                                            // tokenizer // using delimiter ","
            String matricNum_ = star.nextToken().trim(); // first token: matricNum
            if (matricNum_.equalsIgnoreCase(matricNum)) {
                String courseIndex = star.nextToken().trim(); // second token: courseIndex
                alr.add(courseIndex);
            }
        }
        return alr; // list of CourseIdx of a Student
    }

    /** Read String from text file and return list of String[] {CourseIndexStr, MatricNum} in Waitlist */
    public static ArrayList<String[]> readFile() throws IOException {
        ArrayList<String> stringArray = read(FILEPATH);
        ArrayList<String[]> alr = new ArrayList<>(); // to store CourseIndexs

        for (int i = 1; i < stringArray.size(); i++) {
            String st = (String) stringArray.get(i);
            StringTokenizer star = new StringTokenizer(st, SEPERATOR); // pass in the string to the string
                                                                            // tokenizer // using delimiter ","
            String matricNum_ = star.nextToken().trim(); // first token: matricNum
            String courseIndex_ = star.nextToken().trim(); // second token: courseIndex
            String[] s = {matricNum_, courseIndex_};
            alr.add(s);
        }
        return alr; // list of CourseIdx of a Student
    }


    /** Save waitlist student in database after adding a 0-vacancies course 
     * Can just check if the student in waitlist of this courseIndex, not the course
    */
    public static void addWaitlist(String matricNum, String courseIndex) throws IOException, AlreadyInWaitlist{
        ArrayList<String> stringArray = read(FILEPATH); // to store the old database

        //check if the student in wailist of this courseIndex
        for (int i = 1; i < stringArray.size(); i++) {
            String st = (String) stringArray.get(i);
            StringTokenizer star = new StringTokenizer(st, SEPERATOR); // pass in the string to the string
                                                                       // tokenizer using delimiter ","
            String matricNum_ = star.nextToken().trim(); // first token: matricNum
            String courseIndex_ = star.nextToken().trim(); // second token: courseIndex
            if (matricNum_.equalsIgnoreCase(matricNum) && courseIndex_.equalsIgnoreCase(courseIndex)) {
                throw new AlreadyInWaitlist(courseIndex_);
            }
        }
        // if matricNum and courseIndex is new, add to waitlist
        StringBuilder st = new StringBuilder();
        st.append(matricNum);
        st.append(SEPERATOR);
        st.append(courseIndex);
        stringArray.add(st.toString());
        write(FILEPATH, stringArray);
    }


    /** Delete wailist registration when the vacancy is available or admin add for a student
     * Cannot check if the student is in waitlist of this courseIndex yet 
     */
    public static void delWaitlistCourses(String matricNum, String courseIndex) throws IOException {
        ArrayList<String> stringArray = read(FILEPATH); // to store the old database
        ArrayList<String> al = new ArrayList<>();
        String HEADING = "matricNum,courseIndex";
        al.add(HEADING);

        for (int i = 1; i < stringArray.size(); i++) {
            String st = (String) stringArray.get(i);
            StringTokenizer star = new StringTokenizer(st, SEPERATOR); // pass in the string to the string
                                                                       // tokenizer using delimiter ","
            String matricNum_ = star.nextToken().trim(); // first token: matricNum
            String courseIndex_ = star.nextToken().trim(); // second token: courseIndex
            if (!matricNum_.equalsIgnoreCase(matricNum) || !courseIndex_.equalsIgnoreCase(courseIndex)) {
                al.add(stringArray.get(i));
            }
        }
        write(FILEPATH, al);
    }
}
