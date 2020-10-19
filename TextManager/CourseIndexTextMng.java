package TextManager;

import java.io.*;
import java.util.*;

import Constants.FilePath;
import CustomException.WrongCourseCode;
import CustomException.WrongCourseIndex;
import Entity.*;

public class CourseIndexTextMng extends TextManager {

    private final String FILEPATH = FilePath.COURSE_INDEX;
    private final String SEPERATOR = ",";

    /** Read String from text file and return list of CourseIdx objects */
    public ArrayList readFile() throws IOException {
        // read String from text file
        ArrayList stringArray = (ArrayList) read(FILEPATH);
        ArrayList<CourseIndexType> alr = new ArrayList<CourseIndexType>();// to store CourseIndex data

        for (int i = 1; i < stringArray.size(); i++) {
            String st = (String) stringArray.get(i);
            // get individual 'fields' of the string separated by SEPARATOR
            StringTokenizer star = new StringTokenizer(st, this.SEPERATOR); // pass in the string to the string
                                                                            // tokenizer // using delimiter ","

            String index = star.nextToken().trim(); // first token: name
            CourseIndexType courseIndex = new CourseIndexType(index);
            alr.add(courseIndex);
        }
        return alr; // list of CourseIdx
    }

    /**
     * Read course index information about course code, capacity, vacancy, wailist
     */
    public ArrayList<String> readCourseIndex(String courseIndex) throws IOException, WrongCourseIndex {
        ArrayList stringIndexArray = (ArrayList) read(FILEPATH); // read index, course code, vacancy, wailist, capacity

        ArrayList<String> alr = new ArrayList<String>();// to store CourseIndex's attributes
        String courseIndex_;
        String courseCode;
        String capacity;
        String vacancy;
        String wailist;

        for (int i = 1; i < stringIndexArray.size(); i++) {
            String st = (String) stringIndexArray.get(i);
            StringTokenizer star = new StringTokenizer(st, this.SEPERATOR);
            courseIndex_ = star.nextToken().trim();

            if (courseIndex == courseIndex_) {
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
    public void saveCourseIndex(List<CourseIndex> courseIndexes) throws IOException {
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
