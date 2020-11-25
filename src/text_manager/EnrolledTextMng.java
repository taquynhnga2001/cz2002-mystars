package src.text_manager;

import src.constants.FilePath;
import src.custom_exceptions.AlreadyEnrolled;

import java.io.IOException;
import java.util.*;

/**
 * Read and write the CourseEnrolled.csv in the database
 */
public class EnrolledTextMng extends TextManager {

    private static final String FILEPATH = FilePath.COURSE_ENROLLED;
    private static final String SEPERATOR = ",";
    

    /** Read String from text file and return list of CourseIndex objects that a Student enrolled */
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
        return alr; // list of CourseIdx String of a Student
    }


    /** Save courses enrolled in database after adding 
     * Cannot check if the Student has enrolled in this course yet, can just check in this courseIndex
    */
    public static void addEnrolledCourses(String matricNum, String courseIndex) throws IOException, AlreadyEnrolled {
        ArrayList<String> stringArray = read(FILEPATH); // to store the old database

        for (int i = 1; i < stringArray.size(); i++) {
            String st = (String) stringArray.get(i);
            StringTokenizer star = new StringTokenizer(st, SEPERATOR); // pass in the string to the string
                                                                       // tokenizer using delimiter ","
            String matricNum_ = star.nextToken().trim(); // first token: matricNum
            String courseIndex_ = star.nextToken().trim(); // second token: courseIndex
            if (matricNum_.equalsIgnoreCase(matricNum) && courseIndex_.equalsIgnoreCase(courseIndex)) {
                throw new AlreadyEnrolled(courseIndex_);
            }
        }
        // if matricNum and courseIndex is new, add to enrolled
        StringBuilder st = new StringBuilder();
        st.append(matricNum);
        st.append(SEPERATOR);
        st.append(courseIndex);
        stringArray.add(st.toString());
        write(FILEPATH, stringArray);
    }


    /** Delete enrolled registration when drop course or swop with another student.
     *  Cannot check if the student enrolled this courseIndex yet */
    public static void delEnrolledCourses(String matricNum, String courseIndex) throws IOException {
        ArrayList<String> stringArray = read(FILEPATH); // to store the old database
        ArrayList<String> al = new ArrayList<>();
        String HEADING = "matricNum,courseIndex";
        al.add(HEADING);

        for (int i = 1; i < stringArray.size(); i++) {
            String st = (String) stringArray.get(i);
            StringTokenizer star = new StringTokenizer(st, SEPERATOR); // pass in the string to the string
                                                                       // tokenizer using delimiter ","
            String matricNum_ = star.nextToken().trim();   // first token: matricNum
            String courseIndex_ = star.nextToken().trim(); // second token: courseIndex
            if (!matricNum_.equalsIgnoreCase(matricNum) || !courseIndex_.equalsIgnoreCase(courseIndex)) {
                al.add(stringArray.get(i));
            }
        }
        write(FILEPATH, al);
    }
}
