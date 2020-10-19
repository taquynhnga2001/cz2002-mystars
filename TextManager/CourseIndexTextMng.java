package TextManager;

import java.io.*;
import java.util.*;

import CustomException.WrongCourseCode;
import CustomException.WrongCourseIndex;
import Entity.*;

public class CourseIndexTextMng extends TextManager {

    private final String FILEPATH = "../database/CourseIndex.csv";

    /** Read String from text file and return list of CourseIdx objects */
    public ArrayList readFile() throws IOException {
        // read String from text file
        ArrayList stringArray = (ArrayList) read(FILEPATH);
        ArrayList<CourseIndex> alr = new ArrayList<CourseIndex>();// to store CourseIndex data

        for (int i = 1; i < stringArray.size(); i++) {
            String st = (String) stringArray.get(i);
            // get individual 'fields' of the string separated by SEPARATOR
            StringTokenizer star = new StringTokenizer(st, this.SEPERATOR); // pass in the string to the string
                                                                            // tokenizer // using delimiter ","

            String index = star.nextToken().trim(); // first token: name
            CourseIndex courseIndex = new CourseIndex(index);
            alr.add(courseIndex);
        }
        return alr; // list of CourseIdx
    }

    /** Read string from text file and return CourseIndex object */
    public CourseIndex returnCourseIndex(String index) throws IOException, WrongCourseIndex {
        ArrayList stringArray = (ArrayList) read(FILEPATH);
        String index_;

        for (int i = 1; i < stringArray.size(); i++) {
            String st = (String) stringArray.get(i);
            // get individual 'fields' of the string separated by SEPARATOR
            StringTokenizer star = new StringTokenizer(st, this.SEPERATOR); // pass in the string to the string
                                                                            // tokenizer // using delimiter ","
            index_ = star.nextToken().trim(); // first token

            if (index_ == index) {
                return new CourseIndex(index_);
            }
        }
        throw new WrongCourseIndex();
    }

    /** Read String from text file and return CourseIndex attributes */
    public ArrayList<String> readCourseIndex(String index) throws IOException, WrongCourseIndex{
        ArrayList stringArray = (ArrayList) read(FILEPATH);
        ArrayList<String> alr = new ArrayList<String>();// to store CourseIndex's attributes
        String index_;
        String classType;
        String group;
        String day;
        String time;
        String venue;
        String remark;

        for (int i = 1; i < stringArray.size(); i++) {
            String st = (String) stringArray.get(i);
            // get individual 'fields' of the string separated by SEPARATOR
            StringTokenizer star = new StringTokenizer(st, this.SEPERATOR); // pass in the string to the string
                                                                            // tokenizer // using delimiter ","
            index_ = star.nextToken().trim(); // first token

            if (index_ == index) {
                classType = star.nextToken().trim(); // second token...
                group = star.nextToken().trim();
                day = star.nextToken().trim();
                time = star.nextToken().trim();
                venue = star.nextToken().trim();
                remark = star.nextToken().trim().replace("|", ",");
                alr.add(index_);
                alr.add(classType);
                alr.add(group);
                alr.add(day);
                alr.add(time);
                alr.add(venue);
                alr.add(remark);
                return alr;
            }
        }
        throw new WrongCourseIndex();
    }

    /** Read course index information about course code, capacity, vacancy, wailist */
    public ArrayList<String> readIndexInfo(String courseIndex) throws IOException, WrongCourseIndex {
        String fileIndexPath = "../database/IndexCourseVacancyWaitlist.csv";
        ArrayList stringIndexArray = (ArrayList) read(fileIndexPath); // read index, course code, vacancy, wailist, capacity

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
            st.append(courseIndex.getClassType().trim());
            st.append(SEPERATOR);
            st.append(courseIndex.getGroup().trim());
            st.append(SEPERATOR);
            st.append(courseIndex.getDay().trim());
            st.append(SEPERATOR);
            st.append(courseIndex.getTime().trim());
            st.append(SEPERATOR);
            st.append(courseIndex.getVenue().trim());
            st.append(SEPERATOR);
            st.append(courseIndex.getRemark().trim().replace(",", "|"));
            al.add(st.toString());
        }
        write("../database/CourseIndex.csv", al);
    }
    
}
