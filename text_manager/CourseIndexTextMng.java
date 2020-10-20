package text_manager;

import java.io.*;
import java.util.*;

import constants.FilePath;
import custom_exceptions.*;
import entity.*;

public class CourseIndexTextMng extends TextManager {

    private static final String FILEPATH = FilePath.COURSE_INDEX;
    private static final String SEPERATOR = ",";

    /** Read String from text file and return list of CourseIdx objects of a Course */
    public static ArrayList<CourseIndex> readCourseIndexsOfCourse(String courseCode) throws IOException {
        ArrayList<String> stringArray = read(FILEPATH);
        ArrayList<CourseIndex> alr = new ArrayList<>();// to store CourseIndex objects

        for (int i = 1; i < stringArray.size(); i++) {
            String st = (String) stringArray.get(i);
            // get individual 'fields' of the string separated by SEPARATOR
            StringTokenizer star = new StringTokenizer(st, SEPERATOR); // pass in the string to the string
                                                                       // tokenizer using delimiter ","
            String index = star.nextToken().trim(); // first token: name
            String courseCode_ = star.nextToken().trim(); // 2nd token: courseCode

            if (courseCode_.equalsIgnoreCase(courseCode)) {
                CourseIndex courseIndex = new CourseIndex(index);
                alr.add(courseIndex);
            }
        }
        return alr; // list of CourseIdx
    }

    /**
     * Read course index information about course code, capacity, vacancy, wailist
     */
    public static ArrayList<String> readCourseIndex(String courseIndex) throws IOException, WrongCourseIndex {
        ArrayList<String> stringIndexArray = read(FILEPATH); // read index, course code, vacancy, wailist, capacity

        ArrayList<String> alr = new ArrayList<String>();// to store CourseIndex's attributes
        String courseIndex_;
        String courseCode;
        String capacity;
        String vacancy;
        String wailist;

        for (int i = 1; i < stringIndexArray.size(); i++) {
            String st = (String) stringIndexArray.get(i);
            StringTokenizer star = new StringTokenizer(st, SEPERATOR);
            courseIndex_ = star.nextToken().trim();

            if (courseIndex.equalsIgnoreCase(courseIndex_)) {
                courseCode = star.nextToken().trim();
                capacity = star.nextToken().trim();
                vacancy = star.nextToken().trim();
                wailist = star.nextToken().trim();
                alr.add(courseCode);
                alr.add(capacity);
                alr.add(vacancy);
                alr.add(wailist);
                return alr;
            }
        }
        throw new WrongCourseIndex();
    }

    /** Save a list of CourseIndex objects to database */
    public static void saveCourseIndex(List<CourseIndex> courseIndexes) throws IOException {
        List<String> al = new ArrayList<>(); // to store CourseIndex data
        String HEADING = "index,classType,group,day,time,venue,remark";
        al.add(HEADING);

        for (int i = 0; i < courseIndexes.size(); i++) {
            CourseIndex courseIndex = courseIndexes.get(i);
            StringBuilder st = new StringBuilder();
            st.append(courseIndex.getIndex().trim());
            st.append(SEPERATOR);
            st.append(courseIndex.getCourseCode().trim());
            st.append(SEPERATOR);
            st.append(courseIndex.getCapacity());
            st.append(SEPERATOR);
            st.append(courseIndex.getVacancy());
            st.append(SEPERATOR);
            st.append(courseIndex.getWaitlistSize());
            al.add(st.toString());
        }
        write(FILEPATH, al);
    }
}
