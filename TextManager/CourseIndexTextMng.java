package TextManager;

import java.io.*;
import java.util.*;
import java.util.ArrayList;

import Entity.*;

public class CourseIndexTextMng extends TextManager {

    /** Read String from text file and return list of CourseIdx objects */
    public ArrayList readFile(String filePath) throws IOException {
        // read String from text file
        ArrayList stringArray = (ArrayList) read(filePath);
        ArrayList<CourseIndex> alr = new ArrayList<CourseIndex>();// to store CourseIndex data

        for (int i = 1; i < stringArray.size(); i++) {
            String st = (String) stringArray.get(i);
            // get individual 'fields' of the string separated by SEPARATOR
            StringTokenizer star = new StringTokenizer(st, this.SEPERATOR); // pass in the string to the string
                                                                            // tokenizer // using delimiter ","

            String index = star.nextToken().trim(); // first token: name
            CourseIndex courseIndex = new CourseIndex(index);
            // add to Students list
            alr.add(courseIndex);
        }
        return alr; // list of CourseIdx
    }

    /** Read string from text file and return CourseIndex object */
    public CourseIndex returnCourseIndex(String filePath, String index) throws IOException {
        ArrayList stringArray = (ArrayList) read(filePath);
        ArrayList<CourseIndex> alr = new ArrayList<CourseIndex>();// to store CourseIndexes data
        String index_;
        // String classType;
        // String group;
        // String day;
        // String time;
        // String venue;
        // String remark;

        for (int i = 1; i < stringArray.size(); i++) {
            String st = (String) stringArray.get(i);
            // get individual 'fields' of the string separated by SEPARATOR
            StringTokenizer star = new StringTokenizer(st, this.SEPERATOR); // pass in the string to the string
                                                                            // tokenizer // using delimiter ","
            index_ = star.nextToken().trim(); // first token
            // classType = star.nextToken().trim(); // second token...
            // group = star.nextToken().trim();
            // day = star.nextToken().trim();
            // time = star.nextToken().trim();
            // venue = star.nextToken().trim();
            // remark = star.nextToken().trim().replace("|", ",");

            if (index_ == index) {
                return new CourseIndex(index_);
            } else
                continue;
        }
        return null;
    }

    /** Read String from text file and return CourseIndex attributes */
    public ArrayList<String> readCourseIndex(String filePath, String index) throws IOException {
        ArrayList stringArray = (ArrayList) read(filePath);
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
            } else
                continue;
        }
        return null;
    }
    
}
